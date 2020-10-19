<%@ page import="Entities.User" %><%--
  Created by IntelliJ IDEA.
  User: Даня
  Date: 17.10.2020
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile change</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    $(function () {
        //console.log(2);
        $('#button').click(function (event) {

            var search = $('#search').val();

            var tags = [];

            var eventTypes = document.forms['filter'].elements['types[]'];
            var len = eventTypes.length;

            for (var i = 0; i < len; i++) {
                if (eventTypes[i].checked) {
                    tags.push($(eventTypes[i]).val());
                }
            }
            console.log(tags);

            $.ajax({
                type: "POST", url: "Servlets.SearchServlet",
                data: {
                    search: search,
                    tags: JSON.stringify(tags)
                },
                dataType: 'json',
                success: function (result) {
                    for (var i = 0; i < result.length; i++) {
                        $("<div>").html("<option name=\"fav_cockt\" value=\"" + result[i]['id'] + "\">" + result[i]['name'] + "</option>").appendTo($("#result"));
                    }
                }
            });
        });
    });
</script>
<body>
<%
    User user = (User)session.getAttribute("user");
%>
<form action="/BarBookOriginal_war/change_profile" enctype="multipart/form-data" method="post">
    <h3>Picture</h3>
    <input type="file" name="image" accept="image/*">
    <br>
    <h3>Имя</h3>
    <input type="text" name = "name" required value="${user.getName()}">
    <br>
    <h3>О себе</h3>
    <input type="text" name="info" required value="${user.getInformation()}">
    <br>
    <button type="submit" name="submit">Принять</button>
</form>
</body>
</html>
