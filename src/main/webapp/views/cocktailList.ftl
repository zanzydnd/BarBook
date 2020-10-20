<!DOCTYPE html>
<html lang="en">
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

                var tags = [];

                var eventTypes = document.forms['filter'].elements['types[]'];
                var len = eventTypes.length;

                for (var i = 0; i < len; i++) {
                    if (eventTypes[i].checked) {
                        tags.push($(eventTypes[i]).val());
                    }
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
                        for (var i = 0; i < result.length; i++) {
                            $("<div>").html("<a href=\"/BarBookOriginal_war/cocktail?id=" + result[i]['id'] + "\">" + result[i]['name'] + "</a>").appendTo($("#result"));
                        }
                    }
                });
            });
        });
    </script>
</head>


<#include "menu.ftl">


<form id="filter">
    <table>
        <th>По вкусу:</th>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="outside" value="сладкий">
                    <label class="form-check-label" for="outside">Сладкие</label>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="music" value="терпкий">
                    <label class="form-check-label" for="music">Горькие</label>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="art" value="свежий">
                    <label class="form-check-label" for="art">Свежие</label>
                </div>
            </td>
        </tr>
        <th>По алкоголю:</th>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="early" value="джин">
                    <label class="form-check-label" for="early">Джин</label>

                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="middle" value="водка">
                    <label class="form-check-label" for="middle">Водка</label>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="evening" value="виски">
                    <label class="form-check-label" for="evening">Виски</label>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="types[]" type="checkbox" id="rum" value="ром">
                    <label class="form-check-label" for="rum">Ром</label>
                </div>
            </td>
        </tr>
    </table>
    <input name="search" type="text" id="search">
    <button type="button" id="button" name="button">Button</button>
</form>
</html>

<div id="result">

</div>



<div class="cocktails">
    <div class="container">
        <div class="cocktails__inner">

            <div class="cocktail__item">
                <div class="cocktail__item-inner">
                    <#list cocktails as cocktail>
                    <div class="cocktail__img">
                        <img src="${cocktail.img}" class="cocktails__photo" alt=""/>
                    </div>
                    <div class="cocktails__content">
                        <h4 class="cocktail__name">${cocktail.name}</h4>
                        <p class="cocktails__info">${cocktail.inf}</p>
                        <a class="readmore" href="/BarBookOriginal_war/cocktail?id=${cocktail.id}">Read more</a>
                    </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>

<body>