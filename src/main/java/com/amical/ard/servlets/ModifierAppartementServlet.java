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

@WebServlet("/modifierAppartement")
public class ModifierAppartementServlet extends HttpServlet {

    private AppartementDAO appartementDAO;

    @Override
    public void init() throws ServletException {
        EntityManager em = Persistence
                .createEntityManagerFactory("amicalePU")
                .createEntityManager();
        appartementDAO = new AppartementDAO(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ CORRECTION : Integer au lieu de Long
        Integer id = Integer.parseInt(request.getParameter("id"));

        Appartement appartement = appartementDAO.trouverParId(id);

        request.setAttribute("appartement", appartement);
        request.getRequestDispatcher("/pages/modifierAppartement.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ CORRECTION : Integer au lieu de Long
        Integer id = Integer.parseInt(request.getParameter("id"));

        String nomAppartement = request.getParameter("nomAppartement");
        String description = request.getParameter("description");

        // ✅ Récupération + mise à jour
        Appartement appartement = appartementDAO.trouverParId(id);

        if (appartement != null) {
            appartement.setNomAppartement(nomAppartement);
            appartement.setDescription(description);

            appartementDAO.mettreAJour(appartement);
        }

        // ✅ Recharger la liste
        List<Appartement> appartements = appartementDAO.listerTous();

        request.setAttribute("appartements", appartements);
        request.getRequestDispatcher("/pages/listeAppartements.jsp")
                .forward(request, response);
    }
}