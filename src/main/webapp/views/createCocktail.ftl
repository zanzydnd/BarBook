<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Список</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="views/style.css">
    <script>
        var i = 1;

        $(function () {
            $('#plus_inp').click(function (event) {
                i++;
                $("<div>").html("<input required class=\"log recipe__text\" type=\"text\" value=\"" + i + ".\" name=\"1\" id=\"" + i + "\">").appendTo($("#input_plus"));
            });
        });

        $(function () {
            $('#min_inp').click(function (event) {
                console.log(i);
                console.log("#" + i);
                if (i > 1) {
                    $("#" + i).detach();
                    i--;
                }
            });
        });

    </script>
</head>
<body>
<#include "menu.ftl">
<form class="create_cocktail_cont" enctype="multipart/form-data" action="/BarBookOriginal_war/createCocktail" method="post">
    <div class="cabinet__name-surname">
        <div class="cabinet__name">
            <label for="name" class="cabinet__name-label">Название</label>
            <input type="text" required name="name" id="name" class="cabinet__name-field">
        </div>
        <div class="input__photo">
            <div class="img__big">
                <label for="image_big">Фото для главной страницы</label>
                <input type="file" required id="image_big" name="bigPicture" accept="image/*">
            </div>
            <div class="img__small">
                <label for="image_small">Фото маленькое</label>
                <input type="file" required id="image_small" name="smallPicture" accept="image/*">
            </div>
        </div>
    </div>

    <div class="cabinet__info">
        <label for="text" class="cabinet__text-label">Info</label>
        <textarea name="text" name="text" required id="text" class="cabinet__text" rows="10"></textarea>
    </div>

    <div class="ingred__item">
        <div class="ingred__block-photo">
            <#list ingridients as ingridient>
                <a href="/BarBookOriginal_war/ingridient?id=${ingridient.id}" class="ingred__block-item">
                    <img src="/BarBookOriginal_war/img?image_path=${ingridient.smallImg}" class="ingred-img" alt="">
                    <div class="ingred__name">${ingridient.name}</div>
                    <input type="checkbox" value="${ingridient.id}" name="ing" class="ingredient_check">
                </a>
            </#list>
        </div>
    </div>
    <div class="recipe">
        <h4 class="recipe__title">Распишите ваш рецепт по шагам</h4>

        <div class="button_change">
            <button name="plus_inp" id="plus_inp" class="plus_inp" type="button">+</button>
            <button name="min_inp" id="min_inp" type="button" class="plus_minus">-</button>
        </div>

        <input required class="log recipe__text" type="text" value="1." name="1">

        <div name="input_plus" id="input_plus">
        </div>
        <input hidden value="${user.id}" name="user_id">
    </div>
    <h4 class="recipe__title">Укажите теги для вашего коктейля</h4>
    <div class="filters">
        <select id="filter_cat" name="alco" class="filter__item">
            <option value="">Алкоголь</option>
            <option name="types[]" value="виски">Виски</option>
            <option name="types[]" value="ром">Ром</option>
            <option name="types[]" value="водка">Водка</option>
            <option name="types[]" value="джин">Джин</option>
        </select>

        <select id="filter_ing" name="taste" class="filter__item">
            <option value="">Вкус</option>
            <option name="types[]" value="сладкий">Сладкий</option>
            <option name="types[]" value="свежий">Свежий</option>
            <option name="types[]" value="кофейные">Кофейный</option>
            <option name="types[]" value="сливочные">Сливочный</option>
            <option name="types[]" value="терпкий">Терпкий</option>
        </select>

        <select id="filter_dog" name="krep" class="filter__item">
            <option value="">Градус</option>
            <option name="types[]" value="безалкогольные">Безалкогольные</option>
            <option name="types[]" value="крепкие">Крепкие</option>
            <option name="types[]" value="слабоалкогольные">Слабоалкогольные</option>
        </select>
    </div>
    <div class="create">
        <button class="create__btn" name="button_l" id="button_l" type="submit">Создать</button>
    </div>
</form>
</body>
</html>