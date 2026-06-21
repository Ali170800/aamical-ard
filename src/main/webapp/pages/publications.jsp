<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*, java.time.format.DateTimeFormatter" %>

<%
    List<Publication> publications =
        (List<Publication>) request.getAttribute("publications");

    DateTimeFormatter dtf =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Publications</title>
</head>

<body>

<% if (publications != null) {
    for (Publication p : publications) { %>

    <div class="publication">

        <h3><%= p.getAuteur() %></h3>

        <p><%= p.getContenu() %></p>

        <!-- ===================== -->
        <!-- LISTE COMMENTAIRES -->
        <!-- ===================== -->

        <div id="liste-com-<%= p.getId() %>" class="space-y-3 max-h-60 overflow-y-auto">

            <% if (p.getCommentaires() != null) {
                for (CommentairePublication c : p.getCommentaires()) {

                    Utilisateur auteur =
                        com.amical.ard.utils.UserHelper.findById(c.getUtilisateurId());

                    String nomComplet =
                        (auteur != null)
                            ? auteur.getPrenom() + " " + auteur.getNom()
                            : "Utilisateur";
            %>

                <div class="border-b pb-2">
                    <p class="font-bold text-sm text-blue-800">
                        <%= nomComplet %>
                    </p>

                    <p class="text-gray-700 text-sm">
                        <%= c.getCommentaire() %>
                    </p>

                    <p class="text-[10px] text-gray-400">
                        <%= c.getDateCommentaire().format(dtf) %>
                    </p>
                </div>

            <% } } %>

        </div>

        <!-- FORM COMMENTAIRE -->
        <form onsubmit="posterCommentaire(event, <%= p.getId() %>)">
            <input type="hidden" name="publicationId" value="<%= p.getId() %>">
            <input type="text" name="commentaire" placeholder="Écrire..." required>
            <button type="submit">Envoyer</button>
        </form>

    </div>

<% } } %>


<script>
async function posterCommentaire(e, id) {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);

    const res = await fetch('<%= request.getContextPath() %>/etudiant/commenter-publication', {
        method: 'POST',
        body: formData,
        credentials: 'include'
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

        form.reset();
    } else {
        alert("Erreur commentaire");
    }
}
</script>

</body>
</html>