<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="views/style.css">
    <title>Login</title>
</head>

<body>
<div class="login">
    <h2 class="h1_l">Авторизация на сайте</h2>
    <div class="inp">
        <form action="/BarBookOriginal_war/authpath" method="post">
            <input class="log" type="text"  placeholder="login" name="login">
            <input class="pass" type="password" placeholder="password" name="pass">
            <label for="cookie" class="barmen__name">Запомнить меня</label>
            <input type = "checkbox" id = "cookie" name="cookie" value = "yes">
            <input class="btn"  type="submit" value="ВОЙТИ">
        </form>
        <#if errMsg??>
            <a>${errMsg}</a>
        </#if>
    </div>
</div>

</body>
</html>