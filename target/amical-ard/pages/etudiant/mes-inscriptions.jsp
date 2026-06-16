<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Mes Reçus - Caravanes</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-slate-50 min-h-screen p-6">

<div class="max-w-4xl mx-auto">
    <h1 class="text-3xl font-bold text-slate-800 mb-8 flex items-center gap-3">
        🎟️ Mes Reçus d'Inscription
    </h1>

    <c:if test="${empty inscriptions}">
        <div class="bg-white p-16 rounded-3xl text-center shadow">
            <p class="text-slate-500 text-xl">Vous n'avez encore aucune inscription.</p>
        </div>
    </c:if>

    <div class="grid gap-8">
        <c:forEach var="p" items="${inscriptions}">
            <div class="bg-white border-2 border-dashed border-slate-300 rounded-3xl p-10 shadow-md">

                <div class="flex justify-between items-start border-b pb-6 mb-6">
                    <div>
                        <h2 class="text-2xl font-bold text-slate-800">${p.caravane.nom}</h2>
                        <p class="text-slate-600 mt-1">📅 ${p.caravane.date}</p>
                    </div>
                    <div class="text-right">
                        <div class="text-5xl font-black text-red-600">${p.numeroChaise}</div>
                        <p class="text-sm text-slate-500">Numéro de chaise</p>
                    </div>
                </div>

                <div class="grid grid-cols-2 gap-8 text-sm">
                    <div>
                        <p class="text-slate-500">Nom complet</p>
                        <p class="font-semibold text-lg">${p.prenom} ${p.nom}</p>
                    </div>
                    <div>
                        <p class="text-slate-500">Montant</p>
                        <p class="font-semibold text-lg">${p.montant} FCFA</p>
                    </div>
                    <div>
                        <p class="text-slate-500">Date d'inscription</p>
                        <p class="font-semibold text-green-600">${p.dateInscriptionFormatted}</p>
                    </div>
                    <div>
                        <p class="text-slate-500">Statut Paiement</p>
                        <span class="inline-block px-5 py-1 bg-green-100 text-green-700 rounded-full text-sm font-medium">
                            ${p.statutPaiement}
                        </span>
                    </div>
                </div>

                <div class="mt-10 bg-amber-50 border border-amber-200 rounded-2xl p-6 text-center text-amber-800">
                    <p class="font-medium">
                        Veuillez conserver ce reçu (capture d'écran ou impression).<br>
                        Présentez votre <strong>Numéro de Chaise</strong> le jour du départ.
                    </p>
                </div>

                <div class="flex gap-4 mt-8">
                    <button onclick="window.print()"
                            class="flex-1 bg-slate-800 hover:bg-slate-900 text-white py-4 rounded-2xl font-semibold flex items-center justify-center gap-2">
                        <i class="fas fa-print"></i> Imprimer le reçu
                    </button>
                    <a href="${pageContext.request.contextPath}/etudiant/caravanes"
                       class="flex-1 bg-white border border-slate-300 hover:bg-slate-50 py-4 rounded-2xl font-semibold text-center">
                        Retour aux caravanes
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>