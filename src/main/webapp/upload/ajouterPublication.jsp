<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Nouvelle publication</title>

    <script src="https://cdn.tailwindcss.com"></script>
</head>

<body class="bg-gray-100 p-4 sm:p-6 md:p-10">

<div class="max-w-2xl mx-auto mt-6 sm:mt-10 bg-white p-6 sm:p-8 rounded-2xl shadow">

    <h1 class="text-2xl sm:text-3xl font-bold mb-6 break-words">
        Nouvelle publication
    </h1>

    <form method="post"
          action="${pageContext.request.contextPath}/admin/ajouter-publication"
          enctype="multipart/form-data">

        <!-- DESCRIPTION -->
        <div class="mb-4">

            <label class="block font-semibold mb-2">
                Description
            </label>

            <textarea
                    name="description"
                    required
                    class="w-full border rounded-xl p-3 sm:p-4 h-32 sm:h-40 text-sm sm:text-base resize-none">
            </textarea>

        </div>

        <!-- IMAGE -->
        <div class="mb-6">

            <label class="block font-semibold mb-2">
                Choisir une image
            </label>

            <input type="file"
                   name="image"
                   accept=".jpg,.jpeg,.png"
                   required
                   class="w-full border rounded-xl p-3 text-sm sm:text-base bg-white">

        </div>

        <!-- BUTTON -->
        <button
                class="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl font-bold transition">

            Publier

        </button>

    </form>

</div>

</body>
</html>