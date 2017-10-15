<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%response.setCharacterEncoding("UTF-8");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="dist/css/bms.css" />
        <title>信息 - 图书管理系统</title>
    </head>
    <body>
        <div class="header">
            <div class="header-title">图书管理系统</div>
        </div>
        <div class="main-wrapper">
            <div class="button-bar">
                <button type="button" class="nav-button" id="toFindBook" onclick="location.href='FindBook.jsp'">查询图书</button>
                <button type="button" class="nav-button" id="toAddBook" onclick="location.href='AddBook.jsp'">添加图书</button>
                <button type="button" class="nav-button" id="toAddAuthor" onclick="location.href='AddAuthor.jsp'">添加作者</button>
            </div>
            <div class="message">
               <s:property value="message"/>
            </div>
        </div>
    </body>
</html>
