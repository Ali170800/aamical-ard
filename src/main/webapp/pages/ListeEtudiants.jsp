<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Étudiants</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f5f5f5; padding: 20px; margin: 0; }
        .top-buttons { display: flex; justify-content: flex-end; padding: 20px 30px 0 0; }
        .btn-retour {
            background-color: #006400; color: white; padding: 10px 18px; text-decoration: none;
            border-radius: 5px; font-weight: bold;
        }
        .search-container {
            margin: 20px 0; background: white; padding: 15px; border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        input[type="text"] {
            padding: 12px; width: 420px; font-size: 16px; border: 1px solid #ccc; border-radius: 4px;
        }
        .table-container { overflow-x: auto; background: white; padding: 10px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        table { min-width: 1000px; border-collapse: collapse; font-size: 14px; }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: center; }
        th { background-color: #007BFF; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #fff3cd; }

        .action-btn { padding: 6px 10px; text-decoration: none; border-radius: 4px; font-size: 13px; margin: 0 4px; }
        .modifier { background-color: #17a2b8; color: white; }
        .btn-supprimer {
            background-color: #dc3545; color: white; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer;
        }
        .pdf-btn {
            background-color: #28a745; color: white; padding: 10px 18px; border: none; border-radius: 5px; cursor: pointer; font-size: 14px;
        }
    </style>
</head>
<body>

<div class="top-buttons">
    <a href="pages/cahier.jsp" class="btn-retour">⬅ Retour au menu</a>
</div>

<h2>Liste des Étudiants</h2>

<div class="search-container">
    <h3>Recherche (ID, Nom ou Prénom)</h3>
    <input type="text" id="searchInput" placeholder="Tapez ID, Nom ou Prénom..."
           onkeyup="filterTable()" autofocus>

    <button onclick="exportCurrentView()" class="pdf-btn" style="margin-left: 15px;">
        📄 Exporter TOUTE la liste des étudiants en PDF
    </button>
</div>

<div class="table-container">
<table id="etudiantsTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Sexe</th>
        <th>Filière</th>
        <th>Niveau</th>
        <th>Année Univ.</th>
        <th>Téléphone</th>
        <th>Urgence</th>
        <th>Pathologie</th>
        <th>Adresse</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="e" items="${listeInitiale}">
        <tr>
            <td>${e.id}</td>
            <td>${e.nom}</td>
            <td>${e.prenom}</td>
            <td>${e.sexe}</td>
            <td>${e.filiere}</td>
            <td>${e.niveau}</td>
            <td>${e.anneeUniversitaire}</td>
            <td>${e.telephone}</td>
            <td>${e.numeroUrgence}</td>
            <td>${e.pathologie}</td>
            <td>${e.adresse}</td>
            <td>${e.email}</td>
            <td>
                <a class="action-btn modifier" href="modifierEtudiant?id=${e.id}">Modifier</a>
                <form action="supprimerEtudiant" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${e.id}" />
                    <button type="submit" class="btn-supprimer"
                            onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?');">
                        🗑
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>



<script>
function filterTable() {
    const input = document.getElementById("searchInput").value.trim();

    if (input === "") {
        document.querySelectorAll("#etudiantsTable tbody tr").forEach(row => row.style.display = "");
        return;
    }

    const rows = document.querySelectorAll("#etudiantsTable tbody tr");

    rows.forEach(row => {
        const cells = row.getElementsByTagName("td");
        const idText = cells[0] ? cells[0].textContent.trim() : "";
        const nomText = cells[1] ? cells[1].textContent.toLowerCase() : "";
        const prenomText = cells[2] ? cells[2].textContent.toLowerCase() : "";

        let found = false;

        if (/^\d+$/.test(input)) {
            if (idText === input) found = true;           // ID exact
        } else {
            if (nomText.includes(input) || prenomText.includes(input)) found = true;
        }

        row.style.display = found ? "" : "none";
    });
}

// Exporter la vue actuelle (en passant le terme de recherche)
function exportCurrentView() {
    const searchTerm = document.getElementById("searchInput").value.trim();

    if (searchTerm === "") {
        // Si rien n'est recherché, exporter tout
        window.location.href = "exporterRecherchePDF";
    } else {
        // Sinon, on passe le terme de recherche (le servlet doit le gérer)
        window.location.href = "exporterRecherchePDF?search=" + encodeURIComponent(searchTerm);
    }
}
</script>

</body>
</html>