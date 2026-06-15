package com.amical.ard.servlets;

import com.amical.ard.dao.UtilisateurDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EmailUtil;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Random;

@WebServlet("/verifierEmailRecuperation")
public class VerifierEmailRecuperationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        HttpSession session = request.getSession();

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            UtilisateurDAO utilisateurDAO =
                    new UtilisateurDAO(em);

            Utilisateur utilisateur =
                    utilisateurDAO.trouverParEmail(email);

            // =========================
            // Vérification email
            // =========================

            if (utilisateur == null) {

                session.setAttribute(
                        "erreur",
                        "Aucun administrateur trouvé avec cet email."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/auth/motDePasseOublie.jsp"
                );

                return;
            }

            // =========================
            // Génération code
            // =========================

            String code =
                    String.valueOf(
                            100000 + new Random().nextInt(900000)
                    );

            // =========================
            // Stockage session
            // =========================

            session.setAttribute("resetCode", code);
            session.setAttribute("resetEmail", email);

            // =========================
            // Email
            // =========================

            String sujet =
                    "Code de récupération - Amicale AERD";

            String contenu =
                    "Votre code de récupération est : " + code;

            EmailUtil.envoyerEmail(
                    email,
                    sujet,
                    contenu
            );

            // =========================
            // Succès
            // =========================

            session.setAttribute(
                    "success",
                    "Un code a été envoyé à votre adresse email."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/verificationCode.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

            session.setAttribute(
                    "erreur",
                    "Erreur lors de l'envoi du code."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/motDePasseOublie.jsp"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}