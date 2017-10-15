<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="bms.GetAuthorList" %>
<%@ page import="javafx.util.Pair" %>
<%@ page import="java.util.List" %>
<%response.setCharacterEncoding("UTF-8");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="dist/css/bms.css" />
        <title>详细信息 - 图书管理系统</title>
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
            <span class="info-type">图书信息</span>
            <s:form action="UpdateBook.action">
                <div class="attr-block">
                    <span class="attr-text">ISBN</span>
                    <s:textfield readonly="true" value="%{ISBN}" name="ISBN" cssClass="attr-input attr-input-fbd" required="required"/>
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
                                Integer AuthorID = (Integer)request.getAttribute("AuthorID");
                                if (AuthorID.equals(author.getKey())) {
                        %>
                        <option value="<%=author.getKey()%>" selected="selected"><%=author.getValue()%></option>
                        <%
                        } else {
                        %>
                        <option value="<%=author.getKey()%>"><%=author.getValue()%></option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                <div class="attr-block">
                    <span class="attr-text">书名</span>
                    <s:textfield value="%{Title}" name="Title" cssClass="attr-input" required="required"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">出版社</span>
                    <s:textfield value="%{Publisher}" name="Publisher" cssClass="attr-input"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">出版日期</span>
                    <input type="date" value="<s:property value='PublishDate.toString()'/>" name="PublishDate" class="attr-input"/>
                </div>
                <div class="attr-block">
                    <span class="attr-text">单价</span>
                    <s:textfield value="%{Price}" name="Price" cssClass="attr-input" required="required" type="number" step="0.01" min="0"/>
                </div>
                <div class="attr-block">
                    <s:submit value="更改" cssClass="action-button"/>
                    <input type="reset" value="重置" class="action-button"/>
                </div>
            </s:form>
            <span class="info-type">作者信息</span>
            <div class="attr-block">
                <span class="attr-text">姓名</span>
                <span class="attr-text"><s:property value="Name"/></span>
            </div>
            <div class="attr-block">
                <span class="attr-text">年龄</span>
                <span class="attr-text"><s:property value="Age"/></span>
            </div>
            <div class="attr-block">
                <span class="attr-text">国籍</span>
                <span class="attr-text"><s:property value="Country"/></span>
            </div>
        </div>

    </body>
</html>
