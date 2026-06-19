package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.entites.Appartement;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.LogementEtudiant;
import com.amical.ard.services.EmailService;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ajouter-logement")
public class AjouterLogementServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] etudiantIdsStr = request.getParameterValues("etudiantIds");
        String appartementIdStr = request.getParameter("appartementId");

        int successCount = 0;
        int skippedCount = 0;

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            AppartementDAO appartementDAO = new AppartementDAO(em);
            LogementEtudiantDAO logementDAO = new LogementEtudiantDAO(em);
            ActionLogDAO actionLogDAO = new ActionLogDAO(em);

            // Récupérer l'utilisateur connecté pour la traçabilité
            HttpSession session = request.getSession();
            com.amical.ard.entites.Utilisateur utilisateurConnecte =
                    (com.amical.ard.entites.Utilisateur) session.getAttribute("utilisateurConnecte");

            // Validation
            if (etudiantIdsStr == null || etudiantIdsStr.length == 0) {
                request.getSession().setAttribute("error", "❌ Veuillez sélectionner au moins un étudiant.");
                response.sendRedirect(request.getContextPath() + "/formulaire-logement");
                return;
            }

            if (appartementIdStr == null || appartementIdStr.isEmpty()) {
                request.getSession().setAttribute("error", "❌ Veuillez sélectionner un appartement.");
                response.sendRedirect(request.getContextPath() + "/formulaire-logement");
                return;
            }

            Integer appartementId = Integer.parseInt(appartementIdStr);
            Appartement appartement = appartementDAO.trouverParId(appartementId);

            if (appartement == null) {
                request.getSession().setAttribute("error", "❌ Appartement introuvable.");
                response.sendRedirect(request.getContextPath() + "/formulaire-logement");
                return;
            }

            EntityManagerHelper.beginTransaction();

            for (String idStr : etudiantIdsStr) {
                try {
                    Long etudiantId = Long.parseLong(idStr);
                    Etudiant etudiant = etudiantDAO.trouverParId(etudiantId);

                    if (etudiant == null) continue;

                    if (logementDAO.trouverParEtudiantId(etudiantId) != null) {
                        skippedCount++;
                        continue;
                    }

                    LogementEtudiant logement = new LogementEtudiant();
                    logement.setEtudiant(etudiant);
                    logement.setAppartement(appartement);

                    logementDAO.ajouter(logement);

                    // ====================== TRAÇABILITÉ ======================
                    if (utilisateurConnecte != null) {
                        actionLogDAO.enregistrerAction(
                                utilisateurConnecte.getId(),
                                utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom(),
                                utilisateurConnecte.getRole(),
                                "Ajout de logement",
                                "Logement ajouté pour " + etudiant.getPrenom() + " " + etudiant.getNom()
                                        + " dans " + appartement.getNomAppartement()
                        );
                    }

                    // Envoi d'email
                    if (etudiant.getEmail() != null && !etudiant.getEmail().isEmpty()) {
                        String sujet = "Confirmation de logement";
                        String contenu = "Bonjour " + etudiant.getPrenom() + ",\n\n" +
                                "Vous avez été logé(e) dans l'appartement : " + appartement.getNomAppartement() + ".\n" +
                                "Adresse: " + (appartement.getDescription() != null ? appartement.getDescription() : "") + "\n\n" +
                                "Cordialement,\nL'équipe de l'Amicale.";

                        emailService.envoyerEmail(etudiant.getEmail(), sujet, contenu);
                    }

                    successCount++;

                } catch (Exception e) {
                    System.err.println("Erreur pour étudiant ID=" + idStr + ": " + e.getMessage());
                }
            }

            EntityManagerHelper.commit();

            String message = successCount + " étudiant(s) ont été logés avec succès dans " + appartement.getNomAppartement() + " !";
            if (skippedCount > 0) {
                message += " (" + skippedCount + " déjà logé(s) ignoré(s))";
            }

            request.getSession().setAttribute("success", message);
            response.sendRedirect(request.getContextPath() + "/liste-logements");

        } catch (Exception e) {
            EntityManagerHelper.rollback();
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur lors de l'ajout des logements.");
            response.sendRedirect(request.getContextPath() + "/formulaire-logement");
        }
    }
}