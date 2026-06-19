package com.amical.ard.servlets;

import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.util.List;

@WebServlet("/listePaiements")
public class ListePaiementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            List<PaiementLogement> paiements = dao.lister();

            // Log de vérification
            for (PaiementLogement p : paiements) {
                if (p.getEtudiant() == null) {
                    System.out.println("❌ Étudiant manquant pour paiement ID: " + p.getId());
                }
                if (p.getLogementEtudiant() == null) {
                    System.out.println("❌ LogementEtudiant manquant pour paiement ID: " + p.getId());
                } else if (p.getLogementEtudiant().getAppartement() == null) {
                    System.out.println("❌ Appartement manquant pour paiement ID: " + p.getId());
                }
            }

            request.setAttribute("paiements", paiements);
            request.getRequestDispatcher("/pages/ListePaiements.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors du chargement des paiements.");
            request.getRequestDispatcher("/pages/Erreur.jsp")
                    .forward(request, response);

        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }
}