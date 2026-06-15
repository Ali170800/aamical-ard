package com.amical.ard.dao;

import com.amical.ard.entites.FichierJoint;

// Conversion de javax à jakarta
import jakarta.persistence.EntityManager;
import java.util.List;

public class FichierJointDAO {
    private EntityManager em;

    public FichierJointDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(FichierJoint f) {
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
    }
    public List<FichierJoint> listerParRole(String role) {

        return em.createQuery(
                        "SELECT f FROM FichierJoint f " +
                                "WHERE f.roleAuteur = :role " +
                                "ORDER BY f.dateUpload DESC",
                        FichierJoint.class
                )
                .setParameter("role", role)
                .getResultList();
    }
    public FichierJoint trouverParId(int id) {
        return em.find(FichierJoint.class, id);
    }

    public List<FichierJoint> listerTous() {
        return em.createQuery("SELECT f FROM FichierJoint f", FichierJoint.class).getResultList();
    }

    public void modifier(FichierJoint f) {
        em.getTransaction().begin();
        em.merge(f);
        em.getTransaction().commit();
    }

    public void supprimer(FichierJoint f) {
        em.getTransaction().begin();
        em.remove(em.contains(f) ? f : em.merge(f));
        em.getTransaction().commit();
    }
}