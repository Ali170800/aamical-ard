<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Mot de passe oublié Étudiant - Amicale AERD</title>

    <!-- RESPONSIVE VIEWPORT -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://cdn.tailwindcss.com"></script>

    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .card-animation {
            animation: fadeIn 0.5s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(15px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>

</head>

<body class="bg-gradient-to-br from-cyan-900 via-slate-900 to-teal-950 min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md sm:max-w-lg bg-white rounded-3xl shadow-2xl overflow-hidden card-animation">

    <!-- HEADER -->
    <div class="bg-gradient-to-r from-teal-600 to-cyan-700 text-white p-6 sm:p-8 text-center">

        <div class="text-4xl sm:text-5xl mb-3">
            🔐
        </div>

        <h1 class="text-2xl sm:text-3xl font-bold break-words">
            Amicale AERD
        </h1>

        <p class="text-cyan-100 mt-2 text-sm sm:text-base">
            Récupération du mot de passe étudiant
        </p>

    </div>

    <!-- CONTENU -->
    <div class="p-6 sm:p-8">

        <!-- MESSAGE ERREUR -->
        <c:if test="${not empty erreur}">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 sm:px-5 py-3 sm:py-4 rounded-2xl mb-6 text-sm sm:text-base text-center font-semibold">
                ❌ ${erreur}
            </div>
        </c:if>

        <!-- MESSAGE SUCCÈS -->
        <c:if test="${not empty success}">
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 sm:px-5 py-3 sm:py-4 rounded-2xl mb-6 text-sm sm:text-base text-center font-semibold">
                ✅ ${success}
            </div>
        </c:if>

        <!-- DESCRIPTION -->
        <div class="mb-6 text-center text-slate-600 text-xs sm:text-sm leading-6 px-2">
            Entrez votre adresse email afin de recevoir
            un code de vérification sécurisé.
        </div>

        <!-- FORMULAIRE -->
        <form method="post"
              action="${pageContext.request.contextPath}/etudiant/verifierEmailRecuperation">

            <!-- EMAIL -->
            <div class="mb-6">

                <label class="block text-slate-700 font-semibold mb-2 text-sm sm:text-base">
                    Adresse email
                </label>

                <input type="email"
                       name="email"
                       placeholder="Entrez votre adresse email"
                       required
                       class="w-full px-4 py-3 sm:py-4 border border-slate-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-cyan-500 transition duration-300 text-sm sm:text-base">

            </div>

            <!-- BOUTON -->
            <button type="submit"
                    class="w-full bg-gradient-to-r from-teal-600 to-cyan-700 hover:from-teal-700 hover:to-cyan-800 transition duration-300 text-white font-bold py-3 sm:py-4 rounded-2xl shadow-xl text-base sm:text-lg">

                Envoyer le code

            </button>

        </form>

        <!-- RETOUR -->
        <div class="text-center mt-8">

            <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp"
               class="text-cyan-700 hover:text-cyan-900 font-semibold transition duration-300 text-sm sm:text-base">

                ← Retour à la connexion

            </a>

        </div>

    </div>

</div>

</body>
</html>