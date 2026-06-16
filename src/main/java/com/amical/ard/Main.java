package com.amical.ard;

import com.amical.ard.services.TacheAutomatique;

public class Main {
    public static void main(String[] args) {
        // On démarre la tâche automatique
        TacheAutomatique tacheAuto = new TacheAutomatique();
        tacheAuto.demarrer();

        System.out.println("Tâche automatique lancée...");
    }
}