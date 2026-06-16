package com.amical.ard.servlets;

import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/activer")
public class AdminActiverCompteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String pass = request.getParameter("motDePasse");
        String confirm = request.getParameter("confirmMotDePasse");

        if (email == null || pass == null || confirm == null ||
                email.trim().isEmpty() || pass.trim().isEmpty()) {

            request.getSession().setAttribute("erreur", "Tous les champs sont obligatoires");
            response.sendRedirect(request.getContextPath() + "/login.jsp?activationReady=true&email=" + email);
            return;
        }

        email = email.trim().toLowerCase();

        if (!pass.equals(confirm)) {
            request.getSession().setAttribute("erreur", "Les mots de passe ne correspondent pas");
            response.sendRedirect(request.getContextPath() + "/login.jsp?activationReady=true&email=" + email);
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            Utilisateur user = em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE LOWER(TRIM(u.email)) = :email",
                            Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();

            em.getTransaction().begin();
            user.setMotDePasse(pass.trim());
            user.setStatut("ACTIF");
            user.setCodeValidation(null);
            em.merge(user);
            em.getTransaction().commit();

            request.getSession().setAttribute("success", "✅ Compte activé avec succès !");

            response.sendRedirect(request.getContextPath() + "/login.jsp");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            request.getSession().setAttribute("erreur", "Erreur lors de l'activation du compte.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } finally {
            em.close();
        }
    }
}