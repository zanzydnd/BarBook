<meta charset="UTF-8">
<link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Alegreya+SC:wght@500&family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&family=Fira+Sans:wght@400;500&display=swap"
      rel="stylesheet">
<link rel="stylesheet" href="views/style.css">
<title>Cocktail Info</title>

<body>
<#include "menu.ftl">

<form class="create_cocktail_cont" enctype="multipart/form-data" action="/BarBookOriginal_war/redactCocktail?id=${cocktail.id}" method="post">
    <div class="cabinet__name-surname">
        <div class="cabinet__name">
            <label for="name" class="cabinet__name-label">Название</label>
            <input type="text" required name="name" id="name" class="cabinet__name-field" value="${cocktail.name}">
        </div>
        <input type="hidden" name="id_" value="${cocktail.id}">
        <div class="input__photo">
            <div class="img__big">
                <label for="image_big">Фото для главной страницы</label>
                <input type="file" id="image_big" name="bigPicture" accept="image/*">
            </div>
            <div class="img__small">
                <label for="image_small">Фото маленькое</label>
                <input type="file" id="image_small" name="smallPicture" accept="image/*">
            </div>
        </div>
    </div>

    <div class="cabinet__info">
        <label for="text" class="cabinet__text-label">Info</label>
        <textarea name="text" name="text" required id="text" class="cabinet__text" rows="10">${cocktail.inf}</textarea>
    </div>
    <div class="create">
        <button class="create__btn" name="button_l" id="button_l" type="submit">Создать</button>
    </div>
</form>

</body>