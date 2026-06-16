package com.amical.ard.servlets;

import com.amical.ard.entites.Utilisateur;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/verifier")
public class AdminVerifierServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String role = request.getParameter("role");

        if (email == null || role == null || email.trim().isEmpty() || role.trim().isEmpty()) {
            request.setAttribute("erreur", "Email et rôle sont obligatoires");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            Utilisateur user = em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE LOWER(TRIM(u.email)) = :email",
                            Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if ("ACTIF".equalsIgnoreCase(user.getStatut())) {
                request.setAttribute("erreur", "Ce compte est déjà activé.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            if (!user.getRole().equalsIgnoreCase(role)) {
                request.setAttribute("erreur", "Le rôle ne correspond pas à cet utilisateur.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            String code = String.format("%06d", (int)(Math.random() * 1000000));

            em.getTransaction().begin();
            user.setCodeValidation(code);
            user.setStatut("PENDING");
            em.merge(user);
            em.getTransaction().commit();

            new EmailService().envoyerEmail(email, "Code Activation Compte",
                    "Votre code d'activation est : " + code);

            request.setAttribute("email", email);
            request.setAttribute("success", "Code envoyé avec succès à " + email);

            request.getRequestDispatcher("/code-activation.jsp").forward(request, response);

        } catch (NoResultException e) {
            request.setAttribute("erreur", "Aucun utilisateur trouvé avec cet email.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}