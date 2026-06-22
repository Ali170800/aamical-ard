package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/api/voter")
public class VoteServlet extends HttpServlet {

    // Bloque les appels via lien direct (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        response.getWriter().write("{\"success\": false, \"message\": \"Méthode non autorisée.\"}");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession();

        // 1. Vérification session
        Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");
        if (etudiant == null) {
            response.setStatus(403);
            response.getWriter().write("{\"success\": false, \"message\": \"Connectez-vous pour voter.\"}");
            return;
        }

        // 2. Récupération et validation paramètres
        String eIdStr = request.getParameter("electionId");
        String cIdStr = request.getParameter("candidatId");

        if (eIdStr == null || cIdStr == null) {
            response.setStatus(400);
            response.getWriter().write("{\"success\": false, \"message\": \"Paramètres manquants.\"}");
            return;
        }

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            Long electionId = Long.parseLong(eIdStr);
            Long candidatId = Long.parseLong(cIdStr);

            // 3. Vérification élection
            Election election = new ElectionDAO(em).trouverParId(electionId);
            if (election == null || LocalDateTime.now().isAfter(election.getDateFin())) {
                response.getWriter().write("{\"success\": false, \"message\": \"Scrutin fermé ou inexistant.\"}");
                return;
            }

            // 4. Enregistrement du vote
            VoteElectionDAO vDao = new VoteElectionDAO(em);
            em.getTransaction().begin();
            boolean voteEffectue = vDao.voter(electionId, candidatId, etudiant.getId());
            em.getTransaction().commit();

            if (voteEffectue) {
                response.getWriter().write("{\"success\": true, \"message\": \"Vote enregistré avec succès !\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Vous avez déjà voté pour cette élection.\"}");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.setStatus(500);
            response.getWriter().write("{\"success\": false, \"message\": \"Erreur serveur lors de l'enregistrement.\"}");
        } finally {
            em.close();
        }
    }
}