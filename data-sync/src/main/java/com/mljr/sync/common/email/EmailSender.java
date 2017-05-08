package com.mljr.sync.common.email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class EmailSender {
  
    private JavaMailSenderImpl email;

    public EmailSender() {
        email = new JavaMailSenderImpl();
        email.setHost("smtp.exmail.qq.com");
        email.setUsername("datahub-monitor@liyunqiche.com");
        email.setPassword("Password1");//授权码
        email.setPort(25);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);  
        properties.put("mail.smtp.ssl.enable", false);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
        properties.put("mail.smtp.timeout", 25000);  
        email.setJavaMailProperties(properties);  
    }  
      
    //发送邮件  
    public void send(String from,String[] to,String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        if(StringUtils.isBlank(from)){
            from = "datahub-monitor@liyunqiche.com";
        }
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        email.send(message);  
    }  
}  