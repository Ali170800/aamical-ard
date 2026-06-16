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
import java.time.LocalDateTime;

@WebServlet("/etudiant/inscrire-caravane")
public class InscrireCaravaneEtudiantServlet extends HttpServlet {

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

            if (participantDAO.estDejaInscrit(etudiant.getEmail(), caravaneId.longValue())) {
                session.setAttribute("erreur", "Vous êtes déjà inscrit à cette caravane.");
            } else {
                // Générer numéro de chaise
                int nombreActuel = participantDAO.compterParticipants(caravaneId);
                String numeroChaise = "Chaise " + (nombreActuel + 1);

                // Créer la participation
                ParticipantCaravane p = new ParticipantCaravane();
                p.setNom(etudiant.getNom());
                p.setPrenom(etudiant.getPrenom());
                p.setEmail(etudiant.getEmail());
                p.setCaravane(caravane);
                p.setMontant(caravane.getMontant());
                p.setMontantPaye(0);
                p.setStatutPaiement(StatutPaiement.Non_Paye);
                p.setNumeroChaise(numeroChaise);

                // ✅ DATE ET HEURE DE L'INSCRIPTION (CORRECTION)
                p.setDateInscription(LocalDateTime.now());

                participantDAO.ajouter(p);

                // Envoi email
                envoyerRecuParEmail(etudiant, caravane, numeroChaise);

                // Redirection vers la page de reçu
                session.setAttribute("recuInscription", p);
                session.setAttribute("caravaneRecu", caravane);

                response.sendRedirect(request.getContextPath() + "/pages/etudiant/recu-caravane.jsp");
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            session.setAttribute("erreur", "Erreur lors de l'inscription.");
            response.sendRedirect(request.getContextPath() + "/etudiant/caravanes");
        } finally {
            em.close();
        }
    }

    private void envoyerRecuParEmail(Etudiant etudiant, Caravane caravane, String numeroChaise) {
        try {
            String sujet = "✅ Reçu d'Inscription - " + caravane.getNom();
            String message = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() + ",\n\n"
                    + "Votre inscription est confirmée !\n\n"
                    + "📋 DÉTAILS :\n"
                    + "• Caravane : " + caravane.getNom() + "\n"
                    + "• Date : " + caravane.getDate() + "\n"
                    + "• Montant : " + caravane.getMontant() + " FCFA\n"
                    + "• Numéro de Chaise : " + numeroChaise + "\n\n"
                    + "Conservez ce reçu.\n\nL'équipe ARD";

            emailService.envoyerEmail(etudiant.getEmail(), sujet, message);
        } catch (Exception e) {
            System.err.println("Erreur email : " + e.getMessage());
        }
    }
}