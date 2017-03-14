package com.mljr.monitor;

import com.mljr.monitor.mail.DefaultMailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MonitorMain.class)
public class MainSenderTest {
    @Autowired
    private DefaultMailSender mailSender;

    @Test
    public void sendSimpleMail() throws Exception {
        mailSender.send("主题33", "内容44");
    }

}