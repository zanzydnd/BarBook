<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Alegreya+SC:wght@500&family=Fira+Sans:wght@400;500&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="views/style.css">
</head>
<body>

<#include "menu.ftl">

<div class="coctail__info">
    <div class="container">
        <div class="coctail__inner">
            <div class="coctail__header-new">
                <div class="coctail__name">Все бармены</div>
            </div>
            <div class="block__allbarmen">
                <#list barmen as barman>
                <#if user??>
                <#if barman.id == user.id>
                <a href="/BarBookOriginal_war/profile?id=self" class="barmen__item">
                    <#else>
                    <a href="/BarBookOriginal_war/profile?id=${barman.id}" class="barmen__item">
                        </#if>
                        <div class="barmen__photo">
                            <img src="/BarBookOriginal_war/img?image_path=${barman.img}" alt="" class="barmen__img">
                        </div>
                        <div class="barmen__name">${barman.name}</div>
                        <div class="barmen__surname">${barman.login}</div>
                        <div class="barmen__work">barman</div>
                    </a>
                    <#else>
                        <a href="/BarBookOriginal_war/profile?id=${barman.id}" class="barmen__item">
                            <div class="barmen__photo">
                                <img src="/BarBookOriginal_war/img?image_path=${barman.img}" alt="" class="barmen__img">
                            </div>
                            <div class="barmen__name">${barman.name}</div>
                            <div class="barmen__surname">${barman.login}</div>
                            <div class="barmen__work">barman</div>
                        </a>
                    </#if>
                    </#list>
            </div>
        </div>
    </div>
</div>

</body>
</html>