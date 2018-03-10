<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Purchases</title>
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
    <script src="/resources/js/main.js" type="text/javascript"></script>
    <script src="/resources/js/jquery.js" type="text/javascript"></script>

    <link href="<c:url value="/resources/css/jquery-ui-downloaded.css"/>" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
</head>
<body>
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
<div style="background: #00ced169; margin: 10px;">
    <button class="button" onclick="openNav()">Menu</button>
    <button id="categoriesL" class="button">Categories</button>
    <button id="incomesL" class="button">Incomes</button>
</div>
<div class="parent">
    <%--<div id="search-purchases-form">
        <div class="table-title">Search purchases</div>

        <form:form action="${search}" method="post">
            <input title="startDate" type="text" placeholder="Start date" name="startDate"
                   class="input-field">
            <input title="endDate" type="text" placeholder="End date" name="endDate"
                   class="input-field">
            <button type="submit" class="submit-button">search
            </button>
        </form:form>
    </div>--%>
    <%--<c:url var="searchPurchases" value="/purchases-between"/>--%>

    <div id="purchases-between">
        <div class="table-title">Purchases list between dates</div>
        <table class="tg table">
            <tr>
                <th width="60">Category</th>
            </tr>
            <c:forEach items="${categoryList}" var="category">
                <tr>
                    <td>${category.name}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>