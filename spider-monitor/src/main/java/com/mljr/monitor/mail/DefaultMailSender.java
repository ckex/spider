package com.mljr.monitor.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Created by songchi on 17/3/13.
 */
@Service
public class DefaultMailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.recipients}")
    private String[] recipients;

    @Value("${spring.mail.properties.from}")
    private String from;

    private static Logger logger = LoggerFactory.getLogger(DefaultMailSender.class);

    public void send(String subject, String text) {

        this.send(from, recipients, subject, text);
    }

    public void send(String from, String[] to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("邮件发送失败", e);
            e.printStackTrace();
        }

    }
}
