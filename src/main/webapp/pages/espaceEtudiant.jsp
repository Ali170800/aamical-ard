<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Espace Étudiant - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <link
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
        rel="stylesheet">

</head>

<body class="bg-slate-50 font-sans text-slate-800 min-h-screen">

<div class="max-w-6xl mx-auto p-6 md:p-8 space-y-8">

<!-- Header -->

<div class="bg-gradient-to-r from-indigo-600 via-blue-600 to-indigo-700 text-white p-8 rounded-3xl shadow-xl flex flex-col md:flex-row justify-between items-start md:items-center gap-6">

    <div>

        <span class="inline-block bg-white/20 backdrop-blur-sm text-indigo-100 text-xs font-semibold tracking-wider uppercase px-3 py-1 rounded-full mb-3">
            Espace Étudiant
        </span>

        <h1 class="text-4xl font-extrabold tracking-tight">
            ${etudiantConnecte.prenom} ${etudiantConnecte.nom}
        </h1>

        <p class="text-indigo-100/90 text-sm md:text-base mt-3 flex items-center gap-3 flex-wrap">

            <span class="flex items-center gap-1.5">
                🎓
                <span class="font-medium">
                    ${etudiantConnecte.filiere}
                </span>
            </span>

            <span class="h-1 w-1 rounded-full bg-indigo-300"></span>

            <span>
                Niveau :
                <span class="font-bold text-white">
                    ${etudiantConnecte.niveau}
                </span>
            </span>

        </p>

    </div>

    <a href="${pageContext.request.contextPath}/etudiant/logout"
       class="bg-white/95 text-red-600 px-6 py-3 rounded-2xl font-bold shadow-lg shadow-indigo-900/20 hover:bg-white hover:text-red-700 transition-all flex items-center gap-2 text-sm w-full md:w-auto justify-center">

        <i class="fas fa-sign-out-alt"></i>

        Déconnexion

    </a>

</div>

<!-- ✅ COMMUNAUTÉ AERD -->

<div class="bg-white p-8 rounded-3xl shadow-sm border border-slate-100">

    <div class="flex flex-col md:flex-row items-center justify-between gap-6">

        <div>

            <h2 class="text-2xl font-black text-slate-800 flex items-center gap-3">
                🌍 Communauté AERD
            </h2>

            <p class="text-slate-500 mt-2">
                Découvrez les activités, graduations, événements,
                caravanes et publications de l’amicale.
            </p>

        </div>

        <a href="${pageContext.request.contextPath}/liste-publications"
           class="bg-indigo-600 hover:bg-indigo-700 text-white px-8 py-4 rounded-2xl font-bold shadow-xl shadow-indigo-600/10 transition-all duration-200 hover:shadow-none hover:scale-[1.02] active:scale-95 text-sm flex items-center gap-3">

            <i class="fas fa-images"></i>

            Accéder au fil communautaire

        </a>

    </div>

</div>

<!-- Informations personnelles -->

<div class="bg-white p-6 md:p-8 rounded-3xl shadow-sm border border-slate-100">

    <h2 class="text-xl font-bold text-slate-800 mb-6 flex items-center gap-2">

        <span class="p-2 bg-indigo-50 rounded-xl text-indigo-600">
            👤
        </span>

        Informations personnelles

    </h2>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">

        <div class="p-4 bg-slate-50/50 rounded-2xl border border-slate-100">

            <p class="text-slate-400 text-xs font-medium uppercase tracking-wider mb-1">
                Identifiant
            </p>

            <p class="text-lg font-bold text-indigo-700">
                #${etudiantConnecte.id}
            </p>

        </div>

        <div class="p-4 bg-slate-50/50 rounded-2xl border border-slate-100">

            <p class="text-slate-400 text-xs font-medium uppercase tracking-wider mb-1">
                Nom
            </p>

            <p class="text-lg font-bold text-slate-900">
                ${etudiantConnecte.nom}
            </p>

        </div>

        <div class="p-4 bg-slate-50/50 rounded-2xl border border-slate-100">

            <p class="text-slate-400 text-xs font-medium uppercase tracking-wider mb-1">
                Prénom
            </p>

            <p class="text-lg font-bold text-slate-900">
                ${etudiantConnecte.prenom}
            </p>

        </div>

        <div class="p-4 bg-slate-50/50 rounded-2xl border border-slate-100">

            <p class="text-slate-400 text-xs font-medium uppercase tracking-wider mb-1">
                Filière
            </p>

            <p class="text-lg font-bold text-indigo-700">
                ${etudiantConnecte.filiere}
            </p>

        </div>

        <div class="p-4 bg-slate-50/50 rounded-2xl border border-slate-100">

            <p class="text-slate-400 text-xs font-medium uppercase tracking-wider mb-1">
                Niveau
            </p>

            <p class="text-lg font-bold text-slate-900">
                ${etudiantConnecte.niveau}
            </p>

        </div>

    </div>

</div>

<!-- Mon Logement -->

