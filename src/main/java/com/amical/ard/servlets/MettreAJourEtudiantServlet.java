package com.amical.ard.servlets;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EtudiantService;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mettreAJourEtudiant")
public class MettreAJourEtudiantServlet extends HttpServlet {

    private final EtudiantService etudiantService = new EtudiantService(null);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            etudiantService.setEntityManager(EntityManagerHelper.getEntityManager());

            Etudiant existant = etudiantService.trouverParId(id);
            if (existant == null) {
                request.getSession().setAttribute("error", "Étudiant non trouvé !");
                response.sendRedirect("etudiants");
                return;
            }

            // Mise à jour des champs
            existant.setNom(request.getParameter("nom"));
            existant.setPrenom(request.getParameter("prenom"));
            existant.setSexe(request.getParameter("sexe"));
            existant.setFiliere(request.getParameter("filiere"));
            existant.setNiveau(request.getParameter("niveau"));
            existant.setAnneeUniversitaire(request.getParameter("anneeUniversitaire"));
            existant.setTelephone(request.getParameter("telephone"));
            existant.setNumeroUrgence(request.getParameter("numeroUrgence"));
            existant.setPathologie(request.getParameter("pathologie"));
            existant.setAdresse(request.getParameter("adresse"));
            existant.setEmail(request.getParameter("email"));

            EntityManagerHelper.beginTransaction();
            boolean success = etudiantService.mettreAJourEtudiant(existant);
            EntityManagerHelper.commit();

            if (success) {
                request.getSession().setAttribute("success", "Étudiant mis à jour avec succès !");
            } else {
                request.getSession().setAttribute("error", "Échec de la mise à jour de l'étudiant.");
            }

        } catch (Exception e) {
            EntityManagerHelper.rollback();
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur technique : " + e.getMessage());
        } finally {
            EntityManagerHelper.closeEntityManager();
        }

        response.sendRedirect("etudiants");
    }
}