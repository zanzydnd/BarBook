<%@ page import="Entities.Ingridient" %>
<%@ page import="DAO.IngridientDao" %>
<%@ page import="java.util.List" %>
<%@ page import="Entities.Cocktail" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 29.09.2020
  Time: 1:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List<Ingridient> list = (List<Ingridient>) request.getAttribute("ingridients");
    IngridientDao dao = new IngridientDao();
    out.print("<h1> Hello There! </h1>");
    for(Ingridient ingridient:list){
        out.println("<h1>"+ingridient.getName()+"</h1>");
        out.println("<h2>"+ingridient.getInf()+"</h2>");
        List<Cocktail> cock = dao.getCoctailsByIngridient(ingridient);
        out.println("<div>");
        int i = 0;
        out.print("<p>");
        while(i < cock.size()){
            out.print(cock.get(i).getName() + "  ");
            i++;
        }
        out.print("</p>");
        out.println("</div>");
    }
%>
</body>
</html>
