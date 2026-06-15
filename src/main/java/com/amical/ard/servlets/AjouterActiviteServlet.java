package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.entites.Activite;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ajouterActivite")
public class AjouterActiviteServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    // 🔹 Ajout du doGet() pour afficher le formulaire
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("pages/AjouterActivite.jsp").forward(request, response);
    }

    // 🔹 Traitement du formulaire après soumission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String lieu = request.getParameter("lieu");
        String dateStr = request.getParameter("dateActivite");

        try {
            Date dateActivite = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            EntityManager em = emf.createEntityManager();
            ActiviteDAO activiteDAO = new ActiviteDAO(em);

            Activite activite = new Activite();
            activite.setNom(nom);
            activite.setLieu(lieu);
            activite.setDateActivite(dateActivite);

            activiteDAO.ajouter(activite);
            em.close();

            // Redirection vers la liste des activités après insertion
            response.sendRedirect("listeActivites");
        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur lors de l’ajout : " + e.getMessage());
            request.getRequestDispatcher("pages/AjouterActivite.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        if (emf != null) emf.close();
    }
}