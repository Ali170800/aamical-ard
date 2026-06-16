package com.amical.ard.services;

import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.enums.StatutPaiement;

import jakarta.persistence.EntityManager; // ✅ Jakarta

import java.time.LocalDate;

public class ParticipantCaravaneService {

    private ParticipantCaravaneDAO participantCaravaneDAO;

    public ParticipantCaravaneService(EntityManager em) {
        this.participantCaravaneDAO = new ParticipantCaravaneDAO(em);
    }

    /**
     * Inscrire un participant à une caravane
     *
     * @param nom         Nom du participant
     * @param prenom      Prénom du participant
     * @param caravane    Caravane ciblée
     * @param montantPaye Montant payé par le participant (0 s’il n’a pas encore payé)
     */
    public void inscrireParticipant(String nom, String prenom, Caravane caravane, double montantPaye) {
        ParticipantCaravane participant = new ParticipantCaravane();
        participant.setNom(nom);
        participant.setPrenom(prenom);
        participant.setCaravane(caravane);

        participant.setMontant(caravane.getMontant()); // Montant de la caravane
        participant.setMontantPaye((int) montantPaye); // Conversion en int

        // Déterminer le statut automatiquement
        if (montantPaye >= caravane.getMontant()) {
            participant.setStatutPaiement(StatutPaiement.PAYE);
        } else {
            participant.setStatutPaiement(StatutPaiement.Non_Paye);
        }

        participantCaravaneDAO.ajouter(participant);
    }
}