package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.EntityManagerHelper; // Assurez-vous d'utiliser votre classe helper

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet("/participant/ajouter")
public class AjouterParticipantServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // On récupère l'EM du filtre (via EntityManagerHelper)
        EntityManager em = EntityManagerHelper.getEntityManager();

        EtudiantDAO etudiantDAO = new EtudiantDAO(em);
        CaravaneDAO caravaneDAO = new CaravaneDAO(em);

        request.setAttribute("etudiants", etudiantDAO.listerTous());
        request.setAttribute("caravanes", caravaneDAO.listerTous());

        request.getRequestDispatcher("/pages/ajouterParticipant.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] etudiantIds = request.getParameterValues("etudiantIds");
        String caravaneIdStr = request.getParameter("caravaneId");
        String statutStr = request.getParameter("statut");

        if (etudiantIds == null || etudiantIds.length == 0) {
            request.setAttribute("erreur", "Veuillez sélectionner au moins un étudiant.");
            doGet(request, response);
            return;
        }

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            ParticipantCaravaneDAO participantDAO = new ParticipantCaravaneDAO(em);
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            CaravaneDAO caravaneDAO = new CaravaneDAO(em);

            int caravaneId = Integer.parseInt(caravaneIdStr);
            Caravane caravane = caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) throw new Exception("Caravane introuvable");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int successCount = 0;

            for (String idStr : etudiantIds) {
                try {
                    Long etudiantId = Long.parseLong(idStr);
                    Etudiant etudiant = etudiantDAO.trouverParId(etudiantId);
                    if (etudiant == null) continue;

                    ParticipantCaravane p = new ParticipantCaravane();
                    p.setNom(etudiant.getNom() != null ? etudiant.getNom() : "");
                    p.setPrenom(etudiant.getPrenom() != null ? etudiant.getPrenom() : "");
                    p.setEmail(etudiant.getEmail() != null ? etudiant.getEmail() : "");
                    p.setCaravane(caravane);
                    p.setMontant(caravane.getMontant());

                    if ("PAYE".equalsIgnoreCase(statutStr)) {
                        p.setStatutPaiement(StatutPaiement.PAYE);
                        p.setMontantPaye(caravane.getMontant().intValue());

                        if (etudiant.getEmail() != null && !etudiant.getEmail().trim().isEmpty()) {
                            String message = String.format("Bonjour %s %s,\n\nVotre inscription à la caravane %s est confirmée.\nMontant payé : %.2f FCFA.",
                                    etudiant.getPrenom(), etudiant.getNom(), caravane.getNom(), caravane.getMontant());
                            emailService.envoyerEmail(etudiant.getEmail(), "Confirmation de participation", message);
                        }
                    } else {
                        p.setStatutPaiement(StatutPaiement.Non_Paye);
                        p.setMontantPaye(0);
                    }

                    participantDAO.ajouter(p);
                    successCount++;
                } catch (Exception ex) {
                    System.err.println("Erreur étudiant ID=" + idStr + " : " + ex.getMessage());
                }
            }

            // Gestion des logs
            HttpSession session = request.getSession();
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");
            if (user != null) {
                new ActionLogDAO(em).enregistrerAction(user.getId(), user.getPrenom() + " " + user.getNom(),
                        user.getRole(), "Ajout participants caravane", successCount + " ajoutés à : " + caravane.getNom());
            }

            em.getTransaction().commit();
            session.setAttribute("success", successCount + " participant(s) ajouté(s) avec succès !");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            request.getSession().setAttribute("erreur", "Erreur lors de l'ajout : " + e.getMessage());
        }

        // PAS DE em.close() ici : C'est le filtre qui s'en charge.

        response.sendRedirect(request.getContextPath() + "/participants/selectionner");
    }
}