<div class="bg-white p-6 md:p-8 rounded-3xl shadow-sm border border-slate-100">

    <h2 class="text-xl font-bold text-slate-800 mb-6 flex items-center gap-2">

        <span class="p-2 bg-indigo-50 rounded-xl text-indigo-600">
            🏠
        </span>

        Mon Logement

    </h2>

    <c:if test="${not empty etudiantLogement and not empty etudiantLogement.appartement}">

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

            <div class="p-5 bg-indigo-50/50 border border-indigo-100/50 rounded-2xl">

                <p class="text-indigo-600/80 text-xs font-semibold uppercase tracking-wider mb-1">
                    Appartement
                </p>

                <p class="text-2xl font-black text-indigo-950">
                    ${etudiantLogement.appartement.nomAppartement}
                </p>

            </div>

            <div class="p-5 bg-slate-50/80 border border-slate-100 rounded-2xl">

                <p class="text-slate-400 text-xs font-semibold uppercase tracking-wider mb-2">
                    Adresse
                </p>

                <p class="text-base font-medium text-slate-800">

                    ${etudiantLogement.appartement.description != null
                    ? etudiantLogement.appartement.description
                    : 'Non disponible'}

                </p>

            </div>

        </div>

    </c:if>

    <c:if test="${empty etudiantLogement}">

        <div class="p-6 bg-red-50/50 border border-red-100 rounded-2xl text-red-700">

            <p class="font-bold">
                Vous n'êtes pas encore logé(e).
            </p>

        </div>

    </c:if>

</div>

<!-- Mes Paiements -->

<div class="bg-white p-6 md:p-8 rounded-3xl shadow-sm border border-slate-100">

    <h2 class="text-xl font-bold text-slate-800 mb-6 flex items-center gap-2">

        <span class="p-2 bg-indigo-50 rounded-xl text-indigo-600">
            💰
        </span>

        Mes Paiements

    </h2>

    <c:if test="${not empty mesPaiements}">

        <div class="overflow-x-auto">

            <table class="w-full text-left border-collapse">

                <thead class="bg-slate-50">

                <tr>

                    <th class="p-4">
                        Mois / Année
                    </th>

                    <th class="p-4">
                        Montant
                    </th>

                    <th class="p-4">
                        Statut
                    </th>

                </tr>

                </thead>

                <tbody class="divide-y">

                <c:forEach var="p" items="${mesPaiements}">

                    <tr>

                        <td class="p-4 font-medium">
                            ${p.mois}/${p.annee}
                        </td>

                        <td class="p-4 font-bold">
                            ${p.montant} FCFA
                        </td>

                        <td class="p-4">

                            <c:choose>

                                <c:when test="${p.statut == 'PAYE'}">

                                    <span class="bg-green-100 text-green-700 px-3 py-1 rounded-full text-xs font-bold">
                                        Payé
                                    </span>

                                </c:when>

                                <c:otherwise>

                                    <span class="bg-red-100 text-red-700 px-3 py-1 rounded-full text-xs font-bold">
                                        Non Payé
                                    </span>

                                </c:otherwise>

                            </c:choose>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </c:if>

    <c:if test="${empty mesPaiements}">

        <p class="text-slate-400 italic text-center py-8">
            Aucun paiement enregistré pour le moment.
        </p>

    </c:if>

</div>

<!-- Élections & Votes (AJOUTÉ) -->

<div class="bg-white p-8 rounded-3xl shadow-sm border border-slate-100 text-center flex flex-col items-center gap-4">

    <h2 class="text-xl font-bold text-slate-800 flex items-center gap-2">
        🗳️ Élections & Votes
    </h2>

    <p class="text-slate-500 text-sm max-w-md">
        Participez à la vie démocratique de l'amicale en votant pour vos représentants.
    </p>

    <a href="${pageContext.request.contextPath}/etudiant/elections"
       class="bg-violet-600 hover:bg-violet-700 text-white px-8 py-3.5 rounded-2xl font-bold shadow-xl shadow-violet-600/10 transition-all duration-200 hover:shadow-none hover:scale-[1.02] active:scale-95 text-sm flex items-center gap-2">

        Accéder aux scrutins

        <span aria-hidden="true">
            &rarr;
        </span>

    </a>

</div>

<!-- Caravanes -->

<div class="bg-white p-8 rounded-3xl shadow-sm border border-slate-100 text-center flex flex-col items-center gap-4">

    <h2 class="text-xl font-bold text-slate-800 flex items-center gap-2">
        🚌 Caravanes
    </h2>

    <p class="text-slate-500 text-sm max-w-md">
        Consultez les caravanes disponibles et inscrivez-vous directement.
    </p>

    <a href="${pageContext.request.contextPath}/etudiant/caravanes"
       class="bg-indigo-600 hover:bg-indigo-700 text-white px-8 py-3.5 rounded-2xl font-bold shadow-xl shadow-indigo-600/10 transition-all duration-200 hover:shadow-none hover:scale-[1.02] active:scale-95 text-sm flex items-center gap-2">

        Voir les caravanes disponibles

        <span aria-hidden="true">
            &rarr;
        </span>

    </a>

</div>

</div>

</body>
</html>