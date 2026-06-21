package com.amical.ard.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerHelper {

    private static final Logger logger = Logger.getLogger(EntityManagerHelper.class.getName());
    private static final String PERSISTENCE_UNIT = "amicalePU";
    private static volatile EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    // Initialisation sécurisée (Lazy Loading) - Remplace JpaUtil
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            synchronized (EntityManagerHelper.class) {
                if (emf == null || !emf.isOpen()) {
                    try {
                        logger.info("🔄 Initialisation de l'EntityManagerFactory...");
                        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "⛔ Échec critique de création de l'EntityManagerFactory", e);
                        throw new RuntimeException("Erreur critique JPA: vérifiez persistence.xml et vos variables d'environnement.", e);
                    }
                }
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            em = getEntityManagerFactory().createEntityManager();
            threadLocal.set(em);
        }
        return em;
    }

    public static void beginTransaction() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public static void commit() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    public static void rollback() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            if (em.isOpen()) {
                em.close();
            }
            threadLocal.remove();
        }
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}