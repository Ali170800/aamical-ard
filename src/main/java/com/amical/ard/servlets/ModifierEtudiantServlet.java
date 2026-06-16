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

@WebServlet("/modifierEtudiant")
public class ModifierEtudiantServlet extends HttpServlet {

    private final EtudiantService etudiantService = new EtudiantService(null);

    // Afficher le formulaire de modification
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            etudiantService.setEntityManager(EntityManagerHelper.getEntityManager());

            Etudiant etudiant = etudiantService.trouverParId(id);

            if (etudiant != null) {
                request.setAttribute("etudiant", etudiant);
                request.getRequestDispatcher("/pages/modifierEtudiant.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Étudiant non trouvé avec l'ID: " + id);
                response.sendRedirect(request.getContextPath() + "/etudiants");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur de chargement : " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/etudiants");
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    // Traiter la modification
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            etudiantService.setEntityManager(EntityManagerHelper.getEntityManager());

            Etudiant etudiant = etudiantService.trouverParId(id);

            if (etudiant != null) {
                // Mise à jour des champs
                etudiant.setNom(request.getParameter("nom"));
                etudiant.setPrenom(request.getParameter("prenom"));
                etudiant.setSexe(request.getParameter("sexe"));
                etudiant.setFiliere(request.getParameter("filiere"));
                etudiant.setNiveau(request.getParameter("niveau"));
                etudiant.setAnneeUniversitaire(request.getParameter("anneeUniversitaire"));
                etudiant.setTelephone(request.getParameter("telephone"));
                etudiant.setNumeroUrgence(request.getParameter("numeroUrgence"));
                etudiant.setPathologie(request.getParameter("pathologie"));
                etudiant.setAdresse(request.getParameter("adresse"));
                etudiant.setEmail(request.getParameter("email"));

                // Transaction contrôlée par EntityManagerHelper
                EntityManagerHelper.beginTransaction();
                boolean success = etudiantService.mettreAJourEtudiant(etudiant);
                EntityManagerHelper.commit();

                if (success) {
                    request.getSession().setAttribute("success", "Étudiant modifié avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Échec de la modification.");
                }
            }
        } catch (Exception e) {
            EntityManagerHelper.rollback();
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors de la modification : " + e.getMessage());
        } finally {
            EntityManagerHelper.closeEntityManager();
        }

        response.sendRedirect(request.getContextPath() + "/etudiants");
    }
}