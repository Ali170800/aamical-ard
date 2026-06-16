<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ajouter un membre du bureau</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f4f7fa;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1100px;
            margin: 0 auto;
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        h2 {
            color: #0b5394;
            text-align: center;
            margin-bottom: 20px;
        }

        .search-box {
            width: 100%;
            padding: 12px 15px;
            font-size: 16px;
            border: 2px solid #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        th {
            background-color: #0b5394;
            color: white;
        }
        tr:hover {
            background-color: #f8fbff;
        }

        .btn-ajouter {
            background: #28a745;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-ajouter:hover {
            background: #218838;
        }

        .message {
            padding: 12px;
            margin: 15px 0;
            border-radius: 6px;
            font-weight: bold;
        }
        .success { background: #d4edda; color: #155724; }
        .error { background: #f8d7da; color: #721c24; }

        select, input[type="text"] {
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
    </style>
    <script>
        function filtrerEtudiants() {
            const input = document.getElementById("recherche").value.toLowerCase();
            const rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(input) ? "" : "none";
            });
        }
    </script>
</head>
<body>

<div class="container">
    <h2>➕ Ajouter un membre du bureau</h2>

    <c:if test="${not empty success}">
        <div class="message success">${success}</div>
    </c:if>
    <c:if test="${not empty erreur}">
        <div class="message error">${erreur}</div>
    </c:if>

    <!-- Champ de recherche -->
    <input type="text" id="recherche" class="search-box"
           placeholder="🔍 Rechercher par nom, prénom ou email..."
           onkeyup="filtrerEtudiants()" autofocus>

    <table>
        <thead>
            <tr>
                <th>Prénom</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Téléphone</th>
                <th>Rôle</th>
                <th>Année du Mandat</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="etudiant" items="${etudiants}">
            <tr>
                <td>${etudiant.prenom}</td>
                <td>${etudiant.nom}</td>
                <td>${etudiant.email}</td>
                <td>${etudiant.telephone}</td>

                <!-- Formulaire individuel par étudiant -->
                <form method="post" action="${pageContext.request.contextPath}/bureau/ajouter" style="display: contents;">
                    <input type="hidden" name="etudiantId" value="${etudiant.id}">

                    <td>
                        <select name="roleBureau" required>
                            <option value="">-- Rôle --</option>
                            <option value="Président">Président</option>
                            <option value="Vice-Présidente">Vice-Présidente</option>
                            <option value="Secrétaire Général">Secrétaire Général</option>
                            <option value="Adjoint (Secrétaire Général)">Adjoint (Secrétaire Général)</option>
                            <option value="Trésorier">Trésorier</option>
                            <option value="Adjoint (Trésorier)">Adjoint (Trésorier)</option>
                            <option value="Commissaire aux comptes">Commissaire aux comptes</option>
                            <option value="Présidente Commission Sociale">Présidente Commission Sociale</option>
                            <option value="Adjoint (Commission Sociale)">Adjoint (Commission Sociale)</option>
                            <option value="Président Commission Pédagogique">Président Commission Pédagogique</option>
                            <option value="Adjoint (Commission Pédagogique)">Adjoint (Commission Pédagogique)</option>
                            <option value="Président Commission d’Organisation">Président Commission d’Organisation</option>
                            <option value="Adjoint (Commission d’Organisation)">Adjoint (Commission d’Organisation)</option>
                            <option value="Président Commission Sportive">Président Commission Sportive</option>
                            <option value="Adjoint (Commission Sportive)">Adjoint (Commission Sportive)</option>
                            <option value="Présidente Section Féminine">Présidente Section Féminine</option>
                            <option value="Adjointe (Section Féminine)">Adjointe (Section Féminine)</option>
                            <option value="Chargée de Communication">Chargée de Communication</option>
                            <option value="Chargé de Relations extérieures">Chargé de Relations extérieures</option>
                            <option value="Comité des sages">Comité des sages</option>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="anneeMandat" value="2025-2026" required
                               style="width: 110px;">
                    </td>
                    <td>
                        <button type="submit" class="btn-ajouter">Ajouter au bureau</button>
                    </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div style="margin-top: 30px; text-align: center;">
        <a href="${pageContext.request.contextPath}/bureau/selectionner"
           style="padding: 12px 25px; background: #0b5394; color: white; text-decoration: none; border-radius: 6px;">
            Voir la liste des membres du bureau
        </a>
    </div>
</div>

</body>
</html>