package com.amical.ard.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    private static final String EMAIL_FROM = System.getenv("MAIL_USER");
    private static final String PASSWORD = System.getenv("MAIL_PASSWORD");

    public static void envoyerEmail(String destinataire, String sujet, String corps) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (EMAIL_FROM == null || PASSWORD == null) {
                    throw new RuntimeException("MAIL_USER ou MAIL_PASSWORD non définis !");
                }
                return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(corps);

            Transport.send(message);
            System.out.println("✅ Email envoyé à : " + destinataire);

        } catch (MessagingException e) {
            System.err.println("❌ Erreur envoi email : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
