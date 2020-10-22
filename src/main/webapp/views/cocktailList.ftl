<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <link rel="stylesheet" href="style.css">
    <style><#include "style.css"></style>
    <title>List</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(function () {
            //console.log(2);
            $('#button').click(function (event) {
                var search = $('#search').val();
                console.log(search);
                var tags = [];
                var i = 0;
                var select1 = $("#filter_cat option:selected").val();
                if(select1 === ""){
                }
                else{
                    tags[i] = select1;
                    i++;
                }
                var select2 = $("#filter_ing option:selected").val();
                if(select2 === ""){
                }
                else{
                    tags[i] = select2;
                    i++;
                }
                var select3 = $("#filter_dog option:selected").val();
                if(select3 === ""){
                }
                else{
                    tags[i] = select3;
                    i++;
                }
                console.log(tags);
                $.ajax({
                    type: "POST", url: "Servlets.SearchServlet",
                    data: {
                        search: search,
                        tags: JSON.stringify(tags)
                    },
                    dataType: 'json',
                    success: function (result) {
                        $("#delete").detach();
                        $("<div class = \"cocktail__item-inner>\" id=\"delete\"></div>").appendTo($("#vstavka"));
                        for (var i = 0; i < result.length; i++) {
                            $("<div class= \"cocktail__img\">").html("<img src=\"" + result[i]['img'] +"\" class=\"cocktails__photo\" alt=\"\"/></div>" ).appendTo($("#delete"));
                            $("<div class=\"cocktails__content\">").html("<h4 class=\"cocktail__name\">" + result[i]['name'] + "</h4>" + "<a  class=\"readmore\" href=\"/BarBookOriginal_war/cocktail?id=" + result[i]['id'] + "\">Read more</a>").appendTo($("#delete"));
                        }
                    }
                });
            });
        });
    </script>
</head>


<body>
<#include "menu.ftl">

<div class="cocktails">
    <div class="container">
        <div class="search__system">
            <form id="filter" class="filter">
                <input type="search" id= "search" class="search__field" placeholder="Search...">

                <select id="filter_cat" class="filter__item">
                    <option value="">Алкоголь</option>
                    <option name="types[]" value="виски">Виски</option>
                    <option name="types[]" value="ром">Ром</option>
                    <option name="types[]" value="водка">Водка</option>
                    <option name="types[]" value="джин">Джин</option>
                </select>

                <select id="filter_ing" class="filter__item">
                    <option value="">Вкус</option>
                    <option name="types[]" value="сладкий">Сладкий</option>
                    <option name="types[]" value="свежий">Свежий</option>
                    <option name="types[]" value="терпкий">Терпкий</option>
                </select>

                <select id="filter_dog" class="filter__item">
                    <option value="">Градус</option>
                    <option name="types[]" value="Безалкогольные">Безалкогольные</option>
                    <option name="types[]" value="Крепкие">Крепкие</option>
                    <option name="types[]" value="Слабоалкогольные">Слабоалкогольные</option>
                </select>
                <button id="button" class="filter__btn search__btn" type="button">Поиск</button>
            </form>
        </div>

        <div class="cocktails__inner">
            <div class="cocktail__item" id="vstavka">
                <div class="cocktail__item-inner" id ="delete">
                    <#list cocktails as cocktail>
                        <div class="cocktail__img">
                            <img src="${cocktail.img}" class="cocktails__photo" alt=""/>
                        </div>
                        <div class="cocktails__content">
                            <h4 class="cocktail__name">${cocktail.name}</h4>
                            <a class="readmore" href="/BarBookOriginal_war/cocktail?id=${cocktail.id}">Read more</a>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>