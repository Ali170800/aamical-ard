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
    <button onclick="document.getElementById('modalPub').classList.remove('hidden')"
            class="bg-blue-600 text-white px-6 py-3 rounded-full shadow-lg mb-8 hover:bg-blue-700 transition">
        + Nouvelle Publication
    </button>

    <div id="feed" class="space-y-10">
        <% if(publications != null) { for(Publication p : publications){ %>
        <div class="bg-white rounded-3xl shadow-lg p-6" id="pub-<%= p.getId() %>">
            <p class="font-bold text-lg mb-2"><%= request.getAttribute("auteur_publication_" + p.getId()) %></p>
            <img src="<%= request.getContextPath() %>/uploads/<%= p.getImage() %>" class="w-full h-auto object-contain bg-gray-50 rounded-xl">
            <p class="my-4 text-gray-700"><%= p.getDescription() %></p>

            <button onclick="liker('<%= p.getId() %>')" class="text-red-500 font-bold hover:text-red-700">
                <i class="fas fa-heart"></i> J'aime (<span id="like-count-<%= p.getId() %>"><%= p.getNombreLikes() %></span>)
            </button>

            <div id="liste-com-<%= p.getId() %>" class="mt-4 space-y-3">
                <% if(p.getCommentaires() != null) { for(CommentairePublication c : p.getCommentaires()){ %>
                    <div id="comment-<%= c.getId() %>" class="bg-gray-100 p-3 rounded-xl flex justify-between items-center">
                        <div><strong><%= request.getAttribute("auteur_commentaire_" + c.getId()) %></strong>: <%= c.getCommentaire() %></div>
                        <button onclick="supprimerCommentaire('<%= c.getId() %>')" class="text-red-500 text-xs font-bold">Supprimer</button>
                    </div>
                <% } } %>
            </div>

            <form onsubmit="posterCommentaire(event, '<%= p.getId() %>')" class="mt-4">
                <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                <textarea name="commentaire" required placeholder="Écrire un commentaire..." class="w-full border p-2 rounded-xl" rows="2"></textarea>
                <button type="submit" class="bg-green-600 text-white px-4 py-2 mt-2 rounded-lg text-sm">Publier</button>
            </form>
        </div>
        <% } } %>
    </div>
</div>

<div id="modalPub" class="hidden fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
    <form onsubmit="creerPublication(event)" class="bg-white p-8 rounded-3xl w-full max-w-md shadow-2xl">
        <h2 class="text-xl font-bold mb-4">Nouvelle Publication</h2>
        <input type="file" name="image" required class="mb-4 w-full">
        <textarea name="description" required placeholder="Description..." class="w-full border p-3 rounded-xl mb-4"></textarea>
        <div class="flex gap-4">
            <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded-lg flex-1">Partager</button>
            <button type="button" onclick="document.getElementById('modalPub').classList.add('hidden')" class="text-gray-500">Annuler</button>
        </div>
    </form>
</div>

<script>
// 1. LIKE
async function liker(id) {
    const res = await fetch('<%= request.getContextPath() %>/like-publication', {
        method: 'POST', body: 'publicationId=' + id,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    });
    if(res.ok) {
        const data = await res.json();
        document.getElementById('like-count-' + id).innerText = data.nouveauTotal;
    }
}

// 2. POSTER COMMENTAIRE
async function posterCommentaire(e, id) {
    e.preventDefault();
    const res = await fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
        method: 'POST', body: new FormData(e.target)
    });
    if(res.ok) {
        const d = await res.json();
        document.getElementById('liste-com-' + id).insertAdjacentHTML('beforeend', `
            <div id="comment-${d.id}" class="bg-gray-100 p-3 rounded-xl flex justify-between items-center">
                <div><strong>${d.auteur}</strong>: ${d.texte}</div>
                <button onclick="supprimerCommentaire('${d.id}')" class="text-red-500 text-xs font-bold">Supprimer</button>
            </div>`);
        e.target.reset();
    }
}

// 3. SUPPRIMER COMMENTAIRE
async function supprimerCommentaire(id) {
    if(!confirm("Supprimer ce commentaire ?")) return;
    const res = await fetch('<%= request.getContextPath() %>/supprimer-commentaire', {
        method: 'POST', body: 'commentaireId=' + id,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    });
    if(res.ok) document.getElementById('comment-' + id).remove();
}

// 4. CRÉER PUBLICATION
async function creerPublication(e) {
    e.preventDefault();
    const res = await fetch('<%= request.getContextPath() %>/creer-publication', {
        method: 'POST', body: new FormData(e.target)
    });
    if(res.ok) {
        const d = await res.json();
        document.getElementById('feed').insertAdjacentHTML('afterbegin', `
            <div class="bg-white rounded-3xl shadow-lg p-6" id="pub-${d.id}">
                <p class="font-bold">${d.auteur}</p>
                <img src="/uploads/${d.image}" class="w-full rounded-xl my-4">
                <p>${d.description}</p>
            </div>`);
        e.target.reset();
        document.getElementById('modalPub').classList.add('hidden');
    }
}
</script>
</body>
</html>