package com.amical.ard.dao;

import com.amical.ard.entites.*;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

public class VoteElectionDAO {
    private EntityManager em;

    public VoteElectionDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Enregistre un vote de manière anonyme et marque l'étudiant dans la table de contrôle.
     * Note : Assurez-vous que l'appelant gère la transaction (em.getTransaction().begin/commit).
     */
    public boolean voter(Long electionId, Long candidatId, Long etudiantId) {
        try {
            // 1. Vérification dans la table de contrôle
            Long dejaVote = em.createQuery(
                            "SELECT COUNT(vc) FROM VoteControle vc WHERE vc.election.id = :eId AND vc.etudiant.id = :sId", Long.class)
                    .setParameter("eId", electionId)
                    .setParameter("sId", etudiantId)
                    .getSingleResult();

            if (dejaVote > 0) return false;

            // 2. Récupération des références sécurisées
            Election election = em.getReference(Election.class, electionId);
            CandidatElection candidat = em.getReference(CandidatElection.class, candidatId);
            Etudiant etudiant = em.getReference(Etudiant.class, etudiantId);

            // 3. Enregistrement du vote ANONYME (pas de lien avec l'étudiant)
            VoteElection vote = new VoteElection();
            vote.setElection(election);
            vote.setCandidat(candidat);
            vote.setDateVote(LocalDateTime.now());
            em.persist(vote);

            // 4. Marquage de l'étudiant dans la table de contrôle (l'émargement)
            VoteControle controle = new VoteControle();
            controle.setElection(election);
            controle.setEtudiant(etudiant);
            em.persist(controle);

            return true;
        } catch (Exception e) {
            // Loggez l'erreur pour le debug
            System.err.println("Erreur lors de l'enregistrement du vote : " + e.getMessage());
            return false;
        }
    }
}