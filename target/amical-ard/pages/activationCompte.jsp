<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Activation de Compte - Amicale AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-br from-teal-50 to-cyan-100 min-h-screen flex items-center justify-center p-4">

<div class="max-w-md w-full bg-white rounded-3xl shadow-xl overflow-hidden">

    <div class="bg-teal-700 text-white p-8 text-center">
        <h1 class="text-3xl font-bold">🔑 Activation de Compte</h1>
        <p class="text-teal-100 mt-2">Amicale AERD</p>
    </div>

    <div class="p-8">
        <% if (request.getAttribute("erreur") != null) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded-2xl mb-6">
                <%= request.getAttribute("erreur") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/etudiant/activer" method="post">
            <div class="mb-6">
                <label class="block text-slate-700 mb-2 font-medium">Votre Email</label>
                <input type="email" name="email" required
                       class="w-full px-5 py-4 border border-slate-300 rounded-2xl focus:outline-none focus:border-teal-500"
                       placeholder="exemple@zig.univ.sn">
            </div>

            <button type="submit"
                    class="w-full bg-teal-600 hover:bg-teal-700 text-white font-bold py-4 rounded-2xl text-lg">
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