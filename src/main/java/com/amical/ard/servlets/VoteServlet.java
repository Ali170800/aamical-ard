package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/api/voter")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        // On récupère l'EntityManager préparé par le filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        String eIdStr = request.getParameter("electionId");
        String cIdStr = request.getParameter("candidatId");

        try {
            // 1. Récupérer l'élection pour vérifier la date
            Election election = new ElectionDAO(em).trouverParId(Long.parseLong(eIdStr));

            // SÉCURITÉ : Vérifier si le temps est dépassé
            if (election == null || LocalDateTime.now().isAfter(election.getDateFin())) {
                response.sendRedirect(request.getContextPath() + "/pages/voter.jsp?electionId=" + eIdStr + "&status=ferme");
                return;
            }

            // 2. Procéder au vote
            em.getTransaction().begin();
            VoteElectionDAO vDao = new VoteElectionDAO(em);
            boolean ok = vDao.voter(Long.parseLong(eIdStr), Long.parseLong(cIdStr), etudiant.getId());
            em.getTransaction().commit();

            String statut = ok ? "success" : "deja";
            response.sendRedirect(request.getContextPath() + "/pages/voter.jsp?electionId=" + eIdStr + "&status=" + statut);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/pages/voter.jsp?electionId=" + eIdStr + "&status=error");
        }
        // Le filtre se chargera de em.close() automatiquement
    }
}