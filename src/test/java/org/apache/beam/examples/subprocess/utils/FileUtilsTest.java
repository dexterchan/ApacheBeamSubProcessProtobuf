package org.apache.beam.examples.subprocess.utils;

import org.apache.beam.examples.subprocess.ExampleEchoPipeline;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileUtilsTest {

    static final Logger LOG = LoggerFactory.getLogger(FileUtilsTest.class);
    String compressedFile=null;
    @Before
    public void init(){
        compressedFile="/tmp/grid_working_files/cprog.tar.gz";
    }
    @Test
    public void uncompresslocalfile() throws IOException {
        File f = new File(compressedFile);
        LOG.debug(f.getAbsoluteFile().getParent());
        String parentPath = f.getAbsoluteFile().getParent();
        FileUtils.uncompresslocalfile(compressedFile,parentPath);

    }
}