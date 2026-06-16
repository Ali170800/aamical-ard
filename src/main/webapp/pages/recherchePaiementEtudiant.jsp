<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Paiements par Étudiant</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 30px;
            background-color: #f9f9f9;
        }

        h2 {
            background-color: #007bff;
            color: white;
            padding: 15px;
            border-radius: 8px;
            text-align: center;
        }

        .search-box {
            text-align: center;
            margin: 25px 0;
        }

        #searchInput {
            padding: 12px 15px;
            font-size: 17px;
            width: 450px;
            border: 2px solid #007bff;
            border-radius: 6px;
            outline: none;
        }

        #searchInput:focus {
            box-shadow: 0 0 10px rgba(0,123,255,0.4);
        }

        .student-list {
            max-height: 380px;
            overflow-y: auto;
            width: 90%;
            margin: 0 auto 30px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background: white;
        }

        /* AFFICHAGE COMPACT */
        .student-item {
            padding: 14px 18px;
            border-bottom: 1px solid #f0f0f0;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            align-items: center;
            gap: 10px; /* espace réduit */
        }

        .student-item:hover {
            background-color: #e7f3ff;
        }

        .student-item:last-child {
            border-bottom: none;
        }

        .name {
            font-weight: bold;
            color: #222;
        }

        .phone {
            color: #666;
            font-size: 14px;
            font-weight: normal;
        }

        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        .statut-paye {
            background-color: #d4edda;
            color: #155724;
            font-weight: bold;
        }

        .statut-impaye {
            background-color: #f8d7da;
            color: #721c24;
            font-weight: bold;
        }

        .no-results {
            color: red;
            text-align: center;
            margin: 20px;
            font-style: italic;
        }

        .btn-pdf {
            display: block;
            margin: 20px auto;
            padding: 12px 25px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
        }

        .btn-retour {
            background: #6c757d;
            color: white;
            padding: 10px 18px;
            text-decoration: none;
            border-radius: 6px;
        }
    </style>
</head>
<body>

<h2>🔍 Rechercher les paiements d’un étudiant</h2>

<div class="search-box">
    <input type="text" id="searchInput" placeholder="Tapez le nom ou prénom de l'étudiant..." autocomplete="off">
</div>

<div class="student-list" id="studentList">
    <c:forEach var="etudiant" items="${etudiantsLoges}">
        <div class="student-item" data-id="${etudiant.id}">

            <!-- FORMAT FINAL -->
            <span class="name">
                ${etudiant.prenom} ${etudiant.nom}
            </span>

            <span class="phone">
                ${etudiant.telephone != null && etudiant.telephone.trim() != '' ? etudiant.telephone : 'N/A'}
            </span>

        </div>
    </c:forEach>
</div>

<!-- Résultats des paiements -->
<c:if test="${not empty paiementsTrouves}">
    <h3 style="text-align:center;">
        Paiements de <strong>${etudiantTrouve.prenom} ${etudiantTrouve.nom}</strong>
    </h3>

    <table>
        <thead>
        <tr>
            <th>Montant</th>
            <th>Mois</th>
            <th>Année</th>
            <th>Statut</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="paiement" items="${paiementsTrouves}">
            <tr>
                <td>${paiement.montant} FCFA</td>
                <td>${paiement.mois}</td>
                <td>${paiement.annee}</td>
                <td class="${paiement.statut == 'PAYE' ? 'statut-paye' : 'statut-impaye'}">
                    ${paiement.statut}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="generer-pdf-paiements" method="post" style="text-align:center;">
        <input type="hidden" name="etudiantId" value="${etudiantTrouve.id}">
        <button type="submit" class="btn-pdf">📄 Générer PDF</button>
    </form>
</c:if>

<c:if test="${empty paiementsTrouves and param.etudiantId != null}">
    <p class="no-results">❌ Aucun paiement trouvé pour cet étudiant.</p>
</c:if>

<!-- ✅ BOUTON RETOUR CORRIGÉ -->
<a href="<%= request.getContextPath() %>/redirect-to-dashboard" class="btn-retour">
    ⬅ Retour
</a>

<script>
// Recherche dynamique
document.getElementById("searchInput").addEventListener("keyup", function() {
    const filter = this.value.toLowerCase();
    const items = document.querySelectorAll(".student-item");

    items.forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(filter) ? "" : "none";
    });
});

// Clique sur un étudiant
document.querySelectorAll(".student-item").forEach(item => {
    item.addEventListener("click", function() {
        const id = this.getAttribute("data-id");
        window.location.href = "paiement-par-etudiant?etudiantId=" + id;
    });
});
</script>

</body>
</html>