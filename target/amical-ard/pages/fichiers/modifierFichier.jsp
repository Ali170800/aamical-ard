<!-- /pages/fichiers/modifierFichier.jsp -->

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">

    <title>Modifier PDF</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        body{
            background:#f3f4f6;
        }

        .card{
            animation:fadeIn .4s ease;
        }

        @keyframes fadeIn{
            from{
                opacity:0;
                transform:translateY(15px);
            }
            to{
                opacity:1;
                transform:translateY(0);
            }
        }

    </style>

</head>

<body class="min-h-screen flex items-center justify-center p-6">

<div class="card bg-white w-full max-w-2xl rounded-3xl shadow-2xl p-10">

    <!-- HEADER -->
    <div class="text-center mb-8">

        <div class="text-6xl mb-4">
            📄
        </div>

        <h1 class="text-4xl font-black text-gray-800">
            Modifier le PDF
        </h1>

        <p class="text-gray-500 mt-2">
            Modifier les informations ou remplacer le document PDF.
        </p>

    </div>

    <!-- FORMULAIRE -->
    <form method="post"
          enctype="multipart/form-data"
          action="${pageContext.request.contextPath}/fichiers/modifier"
          class="space-y-6">

        <!-- ID -->
        <input type="hidden"
               name="id"
               value="${fichier.id}">

        <!-- TITRE -->
        <div>

            <label class="block text-sm font-bold text-gray-700 mb-2">
                Titre du document
            </label>

            <input type="text"
                   name="titre"
                   value="${fichier.titre}"
                   required
                   class="w-full border border-gray-300 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-indigo-500">

        </div>

        <!-- TYPE -->
        <div>

            <label class="block text-sm font-bold text-gray-700 mb-2">
                Type de document
            </label>

            <select name="typeFichier"
                    class="w-full border border-gray-300 rounded-2xl px-5 py-4 focus:outline-none focus:ring-2 focus:ring-indigo-500">

                <option value="Compte_Rendu"
                    ${fichier.typeFichier == 'Compte_Rendu' ? 'selected' : ''}>
                    Compte rendu
                </option>

                <option value="Bilan_Caravane"
                    ${fichier.typeFichier == 'Bilan_Caravane' ? 'selected' : ''}>
                    Bilan caravane
                </option>

                <option value="Fiche_Activite"
                    ${fichier.typeFichier == 'Fiche_Activite' ? 'selected' : ''}>
                    Fiche activité
                </option>

                <option value="Autre"
                    ${fichier.typeFichier == 'Autre' ? 'selected' : ''}>
                    Autre
                </option>

            </select>

        </div>

        <!-- FICHIER ACTUEL -->
        <div class="bg-gray-100 rounded-2xl p-4">

            <p class="text-sm text-gray-500 mb-1">
                Fichier actuel :
            </p>

            <p class="font-semibold text-gray-800">
                ${fichier.nomFichier}
            </p>

        </div>

        <!-- NOUVEAU PDF -->
        <div>

            <label class="block text-sm font-bold text-gray-700 mb-2">
                Remplacer le PDF (optionnel)
            </label>

            <input type="file"
                   name="pdf"
                   accept="application/pdf"
                   class="w-full border border-gray-300 bg-white rounded-2xl px-5 py-4">

            <p class="text-sm text-gray-500 mt-2">
                Laissez vide si vous ne souhaitez pas remplacer le fichier actuel.
            </p>

        </div>

        <!-- BOUTONS -->
        <div class="flex gap-4 pt-4">

            <button type="submit"
                    class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-4 rounded-2xl transition">

                Enregistrer les modifications

            </button>

            <a href="${pageContext.request.contextPath}/fichiers/liste"
               class="flex-1 text-center bg-gray-700 hover:bg-black text-white font-bold py-4 rounded-2xl transition">

                Retour

            </a>

        </div>

    </form>

</div>

</body>
</html>