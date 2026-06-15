package com.amical.ard.servlets;

import com.amical.ard.dao.UtilisateurDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class ServletLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String motDePasse = request.getParameter("motDePasse");

        // ✅ CORRECTION ICI
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO(em);
        Utilisateur utilisateur = utilisateurDAO.trouverParLogin(login);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            // Connexion réussie
            HttpSession session = request.getSession();
            session.setAttribute("utilisateurConnecte", utilisateur);
            response.sendRedirect(request.getContextPath() + "/pages/cahier.jsp");
        } else {
            // Échec de connexion
            request.setAttribute("erreur", "Login ou mot de passe incorrect");
            request.getRequestDispatcher("/webapp/login.jsp").forward(request, response);
        }

        em.close();
    }
}