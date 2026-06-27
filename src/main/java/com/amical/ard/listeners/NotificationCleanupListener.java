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
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            EntityManager em = null;
            try {
                // Ouverture de la connexion
                em = EntityManagerHelper.getEntityManager();
                NotificationDAO dao = new NotificationDAO(em);
                dao.supprimerVieillesNotifications();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // FERMETURE OBLIGATOIRE : libère la connexion pour le pool
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        }, 0, 2, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}