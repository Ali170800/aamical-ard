package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

@WebServlet("/admin/resultats")
public class ResultatsAdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/tableau-bord");
            return;
        }

        // Récupération de l'EntityManager injecté par le filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        long electionId = Long.parseLong(idStr);
        Election election = new ElectionDAO(em).trouverParId(electionId);

        // 1. Calculs des votes
        VoteElectionDAO vDao = new VoteElectionDAO(em);
        long totalVotes = (long) em.createQuery("SELECT COUNT(v) FROM VoteElection v WHERE v.election.id = :eId", Long.class)
                .setParameter("eId", electionId).getSingleResult();

        // 2. Préparation des scores par candidat
        List<Map<String, Object>> resultats = new ArrayList<>();
        if (election != null && election.getCandidats() != null) {
            for (CandidatElection c : election.getCandidats()) {
                long nbVoix = vDao.compterVotesPourCandidat(c.getId(), electionId);
                int pct = (totalVotes > 0) ? (int)((nbVoix * 100) / totalVotes) : 0;

                Map<String, Object> map = new HashMap<>();
                map.put("nom", c.getNom());
                map.put("prenom", c.getPrenom());
                map.put("nbVoix", nbVoix);
                map.put("pourcentage", pct);
                resultats.add(map);
            }
        }

        // 3. Calcul temps restant
        LocalDateTime maintenant = LocalDateTime.now();
        boolean estTermine = election != null && maintenant.isAfter(election.getDateFin());
        String tempsRestant = "Terminé";

        if (election != null && !estTermine) {
            Duration duree = Duration.between(maintenant, election.getDateFin());
            long jours = duree.toDays();
            long heures = duree.toHoursPart();
            long minutes = duree.toMinutesPart();
            tempsRestant = jours + "j " + heures + "h " + minutes + "min";
        }

        // 4. Envoi à la JSP
        request.setAttribute("election", election);
        request.setAttribute("totalVotes", totalVotes);
        request.setAttribute("estTermine", estTermine);
        request.setAttribute("tempsRestant", tempsRestant);
        request.setAttribute("resultatsCandidats", resultats);

        // Pas de em.close() ici : le filtre s'en occupe automatiquement.
        request.getRequestDispatcher("/pages/resultats.jsp").forward(request, response);
    }
}