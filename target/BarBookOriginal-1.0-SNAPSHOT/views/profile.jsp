<%@ page import="Entities.User" %>
<%@ page import="Entities.Cocktail" %>
<%@ page import="DAO.CocktailDao" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 17.10.2020
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<a href="/BarBookOriginal_war/change_profile">Редактировать профиль</a>
<%
    User user = (User)session.getAttribute("user");
    System.out.println("ussssser " + user.getFavCockt());
    Cocktail fav = (new CocktailDao().getCocktailById(user.getFavCockt()));
    System.out.println("fav" + fav.getId());
%>
<h1>${user.getName()}</h1>
<h2>${user.getInformation()}</h2>
<%
    out.println("<a href=\"/BarBookOriginal_war/cocktail?id=" + fav.getId() + "\">" + fav.getName()+"</a>");
%>
<img src="${user.getImg()}">

</body>
</html>
