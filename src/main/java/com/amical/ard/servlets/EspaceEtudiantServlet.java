package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/espace-etudiant")
public class EspaceEtudiantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Récupération sécurisée de l'étudiant connecté
        Etudiant etudiant = null;
        if (session != null) {
            etudiant = (Etudiant) session.getAttribute("etudiantConnecte");
            if (etudiant == null) {
                etudiant = (Etudiant) session.getAttribute("etudiant");
            }
        }

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp");
            return;
        }

        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            PaiementLogementDAO paiementDAO = new PaiementLogementDAO(em);

            // Recharger l'étudiant complet
            Etudiant etudiantComplet = etudiantDAO.trouverParId(etudiant.getId());

            List<PaiementLogement> mesPaiements = paiementDAO.findByEtudiantId(etudiant.getId());

            request.setAttribute("etudiant", etudiantComplet);
            request.setAttribute("mesPaiements", mesPaiements);

            System.out.println("✅ Accès espace étudiant : " + etudiantComplet.getPrenom() + " " + etudiantComplet.getNom());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur de chargement des données.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        request.getRequestDispatcher("/pages/espaceEtudiant.jsp").forward(request, response);
    }
}