package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.entites.Utilisateur;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/supprimerPaiement")
public class SupprimerPaiementServlet extends HttpServlet {

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

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            PaiementLogementDAO dao =
                    new PaiementLogementDAO(em);

            PaiementLogement paiement =
                    dao.trouverParId(id);

            if (paiement != null) {

                // ==========================
                // INFOS AVANT SUPPRESSION
                // ==========================

                String nomEtudiant =
                        paiement.getLogementEtudiant()
                                .getEtudiant()
                                .getPrenom()
                                + " "
                                + paiement.getLogementEtudiant()
                                .getEtudiant()
                                .getNom();

                int mois =
                        paiement.getMois();

                int annee =
                        paiement.getAnnee();

                double montant =
                        paiement.getMontant();

                // ==========================
                // TRANSACTION
                // ==========================

                em.getTransaction().begin();

                // ==========================
                // SUPPRESSION
                // ==========================

                em.remove(
                        em.contains(paiement)
                                ? paiement
                                : em.merge(paiement)
                );

                // ==========================
                // UTILISATEUR CONNECTÉ
                // ==========================

                HttpSession session =
                        request.getSession();

                Utilisateur utilisateur =
                        (Utilisateur) session.getAttribute(
                                "utilisateurConnecte"
                        );

                // ==========================
                // LOG ACTION
                // ==========================

                if (utilisateur != null) {

                    ActionLogDAO actionLogDAO =
                            new ActionLogDAO(em);

                    String details =
                            "Paiement supprimé | Étudiant : "
                                    + nomEtudiant
                                    + " | Période : "
                                    + mois
                                    + "/"
                                    + annee
                                    + " | Montant : "
                                    + montant
                                    + " FCFA";

                    actionLogDAO.enregistrerAction(
                            utilisateur.getId(),
                            utilisateur.getPrenom()
                                    + " "
                                    + utilisateur.getNom(),
                            utilisateur.getRole(),
                            "Suppression de paiement",
                            details
                    );
                }

                em.getTransaction().commit();

                request.getSession().setAttribute(
                        "success",
                        "Paiement supprimé avec succès."
                );

            } else {

                request.getSession().setAttribute(
                        "erreur",
                        "Paiement introuvable."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            try {

                if (em != null
                        && em.getTransaction() != null
                        && em.getTransaction().isActive()) {

                    em.getTransaction().rollback();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            request.getSession().setAttribute(
                    "erreur",
                    "Erreur lors de la suppression."
            );
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/listePaiements"
        );
    }

    @Override
    public void destroy() {

        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}