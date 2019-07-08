package org.apache.beam.examples.subprocess.utils;

import org.apache.beam.examples.subprocess.ExampleEchoPipeline;
import org.apache.beam.examples.subprocess.configuration.SubProcessConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class ExecutableFileTest {

    static final Logger LOG = LoggerFactory.getLogger(ExecutableFileTest.class);
    @Test
    public void returnOKif_tar_gz_file() {
        String f="cproj.tar.gz";
        SubProcessConfiguration c = new SubProcessConfiguration();
        c.setSourcePath("/tmp");
        c.setWorkerPath("/tmp");

        ExecutableFile ef=new ExecutableFile(c,f);
        String rf=ExecutableFile.refinedExecutableName(f);
        LOG.debug("returned ex name:"+rf);

        assert (rf.equals("cproj"));
    }

    @Test
    public void returnOKif_tgz_file() {
        String f="cproj.tgz";
        SubProcessConfiguration c = new SubProcessConfiguration();
        c.setSourcePath("/tmp");
        c.setWorkerPath("/tmp");

        ExecutableFile ef=new ExecutableFile(c,f);
        String rf=ExecutableFile.refinedExecutableName(f);
        LOG.debug("returned ex name:"+rf);

        assert (rf.equals("cproj"));
    }

    @Test
    public void returnOKif_normal_file() {
        String f="cproj";
        SubProcessConfiguration c = new SubProcessConfiguration();
        c.setSourcePath("/tmp");
        c.setWorkerPath("/tmp");

        ExecutableFile ef=new ExecutableFile(c,f);
        String rf=ExecutableFile.refinedExecutableName(f);
        LOG.debug("returned ex name:"+rf);

        assert (rf.equals("cproj"));
    }

    @Test
    public void returnOKif_shell_file() {
        String f="cproj.sh";
        SubProcessConfiguration c = new SubProcessConfiguration();
        c.setSourcePath("/tmp");
        c.setWorkerPath("/tmp");

        ExecutableFile ef=new ExecutableFile(c,f);
        String rf=ExecutableFile.refinedExecutableName(f);
        LOG.debug("returned ex name:"+rf);

        assert (rf.equals("cproj.sh"));
    }
}