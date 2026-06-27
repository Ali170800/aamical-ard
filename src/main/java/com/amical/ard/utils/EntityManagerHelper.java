package com.amical.ard.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerHelper {

    private static final Logger logger = Logger.getLogger(EntityManagerHelper.class.getName());
    private static final String PERSISTENCE_UNIT = "amicalePU";
    private static final EntityManagerFactory emf;

    // Initialisation unique et thread-safe au démarrage
    static {
        try {
            logger.info("🔄 Initialisation de l'EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "⛔ Échec de création de l'EntityManagerFactory", e);
            throw new RuntimeException("Erreur critique JPA: vérifiez persistence.xml", e);
        }
    }

    // Fournit une nouvelle instance à chaque appel (le filtre gérera la fermeture)
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}