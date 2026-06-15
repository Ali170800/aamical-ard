package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.entites.Activite;

import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/modifierActivite")
public class ModifierActiviteServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = emf.createEntityManager();
        ActiviteDAO activiteDAO = new ActiviteDAO(em);
        Activite activite = activiteDAO.trouverParId(id);
        em.close();

        request.setAttribute("activite", activite);
        request.getRequestDispatcher("pages/ModifierActivite.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String lieu = request.getParameter("lieu");
        String dateStr = request.getParameter("dateActivite");

        EntityManager em = null;

        try {
            Date dateActivite = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            em = emf.createEntityManager();
            ActiviteDAO activiteDAO = new ActiviteDAO(em);

            em.getTransaction().begin();

            // Récupération de l'activité à modifier
            Activite activite = activiteDAO.trouverParId(id);
            activite.setNom(nom);
            activite.setLieu(lieu);
            activite.setDateActivite(dateActivite);

            // 🔥 Important : merge pour assurer que les changements soient pris en compte
            em.merge(activite);

            em.getTransaction().commit();

            // Redirection vers la liste (rechargée avec les nouvelles données)
            response.sendRedirect("listeActivites");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            request.setAttribute("erreur", "Erreur de modification : " + e.getMessage());
            request.getRequestDispatcher("pages/ModifierActivite.jsp").forward(request, response);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) emf.close();
    }
}