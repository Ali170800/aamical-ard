package com.amical.ard.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emf;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("ton-pu-name");
            } catch (Exception e) {
                // On logue l'erreur mais on ne plante pas l'initialisation de la classe
                e.printStackTrace();
            }
        }
        return emf;
    }

    public static void close() {
        if (emf != null) emf.close();
    }
}