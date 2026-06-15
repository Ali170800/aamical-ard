package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

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

        EntityManager em = null;

        try {

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            FichierJointDAO dao =
                    new FichierJointDAO(em);

            request.setAttribute(
                    "fichiers",
                    dao.listerParRole(utilisateur.getRole())
            );

            request.getRequestDispatcher(
                    "/pages/fichiers/listeFichiers.jsp"
            ).forward(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}