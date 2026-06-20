<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*, java.time.format.DateTimeFormatter" %>
<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
    <h1 class="text-3xl font-bold mb-8 text-gray-800">Fil d'actualité</h1>

    <% if(publications != null && !publications.isEmpty()){ %>
        <% for(Publication p : publications){ %>
        <div class="bg-white rounded-3xl shadow-lg mb-10 overflow-hidden">
            <img src="<%= request.getContextPath() %>/uploads/<%= p.getImage() %>"
                 class="w-full h-auto object-contain bg-gray-50" alt="Publication">

            <div class="p-6">
                <div class="flex items-center mb-2">
                    <i class="fas fa-user-circle text-2xl text-blue-600 mr-3"></i>
                    <div>
                        <p class="font-bold text-gray-800"><%= request.getAttribute("auteur_publication_" + p.getId()) %></p>
                        <p class="text-xs text-gray-400">
                            <%= (p.getDatePublication() != null) ? p.getDatePublication().format(dtf) : "Date non disponible" %>
                        </p>
                    </div>
                </div>

                <p class="text-gray-700 text-lg my-4"><%= p.getDescription() %></p>

                <div class="flex gap-6 border-t pt-4">
                    <form onsubmit="handleLike(event, '<%= p.getId() %>')" action="<%= request.getContextPath() %>/like-publication" method="post">
                        <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                        <button type="submit" class="font-bold text-red-500 hover:text-red-700">
                            <i class="fas fa-heart"></i> <span id="like-count-<%= p.getId() %>">J’aime (<%= p.getNombreLikes() %>)</span>
                        </button>
                    </form>

                    <button onclick="document.getElementById('com-<%= p.getId() %>').classList.toggle('hidden')"
                            class="font-bold text-blue-600 hover:text-blue-800">
                        <i class="fas fa-comments"></i> Commentaires (<%= (p.getCommentaires() != null) ? p.getCommentaires().size() : 0 %>)
                    </button>
                </div>

                <div id="com-<%= p.getId() %>" class="hidden mt-6 bg-gray-50 p-4 rounded-2xl">
                    <form onsubmit="handleComment(event, '<%= p.getId() %>')" action="<%= request.getContextPath() %>/etudiant/commenter-publication" method="post" class="mb-4">
                        <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                        <textarea name="commentaire" required placeholder="Écrire un commentaire..." class="w-full p-2 border rounded-xl" rows="2"></textarea>
                        <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-1 rounded-lg text-sm">Publier</button>
                    </form>

                    <div id="com-list-<%= p.getId() %>" class="space-y-3">
                        <% if(p.getCommentaires() != null){
                            for(CommentairePublication c : p.getCommentaires()){
                                String auteur = (String) request.getAttribute("auteur_commentaire_" + c.getId());
                        %>
                            <div class="border-b pb-2">
                                <p class="font-bold text-sm text-blue-800"><%= auteur != null ? auteur : "Utilisateur" %></p>
                                <p class="text-gray-700 text-sm"><%= c.getCommentaire() %></p>
                                <p class="text-[10px] text-gray-400"><%= (c.getDateCommentaire() != null) ? c.getDateCommentaire().format(dtf) : "" %></p>
                            </div>
                        <% } } %>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    <% } %>
</div>

<script>
async function handleLike(e, id) {
    e.preventDefault();
    const res = await fetch(e.target.action, { method: 'POST', body: new FormData(e.target) });
    if (res.ok) {
        const data = await res.json();
        document.getElementById('like-count-' + id).innerText = 'J’aime (' + data.nouveauTotal + ')';
    }
}

async function handleComment(e, id) {
    e.preventDefault();
    const res = await fetch(e.target.action, { method: 'POST', body: new FormData(e.target) });
    if (res.ok) {
        const data = await res.json();
        const list = document.getElementById('com-list-' + id);
        list.innerHTML += `
            <div class="border-b pb-2">
                <p class="font-bold text-sm text-blue-800">${data.auteur}</p>
                <p class="text-gray-700 text-sm">${data.texte}</p>
                <p class="text-[10px] text-gray-400">${data.date}</p>
            </div>`;
        e.target.reset();
    }
}
</script>
</body>
</html>