package com.amical.ard.servlets;

import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/verifier-code")
public class AdminVerifierCodeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String code = request.getParameter("code");

        if (email == null || code == null || email.trim().isEmpty() || code.trim().isEmpty()) {
            request.setAttribute("erreur", "Email et code sont obligatoires");
            request.getRequestDispatcher("/code-activation.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();
        code = code.trim();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            Utilisateur user = em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE LOWER(TRIM(u.email)) = :email",
                            Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if ("ACTIF".equalsIgnoreCase(user.getStatut())) {
                request.setAttribute("erreur", "Compte déjà activé.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            if (user.getCodeValidation() == null ||
                    !user.getCodeValidation().trim().equalsIgnoreCase(code)) {

                request.setAttribute("erreur", "Code incorrect.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/code-activation.jsp").forward(request, response);
                return;
            }

            // Succès → Afficher le formulaire de création de mot de passe
            request.setAttribute("activationReady", true);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Utilisateur introuvable.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}