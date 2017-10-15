<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%response.setCharacterEncoding("UTF-8");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="dist/css/bms.css" />
        <title>添加作者 - 图书管理系统</title>
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
            <s:form action="AddAuthor">
                <div class="attr-block">
                    <span class="attr-text">姓名</span>
                    <s:textfield value="" name="Name" cssClass="attr-input" required="required"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">年龄</span>
                    <s:textfield value="" name="Age" cssClass="attr-input" type="number" min="0" step="1"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">国籍</span>
                    <s:textfield value="" name="Country" cssClass="attr-input"/>
                </div>
                <div class="attr-block">
                    <s:submit value="确认" cssClass="action-button"/>
                </div>
            </s:form>
        </div>
    </body>
</html>
