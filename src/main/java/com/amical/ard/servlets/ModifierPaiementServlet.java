package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/modifierPaiement")
public class ModifierPaiementServlet extends HttpServlet {

    private EntityManager em;

    @Override
    public void init() {

        em = Persistence
                .createEntityManagerFactory("amicalePU")
                .createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            PaiementLogementDAO dao =
                    new PaiementLogementDAO(em);

            PaiementLogement paiement =
                    dao.trouverParId(id);

            // =========================================
            // PAIEMENT INTROUVABLE
            // =========================================

            if (paiement == null) {

                HttpSession session = request.getSession();

                Utilisateur utilisateurConnecte =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                if (utilisateurConnecte != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Échec chargement paiement",
                            "Paiement introuvable ID=" + id
                    );
                }

                request.getSession().setAttribute(
                        "error",
                        "Paiement introuvable."
                );

                response.sendRedirect("listePaiements");
                return;
            }

            request.setAttribute("paiement", paiement);

            request.getRequestDispatcher(
                    "/pages/ModifierPaiement.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            try {

                HttpSession session = request.getSession();

                Utilisateur utilisateurConnecte =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                if (utilisateurConnecte != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Erreur chargement paiement",
                            e.getMessage()
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            request.getSession().setAttribute(
                    "error",
                    "Erreur lors du chargement du paiement."
            );

            response.sendRedirect("listePaiements");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id =
                    Integer.parseInt(request.getParameter("id"));

            double montant =
                    Double.parseDouble(request.getParameter("montant"));

            int mois =
                    Integer.parseInt(request.getParameter("mois"));

            int annee =
                    Integer.parseInt(request.getParameter("annee"));

            StatutPaiement statut =
                    StatutPaiement.valueOf(
                            request.getParameter("statut")
                    );

            PaiementLogementDAO dao =
                    new PaiementLogementDAO(em);

            PaiementLogement paiement =
                    dao.trouverParId(id);

            // =========================================
            // PAIEMENT INTROUVABLE
            // =========================================

            if (paiement == null) {

                HttpSession session = request.getSession();

                Utilisateur utilisateurConnecte =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                if (utilisateurConnecte != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Échec modification paiement",
                            "Paiement introuvable ID=" + id
                    );
                }

                request.getSession().setAttribute(
                        "error",
                        "Paiement introuvable."
                );

                response.sendRedirect("listePaiements");
                return;
            }

            // =========================================
            // VÉRIFICATION DOUBLON
            // =========================================

            Long etudiantId =
                    paiement.getLogementEtudiant()
                            .getEtudiant()
                            .getId();

            boolean doublon =
                    dao.existePaiementPourModification(
                            id,
                            etudiantId,
                            mois,
                            annee
                    );

            if (doublon) {

                HttpSession session = request.getSession();

                Utilisateur utilisateurConnecte =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                if (utilisateurConnecte != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Échec modification paiement",
                            "Tentative doublon paiement ID="
                                    + id
                                    + " pour "
                                    + mois + "/" + annee
                    );
                }

                request.getSession().setAttribute(
                        "error",
                        "Un paiement existe déjà pour cet étudiant pour "
                                + mois + "/" + annee
                );

                response.sendRedirect(
                        "modifierPaiement?id=" + id
                );

                return;
            }

            // =========================================
            // ANCIENNES VALEURS
            // =========================================

            String ancienStatut =
                    paiement.getStatut() != null
                            ? paiement.getStatut().name()
                            : "INCONNU";

            double ancienMontant =
                    paiement.getMontant();

            int ancienMois =
                    paiement.getMois();

            int ancienneAnnee =
                    paiement.getAnnee();

            // =========================================
            // MISE À JOUR
            // =========================================

            paiement.setMontant(montant);
            paiement.setMois(mois);
            paiement.setAnnee(annee);
            paiement.setStatut(statut);

            dao.mettreAJour(paiement);

            // =========================================
            // LOG MODIFICATION
            // =========================================

            HttpSession session = request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            if (utilisateurConnecte != null) {

                ActionLogDAO actionLogDAO =
                        new ActionLogDAO(em);

                String details =
                        String.format(
                                "Paiement ID=%d | Montant %.0f → %.0f | Mois %d/%d → %d/%d | Statut %s → %s",
                                id,
                                ancienMontant,
                                montant,
                                ancienMois,
                                ancienneAnnee,
                                mois,
                                annee,
                                ancienStatut,
                                statut.name()
                        );

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Modification paiement",
                        details
                );
            }

            request.getSession().setAttribute(
                    "success",
                    "Paiement modifié avec succès."
            );

            response.sendRedirect("listePaiements");

        } catch (Exception e) {

            e.printStackTrace();

            try {

                HttpSession session = request.getSession();

                Utilisateur utilisateurConnecte =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                if (utilisateurConnecte != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Erreur modification paiement",
                            e.getMessage()
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            request.getSession().setAttribute(
                    "error",
                    "Erreur lors de la modification : "
                            + e.getMessage()
            );

            response.sendRedirect("listePaiements");
        }
    }

    @Override
    public void destroy() {

        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}