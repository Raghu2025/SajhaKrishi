package com.SajhaKrishi.utils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    private static final String HOST     = "sandbox.smtp.mailtrap.io";
    private static final String PORT     = "2525";
    private static final String FROM     = "noreply@sajhakrishi.com";
    private static final String USERNAME = "015992bb1d3afb";
    private static final String PASSWORD = "2a3e51685035cc";

    public static void sendEmail(String toEmail, String subject, String body) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            HOST);
        props.put("mail.smtp.port",            PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD); // use USERNAME not FROM
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        Transport.send(message);
    }
}