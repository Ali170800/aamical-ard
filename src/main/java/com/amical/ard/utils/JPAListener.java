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
        emf = Persistence.createEntityManagerFactory("amicalePU"); // nom de ton persistence.xml
        sce.getServletContext().setAttribute("emf", emf); // ceci permet à ta servlet de le retrouver
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}