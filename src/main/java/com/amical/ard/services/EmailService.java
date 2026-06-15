package com.amical.ard.services;

import com.amical.ard.entites.Bureau;
import com.amical.ard.entites.Etudiant;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;

public class EmailService {

    private final String fromEmail = "amicalediourbel77@gmail.com"; // NE PAS MODIFIER
    private final String password = "amrn yjbb jkzk zsvt";           // NE PAS MODIFIER
    private Session session;

    public EmailService() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        session = Session.getInstance(mailProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
    }

    public void envoyerEmail(String destinataire, String sujet, String messageTexte) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(messageTexte);

            Transport.send(message);
            System.out.println("✅ E-mail envoyé avec succès à : " + destinataire);
        } catch (MessagingException e) {
            System.err.println("❌ Erreur lors de l’envoi de l’e-mail.");
            e.printStackTrace();
        }
    }

    public void envoyerEmailAuxMembresBureau(List<Bureau> membres, String sujet, String contenu) {
        for (Bureau membre : membres) {
            if (membre.getEmail() != null && !membre.getEmail().isEmpty()) {
                envoyerEmail(membre.getEmail(), sujet, contenu);
            }
        }
    }

    public void envoyerEmailAuxEtudiantsLoges(List<Etudiant> etudiants, String sujet, String contenu) {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getEmail() != null && !etudiant.getEmail().isEmpty()) {
                envoyerEmail(etudiant.getEmail(), sujet, contenu);
            }
        }
    }
}