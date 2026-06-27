package com.amical.ard.listeners;

import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.utils.EntityManagerHelper;
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
        // Le test est configuré pour s'exécuter toutes les 2 minutes
        scheduler.scheduleAtFixedRate(() -> {
            try {
                NotificationDAO dao = new NotificationDAO(EntityManagerHelper.getEntityManager());
                dao.supprimerVieillesNotifications();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}