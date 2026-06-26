<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Connexion Étudiant - Amicale ARD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body class="bg-gradient-to-r from-teal-50 to-white min-h-screen flex items-center justify-center p-4">

<div class="relative w-full max-w-md bg-white rounded-3xl shadow-2xl p-6 sm:p-8 overflow-hidden">

    <!-- Bouton Sortie -->
    <div class="absolute top-4 right-4">
        <a href="${pageContext.request.contextPath}/accueil.jsp"
           class="text-slate-400 hover:text-slate-600 font-semibold text-sm transition">
            Sortie
        </a>
    </div>

    <!-- Titre -->
    <h2 class="text-2xl sm:text-3xl font-bold text-center text-teal-700 mt-6 mb-8">
        🔑 Connexion Étudiant
    </h2>

    <!-- Messages -->
    <c:if test="${param.success == 'dejaActive'}">
        <div class="bg-green-100 border border-green-200 text-green-700 p-4 rounded-2xl mb-5 text-center">
            ✅ Votre compte est déjà activé.
        </div>
    </c:if>

    <c:if test="${not empty erreur}">
        <div class="bg-red-100 border border-red-200 text-red-700 p-4 rounded-2xl mb-5 text-center">
            ${erreur}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="bg-green-100 border border-green-200 text-green-700 p-4 rounded-2xl mb-5 text-center">
            ${success}
        </div>
    </c:if>

    <!-- Formulaire -->
    <form method="post" action="${pageContext.request.contextPath}/etudiant/connexion" class="space-y-4">

        <input
                type="email"
                name="email"
                placeholder="Votre email"
                required
                class="w-full p-4 border border-slate-300 rounded-2xl focus:ring-2 focus:ring-teal-500 focus:outline-none">

        <input
                type="password"
                name="motDePasse"
                placeholder="Mot de passe"
                required
                class="w-full p-4 border border-slate-300 rounded-2xl focus:ring-2 focus:ring-teal-500 focus:outline-none">

        <button
                type="submit"
                class="w-full bg-teal-600 hover:bg-teal-700 transition text-white font-bold p-4 rounded-2xl">
            Se connecter
        </button>

    </form>

    <!-- Liens -->
    <div class="mt-6 text-center space-y-4">

        <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublieEtudiant.jsp"
           class="block text-blue-600 hover:text-blue-700 font-semibold">
            Mot de passe oublié ?
        </a>

        <div class="border-t pt-4">

            <p class="text-slate-500 mb-2">
                Pas encore de compte ?
            </p>

            <a href="${pageContext.request.contextPath}/etudiant/inscription"
               class="text-teal-600 hover:text-teal-700 font-bold">
                ➕ Créer un compte
            </a>

        </div>

        <!-- Activation du compte -->
        <div>

            <a href="${pageContext.request.contextPath}/pages/activationCompte.jsp"
               class="text-red-600 hover:text-red-700 font-bold">
                🔑 Activer mon compte
            </a>

        </div>

    </div>

</div>

</body>
</html>