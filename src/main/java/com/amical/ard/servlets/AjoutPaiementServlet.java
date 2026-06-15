package com.amical.ard.servlets;

import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.ActionLog;
import com.amical.ard.entites.LogementEtudiant;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ajouterPaiement")
public class AjoutPaiementServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String[] etudiantIdsStr =
                request.getParameterValues("etudiantIds");

        if (etudiantIdsStr == null
                || etudiantIdsStr.length == 0) {

            request.getSession().setAttribute(
                    "error",
                    "Veuillez sélectionner au moins un étudiant."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/afficherFormulairePaiement"
            );

            return;
        }

        HttpSession session = request.getSession();

        Utilisateur utilisateurConnecte =
                (Utilisateur) session.getAttribute(
                        "utilisateurConnecte"
                );

        EntityManager em = null;

        try {

            String montantStr =
                    request.getParameter("montant");

            String moisStr =
                    request.getParameter("mois");

            String anneeStr =
                    request.getParameter("annee");

            String statutString =
                    request.getParameter("statut");

            double montant =
                    Double.parseDouble(montantStr);

            int mois =
                    Integer.parseInt(moisStr);

            int annee =
                    Integer.parseInt(anneeStr);

            StatutPaiement statut =
                    StatutPaiement.valueOf(statutString);

            em = EntityManagerHelper.getEntityManager();

            LogementEtudiantDAO logementDAO =
                    new LogementEtudiantDAO(em);

            PaiementLogementDAO paiementDAO =
                    new PaiementLogementDAO(em);

            // ======================================================
            // VÉRIFICATION DES DOUBLONS
            // ======================================================

            List<String> doublons =
                    new ArrayList<>();

            for (String idStr : etudiantIdsStr) {

                Long etudiantId =
                        Long.parseLong(idStr);

                LogementEtudiant logementEtudiant =
                        logementDAO.trouverParEtudiantId(
                                etudiantId
                        );

                if (logementEtudiant == null) {
                    continue;
                }

                boolean existe =
                        paiementDAO.existePaiement(
                                etudiantId,
                                mois,
                                annee
                        );

                if (existe) {

                    String nomComplet =
                            logementEtudiant
                                    .getEtudiant()
                                    .getPrenom()
                                    + " "
                                    + logementEtudiant
                                    .getEtudiant()
                                    .getNom();

                    doublons.add(nomComplet);
                }
            }

            // ======================================================
            // SI DOUBLONS
            // ======================================================

            if (!doublons.isEmpty()) {

                StringBuilder message =
                        new StringBuilder();

                message.append(
                        "Les étudiants suivants possèdent déjà "
                );

                message.append(
                        "un paiement pour "
                );

                message.append(mois)
                        .append("/")
                        .append(annee)
                        .append(" : ");

                for (String nom : doublons) {

                    message.append("<br>• ")
                            .append(nom);
                }

                request.getSession().setAttribute(
                        "error",
                        message.toString()
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/afficherFormulairePaiement"
                );

                return;
            }

            // ======================================================
            // INSERTION DES PAIEMENTS
            // ======================================================

            EntityManagerHelper.beginTransaction();

            int successCount = 0;

            for (String idStr : etudiantIdsStr) {

                Long etudiantId =
                        Long.parseLong(idStr);

                LogementEtudiant logementEtudiant =
                        logementDAO.trouverParEtudiantId(
                                etudiantId
                        );

                if (logementEtudiant == null) {
                    continue;
                }

                PaiementLogement paiement =
                        new PaiementLogement();

                paiement.setMontant(montant);

                paiement.setMois(mois);

                paiement.setAnnee(annee);

                paiement.setStatut(statut);

                paiement.setLogementEtudiant(
                        logementEtudiant
                );

                paiementDAO.ajouter(paiement);

                // ======================================================
                // LOG D'ACTION
                // ======================================================

                ActionLog log = new ActionLog();

                log.setUtilisateurId(
                        utilisateurConnecte.getId()
                );

                log.setNomUtilisateur(
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom()
                );

                log.setRole(
                        utilisateurConnecte.getRole()
                );

                log.setAction(
                        "Ajout paiement logement"
                );

                log.setDetails(
                        "Paiement ajoute pour "
                                + logementEtudiant
                                .getEtudiant()
                                .getPrenom()
                                + " "
                                + logementEtudiant
                                .getEtudiant()
                                .getNom()
                                + " | Appartement : "
                                + logementEtudiant
                                .getAppartement()
                                .getNomAppartement()
                                + " | Mois : "
                                + mois
                                + "/"
                                + annee
                                + " | Montant : "
                                + ((int) montant)
                                + " FCFA"
                );

                em.persist(log);

                // ======================================================
                // EMAIL SI PAIEMENT PAYÉ
                // ======================================================

                if (statut == StatutPaiement.PAYE) {

                    String email =
                            logementEtudiant
                                    .getEtudiant()
                                    .getEmail();

                    if (email != null
                            && !email.trim().isEmpty()) {

                        String sujet =
                                "Confirmation de paiement de logement";

                        String contenu =
                                "Bonjour "
                                        + logementEtudiant
                                        .getEtudiant()
                                        .getPrenom()
                                        + ",\n\n"

                                        + "Nous confirmons la réception "
                                        + "de votre paiement pour le logement "
                                        + "situé dans l'appartement : "

                                        + logementEtudiant
                                        .getAppartement()
                                        .getNomAppartement()

                                        + ".\n\n"

                                        + "Montant payé : "
                                        + ((int) montant)
                                        + " FCFA\n"

                                        + "Mois concerné : "
                                        + mois
                                        + "/"
                                        + annee
                                        + "\n"

                                        + "Statut : Payé\n\n"

                                        + "Merci pour votre régularité.\n"

                                        + "Amicale des Ressortissants "
                                        + "de Diourbel.";

                        emailService.envoyerEmail(
                                email,
                                sujet,
                                contenu
                        );
                    }
                }

                // ======================================================
                // CONSOLE LOG
                // ======================================================

                System.out.println(
                        "[LOG PAIEMENT] "
                                + utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom()
                                + " a ajouté un paiement pour "
                                + logementEtudiant
                                .getEtudiant()
                                .getPrenom()
                                + " "
                                + logementEtudiant
                                .getEtudiant()
                                .getNom()
                                + " | Mois : "
                                + mois
                                + "/"
                                + annee
                                + " | Montant : "
                                + ((int) montant)
                                + " FCFA"
                );

                successCount++;
            }

            EntityManagerHelper.commit();

            request.getSession().setAttribute(
                    "success",
                    successCount
                            + " paiement(s) enregistré(s) avec succès."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/listePaiements"
            );

        } catch (Exception e) {

            e.printStackTrace();

            EntityManagerHelper.rollback();

            request.getSession().setAttribute(
                    "error",
                    "Erreur lors de l'ajout des paiements : "
                            + e.getMessage()
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/afficherFormulairePaiement"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}