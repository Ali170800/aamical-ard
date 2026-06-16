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

    private final EtudiantService etudiantService = new EtudiantService(null);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            etudiantService.setEntityManager(EntityManagerHelper.getEntityManager());

            EntityManagerHelper.beginTransaction();
            boolean success = etudiantService.supprimerEtudiant(id);
            EntityManagerHelper.commit();

            if (success) {
                request.getSession().setAttribute("success", "Étudiant supprimé avec succès !");
            } else {
                request.getSession().setAttribute("error", "Étudiant non trouvé ou impossible à supprimer.");
            }

        } catch (Exception e) {
            EntityManagerHelper.rollback();
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        } finally {
            EntityManagerHelper.closeEntityManager();
        }

        response.sendRedirect(request.getContextPath() + "/etudiants");
    }
}