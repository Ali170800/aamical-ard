package com.amical.ard.servlets;

import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/generer-pdf")
public class GenererPdfServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des critères de filtrage
        String moisStr = request.getParameter("mois");
        String anneeStr = request.getParameter("annee");
        String statutStr = request.getParameter("statut");

        Integer mois = (moisStr != null && !moisStr.isEmpty()) ? Integer.parseInt(moisStr) : null;
        Integer annee = (anneeStr != null && !anneeStr.isEmpty()) ? Integer.parseInt(anneeStr) : null;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("amicalePU");
        EntityManager em = emf.createEntityManager();

        try {
            PaiementLogementDAO dao = new PaiementLogementDAO(em);
            List<PaiementLogement> paiements = dao.rechercherParCritere(mois, annee, statutStr);

            // Configuration directe du PDF (sans sauvegarde sur disque)
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Paiements_Recherche_" + System.currentTimeMillis() + ".pdf");

            Document document = new Document(PageSize.A4.rotate()); // Paysage pour mieux afficher les colonnes
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Titre
            Font titreFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph titre = new Paragraph("Résultat de la Recherche des Paiements", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(15);
            document.add(titre);

            // Informations de filtre
            Paragraph filtres = new Paragraph();
            filtres.setSpacingAfter(10);
            if (mois != null) filtres.add("Mois : " + mois + "   ");
            if (annee != null) filtres.add("Année : " + annee + "   ");
            if (statutStr != null && !statutStr.isEmpty()) filtres.add("Statut : " + statutStr);
            document.add(filtres);

            document.add(new Paragraph("Nombre total de paiements : " + paiements.size(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            document.add(new Paragraph(" "));

            // Tableau des paiements
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.2f, 2.2f, 1.6f, 1.4f, 1.4f, 1.8f, 2.5f});

            // En-têtes
            String[] headers = {"Nom", "Prénom", "Montant (FCFA)", "Mois", "Année", "Statut", "Appartement"};
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new BaseColor(0, 102, 204));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }

            // Données
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            for (PaiementLogement p : paiements) {
                table.addCell(new Phrase(p.getEtudiant() != null ? p.getEtudiant().getNom() : "", dataFont));
                table.addCell(new Phrase(p.getEtudiant() != null ? p.getEtudiant().getPrenom() : "", dataFont));
                table.addCell(new Phrase(String.valueOf(p.getMontant()), dataFont));
                table.addCell(new Phrase(String.valueOf(p.getMois()), dataFont));
                table.addCell(new Phrase(String.valueOf(p.getAnnee()), dataFont));
                table.addCell(new Phrase(p.getStatut() != null ? p.getStatut().name() : "N/A", dataFont));
                table.addCell(new Phrase(p.getAppartement() != null ? p.getAppartement().getNomAppartement() : "", dataFont));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().write("<h3>Erreur lors de la génération du PDF :<br>" + e.getMessage() + "</h3>");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}