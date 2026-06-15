package com.amical.ard.dao;

import com.amical.ard.entites.CompteRendu;

// Conversion de javax à jakarta
import jakarta.persistence.EntityManager;
import java.util.List;

public class CompteRenduDAO {
    private EntityManager em;

    public CompteRenduDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(CompteRendu c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public CompteRendu trouverParId(int id) {
        return em.find(CompteRendu.class, id);
    }

    public List<CompteRendu> listerTous() {
        return em.createQuery("SELECT c FROM CompteRendu c", CompteRendu.class).getResultList();
    }

    public void modifier(CompteRendu c) {
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public void supprimer(CompteRendu c) {
        em.getTransaction().begin();
        em.remove(em.contains(c) ? c : em.merge(c));
        em.getTransaction().commit();
    }
}