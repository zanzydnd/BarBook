<%@ page import="Entities.Cocktail" %>
<%@ page import="DAO.CocktailDao" %>
<%@ page import="Entities.Ingridient" %>
<%@ page import="java.util.List" %>
<%@ page import="Entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 29.09.2020
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        CocktailDao dao = new CocktailDao();
        Cocktail cocktail = dao.getCocktailById(Integer.parseInt(request.getParameter("id")));
        System.out.println(Integer.parseInt(request.getParameter("id")));
    %>
    <title>Cocktail</title>
</head>
<body>
<div class="men">
    <nav class="two">
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="/BarBookOriginal_war/cocktails">Cocktails</a> </li>
            <li><a href="/BarBookOriginal_war/ingridients">Ingridients</a></li>
            <%
                if(session.getAttribute("user") != null){
                    out.println("<li><a href=\"/BarBookOriginal_war/profile\">"+ ((User) session.getAttribute("user")).getLogin()+"</a></li>");
                }
                else{
                    out.println("<li><a href=\"/BarBookOriginal_war/registration\">Registration</a></li>");
                    out.println("<li><a href=\"/BarBookOriginal_war/auth\">Login</a></li>");
                }
            %>
        </ul>
    </nav>
</div>
    <%
        out.println("<h1>"+cocktail.getName()+"</h1>");%>
        <form action="/cocktail" method="post">
            <button> Мне нравится <%out.print(cocktail.getRating());%></button>
        </form>
    <%
        out.println("<h2>"+cocktail.getInf()+"</h2>");
        List<Ingridient> list = dao.getRecepie(cocktail);
        out.println( "<div>");
        int i = 0;
        out.print("<p>");
        while(i < list.size()){
            out.print(list.get(i).getName() + "  ");
            i++;
        }
        out.print("</p>");
        out.println("</div>");
    %>
</body>
</html>
