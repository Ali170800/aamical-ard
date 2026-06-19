package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/fichiers/liste")
public class ListeFichiersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        if (utilisateur == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        // Récupération via le Helper global
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            FichierJointDAO dao = new FichierJointDAO(em);

            request.setAttribute(
                    "fichiers",
                    dao.listerParRole(utilisateur.getRole())
            );

            request.getRequestDispatcher(
                    "/pages/fichiers/listeFichiers.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des fichiers.");
        }
        // finally { em.close(); } supprimé : géré par le Filtre
    }
}