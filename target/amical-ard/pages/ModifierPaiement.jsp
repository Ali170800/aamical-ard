<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Modifier Paiement</title>
</head>
<body>
    <h2>Modifier le Paiement</h2>

    <form action="modifierPaiement" method="post">
        <input type="hidden" name="id" value="${paiement.id}" />

        Montant: <input type="number" name="montant" value="${paiement.montant}" step="0.01" required /><br/><br/>

        Mois: <input type="number" name="mois" value="${paiement.mois}" min="1" max="12" required /><br/><br/>

        Année: <input type="number" name="annee" value="${paiement.annee}" required /><br/><br/>

        Statut:
        <select name="statut">
            <option value="PAYE" ${paiement.statut == 'PAYE' ? 'selected' : ''}>Payé</option>
            <option value="IMPAYE" ${paiement.statut == 'IMPAYE' ? 'selected' : ''}>Impayé</option>
        </select><br/><br/>

        <input type="submit" value="Enregistrer les modifications" />
    </form>

    <br/><a href="listePaiements">← Retour à la liste des paiements</a>
</body>
</html>