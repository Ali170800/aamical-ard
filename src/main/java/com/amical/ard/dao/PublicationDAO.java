package com.amical.ard.dao;

import com.amical.ard.entites.Publication;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PublicationDAO {

    private EntityManager em;

    public PublicationDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Publication publication) {
        em.getTransaction().begin();
        em.persist(publication);
        em.getTransaction().commit();
    }

    public List<Publication> lister() {
        return em.createQuery(
                "SELECT p FROM Publication p ORDER BY p.datePublication DESC",
                Publication.class
        ).getResultList();
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

        return em.createQuery(
                "SELECT p FROM Publication p ORDER BY p.datePublication DESC",
                Publication.class
        ).getResultList();
    }
    public void modifier(Publication publication) {

        em.getTransaction().begin();
        em.merge(publication);
        em.getTransaction().commit();
    }
}