package com.amical.ard.servlets;

import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/resultat")
public class ResultatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Long electionId = Long.parseLong(request.getParameter("electionId"));
            EntityManager em = EntityManagerHelper.getEntityManager();

            Long totalVotes = em.createQuery("SELECT COUNT(v) FROM VoteElection v WHERE v.election.id = :eId", Long.class)
                    .setParameter("eId", electionId).getSingleResult();

            List<Object[]> stats = em.createQuery(
                            "SELECT c.id, COUNT(v.id) FROM CandidatElection c LEFT JOIN VoteElection v ON v.candidat.id = c.id " +
                                    "WHERE c.election.id = :eId GROUP BY c.id ORDER BY COUNT(v.id) DESC", Object[].class)
                    .setParameter("eId", electionId).getResultList();

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < stats.size(); i++) {
                Object[] row = stats.get(i);
                double pct = (totalVotes > 0) ? ((double)(Long)row[1] / totalVotes) * 100 : 0.0;
                json.append("{\"id\":").append(row[0]).append(",\"votes\":").append(row[1])
                        .append(",\"pct\":").append(String.format("%.1f", pct).replace(",", ".")).append("}");
                if (i < stats.size() - 1) json.append(",");
            }
            json.append("]");
            response.getWriter().write(json.toString());
            em.close();
        } catch (Exception e) {
            response.getWriter().write("[]");
        }
    }
}