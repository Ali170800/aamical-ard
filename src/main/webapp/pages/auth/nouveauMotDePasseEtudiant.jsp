<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Nouveau mot de passe Étudiant - Amicale AERD</title>

    <!-- RESPONSIVE VIEWPORT -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        body {
            font-family: 'Segoe UI', sans-serif;
        }

        .fade-animation {
            animation: fadeIn 0.6s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .input-style {
            transition: all 0.3s ease;
        }

        .input-style:focus {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(79,70,229,0.15);
        }

        .btn-style {
            transition: all 0.3s ease;
        }

        .btn-style:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 25px rgba(16,185,129,0.25);
        }

    </style>

</head>

<body class="bg-gradient-to-br from-slate-900 via-indigo-900 to-slate-950 min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md sm:max-w-lg bg-white rounded-[30px] shadow-2xl overflow-hidden fade-animation">

    <!-- HEADER -->
    <div class="bg-gradient-to-r from-indigo-700 to-indigo-900 text-white p-8 sm:p-10 text-center">

        <div class="text-4xl sm:text-5xl mb-4">
            🔐
        </div>

        <h1 class="text-2xl sm:text-3xl font-extrabold break-words">
            Amicale AERD
        </h1>

        <p class="text-indigo-100 mt-3 text-sm sm:text-base">
            Nouveau mot de passe étudiant
        </p>

    </div>

    <!-- CONTENU -->
    <div class="p-6 sm:p-8">

        <!-- MESSAGE ERREUR -->
        <c:if test="${not empty erreur}">
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 sm:px-5 py-3 sm:py-4 rounded-2xl mb-6 text-sm sm:text-base">
                ❌ ${erreur}
            </div>
        </c:if>

        <!-- MESSAGE SUCCÈS -->
        <c:if test="${not empty success}">
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 sm:px-5 py-3 sm:py-4 rounded-2xl mb-6 text-sm sm:text-base">
                ✅ ${success}
            </div>
        </c:if>

        <!-- FORMULAIRE -->
        <form method="post"
              action="${pageContext.request.contextPath}/etudiant/changerMotDePasse">

            <!-- MOT DE PASSE -->
            <div class="mb-6">

                <label class="block text-slate-700 font-semibold mb-2 text-sm sm:text-base">
                    Nouveau mot de passe
                </label>

                <input type="password"
                       name="motDePasse"
                       placeholder="Minimum 6 caractères"
                       required
                       minlength="6"
                       class="input-style w-full px-4 sm:px-5 py-3 sm:py-4 border border-slate-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-indigo-500 text-sm sm:text-base">

                <p class="text-slate-500 text-xs sm:text-sm mt-2">
                    Le mot de passe doit contenir au moins 6 caractères.
                </p>

            </div>

            <!-- CONFIRMATION -->
            <div class="mb-8">

                <label class="block text-slate-700 font-semibold mb-2 text-sm sm:text-base">
                    Confirmation du mot de passe
                </label>

                <input type="password"
                       name="confirmation"
                       placeholder="Confirmez le mot de passe"
                       required
                       minlength="6"
                       class="input-style w-full px-4 sm:px-5 py-3 sm:py-4 border border-slate-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-indigo-500 text-sm sm:text-base">

            </div>

            <!-- BOUTON -->
            <button type="submit"
                    class="btn-style w-full bg-gradient-to-r from-emerald-600 to-emerald-700 hover:from-emerald-700 hover:to-emerald-800 text-white font-bold py-3 sm:py-4 rounded-2xl shadow-xl text-base sm:text-lg">

                Modifier le mot de passe

            </button>

        </form>

        <!-- RETOUR -->
        <div class="text-center mt-8">

            <a href="${pageContext.request.contextPath}/pages/connexionEtudiant.jsp"
               class="text-indigo-600 hover:text-indigo-800 font-semibold text-sm sm:text-base">

                ← Retour à la connexion

            </a>

        </div>

    </div>

</div>

</body>
</html>