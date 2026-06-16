<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier Membre Bureau</title>
</head>
<body>
<h2>Modifier le membre du bureau</h2>
<form method="post" action="${pageContext.request.contextPath}/bureau/modifier">
    <input type="hidden" name="id" value="${bureau.id}" />

    <p>Nom : <strong>${bureau.nom}</strong></p>
    <p>Prénom : <strong>${bureau.prenom}</strong></p>

    <label>Poste :</label>
    <input type="text" name="roleBureau" value="${bureau.roleBureau}" required /><br><br>

    <label>Année de mandat :</label>
    <input type="text" name="anneeMandat" value="${bureau.anneeMandat}" required /><br><br>

    <label>Email :</label>
    <input type="email" name="email" value="${bureau.email}" /><br><br>

    <label>Numéro :</label>
    <input type="text" name="numero" value="${bureau.numero}" /><br><br>

    <button type="submit">💾 Enregistrer</button>
</form>
</body>
</html>