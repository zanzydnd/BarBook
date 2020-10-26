<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Registration</title>
    <style>
        <#include "style.css">
    </style>
</head>

<body>
<div class="login">
    <h3 class="h1_l">Регистрация на сайте</h3>
    <div class="inp">
        <form action="/BarBookOriginal_war/auth" method="post">
            <input class="log" type="text"  placeholder="login" name="login">
            <input class="log" type="text" placeholder="name" name="name">
            <input class="log" type="email" placeholder="email" name="email">
            <input class="log" type="text" placeholder="information" name="information">
            <input class="pass" type="password" placeholder="password" name="pass">
            <input class="btn" type="submit" value="Зарегистрироваться">
        </form>
    </div>
</div>

</body>
</html>