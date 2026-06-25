<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Connexion Étudiant - Amicale ARD</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="bg-gradient-to-r from-teal-50 to-white min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md bg-white p-8 rounded-3xl shadow-2xl">
    <h2 class="text-2xl font-bold text-center text-teal-700 mb-8">🔑 Connexion Étudiant</h2>

    <c:if test="${param.success == 'dejaActive'}">
        <div class="bg-green-100 text-green-700 p-4 rounded-2xl mb-6 text-center">✅ Votre compte est déjà activé.</div>
    </c:if>
    <c:if test="${not empty erreur}">
        <div class="bg-red-100 text-red-700 p-4 rounded-2xl mb-6 text-center">${erreur}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="bg-green-100 text-green-700 p-4 rounded-2xl mb-6 text-center">${success}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/etudiant/connexion">
        <input type="email" name="email" placeholder="Votre email" required class="w-full p-4 border border-slate-300 rounded-2xl mb-4">
        <input type="password" name="motDePasse" placeholder="Mot de passe" required class="w-full p-4 border border-slate-300 rounded-2xl mb-4">
        <button type="submit" class="w-full p-4 bg-teal-600 hover:bg-teal-700 text-white font-bold rounded-2xl">Se connecter</button>
    </form>

    <div class="mt-6 text-center space-y-4">
        <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublieEtudiant.jsp" class="text-blue-600 font-bold block">Mot de passe oublié ?</a>
        <div class="border-t pt-4">
            <p class="text-slate-500">Pas encore de compte ?</p>
            <a href="${pageContext.request.contextPath}/etudiant/inscription" class="text-teal-600 font-bold">➕ Créer un compte</a>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/pages/activationCompte.jsp" class="text-red-600 font-bold">🔑 Activer mon compte</a>
        </div>
    </div>
</div>

</body>
</html>