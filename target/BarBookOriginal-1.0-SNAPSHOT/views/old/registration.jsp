<%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 28.09.2020
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/auth" method="post">
    <label>Login:
        <input  type="text" name="login"><br />
    </label>
    <br />
    <label>Name:
        <input type="text" name="name"><br />
    </label>
    <br/>
    <label>Email:
        <input type="email" name="email"><br />
    </label>
    <br/>
    <label>information:
        <input type="text" name="information"><br />
    </label>
    <label>img:
        <input type="text" name="img"><br />
    </label>
    <label>Password:
        <input type="password" name="pass"><br />
    </label>

    <input class="button" type="submit" value="Зарегестрироваться">
</form>
</body>
</html>
