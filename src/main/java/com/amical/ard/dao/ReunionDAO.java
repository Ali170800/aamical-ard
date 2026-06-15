package com.amical.ard.dao;

import com.amical.ard.entites.Reunion;

// ✅ Import Jakarta EE
import jakarta.persistence.EntityManager;
import java.util.List;

public class ReunionDAO {
    private EntityManager em;

    public ReunionDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Reunion r) {
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
    }

    public Reunion trouverParId(int id) {
        return em.find(Reunion.class, id);
    }

    public List<Reunion> listerTous() {
        return em.createQuery("SELECT r FROM Reunion r", Reunion.class).getResultList();
    }

    public void modifier(Reunion r) {
        em.getTransaction().begin();
        em.merge(r);
        em.getTransaction().commit();
    }

    public void supprimer(Reunion r) {
        em.getTransaction().begin();
        em.remove(em.contains(r) ? r : em.merge(r));
        em.getTransaction().commit();
    }
}