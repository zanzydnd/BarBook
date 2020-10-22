<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Alegreya+SC:wght@500&family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&family=Fira+Sans:wght@400;500&display=swap"
          rel="stylesheet">
    <title>Cocktail Info</title>
    <style><#include "style.css"></style>
</head>

<body>
<#include "menu.ftl">

<div class="coctail__info">
    <div class="container">
        <div class="coctail__inner">
            <div class="coctail__header">
                <div class="coctail__name">${ingridient.name}</div>
            </div>

            <div class="single-bg">
                <img src="/BarBookOriginal_war/img?image_path=${ingridient.img}">
            </div>

            <div class="single__about">
                ${ingridient.inf}
            </div>
        </div>
    </div>
</div>
<div class="coctail__info">
    <div class="container">
        <div class="coctail__inner">
            <div class="coctail__header single__header">
                <span class="coctail__name single__name">Коктейли c ${ingridient.name}</span>
            </div>
            <div class="single__inner-items">
                <#list cocktails as cocktail>
                    <div class="single__item">
                        <a href="/BarBookOriginal_war/cocktail?id=${cocktail.id}" class="single__item-photo">
                            <img src="/BarBookOriginal_war/img?image_path=${cocktail.smallImg}" alt="" class="single__cocktail-img">
                            <div class="single__cocktail-name">${cocktail.name}</div>
                        </a>
                        <div class="single__content-ingredient">
                            <#list cocktail.ingridients as ing>
                                <a href="/BarBookOriginal_war/ingridient?id=${ing.id}" class="single__ingredient-item">
                                    <img src="/BarBookOriginal_war/img?image_path=${ing.smallImg}" alt="" class="single__ingred-img">
                                    <div class="single__ingred-name">${ing.name}</div>
                                </a>
                            </#list>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</div>
</body>
</html>