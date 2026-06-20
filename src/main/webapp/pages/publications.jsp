<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Publication" %>
<%@ page import="com.amical.ard.entites.CommentairePublication" %>

<%
    List<Publication> publications = (List<Publication>) request.getAttribute("publications");
    boolean estAdmin = session.getAttribute("utilisateurConnecte") != null;
    String nomUtilisateur = (String) session.getAttribute("nomUtilisateur");
    if(nomUtilisateur == null) nomUtilisateur = "Utilisateur";
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
        <div>
            <h1 class="text-4xl font-black text-gray-800">Fil communautaire AERD</h1>
            <p class="text-gray-500 mt-2">Publications, activités, événements et graduations</p>
        </div>
        <% if(estAdmin){ %>
        <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp" class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-2xl font-semibold shadow">
            <i class="fas fa-plus mr-2"></i> Nouvelle publication
        </a>
        <% } %>
    </div>

    <% if(publications != null && !publications.isEmpty()){ %>
        <% for(Publication publication : publications){ %>
        <div class="bg-white rounded-3xl shadow-lg overflow-hidden mb-10">
            <img src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>" class="w-full h-[500px] object-cover" alt="Publication">

            <div class="p-6">
                <div class="flex items-center justify-between mb-5">
                    <div>
                        <div class="font-bold text-lg text-gray-800">
                            <i class="fas fa-user-circle text-blue-600 mr-2"></i>
                            <%= request.getAttribute("auteur_publication_" + publication.getId()) %>
                        </div>
                        <div class="text-sm text-gray-500 mt-1"><i class="fas fa-clock mr-1"></i> <%= publication.getDatePublication() %></div>
                    </div>
                    <% if(publication.isPeutModifier()){ %>
                    <form action="<%= request.getContextPath() %>/admin/supprimer-publication" method="post" onsubmit="return confirm('Supprimer cette publication ?')">
                        <input type="hidden" name="id" value="<%= publication.getId() %>">
                        <button type="submit" class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-xl text-sm font-semibold"><i class="fas fa-trash mr-1"></i> Supprimer</button>
                    </form>
                    <% } %>
                </div>

                <p class="text-gray-800 text-lg leading-relaxed mb-6"><%= publication.getDescription() %></p>

                <% List<CommentairePublication> commentaires = (List<CommentairePublication>) request.getAttribute("commentaires_" + publication.getId()); %>

                <div class="flex items-center gap-6 mt-6 border-t pt-5">
                    <button type="button" onclick="liker(<%= publication.getId() %>)" class="text-blue-600 font-semibold hover:text-blue-800">
                        👍 J’aime (<span id="like-count-<%= publication.getId() %>"><%= publication.getNombreLikes() %></span>)
                    </button>
                    <button onclick="toggleCommentaires('commentaires_<%= publication.getId() %>')" class="text-green-600 font-semibold hover:text-green-800">
                        💬 Commentaires (<span id="com-count-<%= publication.getId() %>"><%= commentaires != null ? commentaires.size() : 0 %></span>)
                    </button>
                </div>

                <div id="commentaires_<%= publication.getId() %>" class="hidden mt-8 border-t pt-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-4">💬 Commentaires</h3>

                    <form onsubmit="posterCommentaire(event, <%= publication.getId() %>)" class="mb-6">
                        <input type="hidden" name="publicationId" value="<%= publication.getId() %>">
                        <textarea name="commentaire" required placeholder="Ajouter un commentaire..." class="w-full border border-gray-300 rounded-2xl p-4 mb-3" rows="3"></textarea>
                        <button type="submit" class="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded-xl font-semibold">Publier</button>
                    </form>

                    <div id="liste-com-<%= publication.getId() %>" class="space-y-4 max-h-96 overflow-y-auto pr-2">
                        <% if(commentaires != null){ for(CommentairePublication com : commentaires){ %>
                           <div class="bg-gray-100 rounded-2xl p-4">
                               <div class="font-bold text-blue-700 mb-2"><%= request.getAttribute("auteur_commentaire_" + com.getId()) %></div>
                               <div class="text-gray-800"><%= com.getCommentaire() %></div>
                               <div class="text-xs text-gray-500 mt-2"><%= com.getDateCommentaire() %></div>
                           </div>
                        <% }} %>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    <% } %>
</div>

<script>
    const userName = "<%= nomUtilisateur %>";

    function toggleCommentaires(id){
        document.getElementById(id).classList.toggle("hidden");
    }

    function posterCommentaire(event, pubId) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
            method: 'POST',
            body: new URLSearchParams(formData)
        }).then(response => {
            if(response.ok) {
                const nouveauCom = document.createElement('div');
                nouveauCom.className = "bg-gray-100 rounded-2xl p-4";
                nouveauCom.innerHTML = `
                    <div class="font-bold text-blue-700 mb-2">${userName}</div>
                    <div class="text-gray-800">${form.commentaire.value}</div>
                    <div class="text-xs text-gray-500 mt-2">À l'instant</div>
                `;
                document.getElementById('liste-com-' + pubId).appendChild(nouveauCom);

                const countSpan = document.getElementById('com-count-' + pubId);
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
          .then(data => {
              document.getElementById('like-count-' + pubId).innerText = data.nouveauTotal;
          });
    }
</script>
</body>
</html>