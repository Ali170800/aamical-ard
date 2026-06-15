package com.amical.ard.servlets;

import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/recherchePaiement")
public class RecherchePaiementServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des critères de recherche
        String moisStr = request.getParameter("mois");
        String anneeStr = request.getParameter("annee");
        String statut = request.getParameter("statut");

        Integer mois = null;
        Integer annee = null;

        // Conversion des chaînes en entiers
        try {
            if (moisStr != null && !moisStr.isEmpty()) {
                mois = Integer.parseInt(moisStr);
            }
            if (anneeStr != null && !anneeStr.isEmpty()) {
                annee = Integer.parseInt(anneeStr);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        EntityManager em = emf.createEntityManager();

        try {
            PaiementLogementDAO dao = new PaiementLogementDAO(em);

            // 🔎 Rechercher les paiements filtrés par les critères
            List<PaiementLogement> resultats = dao.rechercherParCritere(mois, annee, statut);
            request.setAttribute("resultatsFiltrage", resultats);

            // ✅ Charger la liste complète pour affichage en haut
            List<PaiementLogement> paiements = dao.lister();
            request.setAttribute("paiements", paiements);

            // ✅ Mémoriser les critères pour pré-remplir les champs
            request.setAttribute("moisRecherche", moisStr);
            request.setAttribute("anneeRecherche", anneeStr);
            request.setAttribute("statutRecherche", statut);

            // ✅ Redirection vers la JSP de liste des paiements
            request.getRequestDispatcher("/pages/ListePaiements.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors de la recherche des paiements.");
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