package com.amical.ard.services;

import com.amical.ard.entites.Bureau;
import com.amical.ard.entites.Etudiant;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.Properties;

public class EmailService {

    // Récupération des variables depuis Render (Environment Variables)
    private final String fromEmail = System.getenv("MAIL_USER");
    private final String password = System.getenv("MAIL_PASSWORD");
    private Session session;

    public EmailService() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
// Ajoute ces deux lignes de timeout
        mailProperties.put("mail.smtp.connectiontimeout", "10000"); // 10 secondes
        mailProperties.put("mail.smtp.timeout", "10000");           // 10 secondes
        session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if (fromEmail == null || password == null) {
                    throw new RuntimeException("Variables d'environnement MAIL_USER ou MAIL_PASSWORD non définies !");
                }
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
            System.err.println("❌ Erreur lors de l’envoi de l’e-mail vers : " + destinataire);
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
