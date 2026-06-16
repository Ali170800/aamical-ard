<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ajouter des participants à une caravane</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f2f4f7;
            margin: 0;
            padding: 20px;
        }
        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            max-width: 800px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #004080;
            margin-bottom: 25px;
        }
        .search-box {
            text-align: center;
            margin-bottom: 20px;
        }
        #searchInput {
            padding: 12px 15px;
            font-size: 16px;
            width: 80%;
            border: 2px solid #0066cc;
            border-radius: 8px;
            outline: none;
        }
        .students-container {
            max-height: 450px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            background: #fafafa;
            margin-bottom: 20px;
        }
        .student-item {
            padding: 12px 15px;
            margin: 6px 0;
            background: white;
            border: 1px solid #eee;
            border-radius: 8px;
            display: flex;
            align-items: center;
            font-size: 15.5px;
        }
        .student-item:hover {
            background: #f0f8ff;
        }
        .name {
            font-weight: bold;
            color: #222;
            margin-right: 15px;
        }
        .phone {
            color: #666;           /* Moins foncé comme demandé */
            font-weight: normal;
            min-width: 120px;
        }
        label[for] {
            margin-left: 10px;
            cursor: pointer;
            flex: 1;
        }
        select, button {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border-radius: 6px;
            font-size: 16px;
        }
        button {
            background-color: #0066cc;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
            padding: 14px;
        }
        button:hover {
            background-color: #004d99;
        }
        .actions {
            display: flex;
            justify-content: space-between;
            margin-top: 25px;
        }
        .actions a {
            text-decoration: none;
            padding: 10px 18px;
            background: #ccc;
            color: black;
            border-radius: 6px;
        }
        .success { color: green; text-align: center; margin: 10px 0; font-weight: bold; }
        .error { color: red; text-align: center; margin: 10px 0; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <h2>➕ Ajouter des participants à une caravane</h2>

    <form action="${pageContext.request.contextPath}/participant/ajouter" method="post">

        <div class="search-box">
            <input type="text" id="searchInput" placeholder="Rechercher par nom, prénom ou téléphone...">
        </div>

        <label><strong>Étudiants :</strong></label>
        <div class="students-container" id="studentsList">
            <c:forEach var="etudiant" items="${etudiants}">
                <div class="student-item">
                    <input type="checkbox" name="etudiantIds" value="${etudiant.id}" id="etud_${etudiant.id}">

                    <!-- Format demandé : Nom Prénom en gras + Téléphone moins foncé -->
                    <span class="name">${etudiant.prenom} ${etudiant.nom}</span>
                    <span class="phone">
                        ${etudiant.telephone != null && etudiant.telephone.trim() != '' ? etudiant.telephone : 'N/A'}
                    </span>

                    <label for="etud_${etudiant.id}"></label>
                </div>
            </c:forEach>
        </div>

        <label for="caravane">Caravane :</label>
        <select name="caravaneId" id="caravane" required>
            <option value="">-- Choisir une caravane --</option>
            <c:forEach var="caravane" items="${caravanes}">
                <option value="${caravane.id}">${caravane.nom} (${caravane.montant} FCFA)</option>
            </c:forEach>
        </select>

        <label for="statut">Statut du paiement :</label>
        <select name="statut" id="statut" required>
            <option value="PAYE">Payé</option>
            <option value="Non_Paye">Non Payé</option>
        </select>

        <button type="submit">✅ Ajouter les participants sélectionnés</button>
    </form>

    <c:if test="${not empty success}">
        <p class="success">${success}</p>
    </c:if>
    <c:if test="${not empty erreur}">
        <p class="error">${erreur}</p>
    </c:if>

   <div class="actions">

       <a href="${pageContext.request.contextPath}/redirect-to-dashboard">
           ⬅ Retour au menu
       </a>

       <a href="${pageContext.request.contextPath}/participants/selectionner">
           👥 Voir participants
       </a>

   </div>

<script>
// Recherche dynamique
document.getElementById('searchInput').addEventListener('keyup', function() {
    const filter = this.value.toLowerCase().trim();
    const items = document.querySelectorAll('.student-item');

    items.forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(filter) ? 'flex' : 'none';
    });
});
</script>
</body>
</html>