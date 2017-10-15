<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%response.setCharacterEncoding("UTF-8");%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="dist/css/bms.css" />
        <title>查询图书 - 图书管理系统</title>
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
            <s:form action="FindBook">
                <div class="attr-block">
                    <span class="attr-text">作者姓名</span>
                    <s:textfield value="" name="authorName" cssClass="attr-input"/>
                    <s:submit value="查询" cssClass="action-button"/>
                </div>
            </s:form>
            <table class="zebra-table">
                <thead>
                <th>ISBN</th>
                <th>书名</th>
                <th>作者</th>
                <th>出版社</th>
                <th>出版日期</th>
                <th>单价</th>
                <th>操作</th>
                </thead>
                <s:iterator value="tableContent" var="entry" status="st">
                    <tr>
                        <td><s:property value="#entry.get(\"ISBN\").toString()"/></td>
                        <td>
                            <form id="ShowBook" name="ShowBook" action="ShowBook" method="post" onclick="submit()">
                                <s:textfield type="hidden" name="ISBN" value="%{#entry.get(\"ISBN\").toString()}"/>
                                <a href="javascript:void(0)"><s:property value="#entry.get(\"Title\").toString()"/></a>
                            </form>
                        </td>
                        <td><s:property value="#entry.get(\"Name\").toString()"/></td>
                        <td><s:property value="#entry.get(\"Publisher\").toString()"/></td>
                        <td><s:property value="#entry.get(\"PublishDate\").toString()"/></td>
                        <td><s:property value="#entry.get(\"Price\").toString()"/></td>
                        <td>
                            <s:form action="DeleteBook">
                                <s:textfield type="hidden" name="ISBN" value="%{#entry.get(\"ISBN\").toString()}"/>
                                <s:submit value="删除" cssClass="action-button"/>
                            </s:form>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </body>
</html>
