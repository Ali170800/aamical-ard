package com.amical.ard.services;

import com.amical.ard.entites.Bureau;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EmailUtil;

import java.util.List;

public class EmailService {

    public void envoyerEmail(String destinataire, String sujet, String message) {
        EmailUtil.envoyerEmail(destinataire, sujet, message);
    }

    public void envoyerEmailAuxMembresBureau(List<Bureau> membres, String sujet, String contenu) {
        for (Bureau membre : membres) {
            if (membre.getEmail() != null && !membre.getEmail().isEmpty()) {
                EmailUtil.envoyerEmail(membre.getEmail(), sujet, contenu);
            }
        }
    }

    public void envoyerEmailAuxEtudiantsLoges(List<Etudiant> etudiants, String sujet, String contenu) {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getEmail() != null && !etudiant.getEmail().isEmpty()) {
                EmailUtil.envoyerEmail(etudiant.getEmail(), sujet, contenu);
            }
        }
    }
}
