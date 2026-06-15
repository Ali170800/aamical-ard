package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/valider")
public class EtudiantValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String codeSaisi = request.getParameter("code");

        System.out.println("🔍 Validation demandée - Email: " + email + " | Code saisi: " + codeSaisi);

        if (email == null || codeSaisi == null) {
            request.setAttribute("erreur", "Email et code obligatoires.");
            request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EtudiantDAO dao = new EtudiantDAO(em);

            List<Etudiant> etudiants = em.createQuery(
                "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email", Etudiant.class)
                .setParameter("email", email)
                .getResultList();

            System.out.println("📊 Nombre d'étudiants trouvés : " + etudiants.size());

            if (etudiants.isEmpty()) {
                System.out.println("❌ Aucun étudiant trouvé pour : " + email);
                request.setAttribute("erreur", "Aucun compte trouvé avec cet email.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
                return;
            }

            Etudiant etudiant = etudiants.get(0);
            System.out.println("✅ Étudiant trouvé - ID: " + etudiant.getId() + " | Code en BD: " + etudiant.getCodeValidation());

            if (etudiant.getCodeValidation() != null && etudiant.getCodeValidation().equalsIgnoreCase(codeSaisi.trim())) {
                etudiant.setStatut("ACTIF");
                etudiant.setCodeValidation(null);

                em.getTransaction().begin();
                em.merge(etudiant);
                em.getTransaction().commit();

                System.out.println("🎉 COMPTE ACTIVÉ AVEC SUCCÈS !");

                response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp?success=true");
            } else {
                System.out.println("❌ Code incorrect. Attendu: " + etudiant.getCodeValidation() + " | Reçu: " + codeSaisi);
                request.setAttribute("erreur", "Code incorrect. Code attendu : " + etudiant.getCodeValidation());
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/pages/validationInscription.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}