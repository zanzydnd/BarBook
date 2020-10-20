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

                <div class="coctail__block-ingridient">
                    <div class="coctail__block-ingredients">
                        <div class="coctail__block-ingredientTitle">Необходимые ингредиенты</div>
                        <div class="ingredient-itemInner">

                            <div class="ingredient-item">
                                <div class="ingredient-name">Проссекко</div>
                                <div class="ingredient__count"> 100
                                    <b class="font-helper">мл</b>
                                </div>
                            </div>

                            <div class="ingredient-item">
                                <div class="ingredient-name">Проссекко</div>
                                <div class="ingredient__count"> 100
                                    <b class="font-helper">мл</b>
                                </div>
                            </div>

                            <div class="ingredient-item">
                                <div class="ingredient-name">Проссекко</div>
                                <div class="ingredient__count"> 100
                                    <b class="font-helper">мл</b>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="recipe">
                    <span class="recipe__title">Рецепт коктейля <b class="helper">${cocktail.name}</b></span>

                        <div class="recipe__info">
                            <div class="recipe__list">
                                <#list str as st>
                                    <div class="recipe__list-item">
                                        <div class="recipe__list-count">--</div>
                                        <div class="recipe__list-name">${st.text}</div>
                                    </div>
                                </#list>
                            </div>
                        </div>
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
                            <form action="/" method="post" class="send__comment">
                                <textarea class="text__comment" name="text" id="text" rows="5"
                                          placeholder="Оставьте ваш комментарий"></textarea>
                                <div class="btn-comm">
                                    <button type="button" class="comments__btn">Отправить</button>
                                </div>
                            </form>
                        <#else>
                            <div class="comments__text">Чтобы принять участие в диалоге, необходимо
                                <a href="#" class="color-helper">авторизоваться</a>.
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
