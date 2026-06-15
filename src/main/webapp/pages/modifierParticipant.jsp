<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier Participant</title>
</head>
<body>
<h2>Modifier le participant : ${participant.nom} ${participant.prenom}</h2>

<form method="post" action="${pageContext.request.contextPath}/participant/modifier">
    <input type="hidden" name="id" value="${participant.id}"/>

    <label for="statut">Statut de paiement :</label>
    <select name="statut" id="statut">
        <option value="PAYE" ${participant.statutPaiement == 'PAYE' ? 'selected' : ''}>Payé</option>
        <option value="Non_Paye" ${participant.statutPaiement == 'Non_Paye' ? 'selected' : ''}>Non Payé</option>
    </select>

    <br><br>
    <input type="submit" value="Modifier">
</form>
</body>
</html>