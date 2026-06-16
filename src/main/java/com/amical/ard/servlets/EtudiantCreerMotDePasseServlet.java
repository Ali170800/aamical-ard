package com.amical.ard.servlets;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/creer-motdepasse")
public class EtudiantCreerMotDePasseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String confirm = request.getParameter("confirmMotDePasse");

        if (email == null || motDePasse == null || confirm == null ||
                motDePasse.trim().isEmpty() || !motDePasse.equals(confirm)) {

            request.setAttribute("erreur", "Les mots de passe ne correspondent pas.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/pages/creationMotDePasse.jsp").forward(request, response);
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<Etudiant> list = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email", Etudiant.class)
                    .setParameter("email", email.trim().toLowerCase())
                    .getResultList();

            if (list.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/pages/activationCompte.jsp");
                return;
            }

            Etudiant etudiant = list.get(0);

            // 🚨 IMPORTANT
            if (!"VERIFIED".equalsIgnoreCase(etudiant.getStatut())) {
                response.sendRedirect(request.getContextPath() + "/pages/activationCompte.jsp");
                return;
            }

            em.getTransaction().begin();
            etudiant.setMotDePasse(motDePasse);
            etudiant.setStatut("ACTIF");
            etudiant.setCodeValidation(null);
            em.merge(etudiant);
            em.getTransaction().commit();

            response.sendRedirect(request.getContextPath()
                    + "/pages/connexionEtudiant.jsp?success=activationOK");

        } finally {
            em.close();
        }
    }
}