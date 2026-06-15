package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/exporterRecherchePDF")
public class ExporterRecherchePDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String searchTerm = request.getParameter("search");   // Nouveau paramètre pour recherche instantanée

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            EtudiantDAO dao = new EtudiantDAO(em);
            List<Etudiant> resultats;

            // Si on vient de la recherche instantanée (paramètre "search")
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                // On simule une recherche par ID ou par nom/prénom
                if (searchTerm.trim().matches("\\d+")) {
                    // C'est un nombre → recherche par ID exact
                    resultats = dao.rechercher(searchTerm.trim(), null, null);
                } else {
                    // C'est du texte → recherche par prénom ou nom
                    resultats = dao.rechercher(null, searchTerm.trim(), null);
                }
            }
            // Sinon, on utilise les paramètres de la recherche avancée (ancienne méthode)
            else {
                resultats = dao.rechercher(id, prenom, telephone);
            }

            if (resultats == null || resultats.isEmpty()) {
                response.getWriter().write("Aucun résultat à exporter.");
                return;
            }

            // Configuration PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=etudiants_recherche.pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Paragraph titre = new Paragraph("Liste des étudiants de l’Amicale de Diourbel",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            String[] headers = {"Nom", "Prénom", "Téléphone", "Sexe", "Filière", "Email"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            for (Etudiant e : resultats) {
                table.addCell(e.getNom() != null ? e.getNom() : "");
                table.addCell(e.getPrenom() != null ? e.getPrenom() : "");
                table.addCell(e.getTelephone() != null ? e.getTelephone() : "");
                table.addCell(e.getSexe() != null ? e.getSexe() : "");
                table.addCell(e.getFiliere() != null ? e.getFiliere() : "");
                table.addCell(e.getEmail() != null ? e.getEmail() : "");
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erreur lors de la génération du PDF.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}