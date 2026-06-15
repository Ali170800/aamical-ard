package com.amical.ard.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerHelper {

    private static final Logger logger = Logger.getLogger(EntityManagerHelper.class.getName());
    private static volatile EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal = new ThreadLocal<>();

    static {
        initializeEntityManagerFactory();
    }

    private static void initializeEntityManagerFactory() {
        try {
            logger.info("🔄 Initialisation de l'EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("amicalePU");
            testDatabaseConnection();
            logger.info("✅ EntityManagerFactory créé avec succès");
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "⛔ Échec critique de l'initialisation", ex);
            throw new ExceptionInInitializerError("Échec de l'initialisation de JPA: " + ex.getMessage());
        }
    }

    private static void testDatabaseConnection() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Object result = em.createNativeQuery("SELECT 1").getSingleResult();
            logger.info("🔍 Test de connexion réussi: " + result);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "🔴 Échec du test de connexion à la base", e);
            throw new RuntimeException("La connexion à la base de données a échoué", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // ✅ Méthode unique et propre
    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();
        if (em == null || !em.isOpen()) {
            if (emf == null || !emf.isOpen()) {
                throw new IllegalStateException("EntityManagerFactory n'est pas initialisé");
            }
            em = emf.createEntityManager();
            threadLocal.set(em);
            logger.fine("📘 Nouvel EntityManager créé pour le thread: " + Thread.currentThread().getId());
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            try {
                if (em.isOpen()) {
                    if (em.getTransaction().isActive()) {
                        logger.warning("↩ Transaction active détectée lors de la fermeture - rollback");
                        em.getTransaction().rollback();
                    }
                    em.close();
                    logger.fine("✅ EntityManager fermé pour le thread: " + Thread.currentThread().getId());
                }
            } finally {
                threadLocal.remove();
            }
        }
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            try {
                emf.close();
                logger.info("🔒 EntityManagerFactory fermé avec succès");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erreur lors de la fermeture de l'EntityManagerFactory", e);
            }
            emf = null;
        }
    }

    public static void beginTransaction() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
            logger.fine("🔁 Transaction démarrée");
        }
    }

    public static void commit() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            try {
                em.getTransaction().commit();
                logger.fine("✅ Transaction commitée");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "❌ Échec du commit", e);
                throw e;
            }
        }
    }

    public static void rollback() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            try {
                em.getTransaction().rollback();
                logger.warning("↩ Transaction annulée");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Échec du rollback", e);
            }
        }
    }

    public static void reset() {
        closeEntityManagerFactory();
        threadLocal.remove();
        initializeEntityManagerFactory();
    }
}