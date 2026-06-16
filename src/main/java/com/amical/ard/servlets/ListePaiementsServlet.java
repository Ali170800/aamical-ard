package com.amical.ard.servlets;

import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.List;

@WebServlet("/listePaiements")
public class ListePaiementsServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("amicalePU"); // Nom correct de ta persistence-unit
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();

        try {
            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            List<PaiementLogement> paiements = dao.lister();

            // ✅ Log de vérification (utile uniquement en dev)
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

            // ✅ Envoi des paiements à la JSP
            request.setAttribute("paiements", paiements);
            request.getRequestDispatcher("/pages/ListePaiements.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors du chargement des paiements.");
            request.getRequestDispatcher("/pages/Erreur.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}