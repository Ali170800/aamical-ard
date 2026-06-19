package com.amical.ard.servlets;

import com.amical.ard.services.EtudiantService;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/supprimerEtudiant")
public class SupprimerEtudiantServlet extends HttpServlet {

    // On initialise le service directement avec l'EntityManager du contexte
    private final EtudiantService etudiantService = new EtudiantService(EntityManagerHelper.getEntityManager());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            // Le filtre gère déjà la transaction, on exécute simplement la logique métier
            boolean success = etudiantService.supprimerEtudiant(id);

            if (success) {
                request.getSession().setAttribute("success", "Étudiant supprimé avec succès !");
            } else {
                request.getSession().setAttribute("error", "Étudiant non trouvé ou impossible à supprimer.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        // finally { EntityManagerHelper.closeEntityManager(); } est supprimé : géré par le filtre

        response.sendRedirect(request.getContextPath() + "/etudiants");
    }
}