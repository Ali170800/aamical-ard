package com.amical.ard.servlets;

import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Notification;
import com.amical.ard.utils.EntityManagerHelper;
import com.amical.ard.utils.CloudinaryUtil;
import com.cloudinary.utils.ObjectUtils;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/ajouter-publication")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2 Mo
        maxFileSize = 1024 * 1024 * 200,      // 200 Mo max
        maxRequestSize = 1024 * 1024 * 250    // 250 Mo max
)
public class AjouterPublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            EntityManager em = EntityManagerHelper.getEntityManager();

            // 1. Récupération du fichier
            Part mediaPart = request.getPart("fichierMedia");
            String contentType = mediaPart.getContentType();
            String resourceType = contentType.startsWith("video") ? "video" : "image";

            // 2. Conversion du flux
            byte[] fileContent = mediaPart.getInputStream().readAllBytes();

            // 3. Envoi vers Cloudinary
            Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                    fileContent,
                    ObjectUtils.asMap(
                            "resource_type", resourceType,
                            "folder", "publications"
                    )
            );

            String mediaUrl = (String) uploadResult.get("secure_url");

            // 4. Création et sauvegarde
            Publication publication = new Publication();
            publication.setDescription(request.getParameter("description"));
            publication.setTypeMedia(resourceType.toUpperCase());

            if (resourceType.equals("video")) {
                publication.setVideo(mediaUrl);
                publication.setImage(""); // Correction : forcer chaîne vide pour éviter le NULL
            } else {
                publication.setImage(mediaUrl);
                publication.setVideo(""); // Correction : forcer chaîne vide pour éviter le NULL
            }

            publication.setAuteurId(admin.getId() != null ? admin.getId().longValue() : 0L);
            publication.setAuteurType("ADMIN");
            publication.setAuteurNom(admin.getNom());
            publication.setAuteurPrenom(admin.getPrenom());
            publication.setAuteurRole(admin.getRole());
            publication.setDatePublication(LocalDateTime.now());

            PublicationDAO dao = new PublicationDAO(em);
            dao.ajouter(publication);

            // 5. Notifications
            String nomAuteur = admin.getPrenom() + " " + admin.getNom();
            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            NotificationDAO notifDAO = new NotificationDAO(em);

            List<Etudiant> tousLesEtudiants = etudiantDAO.listerTous();
            for(Etudiant e : tousLesEtudiants) {
                Notification n = new Notification();
                n.setUtilisateurId(e.getId());
                n.setMessage(nomAuteur + " a ajouté une nouvelle publication.");
                n.setDateCreation(LocalDateTime.now());
                n.setEstLu(false);
                notifDAO.ajouter(n);
            }

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'ajout de la publication : " + e.getMessage(), e);
        }
    }
}