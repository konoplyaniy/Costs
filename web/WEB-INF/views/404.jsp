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
<div class="menu-div">
    <button class="button" onclick="openNav()">Menu</button>
    <button id="categoriesL" class="button">Categories</button>
    <button id="incomesL" class="button">Incomes</button>
</div>

<div class="parent" style="
    width: 250px;
    height: 250px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -125px 0 0 -125px;
    padding: 8px;
    text-decoration: none;
    font-size: 32px;
    color: #818181;
    display: block;
    transition: 0.3s;">404 page not found
</div>

</body>
</html>