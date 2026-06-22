<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*, java.time.format.DateTimeFormatter" %>

<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");
    boolean peutAjouter = (user != null);
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Communauté AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">

<div class="max-w-4xl mx-auto py-10 px-4">
    <div class="flex justify-between items-center mb-8">
        <h1 class="text-3xl font-bold text-gray-800">Fil d'actualité</h1>
        <% if (peutAjouter) { %>
            <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
               class="bg-blue-600 text-white px-6 py-2 rounded-xl font-bold hover:bg-blue-700 shadow-lg transition">
                + Ajouter publication
            </a>
        <% } %>
    </div>

    <% if (publications != null) { for (Publication p : publications) {
        String auteur = (String) request.getAttribute("auteur_publication_" + p.getId());
        String role = (String) request.getAttribute("role_auteur_" + p.getId());
        String datePub = (String) request.getAttribute("date_publication_" + p.getId());
    %>

        <div class="bg-white rounded-3xl shadow-lg mb-10 overflow-hidden">
            <%-- Image Cloudinary --%>
            <img src="<%= p.getImage() %>" class="w-full h-auto object-contain bg-gray-50" alt="Image">

            <div class="p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="flex items-center">
                        <i class="fas fa-user-circle text-3xl text-blue-600 mr-3"></i>
                        <div>
                            <p class="font-bold text-gray-900"><%= (auteur != null) ? auteur : "Utilisateur" %>
                                <% if (role != null) { %><span class="text-blue-600 text-sm ml-2 uppercase"><%= role %></span><% } %>
                            </p>
                            <p class="text-xs text-gray-400 mt-1"><%= (datePub != null) ? datePub : "" %></p>
                        </div>
                    </div>
                </div>

                <p class="text-gray-700 text-lg mb-4"><%= p.getDescription() %></p>

                <div class="flex gap-6 border-t pt-4">
                    <button onclick="liker('<%= p.getId() %>')" class="font-bold text-red-500">
                        <i class="fas fa-heart"></i> J’aime (<span id="like-count-<%= p.getId() %>"><%= p.getNombreLikes() %></span>)
                    </button>
                    <button onclick="document.getElementById('com-<%= p.getId() %>').classList.toggle('hidden')" class="font-bold text-blue-600">
                        <i class="fas fa-comments"></i> Commentaires (<%= (p.getCommentaires() != null) ? p.getCommentaires().size() : 0 %>)
                    </button>
                </div>

                <div id="com-<%= p.getId() %>" class="hidden mt-6 bg-gray-50 p-4 rounded-2xl">
                    <form action="<%= request.getContextPath() %>/etudiant/commenter-publication" method="POST" class="mb-4">
                        <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                        <textarea name="commentaire" required class="w-full p-2 border rounded-xl" rows="2"></textarea>
                        <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-1 rounded-lg text-sm">Publier</button>
                    </form>
                </div>
            </div>
        </div>
    <% } } %>
</div>

<script>
async function liker(id) {
    const res = await fetch('<%= request.getContextPath() %>/like-publication', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'publicationId=' + id
    });
    if (res.ok) {
        const d = await res.json();
        document.getElementById('like-count-' + id).innerText = d.nouveauTotal;
    }
}
</script>
</body>
</html>