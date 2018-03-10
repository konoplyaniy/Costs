<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Categories</title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="/resources/js/main.js" type="text/javascript"></script>
    <script src="/resources/js/jquery.js" type="text/javascript"></script>

    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"/>
    <%--<script>--%>
    <%--$(function () {--%>
    <%--$("#category").dialog({--%>
    <%--autoOpen: false--%>
    <%--});--%>

    <%--$("#categoriesL").click(function () {--%>
    <%--$("#category").dialog('open');--%>
    <%--});--%>
    <%--});--%>
    <%--$(function () {--%>
    <%--$("#incomes").dialog({--%>
    <%--autoOpen: false--%>
    <%--});--%>

    <%--$("#incomesL").click(function () {--%>
    <%--$("#incomes").dialog('open');--%>
    <%--});--%>
    <%--});--%>

    <%--</script>--%>
</head>

<%--MENU--%>
<!-- The overlay -->
<div id="navigation" class="overlay">
    <!-- Button to close the overlay navigation -->
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <!-- Overlay content -->
    <div class="overlay-content">
        <a href="/">Home</a>
        <a href="/categories">Categories</a>
        <a href="/purchases">Purchases</a>
        <a href="/incomes">Incomes</a>
    </div>
</div>

<body>
<div class="parent">
    <div class="menu-div">
        <button class="button" onclick="openNav()">Menu</button>
        <button id="categoriesL" class="button">Categories</button>
        <button id="incomesL" class="button">Incomes</button>
    </div>
    <div class="center section">
        <c:url var="addAction" value="/category/add-category"/>

        <form:form action="${addAction}" commandName="category">
            <div class="table-title">Add category</div>
            <table class="table">
                <c:if test="${!empty category.name}">
                    <tr>
                        <td>
                            <form:label path="id">
                                <spring:message text="ID"/>
                            </form:label>
                        </td>
                        <td>
                            <form:input path="id" readonly="true" size="8" disabled="true" cssClass="input-field"/>
                            <form:hidden path="id"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        <form:label path="name">
                            <spring:message text="Name"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="name" cssClass="input-field" required="required"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${!empty category.name}">
                            <input type="submit" class="submit-button"
                                   value="<spring:message text="Edit category"/>"/>
                        </c:if>
                        <c:if test="${empty category.name}">
                            <input type="submit" class="submit-button"
                                   value="<spring:message text="Add category"/>"/>
                        </c:if>
                    </td>
                </tr>
            </table>
        </form:form>
        <c:if test="${!empty errorMSG}">
            <div class="alert error-message">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                <strong>Error! </strong>${errorMSG}
            </div>
        </c:if>
        <c:if test="${!empty successMSG}">
            <div class="alert success-message">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                <strong>Success! </strong>${successMSG}
            </div>
        </c:if>


        <c:if test="${!empty listCategories}">
            <div class="table-title">Category List</div>
            <table class="tg table">
                <tr>
                    <th width="80">Category ID</th>
                    <th width="120">Category</th>
                    <th width="60">Edit</th>
                    <th width="60">Delete</th>
                </tr>
                <c:forEach items="${listCategories}" var="category">
                    <tr>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td><a href="<c:url value='/edit-category/${category.id}' />">Edit</a></td>
                        <td><a href="<c:url value='/remove-category/${category.id}' />">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>
