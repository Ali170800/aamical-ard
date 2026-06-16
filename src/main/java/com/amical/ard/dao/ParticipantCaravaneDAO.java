package com.amical.ard.dao;

import com.amical.ard.entites.ParticipantCaravane;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ParticipantCaravaneDAO {

    private EntityManager em;

    public ParticipantCaravaneDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(ParticipantCaravane p) {
        em.persist(p);
    }

    public ParticipantCaravane trouverParId(int id) {
        return em.find(ParticipantCaravane.class, id);
    }

    public List<ParticipantCaravane> listerTous() {
        return em.createQuery("SELECT p FROM ParticipantCaravane p", ParticipantCaravane.class).getResultList();
    }

    public boolean estDejaInscrit(String email, Long caravaneId) {
        Long count = em.createQuery(
                        "SELECT COUNT(p) FROM ParticipantCaravane p " +
                                "WHERE p.email = :email AND p.caravane.id = :caravaneId", Long.class)
                .setParameter("email", email)
                .setParameter("caravaneId", caravaneId)
                .getSingleResult();
        return count > 0;
    }

    // ✅ Méthode ajoutée pour générer numéro de chaise
    public int compterParticipants(Integer caravaneId) {
        Long count = em.createQuery(
                        "SELECT COUNT(p) FROM ParticipantCaravane p WHERE p.caravane.id = :caravaneId", Long.class)
                .setParameter("caravaneId", caravaneId)
                .getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    public void modifier(ParticipantCaravane p) {
        em.merge(p);
    }
    public List<ParticipantCaravane> trouverParEmail(String email) {
        return em.createQuery(
                        "SELECT p FROM ParticipantCaravane p WHERE p.email = :email ORDER BY p.caravane.date DESC",
                        ParticipantCaravane.class)
                .setParameter("email", email)
                .getResultList();
    }
    public void supprimer(ParticipantCaravane p) {
        em.remove(em.contains(p) ? p : em.merge(p));
    }
    public ParticipantCaravane trouverParEmailEtCaravane(String email, Long caravaneId) {
        try {
            return em.createQuery(
                            "SELECT p FROM ParticipantCaravane p " +
                                    "WHERE p.email = :email AND p.caravane.id = :caravaneId",
                            ParticipantCaravane.class)
                    .setParameter("email", email)
                    .setParameter("caravaneId", caravaneId)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // L'étudiant n'est pas inscrit
        }
    }
}