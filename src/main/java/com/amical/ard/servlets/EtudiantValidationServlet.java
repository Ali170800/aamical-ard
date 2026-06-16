package com.amical.ard.servlets;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/valider")
public class EtudiantValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String codeSaisi = request.getParameter("code");

        if (email == null || codeSaisi == null) {
            response.sendRedirect(request.getContextPath() + "/pages/activationCompte.jsp");
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<Etudiant> list = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email", Etudiant.class)
                    .setParameter("email", email)
                    .getResultList();

            if (list.isEmpty()) {
                request.setAttribute("erreur", "Compte introuvable.");
                request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
                return;
            }

            Etudiant etudiant = list.get(0);

            // 🚨 IMPORTANT : vérifier statut
            if (!"PENDING".equalsIgnoreCase(etudiant.getStatut())) {
                request.setAttribute("erreur", "Veuillez demander un nouveau code.");
                request.getRequestDispatcher("/pages/activationCompte.jsp").forward(request, response);
                return;
            }

            if (etudiant.getCodeValidation() != null &&
                    etudiant.getCodeValidation().equals(codeSaisi.trim())) {

                em.getTransaction().begin();
                etudiant.setStatut("VERIFIED");
                em.merge(etudiant);
                em.getTransaction().commit();

                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/creationMotDePasse.jsp").forward(request, response);

            } else {
                request.setAttribute("erreur", "Code incorrect.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
            }

        } finally {
            em.close();
        }
    }
}