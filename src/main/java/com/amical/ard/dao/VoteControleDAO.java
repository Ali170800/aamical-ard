package com.amical.ard.dao;

import com.amical.ard.entites.VoteControle;
import jakarta.persistence.EntityManager;
import java.util.List;

public class VoteControleDAO {
    private EntityManager em;

    public VoteControleDAO(EntityManager em) {
        this.em = em;
    }

    // Vérifie si un étudiant a déjà participé à une élection précise
    public boolean aDejaVote(Long electionId, Long etudiantId) {
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(vc) FROM VoteControle vc WHERE vc.election.id = :eId AND vc.etudiant.id = :sId", Long.class)
                    .setParameter("eId", electionId)
                    .setParameter("sId", etudiantId)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Sauvegarde l'émargement
    public void enregistrerParticipation(VoteControle participation) {
        em.persist(participation);
    }
}