package com.amical.ard.services;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;

import jakarta.persistence.EntityManager;
import java.util.List;

public class BureauService {

    private BureauDAO bureauDAO;
    private EmailService emailService;

    public BureauService(EntityManager em) {
        this.bureauDAO = new BureauDAO(em);
        this.emailService = new EmailService();
    }

    // Méthode pour envoyer un e-mail à tous les membres du bureau
    public void envoyerEmailATousLesMembres(String sujet, String contenu) {
        List<Bureau> membres = bureauDAO.listerTous();
        emailService.envoyerEmailAuxMembresBureau(membres, sujet, contenu);
    }

    // 👉 Méthode pour récupérer tous les membres du bureau
    public List<Bureau> recupererTousLesMembres() {
        return bureauDAO.listerTous();
    }
}