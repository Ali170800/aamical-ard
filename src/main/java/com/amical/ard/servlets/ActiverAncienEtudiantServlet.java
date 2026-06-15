package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/activer-ancien") // ✅ CORRIGÉ
public class ActiverAncienEtudiantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String codeSaisi = request.getParameter("code");

        if (email == null || codeSaisi == null) {
            request.setAttribute("erreur", "Email et code obligatoires.");
            request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = null;

        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO dao = new EtudiantDAO(em);

            List<Etudiant> etudiants = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email",
                            Etudiant.class
                    )
                    .setParameter("email", email)
                    .getResultList();

            if (etudiants.isEmpty()) {
                request.setAttribute("erreur", "Aucun compte trouvé avec cet email.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
                return;
            }

            Etudiant etudiant = etudiants.get(0);

            if (etudiant.getCodeValidation() != null &&
                    etudiant.getCodeValidation().equalsIgnoreCase(codeSaisi.trim())) {

                etudiant.setStatut("ACTIF");
                etudiant.setCodeValidation(null);

                em.getTransaction().begin();
                em.merge(etudiant);
                em.getTransaction().commit();

                response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp?success=true");

            } else {
                request.setAttribute("erreur", "Code incorrect.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}