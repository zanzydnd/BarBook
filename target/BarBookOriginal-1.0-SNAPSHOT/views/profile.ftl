<#import "macros_for_list.ftl" as Cockt_list>

<!DOCTYPE html>
<html lang="en">

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
                <div class="coctail__name">${user_profile.name}</div>
            </div>

            <div class="single-bg">
                <img src="/BarBookOriginal_war/img?image_path=${user_profile.img}" class="single_image">
            </div>

            <div class="single__about">
                ${user_profile.information}
            </div>
        </div>
    </div>
</div>
<@Cockt_list.Cockt_list cocktail_list = user_profile.favCocktails>
    <#if check=="self">
        <a href="/BarBookOriginal_war/change_profile">Редактировать профиль</a>
    </#if>
</@Cockt_list.Cockt_list>
</body>
</html>