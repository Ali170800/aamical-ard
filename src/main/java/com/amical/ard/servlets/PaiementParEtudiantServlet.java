package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/paiement-par-etudiant")
public class PaiementParEtudiantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Vérification de session (important pour ADMIN et PCS)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateurConnecte") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            PaiementLogementDAO paiementDAO = new PaiementLogementDAO(em);

            // Récupération des étudiants logés
            List<Etudiant> etudiantsLoges = etudiantDAO.findEtudiantsAvecLogement();
            request.setAttribute("etudiantsLoges", etudiantsLoges);

            // Si un étudiant est sélectionné
            String idParam = request.getParameter("etudiantId");
            if (idParam != null && !idParam.trim().isEmpty()) {
                try {
                    Long etudiantId = Long.parseLong(idParam.trim());

                    Etudiant etudiant = etudiantDAO.trouverParId(etudiantId);
                    List<PaiementLogement> paiements = paiementDAO.findByEtudiantId(etudiantId);

                    request.setAttribute("etudiantTrouve", etudiant);
                    request.setAttribute("paiementsTrouves", paiements);

                } catch (NumberFormatException e) {
                    request.setAttribute("erreur", "ID étudiant invalide.");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("erreur", "Impossible de charger les paiements de cet étudiant.");
                }
            }

            // Forward vers la page
            request.getRequestDispatcher("/pages/recherchePaiementEtudiant.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur serveur lors du chargement des données.");
            request.getRequestDispatcher("/pages/recherchePaiementEtudiant.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}