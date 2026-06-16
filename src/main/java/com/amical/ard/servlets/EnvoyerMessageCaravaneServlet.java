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

@WebServlet("/caravane/envoyerMessage")
public class EnvoyerMessageCaravaneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affiche le formulaire
        request.getRequestDispatcher("/pages/envoyerMessage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sujet = request.getParameter("sujet");
        String message = request.getParameter("message");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EtudiantDAO etudiantDAO = new EtudiantDAO(em);

        try {
            List<Etudiant> etudiants = etudiantDAO.listerTous();
            for (Etudiant etu : etudiants) {
                if (etu.getEmail() != null && !etu.getEmail().isEmpty()) {
                	EmailService emailService = new EmailService();
                	emailService.envoyerEmail(etu.getEmail(), sujet, message);
                }
            }
            em.close();
            request.setAttribute("message", "Message envoyé à tous les étudiants.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors de l'envoi du message.");
        }

        request.getRequestDispatcher("/pages/envoyerMessage.jsp").forward(request, response);
    }
}