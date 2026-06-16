package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
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

@WebServlet("/generer-pdf-paiements")
public class GenererPdfPaiementsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long etudiantId = Long.parseLong(req.getParameter("etudiantId"));

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EtudiantDAO etudiantDAO = new EtudiantDAO(em);
        PaiementLogementDAO paiementDAO = new PaiementLogementDAO(em);

        try {
            Etudiant etudiant = etudiantDAO.trouverParId(etudiantId);
            List<PaiementLogement> paiements = paiementDAO.listerParEtudiant(etudiantId);

            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=paiements_" + etudiant.getNom() + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            // Titre
            Font titreFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titre = new Paragraph("Liste des Paiements de " + etudiant.getPrenom() + " " + etudiant.getNom(), titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(20);
            document.add(titre);

            // Tableau
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 2, 2, 2});

            table.addCell("Montant");
            table.addCell("Mois");
            table.addCell("Année");
            table.addCell("Statut");

            for (PaiementLogement p : paiements) {
                table.addCell(String.valueOf(p.getMontant()));
                table.addCell(String.valueOf(p.getMois()));
                table.addCell(String.valueOf(p.getAnnee()));

                Font statutFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                String statutTexte = p.getStatut() != null ? p.getStatut().name() : "INCONNU";

                if ("PAYE".equalsIgnoreCase(statutTexte)) {
                    statutFont.setColor(BaseColor.GREEN);
                } else {
                    statutFont.setColor(BaseColor.RED);
                }

                PdfPCell statutCell = new PdfPCell(new Phrase(statutTexte, statutFont));
                table.addCell(statutCell);
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du PDF", e);
        } finally {
            em.close();
        }
    }
}