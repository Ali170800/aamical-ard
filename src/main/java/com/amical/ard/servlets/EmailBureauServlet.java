package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/emails/bureau")
public class EmailBureauServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            BureauDAO bureauDAO = new BureauDAO(em);
            List<Bureau> membres = bureauDAO.listerTous();
            req.setAttribute("destCount", membres != null ? membres.size() : 0);
        } finally {
            em.close();
        }
        req.getRequestDispatcher("/pages/emailBureau.jsp").forward(req, resp);
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

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            BureauDAO bureauDAO = new BureauDAO(em);
            List<Bureau> membres = bureauDAO.listerTous();

            EmailService emailService = new EmailService();
            emailService.envoyerEmailAuxMembresBureau(membres, sujet, message);

            req.setAttribute("success", "E‑mails envoyés aux membres du bureau.");
            req.setAttribute("destCount", membres != null ? membres.size() : 0);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erreur", "Erreur lors de l’envoi des e‑mails.");
        } finally {
            em.close();
        }

        req.getRequestDispatcher("/pages/emailBureau.jsp").forward(req, resp);
    }
}