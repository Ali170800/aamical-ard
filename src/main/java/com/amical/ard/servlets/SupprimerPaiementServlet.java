package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/supprimerPaiement")
public class SupprimerPaiementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Utilisation de l'EntityManager géré par le Filtre
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            PaiementLogement paiement = dao.trouverParId(id);

            if (paiement != null) {
                // ==========================
                // INFOS AVANT SUPPRESSION
                // ==========================
                String nomEtudiant = paiement.getLogementEtudiant().getEtudiant().getPrenom()
                        + " " + paiement.getLogementEtudiant().getEtudiant().getNom();
                int mois = paiement.getMois();
                int annee = paiement.getAnnee();
                double montant = paiement.getMontant();

                // ==========================
                // SUPPRESSION
                // ==========================
                em.remove(em.contains(paiement) ? paiement : em.merge(paiement));

                // ==========================
                // UTILISATEUR CONNECTÉ
                // ==========================
                HttpSession session = request.getSession();
                Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");

                // ==========================
                // LOG ACTION
                // ==========================
                if (utilisateur != null) {
                    ActionLogDAO actionLogDAO = new ActionLogDAO(em);
                    String details = "Paiement supprimé | Étudiant : " + nomEtudiant
                            + " | Période : " + mois + "/" + annee
                            + " | Montant : " + montant + " FCFA";

                    actionLogDAO.enregistrerAction(
                            utilisateur.getId(),
                            utilisateur.getPrenom() + " " + utilisateur.getNom(),
                            utilisateur.getRole(),
                            "Suppression de paiement",
                            details
                    );
                }

                request.getSession().setAttribute("success", "Paiement supprimé avec succès.");
            } else {
                request.getSession().setAttribute("erreur", "Paiement introuvable.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("erreur", "Erreur lors de la suppression.");
        }
        // Pas de finally { em.close() } ici : le Filtre fermera la connexion pour vous.

        response.sendRedirect(request.getContextPath() + "/listePaiements");
    }
}