package com.amical.ard.servlets;

import com.amical.ard.dao.UtilisateurDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String loginInput = request.getParameter("login");
        String motDePasse = request.getParameter("motDePasse");

        // ================= VALIDATION =================

        if (loginInput == null || motDePasse == null
                || loginInput.trim().isEmpty()
                || motDePasse.trim().isEmpty()) {

            request.setAttribute(
                    "erreur",
                    "Email/Login et mot de passe obligatoires."
            );

            doGet(request, response);
            return;
        }

        loginInput = loginInput.trim().toLowerCase();
        motDePasse = motDePasse.trim();

        EntityManager em = null;

        try {

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            UtilisateurDAO dao = new UtilisateurDAO(em);

            // ================= RECHERCHE UTILISATEUR =================

            Utilisateur user = dao.trouverParEmail(loginInput);

            if (user == null) {
                user = dao.trouverParLogin(loginInput);
            }

            if (user == null) {

                request.setAttribute(
                        "erreur",
                        "Compte introuvable."
                );

                doGet(request, response);
                return;
            }

            // ================= MOT DE PASSE =================

            if (user.getMotDePasse() == null
                    || !user.getMotDePasse().trim().equals(motDePasse)) {

                request.setAttribute(
                        "erreur",
                        "Mot de passe incorrect."
                );

                doGet(request, response);
                return;
            }

            // ================= STATUT =================

            if (!"ACTIF".equalsIgnoreCase(user.getStatut())) {

                request.setAttribute(
                        "erreur",
                        "Compte non activé."
                );

                doGet(request, response);
                return;
            }

            // ================= SESSION =================

            HttpSession session = request.getSession(true);

            session.setAttribute(
                    "utilisateurConnecte",
                    user
            );

            session.setAttribute(
                    "userType",
                    user.getRole()
            );

            session.setAttribute(
                    "username",
                    user.getPrenom() + " " + user.getNom()
            );

            String role = user.getRole() != null
                    ? user.getRole().trim().toUpperCase()
                    : "";

            // ================= REDIRECTION =================

            if ("PCS".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/pcs-dashboard"
                );
            }

            else if ("PCO".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/pco-dashboard"
                );
            }

            else if ("ADMIN".equals(role)
                    || "SUPER_ADMIN".equals(role)
                    || "SUPERADMIN".equals(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/cahier.jsp"
                );
            }

            else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/cahier.jsp"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "erreur",
                    "Erreur serveur."
            );

            doGet(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}