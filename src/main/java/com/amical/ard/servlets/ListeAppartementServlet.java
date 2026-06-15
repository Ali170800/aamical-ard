package com.amical.ard.servlets;

import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.entites.Appartement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/listeAppartements")
public class ListeAppartementServlet extends HttpServlet {

    private AppartementDAO appartementDAO;

    @Override
    public void init() throws ServletException {
        EntityManager em = Persistence.createEntityManagerFactory("amicalePU").createEntityManager();
        appartementDAO = new AppartementDAO(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Charger tous les appartements
        List<Appartement> appartements = appartementDAO.listerTous();

        // Les transmettre à la JSP
        request.setAttribute("appartements", appartements);
        request.getRequestDispatcher("/pages/listeAppartements.jsp").forward(request, response);
    }
}