package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet("/participant/ajouter")
public class AjouterParticipantServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {

            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            CaravaneDAO caravaneDAO = new CaravaneDAO(em);

            request.setAttribute(
                    "etudiants",
                    etudiantDAO.listerTous()
            );

            request.setAttribute(
                    "caravanes",
                    caravaneDAO.listerTous()
            );

        } finally {

            em.close();
        }

        request.getRequestDispatcher(
                "/pages/ajouterParticipant.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String[] etudiantIds =
                request.getParameterValues("etudiantIds");

        String caravaneIdStr =
                request.getParameter("caravaneId");

        String statutStr =
                request.getParameter("statut");

        if (etudiantIds == null || etudiantIds.length == 0) {

            request.setAttribute(
                    "erreur",
                    "Veuillez sélectionner au moins un étudiant."
            );

            doGet(request, response);
            return;
        }

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        ParticipantCaravaneDAO participantDAO =
                new ParticipantCaravaneDAO(em);

        EtudiantDAO etudiantDAO =
                new EtudiantDAO(em);

        CaravaneDAO caravaneDAO =
                new CaravaneDAO(em);

        int successCount = 0;

        try {

            em.getTransaction().begin();

            int caravaneId =
                    Integer.parseInt(caravaneIdStr);

            Caravane caravane =
                    caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) {
                throw new Exception("Caravane introuvable");
            }

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (String idStr : etudiantIds) {

                try {

                    Long etudiantId =
                            Long.parseLong(idStr);

                    Etudiant etudiant =
                            etudiantDAO.trouverParId(etudiantId);

                    if (etudiant == null) {
                        continue;
                    }

                    ParticipantCaravane p =
                            new ParticipantCaravane();

                    p.setNom(
                            etudiant.getNom() != null
                                    ? etudiant.getNom()
                                    : ""
                    );

                    p.setPrenom(
                            etudiant.getPrenom() != null
                                    ? etudiant.getPrenom()
                                    : ""
                    );

                    p.setEmail(
                            etudiant.getEmail() != null
                                    ? etudiant.getEmail()
                                    : ""
                    );

                    p.setCaravane(caravane);

                    p.setMontant(
                            caravane.getMontant()
                    );

                    boolean estPaye =
                            "PAYE".equalsIgnoreCase(statutStr);

                    if (estPaye) {

                        p.setStatutPaiement(
                                StatutPaiement.PAYE
                        );

                        p.setMontantPaye(
                                caravane.getMontant().intValue()
                        );

                        if (etudiant.getEmail() != null
                                && !etudiant.getEmail().trim().isEmpty()) {

                            String sujet =
                                    "Confirmation de votre participation à la caravane";

                            String message =
                                    String.format(
                                            "Bonjour %s %s,\n\n"
                                                    + "Votre inscription à la caravane %s prévue le %s a été confirmée.\n\n"
                                                    + "Montant payé : %.2f FCFA.\n\n"
                                                    + "Merci pour votre participation !\n\n"
                                                    + "L'équipe de l'Amicale.",
                                            etudiant.getPrenom(),
                                            etudiant.getNom(),
                                            caravane.getNom(),
                                            caravane.getDate() != null
                                                    ? caravane.getDate().format(formatter)
                                                    : "N/A",
                                            caravane.getMontant()
                                    );

                            emailService.envoyerEmail(
                                    etudiant.getEmail(),
                                    sujet,
                                    message
                            );
                        }

                    } else {

                        p.setStatutPaiement(
                                StatutPaiement.Non_Paye
                        );

                        p.setMontantPaye(0);
                    }

                    participantDAO.ajouter(p);

                    successCount++;

                } catch (Exception ex) {

                    System.err.println(
                            "Erreur étudiant ID="
                                    + idStr
                                    + " : "
                                    + ex.getMessage()
                    );
                }
            }

            em.getTransaction().commit();

            // =================================================
            // LOGS
            // =================================================

            HttpSession session =
                    request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            if (utilisateurConnecte != null) {

                ActionLogDAO actionLogDAO =
                        new ActionLogDAO(em);

                String details =
                        successCount
                                + " participant(s) ajouté(s) à la caravane : "
                                + caravane.getNom();

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Ajout participants caravane",
                        details
                );
            }

            request.getSession().setAttribute(
                    "success",
                    successCount
                            + " participant(s) ajouté(s) avec succès !"
            );

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            request.getSession().setAttribute(
                    "erreur",
                    "Erreur lors de l'ajout : "
                            + e.getMessage()
            );

        } finally {

            em.close();
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/participants/selectionner"
        );
    }
}