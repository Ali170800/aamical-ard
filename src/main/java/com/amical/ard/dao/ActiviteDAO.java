package com.amical.ard.dao;

import com.amical.ard.entites.Activite;

// CHANGEMENT ICI : on importe depuis jakarta au lieu de javax
import jakarta.persistence.EntityManager;
import java.util.List;

public class ActiviteDAO {
    private EntityManager em;

    public ActiviteDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Activite a) {
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
    }

    public Activite trouverParId(int id) {
        return em.find(Activite.class, id);
    }

    public List<Activite> listerTous() {
        return em.createQuery("SELECT a FROM Activite a", Activite.class).getResultList();
    }
    public Activite trouverParId(Long id) {
        return em.find(Activite.class, id);
    }
    public void modifier(Activite a) {
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
    }

    public void supprimer(Activite a) {
        em.getTransaction().begin();
        em.remove(em.contains(a) ? a : em.merge(a));
        em.getTransaction().commit();
    }
    public void supprimer(Long id) {
        Activite activite = em.find(Activite.class, id);
        if (activite != null) {
            em.remove(activite);
        }
    }
}