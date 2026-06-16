package com.amical.ard.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    private static final String EMAIL_FROM = "alidiouf170800@gmail.com"; // remplace par ton adresse Gmail
    private static final String PASSWORD = "kkilemsspjbfjuwv"; // mot de passe d'application Gmail

    public static void envoyerEmail(String destinataire, String sujet, String corps) {
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Création de la session avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
            }
        });

        session.setDebug(true); // Pour afficher les logs SMTP en console

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(corps);

            // Envoi du message
            Transport.send(message);
            System.out.println("✅ E-mail envoyé à : " + destinataire);

        } catch (MessagingException e) {
            System.err.println("❌ Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            e.printStackTrace(); // Affiche toute la trace
        }
    }
}