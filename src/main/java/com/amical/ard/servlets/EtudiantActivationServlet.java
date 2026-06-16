package com.amical.ard.servlets;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/activer")
public class EtudiantActivationServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("erreur", "Email obligatoire.");
            request.getRequestDispatcher("/pages/activationCompte.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<Etudiant> liste = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email", Etudiant.class)
                    .setParameter("email", email)
                    .getResultList();

            if (liste.isEmpty()) {
                request.setAttribute("erreur", "Aucun compte trouvé.");
                request.getRequestDispatcher("/pages/activationCompte.jsp").forward(request, response);
                return;
            }

            Etudiant etudiant = liste.get(0);

            // ✅ Déjà activé
            if ("ACTIF".equalsIgnoreCase(etudiant.getStatut())) {
                response.sendRedirect(request.getContextPath()
                        + "/pages/connexionEtudiant.jsp?success=dejaActive");
                return;
            }

            // ✅ Générer code pour INACTIF ou PENDING
            String code = String.format("%06d", (int) (Math.random() * 1000000));

            em.getTransaction().begin();
            etudiant.setCodeValidation(code);
            etudiant.setStatut("PENDING");
            em.merge(etudiant);
            em.getTransaction().commit();

            emailService.envoyerEmail(email, "Code Activation", "Votre code : " + code);

            request.setAttribute("email", email);
            request.setAttribute("success", "Code envoyé par email.");

            request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/pages/activationCompte.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}