<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*" %>
<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    boolean estAdmin = session.getAttribute("utilisateurConnecte") != null;
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
    <% for(Publication publication : publications){ %>
    <div class="bg-white rounded-3xl shadow-lg mb-10 p-6">
        <p class="text-gray-800 text-lg mb-6"><%= publication.getDescription() %></p>

        <div id="commentaires_<%= publication.getId() %>" class="border-t pt-6">
            <form onsubmit="publierCommentaire(event, '<%= publication.getId() %>')" action="<%= request.getContextPath() %>/etudiant/commenter-publication" method="post" class="mb-6">
                <input type="hidden" name="publicationId" value="<%= publication.getId() %>">
                <textarea name="commentaire" required class="w-full border rounded-2xl p-4 mb-3" rows="2" placeholder="Ajouter un commentaire..."></textarea>
                <button type="submit" class="bg-green-600 text-white px-5 py-2 rounded-xl">Publier</button>
            </form>

            <div id="liste-com-<%= publication.getId() %>" class="space-y-4">
                <%
                    List<CommentairePublication> comms = (List<CommentairePublication>) request.getAttribute("commentaires_" + publication.getId());
                    if(comms != null){ for(CommentairePublication c : comms){
                        String auteur = (String) request.getAttribute("auteur_commentaire_" + c.getId());
                %>
                    <div class="bg-gray-100 rounded-2xl p-4">
                        <div class="font-bold text-blue-700 text-sm"><i class="fas fa-user-circle mr-2"></i> <%= auteur != null ? auteur : "Utilisateur" %></div>
                        <div class="text-gray-800 text-sm mt-1"><%= c.getCommentaire() %></div>
                        <div class="text-xs text-gray-500 mt-2"><i class="fas fa-clock mr-1"></i> <%= c.getDateCommentaire() %></div>
                    </div>
                <% }} %>
            </div>
        </div>
    </div>
    <% } %>
</div>

<script>
async function publierCommentaire(event, pubId) {
    event.preventDefault();
    const form = event.target;
    const res = await fetch(form.action, { method: 'POST', body: new FormData(form) });
    if (res.ok) {
        const data = await res.json();
        const container = document.getElementById('liste-com-' + pubId);
        container.insertAdjacentHTML('beforeend', `
            <div class="bg-gray-100 rounded-2xl p-4">
                <div class="font-bold text-blue-700 text-sm"><i class="fas fa-user-circle mr-2"></i> ${data.auteur}</div>
                <div class="text-gray-800 text-sm mt-1">${data.texte}</div>
                <div class="text-xs text-gray-500 mt-2"><i class="fas fa-clock mr-1"></i> ${data.date}</div>
            </div>`);
        form.reset();
    }
}
</script>
</body>
</html>