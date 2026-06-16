package com.amical.ard.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JPAListener implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
<<<<<<< HEAD
        emf = Persistence.createEntityManagerFactory("amicalePU"); // nom de ton persistence.xml
        sce.getServletContext().setAttribute("emf", emf); // ceci permet à ta servlet de le retrouver
=======
        try {
            System.out.println("🔄 Initialisation de EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("amicalePU");
            sce.getServletContext().setAttribute("emf", emf);
            System.out.println("✅ EntityManagerFactory initialisé avec succès");
        } catch (Exception e) {
            System.err.println("❌ Erreur critique lors de l'initialisation JPA : " + e.getMessage());
            e.printStackTrace();
            // On ne bloque pas le démarrage complet
        }
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
<<<<<<< HEAD
            emf.close();
=======
            try {
                emf.close();
                System.out.println("✅ EntityManagerFactory fermé");
            } catch (Exception e) {
                System.err.println("Erreur lors de la fermeture de EMF");
            }
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
        }
    }
}