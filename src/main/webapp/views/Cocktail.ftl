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
                <div class="coctail__name">${cocktail.name}</div>
                <#if user??>
                <div class="statistika">
                    <form class="stat__like" action="/BarBookOriginal_war/cocktail?id=${cocktail.id}" method="post">
                        <button type="submit" name="likedCocktId" value="${cocktail.id}">Мне нравится ${cocktail.rating}</button>
                    </form>
                        <#if errMsg??>
                            <a>Вы уже оставляли лайк</a>
                        </#if>
                    <form class="stat__like" method="post" action="/BarBookOriginal_war/favcockt">
                        <button type="submit" name= "favcocktid" value="${cocktail.id}">Мой любимый коктейль</button>
                    </form>
                </div>
                <#else>
                    <a href="/BarBookOriginal_war/auth" class="stat__like">Чтобы оставить лайк авторизируйтесь.</a>
                </#if>
            </div>

            <div class="cocktail-bg">
                <img src="${cocktail.img}" alt="" class="cocktail-img">
            </div>

            <div class="cocktail__content">

                <span class="coctail__sostav">Состав коктейля <b class="helper">${cocktail.name}</b></span>

                <div class="coctail__block-photo">
                    <#list ingridients as ingridient>
                        <a href="/BarBookOriginial_war/ingridient?id=${ingridient.id}" class="cocktail-ingredPhoto">
                            <img src="${ingridient.img}" alt="" class="coctail__block-img">
                        </a>
                    </#list>
                </div>

                <div class="recipe">
                    <span class="recipe__title">Рецепт коктейля <b class="helper">${cocktail.name}</b></span>
                    <div class="recipe__info">
                        <div class="recipe__list">
                            <#list str as s>
                                <div class="recipe__list-item">
                                    <div class="recipe__list-count">--</div>
                                    <div class="recipe__list-name">${s}</div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>

                <div class="description">
                    <span class="recipe__title">Описание коктейля <b class="helper">${cocktail.name}</b></span>
                    <div class="description__text">
                        ${cocktail.inf}
                    </div>
                </div>
            </div>


            <div class="comments">
                <div class="comments__header">
                    <div class="comments__title">Комментарии</div>
                </div>
                <div class="comments__inner">
                    <div class="comments__block-text">
                        <#if user??>
                            <form action="/BarBookOriginal_war/CommentServlet" method="post" class="send__comment">
                                <textarea class="text__comment" name="comment" id="text" rows="5"
                                          placeholder="Оставьте ваш комментарий"></textarea>
                                <input type="hidden" name="cocktail_id" value="${cocktail.id}">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <div class="btn-comm">
                                    <button type="submit" class="comments__btn">Отправить</button>
                                </div>
                            </form>
                        <#else>
                            <div class="comments__text">Чтобы принять участие в диалоге, необходимо
                                <a href="/auth" class="color-helper">авторизоваться</a>.
                            </div>
                        </#if>
                        <div class="comments__list">
                            <#list comments as comment>
                                <div class="comments__list-item">
                                    <div class="comments__avatar">
                                        <img src="${comment.user.img}" alt="" class="comments__avatar-img">
                                    </div>
                                    <div class="comments__info">
                                        <div class="comment__head">
                                            <div class="comments__name">${comment.user.name}</div>
                                        </div>
                                        <div class="comments__text">
                                            ${comment.comm}
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
