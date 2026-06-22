package com.amical.ard.servlets;

import com.amical.ard.dao.ElectionDAO;
import com.amical.ard.entites.Election;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pages/dashboard-elections")
public class DashboardElectionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();
        ElectionDAO electionDAO = new ElectionDAO(em);

        try {
            List<Election> listeElections = electionDAO.listerTout();

            for (Election e : listeElections) {
                long nbVotes = electionDAO.compterVotes(e.getId());
                long totalEtudiants = electionDAO.compterTotalEtudiants();

                e.setNbVotes(nbVotes);
                e.setTotalEtudiants(totalEtudiants);

                double taux = (totalEtudiants > 0) ? (nbVotes * 100.0 / totalEtudiants) : 0;
                e.setTauxParticipation(Math.round(taux * 10) / 10.0);
            }

            request.setAttribute("listeElections", listeElections);
            request.getRequestDispatcher("/pages/dashboard-elections.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}