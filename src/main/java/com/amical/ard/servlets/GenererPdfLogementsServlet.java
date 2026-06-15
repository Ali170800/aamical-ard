package com.amical.ard.servlets;

import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.entites.Appartement;
import com.amical.ard.entites.LogementEtudiant;
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
import java.util.stream.Collectors;

@WebServlet("/generer-pdf-logements")
public class GenererPdfLogementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appartementIdStr = request.getParameter("appartementId");
        String recherche = request.getParameter("recherche");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("amicalePU");
        EntityManager em = emf.createEntityManager();

        try {
            LogementEtudiantDAO logementDAO = new LogementEtudiantDAO(em);
            AppartementDAO appartementDAO = new AppartementDAO(em);

            List<LogementEtudiant> logements = logementDAO.listerTous();

            String nomAppartementFiltre = "Tous les appartements";

            // Filtre par appartement
            if (appartementIdStr != null && !appartementIdStr.isEmpty() && !"tous".equals(appartementIdStr)) {
                Integer appartId = Integer.parseInt(appartementIdStr);
                Appartement appart = appartementDAO.trouverParId(appartId);

                if (appart != null) {
                    nomAppartementFiltre = appart.getNomAppartement();
                }

                logements = logements.stream()
                        .filter(l -> l.getAppartement().getId().equals(appartId))
                        .collect(Collectors.toList());
            }

            // Filtre par recherche (nom ou prénom)
            if (recherche != null && !recherche.trim().isEmpty()) {
                String term = recherche.toLowerCase().trim();
                logements = logements.stream()
                        .filter(l -> {
                            String nom = l.getEtudiant().getNom() != null ? l.getEtudiant().getNom().toLowerCase() : "";
                            String prenom = l.getEtudiant().getPrenom() != null ? l.getEtudiant().getPrenom().toLowerCase() : "";
                            return nom.contains(term) || prenom.contains(term);
                        })
                        .collect(Collectors.toList());
            }

            // Configuration PDF
            response.setContentType("application/pdf");
            String filename = "Etudiants_Loges";
            if (!"Tous les appartements".equals(nomAppartementFiltre)) {
                filename += "_" + nomAppartementFiltre.replace(" ", "_");
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".pdf");

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Titre principal
            Font titreFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLUE);
            Paragraph titre = new Paragraph("Liste des Étudiants Logés", titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(10);
            document.add(titre);

            // Sous-titre avec filtre
            Font subFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Paragraph sousTitre = new Paragraph("Appartement : " + nomAppartementFiltre, subFont);
            sousTitre.setAlignment(Element.ALIGN_CENTER);
            sousTitre.setSpacingAfter(20);
            document.add(sousTitre);

            // Informations supplémentaires
            if (recherche != null && !recherche.trim().isEmpty()) {
                document.add(new Paragraph("Recherche : \"" + recherche + "\"",
                        FontFactory.getFont(FontFactory.HELVETICA, 12)));
            }

            document.add(new Paragraph("Nombre total d'étudiants : " + logements.size(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            document.add(new Paragraph("Date de génération : " + new java.util.Date(),
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            document.add(new Paragraph(" "));

            // Tableau
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.2f, 2.2f, 1.8f, 2.8f, 2f, 3f});

            // En-têtes
            String[] headers = {"Nom", "Prénom", "Téléphone", "Email", "Appartement", "Adresse"};
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
            for (LogementEtudiant l : logements) {
                table.addCell(new Phrase(l.getEtudiant().getNom() != null ? l.getEtudiant().getNom() : "", dataFont));
                table.addCell(new Phrase(l.getEtudiant().getPrenom() != null ? l.getEtudiant().getPrenom() : "", dataFont));
                table.addCell(new Phrase(l.getEtudiant().getTelephone() != null ? l.getEtudiant().getTelephone() : "", dataFont));
                table.addCell(new Phrase(l.getEtudiant().getEmail() != null ? l.getEtudiant().getEmail() : "", dataFont));
                table.addCell(new Phrase(l.getAppartement().getNomAppartement(), dataFont));
                table.addCell(new Phrase(l.getAppartement().getDescription() != null ? l.getAppartement().getDescription() : "", dataFont));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h3>Erreur lors de la génération du PDF : " + e.getMessage() + "</h3>");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}