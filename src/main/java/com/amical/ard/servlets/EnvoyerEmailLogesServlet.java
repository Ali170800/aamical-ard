package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/email/loges")
public class EnvoyerEmailLogesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/emailLoges.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sujet = request.getParameter("sujet");
        String message = request.getParameter("message");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EtudiantDAO etudiantDAO = new EtudiantDAO(em);
        List<Etudiant> etudiantsLoges = etudiantDAO.listerEtudiantsLoges();
        em.close();

        EmailService emailService = new EmailService();
        emailService.envoyerEmailAuxEtudiantsLoges(etudiantsLoges, sujet, message);

        // ✅ Confirmation dans l'application
        request.setAttribute("confirmation", "✅ Les emails ont été envoyés avec succès aux étudiants logés !");
        
        // Retour sur la même page pour afficher le message
        request.getRequestDispatcher("/pages/emailLoges.jsp").forward(request, response);
    }
}