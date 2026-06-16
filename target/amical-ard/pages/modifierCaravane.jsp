<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Modifier Caravane</title>
</head>
<body>

<h2>Modifier la Caravane</h2>

<c:if test="${not empty erreur}">
    <p style="color:red;">${erreur}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/caravane/modifier">
    <input type="hidden" name="id" value="${caravane.id}" />

    <label for="nom">Nom :</label>
    <input type="text" id="nom" name="nom" value="${caravane.nom}" required />
    <br><br>

    <label for="date">Date :</label>
    <input type="date" id="date" name="date" value="${caravane.date}" required />
    <br><br>

    <label for="montant">Montant :</label>
    <input type="number" step="0.01" id="montant" name="montant" value="${caravane.montant}" required />
    <br><br>

    <input type="submit" value="Enregistrer les modifications" />
</form>

<p><a href="${pageContext.request.contextPath}/caravane/lister">⬅ Retour à la liste des caravanes</a></p>

</body>
</html>