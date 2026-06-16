<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Liste des étudiants logés</title>
    <style>
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #aaa;
            text-align: left;
        }
        th {
            background-color: #0077cc;
            color: white;
        }
        .btn-supprimer {
            background-color: #dc3545;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filters {
            width: 90%;
            margin: 20px auto;
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        .stats {
            width: 90%;
            margin: 20px auto;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #ddd;
        }
        .search-input {
            padding: 10px;
            width: 350px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        .btn {
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            color: white;
            margin-right: 8px;
        }
    </style>
</head>
<body>

<div style="width: 90%; margin: 20px auto; display: flex; justify-content: space-between; align-items: center;">
    <h2>Étudiants Logés</h2>
    <div>
        <a href="<%= request.getContextPath() %>/formulaire-logement"
           style="background:#28a745;" class="btn">
            ➕ Ajouter un logement
        </a>

        <a href="<%= request.getContextPath() %>/generer-pdf-logements?appartementId=${empty appartementIdSelectionne ? 'tous' : appartementIdSelectionne}&recherche=${recherche != null ? recherche : ''}"
           style="background:#17a2b8;" class="btn" target="_blank">
            📄 Exporter en PDF
        </a>

        <!-- ✅ Bouton corrigé (redirige selon rôle) -->
        <a href="<%= request.getContextPath() %>/redirect-to-dashboard"
           style="background:#6c757d;" class="btn">
            🏠 Retour au menu
        </a>
    </div>
</div>

<!-- Filtres -->
<div class="filters">
    <form method="get" action="<%= request.getContextPath() %>/liste-logements">
        <select name="appartementId" onchange="this.form.submit()">
            <option value="tous" ${empty appartementIdSelectionne || appartementIdSelectionne == 'tous' ? 'selected' : ''}>
                Tous les appartements
            </option>
            <c:forEach var="app" items="${appartements}">
                <option value="${app.id}" ${appartementIdSelectionne == app.id.toString() ? 'selected' : ''}>
                    ${app.nomAppartement}
                </option>
            </c:forEach>
        </select>

        <input type="text" id="recherche" name="recherche"
               value="${recherche}"
               placeholder="Rechercher par nom ou prénom..."
               class="search-input"
               onkeyup="filtrerTable()">

        <button type="submit" style="padding:10px 20px;">Filtrer</button>
    </form>
</div>

<!-- Tableau -->
<table>
    <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Téléphone</th>
            <th>Email</th>
            <th>Appartement</th>
            <th>Adresse</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="logement" items="${logements}">
        <tr>
            <td>${logement.etudiant.nom}</td>
            <td>${logement.etudiant.prenom}</td>
            <td>${logement.etudiant.telephone}</td>
            <td>${logement.etudiant.email}</td>
            <td>${logement.appartement.nomAppartement}</td>
            <td>${logement.appartement.description}</td>
            <td>
                <form action="supprimer-logement" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${logement.id}" />
                    <button type="submit" class="btn-supprimer"
                            onclick="return confirm('Supprimer ce logement ?')">Supprimer</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Résumé par appartement -->
<div class="stats">
    <h3>Résumé par Appartement</h3>
    <ul>
        <c:forEach var="entry" items="${statsParAppartement}">
            <li><strong>${entry.key}</strong> : ${entry.value} étudiant(s)</li>
        </c:forEach>
    </ul>
    <p><strong>Total :</strong> ${logements.size()} étudiants logés</p>
</div>

<script>
    function filtrerTable() {
        const input = document.getElementById("recherche").value.toLowerCase().trim();
        const rows = document.querySelectorAll("tbody tr");

        rows.forEach(row => {
            const nom = (row.cells[0] ? row.cells[0].textContent : "").toLowerCase();
            const prenom = (row.cells[1] ? row.cells[1].textContent : "").toLowerCase();
            const appartement = (row.cells[4] ? row.cells[4].textContent : "").toLowerCase();

            if (nom.includes(input) || prenom.includes(input) || appartement.includes(input)) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }
</script>

</body>
</html>