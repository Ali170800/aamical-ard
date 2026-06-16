package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.utils.JpaUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/participants/pdf")
public class GenererParticipantsCaravanePdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/participants/selectionner"
            );

            return;
        }

        int caravaneId;

        try {

            caravaneId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            resp.sendRedirect(
                    req.getContextPath()
                            + "/participants/selectionner"
            );

            return;
        }

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            Caravane caravane =
                    caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) {

                resp.sendRedirect(
                        req.getContextPath()
                                + "/participants/selectionner"
                );

                return;
            }

            // ==============================
            // LISTE PARTICIPANTS
            // ==============================

            List<ParticipantCaravane> participants =
                    em.createQuery(
                                    "SELECT p FROM ParticipantCaravane p " +
                                            "WHERE p.caravane.id = :id " +
                                            "ORDER BY p.nom, p.prenom",
                                    ParticipantCaravane.class
                            )
                            .setParameter("id", caravaneId)
                            .getResultList();

            // ==============================
            // CALCULS
            // ==============================

            double montantTotal =
                    participants.stream()
                            .mapToDouble(
                                    p -> p.getMontantPaye() != null
                                            ? p.getMontantPaye()
                                            : 0
                            )
                            .sum();

            int nombreParticipants =
                    participants.size();

            // ==============================
            // CONFIGURATION PDF
            // ==============================

            resp.setContentType("application/pdf");

            String filename =
                    "participants_caravane_"
                            + caravane.getId()
                            + ".pdf";

            resp.setHeader(
                    "Content-Disposition",
                    "attachment; filename=" + filename
            );

            Document doc =
                    new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(
                    doc,
                    resp.getOutputStream()
            );

            doc.open();

            Font titre =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            16
                    );

            Font normal =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            11
                    );

            Font normalBold =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            11
                    );

            // ==============================
            // TITRE
            // ==============================

            Paragraph h1 =
                    new Paragraph(
                            "Participants de la caravane",
                            titre
                    );

            h1.setAlignment(Element.ALIGN_CENTER);
            h1.setSpacingAfter(10);

            doc.add(h1);

            // ==============================
            // INFOS CARAVANE
            // ==============================

            Paragraph infos =
                    new Paragraph(
                            "Caravane : "
                                    + caravane.getNom()
                                    + "\nDate : "
                                    + caravane.getDate()
                                    + "\nMontant fixé : "
                                    + caravane.getMontant()
                                    + " FCFA"
                                    + "\nNombre de participants : "
                                    + nombreParticipants,
                            normal
                    );

            infos.setSpacingAfter(15);

            doc.add(infos);

            // ==============================
            // TABLEAU
            // ==============================

            PdfPTable table =
                    new PdfPTable(8);

            table.setWidthPercentage(100);

            table.setWidths(
                    new float[]{
                            1.0f,
                            2.0f,
                            2.0f,
                            2.0f,
                            2.0f,
                            2.0f,
                            1.8f,
                            1.5f
                    }
            );

            // ==============================
            // HEADERS
            // ==============================

            addHeaderCell(table, "ID");
            addHeaderCell(table, "Nom");
            addHeaderCell(table, "Prénom");
            addHeaderCell(table, "N° Chaise");
            addHeaderCell(table, "Caravane");
            addHeaderCell(table, "Date");
            addHeaderCell(table, "Montant payé");
            addHeaderCell(table, "Statut");

            // ==============================
            // DONNÉES
            // ==============================

            for (ParticipantCaravane p : participants) {

                table.addCell(
                        new Phrase(
                                String.valueOf(p.getId()),
                                normal
                        )
                );

                table.addCell(
                        new Phrase(
                                nullSafe(p.getNom()),
                                normal
                        )
                );

                table.addCell(
                        new Phrase(
                                nullSafe(p.getPrenom()),
                                normal
                        )
                );

                // ✅ NUMÉRO CHAISE

                table.addCell(
                        new Phrase(
                                p.getNumeroChaise() != null
                                        ? p.getNumeroChaise()
                                        : "Non assigné",
                                normal
                        )
                );

                table.addCell(
                        new Phrase(
                                caravane.getNom(),
                                normal
                        )
                );

                table.addCell(
                        new Phrase(
                                String.valueOf(caravane.getDate()),
                                normal
                        )
                );

                table.addCell(
                        new Phrase(
                                String.valueOf(
                                        p.getMontantPaye() != null
                                                ? p.getMontantPaye()
                                                : 0
                                ),
                                normal
                        )
                );

                String statutTxt =
                        p.getStatutPaiement() != null
                                ? p.getStatutPaiement().name()
                                : "Non_Paye";

                PdfPCell statutCell =
                        new PdfPCell(
                                new Phrase(
                                        statutTxt,
                                        normalBold
                                )
                        );

                if ("PAYE".equalsIgnoreCase(statutTxt)) {

                    statutCell.setBackgroundColor(
                            new BaseColor(212, 237, 218)
                    );

                } else {

                    statutCell.setBackgroundColor(
                            new BaseColor(248, 215, 218)
                    );
                }

                statutCell.setHorizontalAlignment(
                        Element.ALIGN_CENTER
                );

                table.addCell(statutCell);
            }

            doc.add(table);

            // ==============================
            // TOTAL
            // ==============================

            Paragraph total =
                    new Paragraph(
                            "\nMontant total encaissé : "
                                    + (int) montantTotal
                                    + " FCFA",
                            normalBold
                    );

            total.setSpacingBefore(10);

            doc.add(total);

            doc.close();

        } catch (Exception e) {

            throw new ServletException(
                    "Erreur lors de la génération du PDF des participants.",
                    e
            );

        } finally {

            em.close();
        }
    }

    // ==========================================
    // HEADER TABLE
    // ==========================================

    private static void addHeaderCell(
            PdfPTable table,
            String text
    ) {

        Font head =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        12,
                        BaseColor.WHITE
                );

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(text, head)
                );

        cell.setBackgroundColor(
                new BaseColor(0, 77, 153)
        );

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        table.addCell(cell);
    }

    // ==========================================
    // NULL SAFE
    // ==========================================

    private static String nullSafe(String v) {

        return v == null
                ? ""
                : v;
    }
}