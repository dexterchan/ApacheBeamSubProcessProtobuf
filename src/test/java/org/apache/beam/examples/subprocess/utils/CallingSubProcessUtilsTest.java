package org.apache.beam.examples.subprocess.utils;

import org.apache.beam.examples.subprocess.ExampleEchoPipeline;
import org.apache.beam.examples.subprocess.ExampleEchoPipelineTest;
import org.apache.beam.examples.subprocess.SubProcessPipelineOptions;
import org.apache.beam.examples.subprocess.configuration.SubProcessConfiguration;
import org.apache.beam.examples.subprocess.kernel.SubProcessCommandLineArgs;
import org.apache.beam.examples.subprocess.kernel.SubProcessKernel;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CallingSubProcessUtilsTest {

    static final Logger LOG = LoggerFactory.getLogger(CallingSubProcessUtilsTest.class);

    SubProcessPipelineOptions options = null;
    SubProcessConfiguration configuration = null;
    List<KV<String, String>> sampleData = null;
    String executableName = null;

    @Before
    public void setupSubProcessPipelineOptions() {


        // Read in the options for the pipeline
        options = PipelineOptionsFactory.as(SubProcessPipelineOptions.class);
        options.setSourcePath("gs://pi_calculation/echo/macos");
        options.setConcurrency(2);
        options.setOutput("/tmp/echotest");


        // Setup the Configuration option used with all transforms
        configuration = options.getSubProcessConfiguration();

        // Create some sample data to be fed to our c++ Echo library
        sampleData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String str = String.valueOf(i);
            sampleData.add(KV.of(str, str));
        }


        executableName = "cprog.tar.gz";
        //executableName="Echo";

    }

    @Test
    public void testDownloadPackageAtSetup() {
        Pipeline p = Pipeline.create(options);

        // Define the pipeline which is two transforms echoing the inputs out to Logs
        PCollection<KV<String, String>> pout = p.apply(Create.of(sampleData))
                .apply("Echo inputs round 1", ParDo.of(new EchoInputDoFn2(configuration, executableName)))
                .apply("Echo inputs round 2", ParDo.of(new EchoInputDoFn2(configuration, "EchoAgain")));;

        pout.apply("Output Result on console", ParDo.of(
                new Output2Text()
        )).apply(TextIO.write().to(options.getOutput()).withSuffix(".out"));;
        p.run();
    }

    public static class Output2Text extends DoFn<KV<String, String>, String> {
        @ProcessElement
        public void processElement(@Element KV<String, String> e, OutputReceiver<String> out) {
            String strout = e.getKey() +":"+e.getValue();
            out.output(strout);
        }
    }

    public static class EchoInputDoFn2 extends DoFn<KV<String, String>, KV<String, String>> {

        static final Logger LOG = LoggerFactory.getLogger(ExampleEchoPipeline.EchoInputDoFn.class);

        private SubProcessConfiguration configuration;
        private String binaryName;

        public EchoInputDoFn2(SubProcessConfiguration configuration, String binary) {
            // Pass in configuration information the name of the filename of the sub-process and the level
            // of concurrency
            this.configuration = configuration;
            this.binaryName = binary;
        }

        @Setup
        public void setUp() throws Exception {
            this.binaryName = CallingSubProcessUtils.setUp(configuration, binaryName);

        }

        @ProcessElement
        public void processElement(ProcessContext c) throws Exception {
            //assert(this.binaryName.equals("cprog"));

            try {
                // Our Library takes a single command in position 0 which it will echo back in the result
                SubProcessCommandLineArgs commands = new SubProcessCommandLineArgs();
                SubProcessCommandLineArgs.Command command = new SubProcessCommandLineArgs.Command(0, String.valueOf(c.element().getValue()));
                commands.putCommand(command);

                // The ProcessingKernel deals with the execution of the process
                SubProcessKernel kernel = new SubProcessKernel(configuration, binaryName);


                // Run the command and work through the results
                List<String> results = kernel.exec(commands);
                for (String s : results) {
                    c.output(KV.of(c.element().getKey(), s));
                }
            } catch (Exception ex) {
                LOG.error("Error processing element ", ex);
                throw ex;
            }
        }
    }

}