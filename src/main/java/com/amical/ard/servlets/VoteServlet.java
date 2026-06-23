package com.amical.ard.servlets;

import com.amical.ard.dao.VoteElectionDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/voter")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/pages/voter.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        String eIdStr = request.getParameter("electionId");
        String cIdStr = request.getParameter("candidatId");

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            VoteElectionDAO vDao = new VoteElectionDAO(em);

            boolean ok = vDao.voter(Long.parseLong(eIdStr), Long.parseLong(cIdStr), etudiant.getId());

            // Commit explicite pour garantir que les données sont écrites avant la redirection
            em.getTransaction().commit();

            String statut = ok ? "success" : "deja";
            response.sendRedirect(request.getContextPath() + "/pages/voter.jsp?electionId=" + eIdStr + "&status=" + statut);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.sendRedirect(request.getContextPath() + "/pages/voter.jsp?electionId=" + eIdStr + "&status=error");
        } finally {
            em.close();
        }
    }
}