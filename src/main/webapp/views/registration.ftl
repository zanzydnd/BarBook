<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="views/style.css">
    <title>Registration</title>

</head>

<body>
<div class="login">
    <h3 class="h1_l">Регистрация на сайте</h3>
    <div class="inp">
        <form action="/BarBookOriginal_war/auth" method="post">
            <input class="log" pattern="^[a-zA-Z](.[a-zA-Z0-9_-]*)" type="text"  placeholder="login" name="login">
            <input class="log"   type="text" placeholder="name" name="name">
            <input class="log" type="email" placeholder="email" name="email">
            <input class="log" type="text" placeholder="information" name="information">
            <input class="pass" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" type="password" placeholder="password" name="pass">
            <input class="btn" type="submit" value="Зарегистрироваться">
            <#if errMsg??>
                <a>${errMsg}</a>
            </#if>
        </form>
    </div>
</div>

</body>
</html>