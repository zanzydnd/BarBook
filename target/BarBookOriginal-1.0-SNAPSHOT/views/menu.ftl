<header class="header fixed">
    <div class="container">
        <div class="header__inner">
            <div class="header__logo">
                <font size="48" color="white" face="Sancreek">BB</font>
            </div>
            <nav class="nav">
                <a class="nav__link" href="/BarBookOriginal_war/">Home</a>
                <a class="nav__link" href="#">Bartenders</a>
                <a class="nav__link" href="/BarBookOriginal_war/cocktlist">Cocktails</a>
                <a class="nav__link" href="#">Contests</a>
                <a class="nav__link" href="/BarBookOriginal_war/ingridientlist">Ingredients</a>
                <#if user??>
                    <a class="nav__link" href="/BarBookOriginal_war/profile">${user.login}</a>
                    <a class="nav__link" href="/BarBookOriginal_war/logout">Log out</a>
                <#else>
                    <a class="nav__link" href="/BarBookOriginal_war/registration">Registration</a>
                    <a class="nav__link" href="/BarBookOriginal_war/auth">Log in</a>
                </#if>
            </nav>
        </div>
    </div>
</header>