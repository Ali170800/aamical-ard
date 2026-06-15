package com.amical.ard.dao;

import com.amical.ard.entites.DepenseActivite;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DepenseActiviteDAO {

    private EntityManager em;

    public DepenseActiviteDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(DepenseActivite d) {
        em.persist(d);
    }

    public DepenseActivite trouverParId(int id) {
        return em.find(DepenseActivite.class, id);
    }

    public List<DepenseActivite> listerParActivite(int idActivite) {
        return em.createQuery("SELECT d FROM DepenseActivite d WHERE d.activite.id = :id", DepenseActivite.class)
                 .setParameter("id", idActivite)
                 .getResultList();
    }
  
    public List<DepenseActivite> listerTous() {
        return em.createQuery("SELECT d FROM DepenseActivite d", DepenseActivite.class)
                 .getResultList();
    }

    public void modifier(DepenseActivite d) {
        em.merge(d);
    }

    public void mettreAJour(DepenseActivite d) {
        em.merge(d);
    }

    public void supprimer(DepenseActivite d) {
        em.remove(em.contains(d) ? d : em.merge(d));
    }
}