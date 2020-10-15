<%@ page import="Entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 28.09.2020
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BarBook</title>
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

        .men{
            width: 100%;
            display: flex;
            flex-direction: column;
        }
    </style>
</head>
<body>
    <div class="men">
        <nav class="two">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li><a href="/BarBookOriginal_war/cocktlist">Cocktails</a> </li>
                <li><a href="/BarBookOriginal_war/ingridientlist">Ingridients</a></li>
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
</body>
</html>
