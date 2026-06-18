package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/modifierPaiement")
public class ModifierPaiementServlet extends HttpServlet {

    // On utilise une Factory statique pour ne pas créer de factory à chaque requête
    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            PaiementLogement paiement = dao.trouverParId(id);

            if (paiement == null) {
                request.getSession().setAttribute("error", "Paiement introuvable.");
                response.sendRedirect("listePaiements");
                return;
            }

            request.setAttribute("paiement", paiement);
            request.getRequestDispatcher("/pages/ModifierPaiement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors du chargement.");
            response.sendRedirect("listePaiements");
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = getEntityManager();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            double montant = Double.parseDouble(request.getParameter("montant"));
            int mois = Integer.parseInt(request.getParameter("mois"));
            int annee = Integer.parseInt(request.getParameter("annee"));
            StatutPaiement statut = StatutPaiement.valueOf(request.getParameter("statut"));

            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            PaiementLogement paiement = dao.trouverParId(id);

            if (paiement == null) {
                request.getSession().setAttribute("error", "Paiement introuvable.");
                response.sendRedirect("listePaiements");
                return;
            }

            // Vérification doublon
            Long etudiantId = paiement.getLogementEtudiant().getEtudiant().getId();
            if (dao.existePaiementPourModification(id, etudiantId, mois, annee)) {
                request.getSession().setAttribute("error", "Doublon détecté pour cette période.");
                response.sendRedirect("modifierPaiement?id=" + id);
                return;
            }

            // Mise à jour des valeurs
            paiement.setMontant(montant);
            paiement.setMois(mois);
            paiement.setAnnee(annee);
            paiement.setStatut(statut);

            // Persistance
            dao.mettreAJour(paiement);

            // Log
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
            if (utilisateur != null) {
                new ActionLogDAO(em).enregistrerAction(
                        utilisateur.getId(),
                        utilisateur.getPrenom() + " " + utilisateur.getNom(),
                        utilisateur.getRole(),
                        "Modification paiement",
                        "Paiement ID=" + id + " modifié."
                );
            }

            request.getSession().setAttribute("success", "Paiement modifié avec succès.");
            response.sendRedirect("listePaiements");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors de la modification : " + e.getMessage());
            response.sendRedirect("listePaiements");
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}