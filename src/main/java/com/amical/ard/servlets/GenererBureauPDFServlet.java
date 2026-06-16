package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.utils.JpaUtil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/bureau/pdf")
public class GenererBureauPDFServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String annee = request.getParameter("annee");

        if (annee == null || annee.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bureau/selectionner");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        // Récupérer les membres du bureau pour cette année
        List<Bureau> membres = em.createQuery(
                "SELECT b FROM Bureau b WHERE b.anneeMandat = :annee", Bureau.class)
                .setParameter("annee", annee)
                .getResultList();

        em.close();

        // Configuration du PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Bureau_" + annee + ".pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Titre du document
            Font titreFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph titre = new Paragraph("Membres du Bureau - Mandat " + annee, titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(20);
            document.add(titre);

            // Tableau des membres
            PdfPTable table = new PdfPTable(6); // 6 colonnes
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 20, 20, 20, 25, 15});

            // En-têtes
            String[] headers = {"ID", "Nom", "Prénom", "Rôle", "Email", "Téléphone"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Contenu
            for (Bureau membre : membres) {
                table.addCell(String.valueOf(membre.getId()));
                table.addCell(membre.getNom());
                table.addCell(membre.getPrenom());
                table.addCell(membre.getRoleBureau());
                table.addCell(membre.getEmail() != null ? membre.getEmail() : "-");
                table.addCell(membre.getNumero() != null ? membre.getNumero() : "-");
            }

            document.add(table);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}