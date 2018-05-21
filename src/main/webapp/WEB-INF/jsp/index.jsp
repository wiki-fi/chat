<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"
%><!DOCTYPE html>
<html>
<head>
    <title>Simple Chat</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Wika web chat">
    <meta name="author" content="Vika Fomina">
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
</head>
<body>
<div class="main">
    <h1>Чат</h1>
    <div class="chat">
        <!--Выводим список сообщений-->
        <ul id="msgs">
            <!--msg_list-->
        </ul>
        <!--Вывод окончен-->
    </div>
    <div class="input">
        <label for="msg"></label>
        <textarea id="msg" name="msg" rows="5" cols="50"></textarea>
        <br/>
        <button id="send" type="button">Send</button>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chat.js"></script>
</body>
</html>