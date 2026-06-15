package com.amical.ard.dao;

import com.amical.ard.entites.Caravane;

// Conversion de javax à jakarta
import jakarta.persistence.EntityManager;
import java.util.List;

public class CaravaneDAO {
    private EntityManager em;

    public CaravaneDAO(EntityManager em) {
        this.em = em;
    }


    public Caravane trouverParId(int id) {
        return em.find(Caravane.class, id);
    }
    public void supprimer(int id) {
        Caravane caravane = em.find(Caravane.class, id);
        if (caravane != null) {
            em.remove(caravane);
        }
    }
    public List<Caravane> listerTous() {
        return em.createQuery("SELECT c FROM Caravane c", Caravane.class).getResultList();
    }
    public void ajouter(Caravane caravane) {
        em.getTransaction().begin();
        em.persist(caravane);
        em.getTransaction().commit();
    }
    public void modifier(Caravane c) {
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public void supprimer(Caravane c) {
        em.getTransaction().begin();
        em.remove(em.contains(c) ? c : em.merge(c));
        em.getTransaction().commit();
    }
}