package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/etudiant/connexion")
public class EtudiantConnexionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        if (email == null || motDePasse == null ||
                email.trim().isEmpty() || motDePasse.trim().isEmpty()) {

            request.setAttribute("erreur", "Email et mot de passe sont obligatoires.");
            request.getRequestDispatcher("/pages/connexionEtudiant.jsp").forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();
            EtudiantDAO dao = new EtudiantDAO(em);

            Etudiant etudiant = dao.trouverParEmailEtMotDePasse(email, motDePasse);

            if (etudiant == null) {
                request.setAttribute("erreur", "Email ou mot de passe incorrect.");
                request.getRequestDispatcher("/pages/connexionEtudiant.jsp").forward(request, response);
                return;
            }

            if (!"ACTIF".equalsIgnoreCase(etudiant.getStatut())) {
                request.setAttribute("erreur", "Votre compte n'est pas encore activé.");
                request.getRequestDispatcher("/pages/connexionEtudiant.jsp").forward(request, response);
                return;
            }

            // ==================== CRÉATION DE SESSION ÉTUDIANT ====================
            HttpSession session = request.getSession(true);
            session.setAttribute("etudiantConnecte", etudiant);
            session.setAttribute("etudiant", etudiant);
            session.setAttribute("userType", "ETUDIANT");
            session.setAttribute("role", "ETUDIANT");

            // Chargement des données utiles
            session.setAttribute("etudiantLogement", dao.getLogementByEtudiant(etudiant.getId()));
            session.setAttribute("mesPaiements", dao.getPaiementsByEtudiant(etudiant.getId()));

            System.out.println("✅ Connexion Étudiant réussie : " + email);

            // Redirection vers l'espace étudiant
            response.sendRedirect(request.getContextPath() + "/espace-etudiant");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur technique lors de la connexion.");
            request.getRequestDispatcher("/pages/connexionEtudiant.jsp").forward(request, response);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}