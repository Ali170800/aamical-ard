<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Publication" %>
<%@ page import="com.amical.ard.entites.CommentairePublication" %>

<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    Object admin = session.getAttribute("utilisateurConnecte");
    boolean estAdmin = admin != null;
    // Récupération du nom de l'utilisateur connecté pour l'affichage immédiat
    String nomUtilisateurConnecte = (String) session.getAttribute("nomUtilisateur");
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
        <% if(estAdmin){ %>
            <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp" class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-2xl font-semibold shadow">
                <i class="fas fa-plus mr-2"></i> Nouvelle publication
            </a>
        <% } %>
    </div>

    <% if(publications != null && !publications.isEmpty()){
       for(Publication publication : publications){
           List<CommentairePublication> commentaires = (List<CommentairePublication>) request.getAttribute("commentaires_" + publication.getId());
    %>
        <div class="bg-white rounded-3xl shadow-lg overflow-hidden mb-10" id="pub-<%= publication.getId() %>">
            <div class="bg-black flex items-center justify-center w-full" style="height: 500px;">
                <img src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>" class="w-full h-full object-contain" alt="Publication">
            </div>

            <div class="p-6">
                <p class="text-gray-800 text-lg leading-relaxed mb-6"><%= publication.getDescription() %></p>

                <div class="flex items-center gap-6 mt-6 border-t pt-5">
                    <button class="text-blue-600 font-semibold hover:text-blue-800" onclick="liker(<%= publication.getId() %>)">
                        👍 J’aime (<span id="like-count-<%= publication.getId() %>"><%= publication.getNombreLikes() %></span>)
                    </button>
                    <button onclick="toggleCommentaires('commentaires_<%= publication.getId() %>')" class="text-green-600 font-semibold hover:text-green-800">
                        💬 Commentaires (<span id="count-com-<%= publication.getId() %>"><%= commentaires != null ? commentaires.size() : 0 %></span>)
                    </button>
                </div>

                <div id="commentaires_<%= publication.getId() %>" class="hidden mt-8 border-t pt-6">
                    <form onsubmit="posterCommentaire(event, <%= publication.getId() %>)" class="mb-6">
                        <input type="hidden" name="publicationId" value="<%= publication.getId() %>">
                        <textarea name="commentaire" required placeholder="Ajouter un commentaire..." class="w-full border border-gray-300 rounded-2xl p-4 mb-3" rows="3"></textarea>
                        <button type="submit" class="bg-green-600 text-white px-5 py-2 rounded-xl font-semibold">Publier</button>
                    </form>

                    <div id="liste-com-<%= publication.getId() %>" class="space-y-4 max-h-80 overflow-y-auto">
                        <% if(commentaires != null) {
                            for(CommentairePublication com : commentaires) {
                                String auteurCom = (String) request.getAttribute("auteur_commentaire_" + com.getId());
                        %>
                            <div class="bg-gray-100 rounded-2xl p-4">
                                <div class="font-bold text-blue-700"><%= auteurCom != null ? auteurCom : "Utilisateur" %></div>
                                <div class="text-gray-800 my-1"><%= com.getCommentaire() %></div>
                                <div class="text-xs text-gray-500"><%= com.getDateCommentaire() %></div>
                            </div>
                        <% }} %>
                    </div>
                </div>
            </div>
        </div>
        <% }} %>
</div>

<script>
    function toggleCommentaires(id) { document.getElementById(id).classList.toggle("hidden"); }

    function posterCommentaire(event, pubId) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
            method: 'POST',
            body: new URLSearchParams(formData)
        }).then(response => {
            if(response.ok) {
                // Créer le nouveau commentaire dynamiquement
                const now = new Date().toLocaleString();
                const nouveauCom = document.createElement('div');
                nouveauCom.className = "bg-gray-100 rounded-2xl p-4";
                nouveauCom.innerHTML = `
                    <div class="font-bold text-blue-700"><%= nomUtilisateurConnecte %></div>
                    <div class="text-gray-800 my-1">${form.commentaire.value}</div>
                    <div class="text-xs text-gray-500">${now}</div>
                `;
                document.getElementById('liste-com-' + pubId).appendChild(nouveauCom);

                // Mettre à jour le compteur
                const countSpan = document.getElementById('count-com-' + pubId);
                countSpan.innerText = parseInt(countSpan.innerText) + 1;

                form.reset();
            }
        });
    }

    function liker(pubId) {
        fetch('<%= request.getContextPath() %>/like-publication', {
            method: 'POST',
            body: 'publicationId=' + pubId,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(res => res.json())
          .then(data => { document.getElementById('like-count-' + pubId).innerText = data.nouveauTotal; });
    }
</script>
</body>
</html>