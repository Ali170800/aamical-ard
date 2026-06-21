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
    <title>Fil d'actualité</title>

    <style>
        body { font-family: Arial; background: #f5f5f5; }
        .post { background: white; margin: 20px auto; padding: 15px; width: 600px; border-radius: 10px; }
        .comment-box { margin-top: 10px; }
    </style>
</head>

<body>

<% if (publications != null) { %>
    <% for (Publication p : publications) { %>

        <div class="post">

            <h3><%= p.getAuteur() %></h3>
            <p><%= p.getContenu() %></p>

            <!-- IMAGE -->
            <% if (p.getImage() != null) { %>
                <img src="<%= request.getContextPath() %>/uploads/<%= p.getImage() %>" style="max-width:100%;">
            <% } %>

            <!-- LIKE -->
            <p>
                ❤️ <span id="like-<%= p.getId() %>"><%= p.getLikes() %></span>
            </p>

            <!-- COMMENTAIRE COUNT -->
            <p>
                💬 <span id="com-size-<%= p.getId() %>"><%= p.getCommentaires().size() %></span>
            </p>

            <!-- LISTE COMMENTAIRES -->
            <div id="liste-com-<%= p.getId() %>">
                <% for (CommentairePublication c : p.getCommentaires()) { %>
                    <div class="border-b pb-2">
                        <p class="font-bold text-sm text-blue-800"><%= c.getAuteur() %></p>
                        <p class="text-gray-700 text-sm"><%= c.getTexte() %></p>
                        <p class="text-[10px] text-gray-400"><%= dtf.format(c.getDate()) %></p>
                    </div>
                <% } %>
            </div>

            <!-- FORM COMMENTAIRE -->
            <form onsubmit="posterCommentaire(event, <%= p.getId() %>)" class="comment-box">
                <input type="hidden" name="publicationId" value="<%= p.getId() %>">
                <input type="text" name="texte" placeholder="Écrire un commentaire..." required>
                <button type="submit">Publier</button>
            </form>

        </div>

    <% } %>
<% } %>


<!-- JS AJAX -->
<script>
async function posterCommentaire(e, id) {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    try {
        const res = await fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
            method: 'POST',
            credentials: 'include',
            body: formData
        });

        if (res.ok) {
            const d = await res.json();

            document.getElementById('liste-com-' + id).insertAdjacentHTML('beforeend', `
                <div class="border-b pb-2">
                    <p class="font-bold text-sm text-blue-800">${d.auteur}</p>
                    <p class="text-gray-700 text-sm">${d.texte}</p>
                    <p class="text-[10px] text-gray-400">${d.date}</p>
                </div>
            `);

            document.getElementById('com-size-' + id).innerText =
                parseInt(document.getElementById('com-size-' + id).innerText) + 1;

            form.reset();

        } else {
            alert("Erreur lors de la publication. Vérifiez votre connexion.");
        }

    } catch (err) {
        console.error("Erreur:", err);
    }
}
</script>

</body>
</html>