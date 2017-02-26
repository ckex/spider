package com.mljr.operators.service;

import com.mljr.operators.SpringBootOperatorsMain;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootOperatorsMain.class)
public class BaseTest {

    @Before
    public void testInit() {

    }

    @After
    public void testAfter() {

    }

    protected List<String> readFileLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
