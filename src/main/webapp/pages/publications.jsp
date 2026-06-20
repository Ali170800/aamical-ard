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
    <div class="flex justify-between items-center mb-8">
        <h1 class="text-3xl font-bold text-gray-800">Fil d'actualité</h1>
        <a href="<%= request.getContextPath() %>/ajouter-publication"
           class="bg-blue-600 text-white px-6 py-2 rounded-xl font-bold hover:bg-blue-700 shadow-lg">+ Ajouter publication</a>
    </div>

    <% if(publications != null && !publications.isEmpty()){ %>
        <% for(Publication p : publications){
            // Récupération sécurisée des attributs
            Boolean estAuteur = (Boolean) request.getAttribute("est_auteur_" + p.getId());
            if(estAuteur == null) estAuteur = false;
        %>
        <div class="bg-white rounded-3xl shadow-lg mb-10 overflow-hidden" id="pub-<%= p.getId() %>">
            <img src="<%= request.getContextPath() %>/uploads/<%= p.getImage() %>" class="w-full h-auto object-contain bg-gray-50">

            <div class="p-6">
                <div class="flex items-center justify-between mb-4">
                    <div class="flex items-center">
                        <i class="fas fa-user-circle text-2xl text-blue-600 mr-3"></i>
                        <div>
                            <p class="font-bold text-gray-800"><%= request.getAttribute("auteur_publication_" + p.getId()) %></p>
                            <p class="text-xs text-blue-600 font-bold uppercase"><%= request.getAttribute("role_auteur_" + p.getId()) %></p>
                        </div>
                    </div>
                    <% if(estAuteur) { %>
                        <form action="<%= request.getContextPath() %>/supprimer-publication" method="post" onsubmit="return confirm('Confirmer ?');">
                            <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                            <button type="submit" class="text-red-500 hover:text-red-700 text-sm font-bold"><i class="fas fa-trash"></i> Supprimer</button>
                        </form>
                    <% } %>
                </div>

                <p class="text-gray-700 text-lg my-4"><%= p.getDescription() %></p>

                <div class="flex gap-6 border-t pt-4">
                    <button onclick="liker('<%= p.getId() %>')" class="font-bold text-red-500"><i class="fas fa-heart"></i> J’aime (<span id="like-count-<%= p.getId() %>"><%= p.getNombreLikes() %></span>)</button>
                    <button onclick="document.getElementById('com-<%= p.getId() %>').classList.toggle('hidden')" class="font-bold text-blue-600"><i class="fas fa-comments"></i> Commentaires (<span id="com-size-<%= p.getId() %>"><%= (p.getCommentaires() != null) ? p.getCommentaires().size() : 0 %></span>)</button>
                </div>

                <div id="com-<%= p.getId() %>" class="hidden mt-6 bg-gray-50 p-4 rounded-2xl">
                    <form onsubmit="posterCommentaire(event, '<%= p.getId() %>')" class="mb-4">
                        <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                        <textarea name="commentaire" required placeholder="Commentaire..." class="w-full p-2 border rounded-xl" rows="2"></textarea>
                        <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-1 rounded-lg text-sm">Publier</button>
                    </form>
                    <div id="liste-com-<%= p.getId() %>" class="space-y-3 max-h-60 overflow-y-auto">
                        <% if(p.getCommentaires() != null){ for(CommentairePublication c : p.getCommentaires()){ %>
                            <div class="border-b pb-2">
                                <p class="font-bold text-sm text-blue-800"><%= request.getAttribute("auteur_commentaire_" + c.getId()) %></p>
                                <p class="text-gray-700 text-sm"><%= c.getCommentaire() %></p>
                                <p class="text-[10px] text-gray-400"><%= c.getDateCommentaire().format(dtf) %></p>
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
async function liker(id) {
    const res = await fetch('<%= request.getContextPath() %>/like-publication', { method: 'POST', body: 'publicationId=' + id, headers: {'Content-Type': 'application/x-www-form-urlencoded'} });
    if(res.ok) { const d = await res.json(); document.getElementById('like-count-' + id).innerText = d.nouveauTotal; }
}
async function posterCommentaire(e, id) {
    e.preventDefault();
    const res = await fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', { method: 'POST', body: new FormData(e.target) });
    if(res.ok) {
        const d = await res.json();
        document.getElementById('liste-com-' + id).insertAdjacentHTML('beforeend', `<div class="border-b pb-2"><p class="font-bold text-sm text-blue-800">${d.auteur}</p><p class="text-gray-700 text-sm">${d.texte}</p><p class="text-[10px] text-gray-400">${d.date}</p></div>`);
        document.getElementById('com-size-' + id).innerText = parseInt(document.getElementById('com-size-' + id).innerText) + 1;
        e.target.reset();
    }
}
</script>
</body>
</html>