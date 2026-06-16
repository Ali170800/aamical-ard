package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/caravane/creer")
public class CreerCaravaneServlet extends HttpServlet {

    // ✅ Affichage du formulaire
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/pages/creerCaravane.jsp")
                .forward(request, response);
    }

    // ✅ Traitement du formulaire
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String dateStr = request.getParameter("date");
        String montantStr = request.getParameter("montant");

        EntityManager em = null;

        try {

            // ==========================
            // VALIDATION
            // ==========================

            if (nom == null || nom.trim().isEmpty()
                    || dateStr == null || dateStr.trim().isEmpty()
                    || montantStr == null || montantStr.trim().isEmpty()) {

                request.setAttribute(
                        "erreur",
                        "Tous les champs sont obligatoires."
                );

                request.getRequestDispatcher("/pages/creerCaravane.jsp")
                        .forward(request, response);

                return;
            }

            LocalDate date = LocalDate.parse(dateStr);
            double montant = Double.parseDouble(montantStr);

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // ==========================
            // UTILISATEUR CONNECTÉ
            // ==========================

            HttpSession session = request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute("utilisateurConnecte");

            // ==========================
            // CRÉATION CARAVANE
            // ==========================

            Caravane caravane = new Caravane();

            caravane.setNom(nom.trim());
            caravane.setDate(date);
            caravane.setMontant(montant);

            caravaneDAO.ajouter(caravane);

            // ==========================
            // LOG ACTION
            // ==========================

            if (utilisateurConnecte != null) {

                String details =
                        "Caravane : " + nom
                                + " | Date : " + date
                                + " | Montant : " + montant + " FCFA";

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Création caravane",
                        details
                );
            }

            // ==========================
            // SUCCÈS
            // ==========================

            request.getSession().setAttribute(
                    "success",
                    "Caravane créée avec succès."
            );

            response.sendRedirect(
                    request.getContextPath() + "/caravane/lister"
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "erreur",
                    "Erreur lors de la création de la caravane : "
                            + e.getMessage()
            );

            request.getRequestDispatcher("/pages/creerCaravane.jsp")
                    .forward(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}