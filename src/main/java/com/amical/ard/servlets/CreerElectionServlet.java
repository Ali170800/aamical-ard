package com.amical.ard.servlets;

import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/admin/creer-election")
public class CreerElectionServlet extends HttpServlet {

    // AJOUTEZ CETTE MÉTHODE POUR RÉGLER L'ERREUR 405
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/creer-election.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String titre = request.getParameter("titre");
        String[] noms = request.getParameterValues("nom");
        String[] prenoms = request.getParameterValues("prenom");
        LocalDateTime debut = LocalDateTime.parse(request.getParameter("dateDebut"));
        LocalDateTime fin = LocalDateTime.parse(request.getParameter("dateFin"));

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            em.getTransaction().begin();
            Election e = new Election();
            e.setTitre(titre);
            e.setDateDebut(debut);
            e.setDateFin(fin);
            em.persist(e);

            if (noms != null) {
                for (int i = 0; i < noms.length; i++) {
                    CandidatElection c = new CandidatElection();
                    c.setNom(noms[i]);
                    c.setPrenom(prenoms[i]);
                    c.setElection(e);
                    em.persist(c);
                }
            }
            em.getTransaction().commit();
            response.sendRedirect(request.getContextPath() + "/pages/dashboard-elections");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.sendRedirect(request.getContextPath() + "/pages/creer-election?error=1");
        } finally {
            em.close();
        }
    }
}