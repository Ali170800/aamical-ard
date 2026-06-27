package com.amical.ard.listeners;

import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class NotificationCleanupListener implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialisation du scheduler pour nettoyer les notifications en arrière-plan
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            // On ouvre une instance locale pour ce thread de nettoyage
            EntityManager em = null;
            try {
                // Utilisation de la méthode corrigée dans EntityManagerHelper
                em = EntityManagerHelper.getEntityManager();

                NotificationDAO dao = new NotificationDAO(em);
                dao.supprimerVieillesNotifications();

            } catch (Exception e) {
                // On logue l'erreur sans arrêter le scheduler
                System.err.println("❌ Erreur dans NotificationCleanupListener : " + e.getMessage());
                e.printStackTrace();
            } finally {
                // FERMETURE OBLIGATOIRE :
                // Ici, on ferme la connexion manuellement car aucun Filtre n'est présent
                // pour le faire à notre place dans ce thread de fond.
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        }, 0, 2, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Arrêt propre du scheduler lors de l'arrêt du serveur
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}