<%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 28.09.2020
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/authpath" method="post">
    <label>Username:
        <input  type="text" name="login"><br />
    </label>
    <br />
    <label>Password:
        <input type="password" name="pass"><br />
    </label>
    <input type = "checkbox" id = "cookie" name="cookie" value = "yes">
    <label for="cookie">Запомнить меня</label>
    <input class="button" type="submit" value="Войти">
</form>
</body>
</html>
