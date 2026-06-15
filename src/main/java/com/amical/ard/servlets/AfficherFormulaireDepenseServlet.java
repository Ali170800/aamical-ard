package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.entites.Activite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/formulaireDepense")
public class AfficherFormulaireDepenseServlet extends HttpServlet {

    private EntityManager em;

    @Override
    public void init() throws ServletException {
        em = Persistence.createEntityManagerFactory("amicalePU").createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ActiviteDAO activiteDAO = new ActiviteDAO(em);
        List<Activite> activites = activiteDAO.listerTous();

        request.setAttribute("listeActivites", activites);
        request.getRequestDispatcher("/pages/AjouterDepense.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        em.close();
    }
}