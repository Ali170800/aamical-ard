package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            LocalDate today = LocalDate.now();

            // Mois précédent (celui qui doit être payé)
            int moisPrecedent = today.getMonthValue() - 1;
            int annee = today.getYear();

            if (moisPrecedent == 0) {
                moisPrecedent = 12;
                annee--;
            }

            // Nom du mois en français
            String nomMois = LocalDate.of(annee, moisPrecedent, 1)
                    .getMonth()
                    .getDisplayName(TextStyle.FULL, Locale.FRANCE);

            EtudiantDAO etudiantDAO = new EtudiantDAO(em);

            List<Etudiant> etudiantsEnRetard = em.createQuery(
                            "SELECT DISTINCT le.etudiant FROM LogementEtudiant le " +
                                    "WHERE le.etudiant.id NOT IN (" +
                                    "   SELECT p.logementEtudiant.etudiant.id FROM PaiementLogement p " +
                                    "   WHERE p.mois = :mois AND p.annee = :annee AND p.statut = 'PAYE'" +
                                    ")", Etudiant.class)
                    .setParameter("mois", moisPrecedent)
                    .setParameter("annee", annee)
                    .getResultList();

            int nombre = etudiantsEnRetard.size();

            request.setAttribute("etudiantsEnRetard", etudiantsEnRetard);
            request.setAttribute("nombreEnRetard", nombre);
            request.setAttribute("nomMois", nomMois);
            request.setAttribute("annee", annee);

            // Pour le badge dans le header
            request.getSession().setAttribute("nombreNotifications", nombre);

            request.getRequestDispatcher("/pages/notifications.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }
}