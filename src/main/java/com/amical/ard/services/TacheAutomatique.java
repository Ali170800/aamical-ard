package com.amical.ard.services;

import com.amical.ard.utils.EntityManagerHelper;

import java.time.*;
import java.util.concurrent.*;

public class TacheAutomatique {

    public void demarrer() {
        Runnable tache = () -> {
            System.out.println("📤 Envoi automatique du 5 à 10h...");
            new EmailManuelService(EntityManagerHelper.getEntityManager())
                .envoyerAuxEtudiantsLoges(
                    "Rappel de Paiement",
                    "Bonjour, ceci est un rappel de paiement de votre logement étudiant. Veuillez régulariser votre situation. Merci."
                );
        };

        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime prochaineDate = maintenant.withDayOfMonth(5).withHour(10).withMinute(0).withSecond(0);

        // Si la date est déjà passée ce mois-ci, on prend le mois prochain
        if (prochaineDate.isBefore(maintenant)) {
            prochaineDate = prochaineDate.plusMonths(1);
        }

        long delayInitial = Duration.between(maintenant, prochaineDate).toMillis();
        long periode30Jours = TimeUnit.DAYS.toMillis(30);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(tache, delayInitial, periode30Jours, TimeUnit.MILLISECONDS);
    }
}