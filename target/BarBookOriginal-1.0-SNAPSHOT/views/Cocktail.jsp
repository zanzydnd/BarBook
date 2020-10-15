<%@ page import="Entities.Cocktail" %>
<%@ page import="DAO.CocktailDao" %>
<%@ page import="Entities.Ingridient" %>
<%@ page import="java.util.List" %>
<%@ page import="Entities.User" %>
<%@ page import="DAO.CommentsDao" %>
<%@ page import="Entities.Comment" %>
<%@ page import="DAO.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
    <%
        CocktailDao dao = new CocktailDao();
        Cocktail cocktail = dao.getCocktailById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("cocktail",cocktail);
        System.out.println(Integer.parseInt(request.getParameter("id")));
        User user = (User)session.getAttribute("user");
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
                    out.println("<li><a href=\"/BarBookOriginal_war/logout\">Logout</a></li>");
                }
                else{
                    out.println("<li><a href=\"/BarBookOriginal_war/registration\">Registration</a></li>");
                    out.println("<li><a href=\"/BarBookOriginal_war/auth\">Login</a></li>");
                }
            %>
        </ul>
    </nav>
</div>
    <%out.println("<h1>"+cocktail.getName()+"</h1>");%>
        <%if(session.getAttribute("user")!= null){%>
<form method="post" action=<%out.print("/BarBookOriginal_war/cocktail?id=" + cocktail.getId());%>>
            <button type = "submit"  name="likedCocktId" value= <%out.print("\""  +cocktail.getId() + "\"");%> > Мне нравится <%out.print(cocktail.getRating());%></button>
        </form>
<%--
<form id = "like">
    <input type="hidden" name="user_id" value="${user.getId()}">
    <input type="hidden" name="likedCocktId" value ="${cocktail.getId()}">
    <button type = "button" name="submit" value="Submit">Мне нравится <div id="rate">${cocktail.getRating()}</div></button>
</form>
<div id ="err"></div>
<script src="like.js"></script>
   --%>
            <%
                if(request.getAttribute("errMsg") != null){
                    out.print("<p>Вы уже оставляли лайк</p>");
                }
            %>
        <%} else{%>
            <p>Чтобы оставить лайк, авторизируйтесь.</p>
        <%}%>
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
    <h2>Комментарии</h2>
    <form action="${pageContext.request.contextPath}/CommentServlet" method="post">
        <input type ="text" name = "comment">
        <input type="hidden" name="cocktail_id" value=<%out.print("\""+ request.getParameter("id") + "\"");%>>
        <%if(session.getAttribute("user") != null){%>
        <input type="hidden" name="user_id" value=<%out.print("\""+ ((User)session.getAttribute("user")).getId() + "\"");%>>
        <%}%>
        <button type="submit">Оставить</button>
    </form>

    <%
        CommentsDao commentsDao = new CommentsDao();
        List<Comment> commList = commentsDao.getComments(cocktail);
        i = 0;
        while (i < commList.size()){
            Comment comment = commList.get(i);
            out.print("<h2>" + comment.getUser().getName() +"</h2>");
            out.print("<p>" + comment.getComm() + "</p>");
            i++;
        }
    %>
</body>
</html>
