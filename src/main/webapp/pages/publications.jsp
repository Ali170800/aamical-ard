<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.amical.ard.entites.*" %>

<%
    List<Publication> publications =
            (List<Publication>) request.getAttribute("publications");

    Object admin = session.getAttribute("utilisateurConnecte");
    boolean estAdmin = admin != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Communauté AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          rel="stylesheet">
</head>

<body class="bg-gray-100">

<div class="max-w-4xl mx-auto py-10 px-4">

    <% if(publications != null && !publications.isEmpty()){ %>

        <% for(Publication publication : publications){ %>

        <div class="bg-white rounded-3xl shadow-lg overflow-hidden mb-10">

            <!-- IMAGE -->

            <img
                    src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>"
                    class="w-full h-[500px] object-cover"
                    alt="Publication">

            <div class="p-6">

                <!-- AUTEUR PUBLICATION -->

                <div class="font-bold text-lg text-gray-800 mb-2">

                    <i class="fas fa-user-circle text-blue-600 mr-2"></i>

                    <%= request.getAttribute(
                            "auteur_publication_" +
                            publication.getId()
                    ) %>

                </div>

                <!-- DESCRIPTION -->

                <p class="text-gray-800 text-lg leading-relaxed mb-6">

                    <%= publication.getDescription() %>

                </p>

                <!-- COMMENTAIRES -->

                <div id="commentaires_<%= publication.getId() %>"
                     class="border-t pt-6">

                    <!-- FORMULAIRE -->

                    <form
                            onsubmit="publierCommentaire(event,'<%= publication.getId() %>')"
                            action="<%= request.getContextPath() %>/etudiant/commenter-publication"
                            method="post"
                            class="mb-6">

                        <input
                                type="hidden"
                                name="publicationId"
                                value="<%= publication.getId() %>">

                        <textarea
                                name="commentaire"
                                required
                                rows="2"
                                placeholder="Ajouter un commentaire..."
                                class="w-full border border-gray-300 rounded-2xl p-4 mb-3"></textarea>

                        <button
                                type="submit"
                                class="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded-xl font-semibold">

                            Publier

                        </button>

                    </form>

                    <!-- LISTE COMMENTAIRES -->

                    <div
                            id="liste-com-<%= publication.getId() %>"
                            class="space-y-4 max-h-96 overflow-y-auto pr-2">

                        <%
                            List<CommentairePublication> comms =
                                    (List<CommentairePublication>)
                                            request.getAttribute(
                                                    "commentaires_" +
                                                    publication.getId()
                                            );

                            if(comms != null && !comms.isEmpty()){

                                for(CommentairePublication c : comms){

                                    String auteur =
                                            (String) request.getAttribute(
                                                    "auteur_commentaire_" +
                                                    c.getId()
                                            );
                        %>

                        <div class="bg-gray-100 rounded-2xl p-4">

                            <!-- NOM -->

                            <div class="font-bold text-blue-700 text-sm">

                                <i class="fas fa-user-circle mr-2"></i>

                                <%= auteur != null
                                        ? auteur
                                        : "Utilisateur" %>

                            </div>

                            <!-- COMMENTAIRE -->

                            <div class="text-gray-800 text-sm mt-1">

                                <%= c.getCommentaire() %>

                            </div>

                            <!-- DATE -->

                            <div class="text-xs text-gray-500 mt-2">

                                <i class="fas fa-clock mr-1"></i>

                                <%= c.getDateCommentaire() %>

                            </div>

                        </div>

                        <%
                                }

                            } else {
                        %>

                        <p class="text-gray-500 italic">

                            Aucun commentaire pour le moment.

                        </p>

                        <%
                            }
                        %>

                    </div>

                </div>

            </div>

        </div>

        <% } %>

    <% } else { %>

        <div class="bg-white rounded-3xl shadow p-10 text-center">

            <h2 class="text-xl font-bold text-gray-700">

                Aucune publication disponible

            </h2>

        </div>

    <% } %>

</div>

<script>

async function publierCommentaire(event, pubId){

    event.preventDefault();

    const form = event.target;

    const response =
            await fetch(
                    form.action,
                    {
                        method:'POST',
                        body:new FormData(form)
                    }
            );

    if(response.ok){

        const data = await response.json();

        const container =
                document.getElementById(
                        'liste-com-' + pubId
                );

        if(container.querySelector('p')){
            container.innerHTML = '';
        }

        container.insertAdjacentHTML(
                'beforeend',
                `
                <div class="bg-gray-100 rounded-2xl p-4">

                    <div class="font-bold text-blue-700 text-sm">

                        <i class="fas fa-user-circle mr-2"></i>

                        ${data.auteur}

                    </div>

                    <div class="text-gray-800 text-sm mt-1">

                        ${data.texte}

                    </div>

                    <div class="text-xs text-gray-500 mt-2">

                        <i class="fas fa-clock mr-1"></i>

                        ${data.date}

                    </div>

                </div>
                `
        );

        form.reset();
    }
}

</script>

</body>
</html>