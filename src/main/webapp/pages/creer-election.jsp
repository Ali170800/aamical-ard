<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr" class="h-full bg-gray-50">
<head>
    <meta charset="UTF-8">
    <title>Créer une élection - Administration</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="p-10">
<div class="max-w-2xl mx-auto bg-white p-8 rounded-3xl shadow-xl">
    <h1 class="text-2xl font-black mb-6">Créer une nouvelle élection</h1>
    <form action="<%= request.getContextPath() %>/admin/creer-election" method="POST" class="space-y-6">
        <div>
            <label class="block text-xs font-bold uppercase mb-2">Titre de l'élection</label>
            <input type="text" name="titre" required class="w-full bg-gray-50 border rounded-xl py-3 px-4 outline-none">
        </div>
        <div class="grid grid-cols-2 gap-4">
            <div><label class="block text-xs font-bold uppercase mb-2">Date début</label><input type="datetime-local" name="dateDebut" required class="w-full bg-gray-50 border rounded-xl py-3 px-4"></div>
            <div><label class="block text-xs font-bold uppercase mb-2">Date fin</label><input type="datetime-local" name="dateFin" required class="w-full bg-gray-50 border rounded-xl py-3 px-4"></div>
        </div>

        <div id="candidats-container" class="space-y-4">
            <h2 class="text-sm font-bold border-t pt-4">Liste des candidats</h2>
            <div class="grid grid-cols-2 gap-4">
                <input type="text" name="nom" placeholder="Nom" required class="bg-gray-50 border rounded-xl py-2 px-4">
                <input type="text" name="prenom" placeholder="Prénom" required class="bg-gray-50 border rounded-xl py-2 px-4">
            </div>
        </div>
        <button type="button" onclick="ajouterCandidat()" class="text-indigo-600 text-sm font-bold">+ Ajouter un candidat</button>

        <button type="submit" class="w-full py-3 bg-indigo-600 text-white font-bold rounded-xl">Créer l'élection</button>
    </form>
</div>
<script>
function ajouterCandidat() {
    const container = document.getElementById('candidats-container');
    const div = document.createElement('div');
    div.className = "grid grid-cols-2 gap-4";
    div.innerHTML = '<input type="text" name="nom" placeholder="Nom" required class="bg-gray-50 border rounded-xl py-2 px-4"><input type="text" name="prenom" placeholder="Prénom" required class="bg-gray-50 border rounded-xl py-2 px-4">';
    container.appendChild(div);
}
</script>
</body>
</html>