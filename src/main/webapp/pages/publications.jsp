<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*, java.time.format.DateTimeFormatter" %>
<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    String role = (String) session.getAttribute("role");
    // Récupération de l'ID utilisateur connecté pour vérifier la propriété de la publication
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");
    Long currentUserId = (user != null) ? user.getId().longValue() : null;
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
    <div class="flex justify-between items-center mb-10">
        <h1 class="text-4xl font-black text-gray-800">Fil communautaire AERD</h1>
        <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
           class="bg-blue-600 text-white px-6 py-3 rounded-2xl font-semibold shadow hover:bg-blue-700">
            + Nouvelle publication
        </a>
    </div>

    <% if (publications != null) { for (Publication p : publications) { %>
    <div class="bg-white rounded-3xl shadow-lg mb-10 overflow-hidden">
        <div class="p-6 flex justify-between items-center border-b">
            <div>
                <p class="font-bold text-lg text-gray-900">
                    <%= request.getAttribute("auteur_publication_" + p.getId()) %>
                    <span class="text-gray-400 font-normal">|</span>
                    <span class="text-blue-600 text-sm font-semibold uppercase"><%= request.getAttribute("role_auteur_" + p.getId()) %></span>
                </p>
                <p class="text-xs text-gray-400"><%= request.getAttribute("date_publication_" + p.getId()) %></p>
            </div>
            <%
               // Condition : ADMIN ou Auteur de la publication
               boolean peutSupprimer = "ADMIN".equals(role) || (currentUserId != null && currentUserId.equals(p.getAuteurId()));
               if (peutSupprimer) {
            %>
            <a href="<%= request.getContextPath() %>/admin/supprimer-publication?id=<%= p.getId() %>"
               onclick="return confirm('Confirmer la suppression ?')" class="text-red-600 font-bold text-sm">
               <i class="fas fa-trash"></i> Supprimer
            </a>
            <% } %>
        </div>

        <img src="<%= request.getContextPath() %>/uploads/<%= p.getImage() %>" class="w-full h-auto object-contain bg-gray-50">

        <div class="p-6">
            <p class="text-gray-800 text-lg mb-6"><%= p.getDescription() %></p>

            <div class="flex gap-6 mb-4">
                <button onclick="liker('<%= p.getId() %>')" class="text-red-500 font-bold">
                    <i class="fas fa-heart"></i> <span id="like-count-<%= p.getId() %>"><%= p.getNombreLikes() %></span> J'aime
                </button>
                <button onclick="document.getElementById('com-<%= p.getId() %>').classList.toggle('hidden')" class="text-blue-600 font-bold">
                    <i class="fas fa-comments"></i> Commentaires (<span id="count-<%= p.getId() %>"><%= p.getCommentaires().size() %></span>)
                </button>
            </div>

            <div id="com-<%= p.getId() %>" class="hidden bg-gray-50 p-4 rounded-2xl">
                <form onsubmit="posterCommentaire(event, '<%= p.getId() %>')">
                    <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                    <textarea name="commentaire" required class="w-full p-2 border rounded-xl" rows="2"></textarea>
                    <button type="submit" class="mt-2 bg-blue-600 text-white px-4 py-1 rounded-lg text-sm">Publier</button>
                </form>
                <div id="liste-<%= p.getId() %>" class="mt-4 space-y-3 max-h-40 overflow-y-auto">
                    <% for(CommentairePublication c : p.getCommentaires()){ %>
                        <div class="border-b pb-2 text-sm">
                            <p class="font-bold text-blue-800"><%= request.getAttribute("auteur_commentaire_" + c.getId()) %></p>
                            <p><%= c.getCommentaire() %></p>
                            <p class="text-[10px] text-gray-400"><%= c.getDateCommentaire().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></p>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
    <% } } %>
</div>

<script>
async function liker(id) {
    const res = await fetch('<%= request.getContextPath() %>/like-publication', {
        method: 'POST', body: 'publicationId=' + id, headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    });
    if(res.ok) {
        const d = await res.json();
        document.getElementById('like-count-' + id).innerText = d.nouveauTotal;
    }
}

async function posterCommentaire(e, id) {
    e.preventDefault();
    const res = await fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', { method: 'POST', body: new FormData(e.target) });
    if(res.ok) {
        const d = await res.json();
        document.getElementById('liste-' + id).insertAdjacentHTML('beforeend', `
            <div class="border-b pb-2 text-sm">
                <p class="font-bold text-blue-800">${d.auteur}</p>
                <p>${d.texte}</p>
                <p class="text-[10px] text-gray-400">${d.date}</p>
            </div>`);
        document.getElementById('count-' + id).innerText = parseInt(document.getElementById('count-' + id).innerText) + 1;
        e.target.reset();
    }
}
</script>
</body>
</html>