package com.amical.ard.servlets;

import com.amical.ard.dao.VoteElectionDAO;
import com.amical.ard.dao.ElectionDAO;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/etudiant/voter")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession();

        Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");
        if (etudiant == null) {
            response.setStatus(403);
            response.getWriter().write("{\"success\": false, \"message\": \"Connectez-vous pour voter.\"}");
            return;
        }

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            Long electionId = Long.parseLong(request.getParameter("electionId"));
            Long candidatId = Long.parseLong(request.getParameter("candidatId"));

            ElectionDAO eDao = new ElectionDAO(em);
            Election election = eDao.trouverParId(electionId);

            if (election == null || LocalDateTime.now().isAfter(election.getDateFin())) {
                response.getWriter().write("{\"success\": false, \"message\": \"Scrutin fermé.\"}");
                return;
            }

            VoteElectionDAO vDao = new VoteElectionDAO(em);
            em.getTransaction().begin();
            boolean voteEffectue = vDao.voter(electionId, candidatId, etudiant.getId());
            em.getTransaction().commit();

            if (voteEffectue) {
                response.getWriter().write("{\"success\": true, \"message\": \"Vote enregistré !\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Vous avez déjà voté.\"}");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"success\": false, \"message\": \"Erreur serveur.\"}");
        } finally {
            em.close();
        }
    }
}