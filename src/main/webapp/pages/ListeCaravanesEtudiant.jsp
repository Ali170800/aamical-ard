<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Caravanes - Espace Étudiant</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-slate-50 min-h-screen">

<div class="max-w-5xl mx-auto p-6 relative">

    <div class="absolute top-6 right-6">
        <a href="${pageContext.request.contextPath}/pages/espaceEtudiant.jsp"
           class="bg-gray-200 text-gray-700 px-4 py-2 rounded-xl font-bold hover:bg-gray-300 transition text-sm">
           ⬅ Sortie
        </a>
    </div>

    <h1 class="text-3xl font-bold text-slate-800 mb-8 mt-12">🚌 Caravanes Disponibles</h1>

    <c:if test="${not empty success}">
        <div class="bg-green-100 border border-green-400 text-green-700 px-5 py-4 rounded-2xl mb-6">${success}</div>
    </c:if>
    <c:if test="${not empty erreur}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-5 py-4 rounded-2xl mb-6">${erreur}</div>
    </c:if>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <c:forEach var="caravane" items="${caravanes}">
            <div class="bg-white border border-slate-200 rounded-3xl p-8 shadow hover:shadow-lg transition-all">

                <div class="flex justify-between items-start mb-6">
                    <div>
                        <h2 class="text-2xl font-bold">${caravane.nom}</h2>
                        <p class="text-slate-600">${caravane.date}</p>
                    </div>
                    <p class="text-3xl font-bold text-indigo-600">${caravane.montant} FCFA</p>
                </div>

                <c:if test="${caravane.dejaInscrit}">
                    <div class="bg-green-50 border border-green-300 p-6 rounded-2xl text-center">
                        <p class="text-green-700 font-semibold">✅ Vous êtes déjà inscrit</p>
                        <p class="mt-3">Numéro de chaise : <strong>${caravane.numeroChaise}</strong></p>
                    </div>
                </c:if>

                <c:if test="${!caravane.dejaInscrit}">
                    <form action="${pageContext.request.contextPath}/etudiant/payer-caravane" method="post">
                        <input type="hidden" name="caravaneId" value="${caravane.id}">
                        <button type="submit"
                                class="w-full bg-green-600 hover:bg-green-700 text-white py-4 rounded-2xl font-semibold text-lg transition">
                            💰 Payer mon caravane via Wave (${caravane.montant} FCFA)
                        </button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>