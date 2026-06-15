package com.amical.ard.services;

import com.amical.ard.entites.Bureau;
import com.amical.ard.entites.Etudiant;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class EmailManuelService {

    private final EmailService emailService;
    private final EtudiantService etudiantService;
    private final BureauService bureauService;

    public EmailManuelService(EntityManager em) {
        this.emailService = new EmailService();
        this.etudiantService = new EtudiantService(em);
        this.bureauService = new BureauService(em);
    }

    public void envoyerAuxEtudiants(String sujet, String message) {
        List<Etudiant> etudiants = etudiantService.recupererTousLesEtudiants();
        for (Etudiant e : etudiants) {
            emailService.envoyerEmail(e.getEmail(), sujet, message);
        }
    }

    public void envoyerAuxEtudiantsLoges(String sujet, String message) {
        List<Etudiant> etudiantsLoges = etudiantService.recupererEtudiantsLoges();
        for (Etudiant e : etudiantsLoges) {
            emailService.envoyerEmail(e.getEmail(), sujet, message);
        }
    }

    public void envoyerAuxMembresBureau(String sujet, String message) {
        List<Bureau> membres = bureauService.recupererTousLesMembres();
        for (Bureau b : membres) {
            emailService.envoyerEmail(b.getEmail(), sujet, message);
        }
    }

    public void verifierEtEnvoyerMessageMensuel() {
        LocalDate aujourdHui = LocalDate.now();
        if (aujourdHui.getDayOfMonth() == 5) { // Correction: vérification du 5 (au lieu du 23)
            String sujet = "📢 Paiement du logement - Rappel mensuel";
            String contenu = "Bonjour,\n\nNous vous rappelons que le paiement de votre logement est attendu ce mois-ci.\nMerci de régulariser votre situation au plus vite.\n\nCordialement,\nLe PCS";
            envoyerAuxEtudiantsLoges(sujet, contenu);
        } else {
            System.out.println("✅ Aujourd'hui, nous ne sommes pas le 5. Aucun email automatique envoyé.");
        }
    }
}