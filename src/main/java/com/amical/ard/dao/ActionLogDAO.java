package com.amical.ard.dao;

import com.amical.ard.entites.ActionLog;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ActionLogDAO {

    private EntityManager em;

    public ActionLogDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Enregistrer une action
     */
    public void enregistrerAction(ActionLog log) {

        if (!em.getTransaction().isActive()) {

            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();

        } else {

            em.persist(log);
        }
    }

    /**
     * Enregistrer une action avec paramètres
     */
    public void enregistrerAction(Integer utilisateurId,
                                  String nomUtilisateur,
                                  String role,
                                  String action,
                                  String details) {

        ActionLog log = new ActionLog(
                utilisateurId,
                nomUtilisateur,
                role,
                action,
                details
        );

        if (!em.getTransaction().isActive()) {

            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();

        } else {

            em.persist(log);
        }
    }

    /**
     * Toutes les actions
     */
    public List<ActionLog> getToutesActions() {

        return em.createQuery(
                        "SELECT a FROM ActionLog a ORDER BY a.dateAction DESC",
                        ActionLog.class
                )
                .getResultList();
    }

    /**
     * Dernières actions globales
     */
    public List<ActionLog> getDernieresActions(int limit) {

        return em.createQuery(
                        "SELECT a FROM ActionLog a ORDER BY a.dateAction DESC",
                        ActionLog.class
                )
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 🔥 Dernières actions par rôle
     */
    public List<ActionLog> getDernieresActionsParRole(String role,
                                                      int limit) {

        return em.createQuery(
                        "SELECT a FROM ActionLog a " +
                                "WHERE UPPER(a.role) = :role " +
                                "ORDER BY a.dateAction DESC",
                        ActionLog.class
                )
                .setParameter("role", role.toUpperCase())
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Toutes les actions par rôle
     */
    public List<ActionLog> getActionsParRole(String role) {

        return em.createQuery(
                        "SELECT a FROM ActionLog a " +
                                "WHERE UPPER(a.role) = :role " +
                                "ORDER BY a.dateAction DESC",
                        ActionLog.class
                )
                .setParameter("role", role.toUpperCase())
                .getResultList();
    }
}