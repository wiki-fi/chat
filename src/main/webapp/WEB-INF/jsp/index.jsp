<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!--<link rel = "stylesheet" href="main.css">-->
    <!--<link rel = "stylesheet" href="main2.css">-->
</head>
<body>
<h1>Чат</h1>
<!--Выводим список сообщений-->
<ul>
    <!--msg_list-->
</ul>
<!--Вывод окончен-->
<form action="/" method="POST" enctype="application/x-www-form-urlencoded">
    <label>
        <input type="text" name ="message">
    </label>
    <input type="hidden" name="user_id" value="<!--user_id-->">
    <button type="submit">Send</button>
</form>
</body>
</html>