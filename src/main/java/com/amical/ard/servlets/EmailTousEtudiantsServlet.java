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

@WebServlet("/emails/etudiants")
public class EmailTousEtudiantsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = null;

        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO dao = new EtudiantDAO(em);

            List<Etudiant> etudiants = dao.listerTous(); // ✅ CORRIGÉ

            req.setAttribute("destCount",
                    etudiants != null ? etudiants.size() : 0);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        req.getRequestDispatcher("/pages/emailEtudiants.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sujet = req.getParameter("sujet");
        String message = req.getParameter("message");

        if (sujet == null || sujet.isBlank() || message == null || message.isBlank()) {
            req.setAttribute("erreur", "Sujet et message sont obligatoires.");
            doGet(req, resp);
            return;
        }

        EntityManager em = null;

        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO dao = new EtudiantDAO(em);

            List<Etudiant> etudiants = dao.listerTous(); // ✅ CORRIGÉ

            EmailService emailService = new EmailService();

            for (Etudiant e : etudiants) {
                if (e.getEmail() != null && !e.getEmail().isEmpty()) {
                    emailService.envoyerEmail(
                            e.getEmail(),
                            sujet,
                            message
                    );
                }
            }

            req.setAttribute("success", "E-mails envoyés à tous les étudiants.");
            req.setAttribute("destCount", etudiants.size());

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erreur", "Erreur lors de l’envoi des e-mails.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        req.getRequestDispatcher("/pages/emailEtudiants.jsp")
                .forward(req, resp);
    }
}