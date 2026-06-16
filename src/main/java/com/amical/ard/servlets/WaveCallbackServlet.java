package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.EtudiantDAO;
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

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

@WebServlet("/etudiant/wave/callback")
public class WaveCallbackServlet extends HttpServlet {

    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        String payload = jsonBody.toString();
        System.out.println("🔄 Callback PayDunya/Wave reçu : " + payload);

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            JSONObject json = new JSONObject(payload);
            String status = json.optString("status");
            JSONObject customData = json.optJSONObject("custom_data");

            if (customData == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            Long etudiantId = customData.optLong("etudiant_id");
            Integer caravaneId = customData.optInt("caravane_id");

            if (etudiantId == 0 || caravaneId == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Récupération des entités
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            CaravaneDAO caravaneDAO = new CaravaneDAO(em);
            ParticipantCaravaneDAO participantDAO = new ParticipantCaravaneDAO(em);

            Etudiant etudiant = etudiantDAO.trouverParId(etudiantId);
            Caravane caravane = caravaneDAO.trouverParId(caravaneId);

            if (etudiant == null || caravane == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            em.getTransaction().begin();

            // Vérifier si déjà inscrit
            if (!participantDAO.estDejaInscrit(etudiant.getEmail(), caravaneId.longValue())) {

                // Générer numéro de chaise
                int nombreActuel = participantDAO.compterParticipants(caravaneId);
                String numeroChaise = "C-" + String.format("%03d", nombreActuel + 1);

                ParticipantCaravane p = new ParticipantCaravane();
                p.setNom(etudiant.getNom());
                p.setPrenom(etudiant.getPrenom());
                p.setEmail(etudiant.getEmail());
                p.setCaravane(caravane);
                p.setMontant(caravane.getMontant());
                p.setMontantPaye(caravane.getMontant().intValue());
                p.setStatutPaiement("SUCCEEDED".equalsIgnoreCase(status) ?
                        StatutPaiement.PAYE : StatutPaiement.Non_Paye);
                p.setSourceInscription("ETUDIANT");
                p.setNumeroChaise(numeroChaise);

                participantDAO.ajouter(p);

                // Envoyer le reçu
                envoyerRecuParEmail(etudiant, caravane, numeroChaise, status);

                System.out.println("✅ Inscription créée avec succès via paiement Wave - Chaise: " + numeroChaise);
            }

            em.getTransaction().commit();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            em.close();
        }
    }

    private void envoyerRecuParEmail(Etudiant etudiant, Caravane caravane, String numeroChaise, String status) {
        try {
            String sujet = "✅ Paiement & Inscription Confirmés - " + caravane.getNom();
            String message = String.format("""
                    Bonjour %s %s,

                    Votre paiement a été reçu avec succès !

                    📋 DÉTAILS :
                    • Caravane : %s
                    • Date     : %s
                    • Montant payé : %s FCFA
                    • Statut   : %s
                    • Numéro de Chaise : %s

                    Merci et bon voyage !
                    L'équipe Amicale ARD
                    """,
                    etudiant.getPrenom(), etudiant.getNom(),
                    caravane.getNom(), caravane.getDate(),
                    caravane.getMontant(), status, numeroChaise);

            emailService.envoyerEmail(etudiant.getEmail(), sujet, message);
        } catch (Exception e) {
            System.err.println("Erreur envoi email reçu : " + e.getMessage());
        }
    }
}