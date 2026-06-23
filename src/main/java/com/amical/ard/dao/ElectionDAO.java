package com.amical.ard.dao;

import com.amical.ard.entites.Election;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ElectionDAO {
    private EntityManager em;

    public ElectionDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Election election) {
        em.persist(election);
    }

    public Election trouverParId(Long id) {
        return em.find(Election.class, id);
    }

    // Corrigé pour correspondre au nom appelé dans le Servlet (listerTout)
    public List<Election> listerTout() {
        return em.createQuery("SELECT e FROM Election e ORDER BY e.dateCreation DESC", Election.class)
                .getResultList();
    }
    public List<Election> toutesLesElections() {
        return em.createQuery("SELECT e FROM Election e", Election.class)
                .getResultList();
    }

    // --- MÉTHODES POUR LE DASHBOARD (Statistiques) ---

    public long compterVotes(Long electionId) {
        try {
            return em.createQuery("SELECT COUNT(v) FROM Vote v WHERE v.election.id = :electionId", Long.class)
                    .setParameter("electionId", electionId)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public long compterTotalEtudiants() {
        try {
            return em.createQuery("SELECT COUNT(e) FROM Etudiant e", Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    public void supprimer(Long id) {
        Election e = em.find(Election.class, id);
        if (e != null) {
            // 1. Supprimer les entrées dans vote_controle liées à cette élection
            em.createQuery("DELETE FROM VoteControle v WHERE v.election.id = :eId")
                    .setParameter("eId", id)
                    .executeUpdate();

            // 2. Supprimer les votes liés (si tu as aussi une table VoteElection)
            em.createQuery("DELETE FROM VoteElection v WHERE v.election.id = :eId")
                    .setParameter("eId", id)
                    .executeUpdate();

            // 3. Supprimer les candidats liés (si nécessaire, selon ton modèle)
            em.createQuery("DELETE FROM CandidatElection c WHERE c.election.id = :eId")
                    .setParameter("eId", id)
                    .executeUpdate();

            // 4. Enfin, supprimer l'élection elle-même
            em.remove(e);
        }
    }
}