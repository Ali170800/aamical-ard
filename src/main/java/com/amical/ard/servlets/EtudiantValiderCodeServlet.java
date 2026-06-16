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

@WebServlet("/etudiant/verifier-code")
public class EtudiantValiderCodeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String codeSaisi = request.getParameter("code");

        if (email == null || codeSaisi == null) {
            request.setAttribute("erreur", "Email et code sont obligatoires.");
            request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            EtudiantDAO dao = new EtudiantDAO(em);

            List<Etudiant> list = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(TRIM(e.email)) = :email", Etudiant.class)
                    .setParameter("email", email)
                    .getResultList();

            if (list.isEmpty()) {
                request.setAttribute("erreur", "Compte introuvable.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
                return;
            }

            Etudiant etudiant = list.get(0);

            if (!codeSaisi.equalsIgnoreCase(etudiant.getCodeValidation())) {
                request.setAttribute("erreur", "Code incorrect.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
                return;
            }

            // Code correct → on passe à la création du mot de passe
            request.setAttribute("email", email);
            request.getRequestDispatcher("/pages/creationMotDePasse.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique.");
            request.getRequestDispatcher("/pages/verificationCode.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}