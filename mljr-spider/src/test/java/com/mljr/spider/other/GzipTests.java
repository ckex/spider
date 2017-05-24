package com.mljr.spider.other;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class GzipTests {

    public GzipTests() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws Exception {
        String targetfile = "/data/gps/2017-05-23/2017-05-23-16-34.gzip";
        String sourcefile = "/data/gps/2017-05-23/2017-05-23-16-34.json";
        FileUtils.writeByteArrayToFile(new File(targetfile), FileUtils.readFileToByteArray(new File(sourcefile)), true);
    }

}
