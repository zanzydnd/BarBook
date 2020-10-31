<#macro Hello user_name>
    <div class="intro__inner1">
        <h1 class="intro__title1">Hello, ${user_name}</h1>
    </div>
    <div class="intro__line-helper">
        <div class="intro__line"></div>
    </div>
    <div class="intro__inner2">
        <h1 class="intro__title2">It's nice to have you back!</h1>
    </div>
</#macro>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Sancreek&family=Tinos&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <link rel="stylesheet" href="views/style.css">
    <title>BarBook</title>
</head>
<body>

<header class="header fixed">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo">
                <font size="48" color="white" face="Sancreek">BB</font>
            </div>
            <nav class="nav">
                <a class="nav__link" href="/BarBookOriginal_war/">Home</a>
                <a class="nav__link" href="/BarBookOriginal_war/barmen">Bartenders</a>
                <a class="nav__link" href="/BarBookOriginal_war/cocktlist">Cocktails</a>
                <a class="nav__link" href="/BarBookOriginal_war/contestlist">Contests</a>
                <a class="nav__link" href="/BarBookOriginal_war/ingridientlist">Ingredients</a>
                <#if user??>
                    <a class="nav__link" href="/BarBookOriginal_war/profile?id=self">${user.login}</a>
                    <a class="nav__link" href="/BarBookOriginal_war/logout">Log out</a>
                <#else>
                    <a class="nav__link" href="/BarBookOriginal_war/registration">Registration</a>
                    <a class="nav__link" href="/BarBookOriginal_war/auth">Log in</a>
                </#if>
            </nav>
        </div>
    </div>
</header>

<div class="slide">
    <div class="intro intro1">
        <div class="container">
            <div class="slider__inner">
                <#if user??>
                    <@Hello user_name=user.name/>
                <#else>
                    <div class="intro__inner1">
                        <h1 class="intro__title1">BarBook</h1>
                    </div>
                    <div class="intro__line-helper">
                        <div class="intro__line"></div>
                    </div>
                    <div class="intro__inner2">
                        <h1 class="intro__title2">BEST SERVICE FOR BARTENDERS</h1>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <div class="intro intro2">
        <div class="container">
            <div class="slider__inner">
                <div class="intro__inner1">
                    <h1 class="intro__title1">BarBook</h1>
                </div>
                <div class="intro__line-helper">
                    <div class="intro__line"></div>
                </div>
                <div class="intro__inner2">
                    <h1 class="intro__title2">BEST SERVICE FOR BARTENDERS</h1>
                </div>
            </div>
        </div>
    </div>

</div>


<div class="aboutUs">
    <div class="container">
        <div class="aboutUs__inner">

            <div class="aboutUs__col">
                <div class="aboutUs__inner1">
                    <h4 class="aboutUs__subtitle">Shortly about us</h4>
                </div>
                <div class="aboutUs__inner2">
                    <h1 class="aboutUs__title">Our cocktail menu is inspired by both vintage and modern recipes</h1>
                </div>
                <div class="aboutUs__inner3">
                    <h3 class="aboutUs__text">
                        our service was created in order to make life easier for bartenders
                        in search of recipes for new and bright cocktails, and anyone can pick
                        up something new for themselves
                    </h3>
                </div>
            </div>

            <div class="aboutUs__col">
                <div class="aboutUs__img">
                    <img src="img/home-1-welcome.png" alt="">
                </div>
            </div>

            <div class="aboutUs__col">
                <div class="aboutUs__img">
                    <img class="aboutUs__photo" src="img/img_banner.jpg" alt="">
                </div>
                <div class="aboutUs__banner">
                    <h1 class="banner__title">Some info</h1>
                </div>
            </div>

        </div>
    </div>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script><#include "app.js"></script>
</body>
</html>