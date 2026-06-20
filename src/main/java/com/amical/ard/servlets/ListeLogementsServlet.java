package com.amical.ard.servlets;

import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.entites.LogementEtudiant;
import com.amical.ard.entites.Appartement;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/liste-logements")
public class ListeLogementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // On récupère l'EM, le filtre se chargera de le fermer automatiquement après le forward
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            LogementEtudiantDAO logementDAO = new LogementEtudiantDAO(em);
            AppartementDAO appartementDAO = new AppartementDAO(em);

            List<LogementEtudiant> tousLogements = logementDAO.listerTous();
            List<Appartement> appartements = appartementDAO.listerTous();

            // Récupération des paramètres de filtre
            String appartementIdStr = request.getParameter("appartementId");
            String recherche = request.getParameter("recherche");

            List<LogementEtudiant> logementsFiltres = tousLogements;

            // Filtre par appartement
            if (appartementIdStr != null
                    && !appartementIdStr.isEmpty()
                    && !"tous".equals(appartementIdStr)) {

                Integer appartId = Integer.parseInt(appartementIdStr);

                logementsFiltres = logementsFiltres.stream()
                        .filter(l -> l.getAppartement() != null && l.getAppartement().getId().equals(appartId))
                        .collect(Collectors.toList());
            }

            // Recherche dynamique (nom ou prénom)
            if (recherche != null && !recherche.trim().isEmpty()) {
                String term = recherche.toLowerCase().trim();

                logementsFiltres = logementsFiltres.stream()
                        .filter(l -> {
                            String nom = (l.getEtudiant() != null && l.getEtudiant().getNom() != null)
                                    ? l.getEtudiant().getNom().toLowerCase() : "";
                            String prenom = (l.getEtudiant() != null && l.getEtudiant().getPrenom() != null)
                                    ? l.getEtudiant().getPrenom().toLowerCase() : "";
                            return nom.contains(term) || prenom.contains(term);
                        })
                        .collect(Collectors.toList());
            }

            // Calcul du résumé par appartement
            Map<String, Long> statsParAppartement = tousLogements.stream()
                    .collect(Collectors.groupingBy(
                            l -> (l.getAppartement() != null) ? l.getAppartement().getNomAppartement() : "Inconnu",
                            Collectors.counting()
                    ));

            request.setAttribute("logements", logementsFiltres);
            request.setAttribute("appartements", appartements);
            request.setAttribute("statsParAppartement", statsParAppartement);
            request.setAttribute("recherche", recherche);
            request.setAttribute("appartementIdSelectionne", appartementIdStr);

            request.getRequestDispatcher("/pages/ListeLogements.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors du chargement des logements.");
            request.getRequestDispatcher("/pages/Erreur.jsp").forward(request, response);
        }
        // PLUS DE finally { EntityManagerHelper.closeEntityManager(); } ici
    }
}