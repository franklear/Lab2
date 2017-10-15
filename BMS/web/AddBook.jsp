<%@ page import="bms.GetAuthorList" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%response.setCharacterEncoding("UTF-8");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="dist/css/bms.css" />
        <title>添加图书 - 图书管理系统</title>
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
            <s:form action="AddBook">
                <div class="attr-block">
                    <span class="attr-text">ISBN</span>
                    <s:textfield value="" name="ISBN" cssClass="attr-input" required="required"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">书名</span>
                    <s:textfield value="" name="Title" cssClass="attr-input" required="required"/>
                </div>
                <div class="attr-block">
                    <%
                        GetAuthorList getAuthorList = new GetAuthorList();
                        getAuthorList.setName("");
                        getAuthorList.execute();
                        List<Pair<Integer, String>> authorList = getAuthorList.getAuthorList();
                    %>
                    <span class="attr-text">作者</span>
                    <select name="AuthorID" class="attr-input" required="required">
                        <%
                            for (Pair<Integer, String> author : authorList) {
                        %>
                        <option value="<%=author.getKey()%>"><%=author.getValue()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="attr-block">
                    <span class="attr-text">出版社</span>
                    <s:textfield value="" name="Publisher" cssClass="attr-input"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">出版日期</span>
                    <s:textfield type="date" value="" name="PublishDate" cssClass="attr-input"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">单价</span>
                    <s:textfield value="" name="Price" cssClass="attr-input" required="required" type="number" step="0.01" min="0"/>
                </div>
                <div class="attr-block">
                    <s:submit value="确认" cssClass="action-button"/>
                </div>
            </s:form>
        </div>
    </body>
</html>
