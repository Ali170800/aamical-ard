package com.amical.ard.servlets;

import java.io.IOException;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.entites.Appartement;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ajouterAppartement")
public class AjouterAppartementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AppartementDAO appartementDAO;

    @Override
    public void init() {

        appartementDAO =
                new AppartementDAO(
                        EntityManagerHelper.getEntityManager()
                );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            AppartementDAO appartementDAO =
                    new AppartementDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // =========================================
            // RÉCUPÉRATION UTILISATEUR CONNECTÉ
            // =========================================

            HttpSession session =
                    request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            // =========================================
            // DONNÉES FORMULAIRE
            // =========================================

            String nom =
                    request.getParameter("nomAppartement");

            String description =
                    request.getParameter("description");

            // =========================================
            // VALIDATION
            // =========================================

            if (nom == null || nom.trim().isEmpty()) {

                request.setAttribute(
                        "error",
                        "Le nom de l'appartement est requis."
                );

                request.getRequestDispatcher(
                        "/pages/jouter-appartement.jsp"
                ).forward(request, response);

                return;
            }

            // =========================================
            // CRÉATION APPARTEMENT
            // =========================================

            Appartement appartement =
                    new Appartement();

            appartement.setNomAppartement(
                    nom.trim()
            );

            appartement.setDescription(
                    description != null
                            ? description.trim()
                            : null
            );

            // =========================================
            // SAUVEGARDE
            // =========================================

            appartementDAO.ajouter(appartement);

            // =========================================
            // LOG ACTION
            // =========================================

            if (utilisateurConnecte != null) {

                String details =
                        "Appartement ajouté : "
                                + nom;

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Ajout appartement",
                        details
                );
            }

            // =========================================
            // MESSAGE SUCCÈS
            // =========================================

            request.getSession().setAttribute(
                    "success",
                    "Appartement ajouté avec succès !"
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/listeAppartements"
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Erreur lors de l'ajout : "
                            + e.getMessage()
            );

            request.getRequestDispatcher(
                    "/pages/jouter-appartement.jsp"
            ).forward(request, response);

        } finally {

            if (em != null) {
                EntityManagerHelper.closeEntityManager();
            }
        }
    }
}