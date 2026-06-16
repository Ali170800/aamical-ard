package com.amical.ard.servlets;

import com.amical.ard.dao.UtilisateurDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/changerMotDePasse")
public class ChangerMotDePasseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String motDePasse =
                request.getParameter("motDePasse");

        String confirmation =
                request.getParameter("confirmation");

        // =========================================
        // Vérification longueur mot de passe
        // =========================================

        if (motDePasse == null
                || motDePasse.length() < 6) {

            request.getSession().setAttribute(
                    "erreur",
                    "Le mot de passe doit contenir au moins 6 caractères."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasse.jsp"
            );

            return;
        }

        // =========================================
        // Vérification confirmation
        // =========================================

        if (!motDePasse.equals(confirmation)) {

            request.getSession().setAttribute(
                    "erreur",
                    "Les mots de passe ne correspondent pas."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasse.jsp"
            );

            return;
        }

        HttpSession session =
                request.getSession();

        String email =
                (String) session.getAttribute("resetEmail");

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            UtilisateurDAO utilisateurDAO =
                    new UtilisateurDAO(em);

            Utilisateur utilisateur =
                    utilisateurDAO.trouverParEmail(email);

            if (utilisateur == null) {

                request.getSession().setAttribute(
                        "erreur",
                        "Utilisateur introuvable."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/auth/motDePasseOublie.jsp"
                );

                return;
            }

            // =========================================
            // Transaction
            // =========================================

            EntityManagerHelper.beginTransaction();

            utilisateur.setMotDePasse(motDePasse);

            em.merge(utilisateur);

            EntityManagerHelper.commit();

            // =========================================
            // Nettoyage session
            // =========================================

            session.removeAttribute("resetCode");
            session.removeAttribute("resetEmail");

            request.getSession().setAttribute(
                    "success",
                    "Mot de passe modifié avec succès."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

            EntityManagerHelper.rollback();

            request.getSession().setAttribute(
                    "erreur",
                    "Erreur lors du changement du mot de passe."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasse.jsp"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}