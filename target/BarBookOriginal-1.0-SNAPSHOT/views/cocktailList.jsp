<%@ page import="Entities.Cocktail" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.CocktailDao" %>
<%@ page import="Entities.Ingridient" %>
<%@ page import="Entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 29.09.2020
  Time: 0:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cocktail List</title>
    <style>
        @import url(https://fonts.googleapis.com/css?family=Lora);

        body {
            margin: 0;
        }

        h2 {
            font-family: monospace;
            color: #606060;
        }

        ul {
            list-style: none;
            margin: 0 auto;
        }

        a {
            text-decoration: none;
            font-family: 'Lora', serif;
            transition: .5s linear;
        }

        nav {
            display: block;
            width: 79%;
            margin: 0 auto;
        }

        .two ul {
            background: #D4E7EE;
            padding: 0;
        }

        .two li {
            float: left;
        }

        .two a {
            display: block;
            padding: 1em;
            border-right: 1px solid #ADC0CE;
            background: rgba(173, 192, 206, .3);
            color: #29838C;
        }

        .two a:hover {
            background: #ADC0CE
        }

        .men {
            width: 100%;
            display: flex;
            flex-direction: column;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(function () {
            //console.log(2);
            $('#button').click(function (event) {

                var search = $('#search').val();
                var tags = $('#filter').serialize();
                console.log(tags);
                $.ajax({
                    type: "POST", url: "Servlets.SearchServlet",
                    data : {search: search},
                    dataType:'json',
                    success: function (result) {
                        for(var i = 0; i < result.length;i++){
                            $("<div>").html("<a href=\"/BarBookOriginal_war/cocktail?id=" + result[i]['id'] + "\">" + result[i]['name'] + "</a>").appendTo($("#result"));
                        }
                        // for (var a in result) {
                        //     console.log(result);
                        //     $("<div>").html("<a href=\"/BarBookOriginal_war/cocktail?id=" + a + "\">" + a[a] + "</a>").appendTo($("#result"));
                        // }
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="men">
    <nav class="two">
        <ul>
            <li><a href="/BarBookOriginal_war/">Home</a></li>
            <li><a href="/BarBookOriginal_war/cocktaillist">Cocktails</a></li>
            <li><a href="/BarBookOriginal_war/ingridients">Ingridients</a></li>
            <%
                if (session.getAttribute("user") != null) {
                    out.println("<li><a href=\"/BarBookOriginal_war/profile\">" + ((User) session.getAttribute("user")).getLogin() + "</a></li>");
                    out.println("<li><a href=\"/BarBookOriginal_war/logout\">Logout</a></li>");
                } else {
                    out.println("<li><a href=\"/BarBookOriginal_war/registration\">Registration</a></li>");
                    out.println("<li><a href=\"/BarBookOriginal_war/auth\">Login</a></li>");
                }
            %>
        </ul>
    </nav>
</div>
<div>
    <form id="filter">
        <table>
            <th>По вкусу:</th>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="outside" value="outside">
                        <label class="form-check-label" for="outside">Сладкие</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="music" value="music">
                        <label class="form-check-label" for="music">Горькие</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="art" value="art">
                        <label class="form-check-label" for="art">Свежие</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="new places" value="new place">
                        <label class="form-check-label" for="new places">Новые места</label>
                    </div>
                </td>
            </tr>
            <th>По алкоголю:</th>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="early" value="early">
                        <label class="form-check-label" for="early">Джин</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="middle" value="middle">
                        <label class="form-check-label" for="middle">Водка</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="evening" value="evening">
                        <label class="form-check-label" for="evening">Виски</label>
                    </div>
                </td>
            </tr>
        </table>
        <input class="sub" type="submit" value="Принять" style="margin-bottom: 5px"></form>
    <input name="search" type="text" id="search">
    <button type="button" id="button" name="button">Button</button>
</div>

<div id="result">

</div>

<%
    List<Cocktail> p = (List) request.getAttribute("cockts");
    out.print("<h1> Hello There! </h1>");
    for (Cocktail cock : p) {
        out.println("<h1><a href=\"/BarBookOriginal_war/cocktail?id=" + cock.getId() + "\">" + cock.getName() + "</a></h1>");
        out.println("<h2>" + cock.getInf() + "</h2>");
    }
%>

</body>
</html>
