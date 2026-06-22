package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.utils.EntityManagerHelper;
import com.amical.ard.utils.CloudinaryUtil;
import com.cloudinary.utils.ObjectUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/admin/ajouter-publication")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
public class AjouterPublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object admin = session.getAttribute("utilisateurConnecte");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // Infos auteur via Reflection
            Integer auteurId = (Integer) admin.getClass().getMethod("getId").invoke(admin);
            String nom = (String) admin.getClass().getMethod("getNom").invoke(admin);
            String prenom = (String) admin.getClass().getMethod("getPrenom").invoke(admin);
            String role = (String) admin.getClass().getMethod("getRole").invoke(admin);

            // 1. Récupération du Part
            Part imagePart = request.getPart("image");

            // 2. Conversion du flux en tableau d'octets (Solution pour l'erreur Unrecognized file parameter)
            byte[] fileContent = imagePart.getInputStream().readAllBytes();

            // 3. Envoi vers Cloudinary
            Map uploadResult = CloudinaryUtil.getCloudinary().uploader().upload(
                    fileContent,
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "folder", "publications"
                    )
            );

            String imageUrl = (String) uploadResult.get("secure_url");

            // 4. Création et sauvegarde de la Publication
            Publication publication = new Publication();
            publication.setDescription(request.getParameter("description"));
            publication.setImage(imageUrl);
            publication.setAuteurId(auteurId.longValue());
            publication.setAuteurType("ADMIN");
            publication.setAuteurNom(nom);
            publication.setAuteurPrenom(prenom);
            publication.setAuteurRole(role);
            publication.setDatePublication(LocalDateTime.now());

            PublicationDAO dao = new PublicationDAO(EntityManagerHelper.getEntityManager());
            dao.ajouter(publication);

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de l'ajout de la publication : " + e.getMessage(), e);
        }
    }
}