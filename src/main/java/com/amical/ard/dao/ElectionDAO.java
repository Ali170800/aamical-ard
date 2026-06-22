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

    public void supprimer(Long id) {
        Election e = trouverParId(id);
        if (e != null) {
            em.remove(e);
        }
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
}