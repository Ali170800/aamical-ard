<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Nouvelle publication</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

<div class="max-w-2xl mx-auto mt-10 bg-white p-8 rounded-2xl shadow">

    <h1 class="text-3xl font-bold mb-6">
        Nouvelle publication
    </h1>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/ajouter-publication"
          enctype="multipart/form-data">

        <div class="mb-4">
            <label class="block font-semibold mb-2">
                Description
            </label>

            <textarea
                    name="description"
                    required
                    class="w-full border rounded-xl p-4 h-40"></textarea>
        </div>

        <div class="mb-6">
            <label class="block font-semibold mb-2">
                Choisir une image
            </label>

            <input type="file"
                   name="image"
                   accept=".jpg,.jpeg,.png"
                   required
                   class="w-full border rounded-xl p-3">
        </div>

        <button
                class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl">
            Publier
        </button>

    </form>

</div>

</body>
</html>