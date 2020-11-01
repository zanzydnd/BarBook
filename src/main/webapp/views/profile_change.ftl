<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Alegreya+SC:wght@500&family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&family=Fira+Sans:wght@400;500&display=swap"
          rel="stylesheet">
    <title>Cocktail Info</title>
    <link rel="stylesheet" href="views/style.css">
</head>

<body>

<#include "menu.ftl">


<div class="cabinet">
    <div class="container">
        <form enctype="multipart/form-data" method="post" class="cabinet__inner" action="/BarBookOriginal_war/change_profile">
            <div class="cabinet__avatar">
                <img src="/BarBookOriginal_war/img?image_path=${user.img}" alt="" class="cabinet__img">
                <span>
                    <input type="file" name="image" accept="image/*" class="change__photo">
                </span>
            </div>

            <div class="cabinet__date">
                <div class="cabinet__name-surname">
                    <div class="cabinet__name">
                        <label for="name" class="cabinet__name-label">Name</label>
                        <input type="text" required name= "name" id="name" class="cabinet__name-field" value="${user.name}">
                    </div>
                </div>

                <div class="cabinet__info">
                    <label for="text" class="cabinet__text-label">Info</label>
                    <textarea name="text" name="text" required id="text" class="cabinet__text" rows="10">${user.information}</textarea>
                </div>

                <div class="cabinet__btn">
                    <button class="cabinet__save" type="submit">Save</button>
                </div>
            </div>
        </form>
        <#if errMsg??>
            <p>${errMsg}</p>
        </#if>
    </div>
</div>
</body>
</html>