package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.enums.StatutPaiement;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/etudiant/confirmer-paiement")
public class ConfirmerPaiementCaravaneServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Etudiant etudiant = (Etudiant) (session != null ? session.getAttribute("etudiantConnecte") : null);

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp");
            return;
        }

        String caravaneIdStr = request.getParameter("caravaneId");

        if (caravaneIdStr == null || caravaneIdStr.isEmpty()) {
            session.setAttribute("erreur", "Caravane invalide.");
            response.sendRedirect(request.getContextPath() + "/etudiant/caravanes");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            CaravaneDAO caravaneDAO = new CaravaneDAO(em);
            ParticipantCaravaneDAO participantDAO = new ParticipantCaravaneDAO(em);

            Integer caravaneId = Integer.parseInt(caravaneIdStr);
            Caravane caravane = caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) {
                session.setAttribute("erreur", "Caravane introuvable.");
                response.sendRedirect(request.getContextPath() + "/etudiant/caravanes");
                return;
            }

            // Vérifier si l'étudiant est déjà inscrit
            if (participantDAO.estDejaInscrit(etudiant.getEmail(), caravaneId.longValue())) {
                session.setAttribute("erreur", "Vous êtes déjà inscrit à cette caravane.");
            } else {
                // Générer numéro de chaise unique
                int nombreActuel = participantDAO.compterParticipants(caravaneId);
                String numeroChaise = "C-" + String.format("%03d", nombreActuel + 1);

                // Créer l'inscription
                ParticipantCaravane p = new ParticipantCaravane();
                p.setNom(etudiant.getNom());
                p.setPrenom(etudiant.getPrenom());
                p.setEmail(etudiant.getEmail());
                p.setCaravane(caravane);
                p.setMontant(caravane.getMontant());
                p.setMontantPaye(0); // L'admin pourra le valider plus tard
                p.setStatutPaiement(StatutPaiement.Non_Paye); // En attente de vérification
                p.setSourceInscription("ETUDIANT");
                p.setNumeroChaise(numeroChaise);

                participantDAO.ajouter(p);

                // Envoyer le reçu par email
                envoyerRecu(etudiant, caravane, numeroChaise);

                session.setAttribute("dernierRecu", p);
                session.setAttribute("caravaneRecu", caravane);
                session.setAttribute("success", "Inscription confirmée ! Votre numéro de chaise est : " + numeroChaise);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            session.setAttribute("erreur", "Erreur lors de la confirmation du paiement.");
        } finally {
            em.close();
        }

        response.sendRedirect(request.getContextPath() + "/pages/etudiant/recu-caravane.jsp");
    }

    private void envoyerRecu(Etudiant etudiant, Caravane caravane, String numeroChaise) {
        try {
            String sujet = "✅ Confirmation d'Inscription - " + caravane.getNom();

            String message = """
                    Bonjour %s %s,

                    Nous avons bien enregistré votre confirmation de paiement pour la caravane.

                    📋 DÉTAILS :
                    • Caravane : %s
                    • Date     : %s
                    • Montant  : %s FCFA
                    • Numéro de Chaise : %s

                    Veuillez conserver ce reçu.
                    L'équipe Amicale ARD
                    """.formatted(etudiant.getPrenom(), etudiant.getNom(),
                    caravane.getNom(), caravane.getDate(),
                    caravane.getMontant(), numeroChaise);

            emailService.envoyerEmail(etudiant.getEmail(), sujet, message);
        } catch (Exception e) {
            System.err.println("Erreur envoi email : " + e.getMessage());
        }
    }
}