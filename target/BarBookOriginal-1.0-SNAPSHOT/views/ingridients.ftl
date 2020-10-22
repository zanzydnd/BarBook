<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Alegreya+SC:wght@500&family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&family=Fira+Sans:wght@400;500&display=swap"
          rel="stylesheet">
    <style><#include "style.css"></style>
    <title>Cocktail Info</title>
</head>

<body>
<#include "menu.ftl">
<div class="coctail__info">
    <div class="container">
        <div class="coctail__inner ingred__inner">
            <form id="search" class="filter">
                <input type="search" id="search" class="search__field" placeholder="Search...">
                <button type="submit" id="button">Поиск</button>
            </form>
            <div class="ingred__item">
                <div class="ingred__block-photo">
                <#list ingridients as ingridient>
                    <a href="/BarBookOriginal_war/ingridient?id=${ingridient.id}" class="ingred__block-item">
                        <img src="${ingridient.img}" class="ingred-img" alt="">
                        <div class="ingred__name">${ingridient.name}</div>
                    </a>
                </#list>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>