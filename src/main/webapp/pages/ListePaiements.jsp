<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Liste des Paiements</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f2f2f2; margin: 20px; }
        h2, h3 { color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; background-color: #fff; }
        th, td { padding: 12px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #004d99; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .statut-paye { color: green; font-weight: bold; }
        .statut-impaye { color: red; font-weight: bold; }
        .btn { padding: 8px 14px; border-radius: 4px; text-decoration: none; color: white; font-size: 14px; border: none; cursor: pointer; }
        .btn-ajouter { background-color: #007bff; }
        .btn-retour { background-color: #6c757d; }
        .btn-modifier { background-color: #28a745; }
        .btn-supprimer { background-color: #dc3545; }
        .actions { display: flex; gap: 8px; justify-content: center; }
        .message-success { background: #d4edda; color: #155724; padding: 12px; border-radius: 5px; margin-bottom: 20px; }
        .message-error { background: #f8d7da; color: #721c24; padding: 12px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>

<body>

<div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px;">
    <h2>📋 Liste complète des paiements</h2>
    <div style="display:flex; gap:10px;">
        <a href="<%= request.getContextPath() %>/afficherFormulairePaiement" class="btn btn-ajouter">➕ Ajouter un paiement</a>
        <a href="<%= request.getContextPath() %>/redirect-to-dashboard" class="btn btn-retour">↩ Retour</a>
    </div>
</div>

<c:if test="${not empty success}"><div class="message-success">${success}</div></c:if>
<c:if test="${not empty erreur}"><div class="message-error">${erreur}</div></c:if>

<div style="margin-bottom: 15px;">
    <input type="text" id="searchInput" onkeyup="filtrerTableau()"
           placeholder="🔍 Rechercher un étudiant par nom ou prénom..."
           style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
</div>

<form action="<%= request.getContextPath() %>/supprimerMultiplePaiements" method="post" id="formSuppression">
    <div style="margin-bottom: 10px;">
        <button type="submit" class="btn btn-supprimer" onclick="return confirm('Voulez-vous vraiment supprimer les paiements sélectionnés ?');">
            🗑 Supprimer la sélection
        </button>
    </div>

    <table>
        <thead>
            <tr>
                <th><input type="checkbox" onclick="toggleAll(this)"></th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Montant</th>
                <th>Mois</th>
                <th>Année</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="tableBody">
            <c:forEach var="paiement" items="${paiements}">
                <tr>
                    <td><input type="checkbox" name="ids" value="${paiement.id}"></td>
                    <td>${paiement.logementEtudiant.etudiant.nom}</td>
                    <td>${paiement.logementEtudiant.etudiant.prenom}</td>
                    <td>${paiement.montant} FCFA</td>
                    <td>${paiement.mois}</td>
                    <td>${paiement.annee}</td>
                    <td class="${paiement.statut == 'PAYE' ? 'statut-paye' : 'statut-impaye'}">${paiement.statut}</td>
                    <td>
                        <div class="actions">
                            <a href="<%= request.getContextPath() %>/modifierPaiement?id=${paiement.id}" class="btn btn-modifier">Modifier</a>
                            <a href="<%= request.getContextPath() %>/supprimerPaiement?id=${paiement.id}" class="btn btn-supprimer" onclick="return confirm('Supprimer ce paiement ?');">Supprimer</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</form>

<script>
    // Sélection multiple
    function toggleAll(source) {
        let checkboxes = document.getElementsByName('ids');
        for(let i=0; i<checkboxes.length; i++) checkboxes[i].checked = source.checked;
    }

    // Filtrage dynamique (WhatsApp style)
    function filtrerTableau() {
        let filter = document.getElementById("searchInput").value.toUpperCase();
        let tr = document.getElementById("tableBody").getElementsByTagName("tr");
        for (let i = 0; i < tr.length; i++) {
            let nom = tr[i].getElementsByTagName("td")[1].textContent.toUpperCase();
            let prenom = tr[i].getElementsByTagName("td")[2].textContent.toUpperCase();
            tr[i].style.display = (nom.indexOf(filter) > -1 || prenom.indexOf(filter) > -1) ? "" : "none";
        }
    }
</script>

<div class="section">
    <h3>🔍 Recherche avancée (Filtrer les paiements)</h3>
    <form method="post" action="recherchePaiement">
        <label>Mois :</label>
        <select name="mois">
            <option value="">--Tous--</option>
            <c:forEach var="i" begin="1" end="12"><option value="${i}" ${moisRecherche == i ? 'selected' : ''}>${i}</option></c:forEach>
        </select>
        <label>Année :</label>
        <select name="annee">
            <option value="">--Toutes--</option>
            <c:forEach var="i" begin="2023" end="2035"><option value="${i}" ${anneeRecherche == i ? 'selected' : ''}>${i}</option></c:forEach>
        </select>
        <label>Statut :</label>
        <select name="statut">
            <option value="">--Tous--</option>
            <option value="PAYE" ${statutRecherche == 'PAYE' ? 'selected' : ''}>Payé</option>
            <option value="IMPAYE" ${statutRecherche == 'IMPAYE' ? 'selected' : ''}>Impayé</option>
        </select>
        <input type="submit" value="🔎 Rechercher">
    </form>
</div>

<c:if test="${not empty resultatsFiltrage}">
    <div class="section">
        <h3>📄 Résultat du filtre</h3>
        <table>
            <tr><th>Nom</th><th>Prénom</th><th>Montant</th><th>Mois</th><th>Année</th><th>Statut</th><th>Appartement</th></tr>
            <c:forEach var="paiement" items="${resultatsFiltrage}">
                <tr>
                    <td>${paiement.logementEtudiant.etudiant.nom}</td>
                    <td>${paiement.logementEtudiant.etudiant.prenom}</td>
                    <td>${paiement.montant} FCFA</td>
                    <td>${paiement.mois}</td>
                    <td>${paiement.annee}</td>
                    <td class="${paiement.statut == 'PAYE' ? 'statut-paye' : 'statut-impaye'}">${paiement.statut}</td>
                    <td>${paiement.logementEtudiant.appartement.nomAppartement}</td>
                </tr>
            </c:forEach>
        </table>
        <form method="post" action="generer-pdf">
            <input type="hidden" name="mois" value="${moisRecherche}">
            <input type="hidden" name="annee" value="${anneeRecherche}">
            <input type="hidden" name="statut" value="${statutRecherche}">
            <input type="submit" value="📄 Générer PDF des résultats">
        </form>
    </div>
</c:if>

</body>
</html>