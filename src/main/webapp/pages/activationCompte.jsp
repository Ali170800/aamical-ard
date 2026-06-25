<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">

    <!-- Responsive Mobile -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Activation de Compte - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gradient-to-br from-teal-50 to-cyan-100 min-h-screen flex items-center justify-center p-3 sm:p-4 md:p-6">

<div class="w-full max-w-md bg-white rounded-3xl shadow-xl overflow-hidden">

    <!-- HEADER -->
    <div class="bg-teal-700 text-white p-6 sm:p-8 text-center">

        <h1 class="text-2xl sm:text-3xl font-bold">
            🔑 Activation de Compte
        </h1>

        <p class="text-teal-100 mt-2 text-sm sm:text-base">
            Amicale AERD
        </p>

    </div>

    <!-- CONTENU -->
    <div class="p-5 sm:p-8">

        <% if (request.getAttribute("erreur") != null) { %>

            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-2xl mb-6 break-words">
                <%= request.getAttribute("erreur") %>
            </div>

        <% } %>

        <form action="${pageContext.request.contextPath}/etudiant/activer"
              method="post">

            <div class="mb-6">

                <label class="block text-slate-700 mb-2 font-medium text-sm sm:text-base">
                    Votre Email
                </label>

                <input
                        type="email"
                        name="email"
                        required
                        placeholder="exemple@zig.univ.sn"
                        class="w-full px-4 sm:px-5 py-3 sm:py-4 border border-slate-300 rounded-2xl focus:outline-none focus:border-teal-500 text-sm sm:text-base"
                >

            </div>

            <button
                    type="submit"
                    class="w-full bg-teal-600 hover:bg-teal-700 transition-colors duration-300 text-white font-bold py-3 sm:py-4 rounded-2xl text-base sm:text-lg">

                Recevoir le code

            </button>

        </form>

        <div class="text-center mt-6">

            <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp"
               class="text-teal-600 hover:underline text-sm">

                ← Retour à la connexion

            </a>

        </div>

    </div>

</div>

</body>
</html>