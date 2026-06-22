package com.amical.ard.servlets;

import com.amical.ard.dao.ElectionDAO;
import com.amical.ard.entites.CandidatElection;
import com.amical.ard.entites.Election;
import com.amical.ard.utils.CloudinaryUtil;
import com.amical.ard.utils.EntityManagerHelper;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/ajouter-election")
@MultipartConfig
public class AjouterElectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/ajouter-election.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            em.getTransaction().begin();

            // 1. Création de l'élection
            Election election = new Election();
            election.setTitre(request.getParameter("titre"));
            election.setDescription(request.getParameter("description"));
            election.setPoste(request.getParameter("poste"));
            election.setDateDebut(LocalDateTime.parse(request.getParameter("dateDebut")));
            election.setDateFin(LocalDateTime.parse(request.getParameter("dateFin")));
            election.setDateCreation(LocalDateTime.now());

            // 2. Gestion des candidats
            List<CandidatElection> candidats = new ArrayList<>();
            String nbCandidatsStr = request.getParameter("nbCandidats");
            int nbCandidats = (nbCandidatsStr != null) ? Integer.parseInt(nbCandidatsStr) : 0;

            for (int i = 0; i < nbCandidats; i++) {
                CandidatElection candidat = new CandidatElection();
                candidat.setNom(request.getParameter("nomCandidat_" + i));
                candidat.setPrenom(request.getParameter("prenomCandidat_" + i));
                candidat.setFiliere(request.getParameter("filiereCandidat_" + i));
                candidat.setPromotion(request.getParameter("promoCandidat_" + i));
                candidat.setDescription(request.getParameter("descCandidat_" + i));
                candidat.setElection(election);

                // 3. Upload photo Cloudinary
                Part photoPart = request.getPart("photoCandidat_" + i);
                if (photoPart != null && photoPart.getSize() > 0) {
                    byte[] fileContent = photoPart.getInputStream().readAllBytes();
                    Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                            fileContent,
                            ObjectUtils.asMap("resource_type", "image", "folder", "elections")
                    );
                    candidat.setPhoto((String) uploadResult.get("secure_url"));
                }
                candidats.add(candidat);
            }

            election.setCandidats(candidats);

            // 4. Enregistrement via DAO
            ElectionDAO dao = new ElectionDAO(em);
            dao.ajouter(election);

            em.getTransaction().commit();
            request.getSession().setAttribute("success", "Élection créée avec succès !");
            response.sendRedirect(request.getContextPath() + "/pages/dashboard-elections");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            request.getSession().setAttribute("erreur", "Erreur lors de la création : " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pages/ajouter-election");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}