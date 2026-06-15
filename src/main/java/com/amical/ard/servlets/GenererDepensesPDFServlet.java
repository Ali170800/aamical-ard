package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.dao.DepenseActiviteDAO;
import com.amical.ard.entites.Activite;
import com.amical.ard.entites.DepenseActivite;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/genererPDF")
public class GenererDepensesPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idActivite = Integer.parseInt(request.getParameter("idActivite"));

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        ActiviteDAO activiteDAO = new ActiviteDAO(em);
        DepenseActiviteDAO depenseDAO = new DepenseActiviteDAO(em);

        try {
            Activite activite = activiteDAO.trouverParId(idActivite);
            List<DepenseActivite> depenses = depenseDAO.listerParActivite(idActivite);

            double total = depenses.stream()
                    .map(DepenseActivite::getMontant)
                    .mapToDouble(BigDecimal::doubleValue)
                    .sum();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=depenses_activite_" + idActivite + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Titre et infos de l’activité
            Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Rapport de Dépenses", fontTitre));
            document.add(new Paragraph("Activité : " + activite.getNom(), fontNormal));
            document.add(new Paragraph("Lieu : " + activite.getLieu(), fontNormal));
            document.add(new Paragraph("Date : " + activite.getDateActivite(), fontNormal));
            document.add(new Paragraph(" ")); // Ligne vide

            // Tableau des dépenses
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 2});

            table.addCell("Libellé");
            table.addCell("Montant (FCFA)");

            for (DepenseActivite d : depenses) {
                table.addCell(d.getLibelle());
                table.addCell(d.getMontant().toString());
            }

            // Ligne total
            PdfPCell cellTotal = new PdfPCell(new Phrase("Total"));
            cellTotal.setColspan(1);
            cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellTotal);
            table.addCell(String.format("%.2f", total));

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new IOException(e);
        } finally {
            em.close();
        }
    }
}