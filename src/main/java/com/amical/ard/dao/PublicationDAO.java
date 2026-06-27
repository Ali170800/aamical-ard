package com.amical.ard.dao;

import com.amical.ard.entites.Publication;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class PublicationDAO {
    private EntityManager em;

    public PublicationDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Publication publication) {
        // FORCE la date actuelle au moment de l'enregistrement
        if (publication.getDatePublication() == null) {
            publication.setDatePublication(java.time.LocalDateTime.now());
        }

        em.getTransaction().begin();
        em.persist(publication);
        em.getTransaction().commit();
    }

    public List<Publication> lister() {
        return em.createQuery("SELECT p FROM Publication p ORDER BY p.datePublication DESC", Publication.class).getResultList();
    }

    public Publication trouver(Long id) {
        return em.find(Publication.class, id);
    }

    public void supprimer(Long id) {
        Publication p = em.find(Publication.class, id);
        if (p != null) {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        }
    }

    public List<Publication> listerToutes() {
        return em.createQuery("SELECT p FROM Publication p ORDER BY p.datePublication DESC", Publication.class).getResultList();
    }

    public void modifier(Publication publication) {
        em.getTransaction().begin();
        em.merge(publication);
        em.getTransaction().commit();
    }

    public Publication findById(Long id) {
        return em.find(Publication.class, id);
    }

    public void supprimerPublicationsAnciennes() {
        LocalDateTime seuil = LocalDateTime.now().minusDays(14);

        em.getTransaction().begin();
        // On ne supprime que si la date est bien antérieure à (Maintenant - 14 jours)
        em.createQuery("DELETE FROM Publication p WHERE p.datePublication < :seuil")
                .setParameter("seuil", seuil)
                .executeUpdate();
        em.getTransaction().commit();
    }

    public Long compterPublicationsAnciennes() {
        LocalDateTime seuil = LocalDateTime.now().minusDays(14);
        return em.createQuery("SELECT COUNT(p) FROM Publication p WHERE p.datePublication < :seuil", Long.class)
                .setParameter("seuil", seuil)
                .getSingleResult();
    }

}