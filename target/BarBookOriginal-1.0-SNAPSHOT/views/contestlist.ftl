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
<div class="contests">
    <div class="container">
        <div class="contests__header">
            <div class="contest__line"></div>
            <div class="contests__header__text">
                Мероприятия наших партнеров
            </div>
            <div class="contest__line"></div>
        </div>

        <div class="blog">
            <#list contests as contest>
                <div class="blog__item">
                    <div class="blog__head">
                        <a href="#" data-toggle="modal" data-target="#i${contest.id}">
                            <img class="blog__photo" src="/BarBookOriginal_war/img?image_path=${contest.img}" alt="">
                        </a>
                        <div class="blog__date">
                            <div class="blog__date-day">${contest.day}</div>
                            ${contest.month}
                        </div>
                    </div>
                    <div class="blog__content">
                        <a href="#" class="blog__title" data-toggle="modal"
                           data-target="#i${contest.id}">${contest.name}</a>
                        <div class="blog__text">${contest.info}</div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>

<#list contests as contest>
    <div class="modal fade" id="i${contest.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle "
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="i${contest.id}">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="modal-bg">
                        <img src="/BarBookOriginal_war/img?image_path=${contest.img}" alt="" class="modal__img">
                    </div>

                    <div class="modal__content">
                        <div class="modal__title blog__title">${contest.name}</div>
                        <div class="modal__stat">
                            <span class="blog__stat-item modal__stat-item"><i class="fa fa-calendar"
                                                                              aria-hidden="true"></i>${contest.day} ${contest.month}</span>
                        </div>
                        <div class="modal__text blog__text">
                            ${contest.info}
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</#list>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
<script><#include "app.js"></script>
</body>
</html>