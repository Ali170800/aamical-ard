
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.List" %>

<%@ page import="com.amical.ard.entites.Publication" %>
<%@ page import="com.amical.ard.entites.CommentairePublication" %>

<%

    List<Publication> publications =
            (List<Publication>)
                    request.getAttribute("publications");

    Object admin =
            session.getAttribute("utilisateurConnecte");

    boolean estAdmin =
            admin != null;

%>

<!DOCTYPE html>

<html lang="fr">

<head>

    <meta charset="UTF-8">

    <title>Communauté AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
            rel="stylesheet">

</head>

<body class="bg-gray-100">

<div class="max-w-4xl mx-auto py-10 px-4">

    <!-- HEADER -->

    <div class="flex justify-between items-center mb-10">

        <div>

            <h1 class="text-4xl font-black text-gray-800">

                Fil communautaire AERD

            </h1>

            <p class="text-gray-500 mt-2">

                Publications, activités, événements et graduations

            </p>

        </div>

        <% if(estAdmin){ %>

        <a href="<%= request.getContextPath() %>/upload/ajouterPublication.jsp"
           class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-2xl font-semibold shadow">

            <i class="fas fa-plus mr-2"></i>

            Nouvelle publication

        </a>

        <% } %>

    </div>

    <!-- LISTE PUBLICATIONS -->

    <% if(publications != null && !publications.isEmpty()){ %>

        <% for(Publication publication : publications){ %>

        <div class="bg-white rounded-3xl shadow-lg overflow-hidden mb-10">

            <!-- IMAGE -->

            <img
                    src="<%= request.getContextPath() %>/uploads/<%= publication.getImage() %>"
                    class="w-full h-[500px] object-cover"
                    alt="Publication">

            <!-- CONTENU -->

            <div class="p-6">

                <!-- AUTEUR -->

                <div class="flex items-center justify-between mb-5">

                    <div>

                        <div class="font-bold text-lg text-gray-800">

                            <i class="fas fa-user-circle text-blue-600 mr-2"></i>

                            <%= request.getAttribute(
                                    "auteur_publication_" +
                                            publication.getId()
                            ) %>

                        </div>

                        <div class="text-sm text-gray-500 mt-1">

                            <i class="fas fa-clock mr-1"></i>

                            <%= publication.getDatePublication() %>

                        </div>

                    </div>

                    <!-- SUPPRIMER SEULEMENT -->

                    <% if(publication.isPeutModifier()){ %>

                    <div>

                        <form action="<%= request.getContextPath() %>/admin/supprimer-publication"
                              method="post"
                              onsubmit="return confirm('Supprimer cette publication ?')">

                            <input type="hidden"
                                   name="id"
                                   value="<%= publication.getId() %>">

                            <button type="submit"
                                    class="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-xl text-sm font-semibold">

                                <i class="fas fa-trash mr-1"></i>

                                Supprimer

                            </button>

                        </form>

                    </div>

                    <% } %>

                </div>

                <!-- DESCRIPTION -->

                <p class="text-gray-800 text-lg leading-relaxed mb-6">

                    <%= publication.getDescription() %>

                </p>

                <!-- COMMENTAIRES -->

                <%

                    List<CommentairePublication> commentaires =
                            (List<CommentairePublication>)
                                    request.getAttribute(
                                            "commentaires_" +
                                                    publication.getId()
                                    );

                %>

                <!-- ACTIONS -->

                <div class="flex items-center gap-6 mt-6 border-t pt-5">

                    <!-- LIKE -->

                    <form action="<%= request.getContextPath() %>/like-publication"
                          method="post">

                        <input type="hidden"
                               name="publicationId"
                               value="<%= publication.getId() %>">

                        <button type="submit"
                                class="text-blue-600 font-semibold hover:text-blue-800">

                            👍 J’aime
                            (
                            <%= publication.getNombreLikes() %>
                            )

                        </button>

                    </form>

                    <!-- COMMENTAIRES -->

                    <button
                            onclick="toggleCommentaires('commentaires_<%= publication.getId() %>')"
                            class="text-green-600 font-semibold hover:text-green-800">

                        💬 Commentaires
                        (
                        <%= commentaires != null
                                ? commentaires.size()
                                : 0 %>
                        )

                    </button>

                </div>

                <!-- BLOC COMMENTAIRES -->

                <div id="commentaires_<%= publication.getId() %>"
                     class="hidden mt-8 border-t pt-6">

                    <h3 class="text-xl font-bold text-gray-800 mb-4">

                        💬 Commentaires

                    </h3>

                    <!-- FORM COMMENTAIRE -->

                    <form action="<%= request.getContextPath() %>/etudiant/commenter-publication"
                          method="post"
                          class="mb-6">

                        <input type="hidden"
                               name="publicationId"
                               value="<%= publication.getId() %>">

                        <textarea
                                name="commentaire"
                                required
                                placeholder="Ajouter un commentaire..."
                                class="w-full border border-gray-300 rounded-2xl p-4 mb-3 resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
                                rows="3"></textarea>

                        <button type="submit"
                                class="bg-green-600 hover:bg-green-700 text-white px-5 py-2 rounded-xl font-semibold">

                            Publier le commentaire

                        </button>

                    </form>

                    <!-- LISTE COMMENTAIRES -->

                    <% if(commentaires != null &&
                            !commentaires.isEmpty()){ %>

                    <div class="space-y-4 max-h-96 overflow-y-auto pr-2">

                        <% for(CommentairePublication commentaire : commentaires){ %>

                        <div class="bg-gray-100 rounded-2xl p-4">

                            <!-- AUTEUR -->

                            <div class="font-bold text-blue-700 mb-2">

                                <%

                                    String auteurCommentaire =
                                            (String)
                                                    request.getAttribute(
                                                            "auteur_commentaire_" +
                                                                    commentaire.getId()
                                                    );

                                    if(auteurCommentaire == null){

                                        auteurCommentaire =
                                                "Utilisateur";
                                    }

                                %>

                                <%= auteurCommentaire %>

                            </div>

                            <!-- COMMENTAIRE -->

                            <div class="text-gray-800">

                                <%= commentaire.getCommentaire() %>

                            </div>

                            <!-- DATE -->

                            <div class="text-xs text-gray-500 mt-2">

                                <%= commentaire.getDateCommentaire() %>

                            </div>

                        </div>

                        <% } %>

                    </div>

                    <% } else { %>

                    <p class="text-gray-500 italic">

                        Aucun commentaire pour le moment.

                    </p>

                    <% } %>

                </div>

            </div>

        </div>

        <% } %>

    <% } else { %>

    <!-- VIDE -->

    <div class="bg-white rounded-3xl shadow p-16 text-center">

        <h2 class="text-2xl font-bold text-gray-700 mb-3">

            Aucune publication disponible

        </h2>

    </div>

    <% } %>

</div>

<!-- SCRIPT -->

<script>

    function toggleCommentaires(id){

        const bloc =
                document.getElementById(id);

        bloc.classList.toggle("hidden");
    }

</script>

</body>

</html>
```
