<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Home</title>
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
        <a id="purchasesLink" href="/purchases">Purchases</a>
        <a href="/incomes">Incomes</a>
    </div>
</div>
<%--Menu end--%>

<%--&lt;%&ndash;pop-ups&ndash;%&gt;--%>
<%--<div id="category" title="Categories" hidden="hidden">--%>
<%--<jsp:include page="/categories"/>--%>
<%--</div>--%>
<%--<div id="incomes" title="Incomes" hidden="hidden" style="width: 400px">--%>
<%--<jsp:include page="/incomes"/>--%>
<%--</div>--%>

<div class="parent">
    <div class="menu-div">
        <button class="button" onclick="openNav()">Menu</button>
        <button id="categoriesL" class="button">Categories</button>
        <button id="incomesL" class="button">Incomes</button>
    </div>
    <div>
        <img src="resources/images/purchasing.svg" alt="Purchases"
             onclick="document.getElementById('purchasesLink').click();" class="top-image">
    </div>
</div>

</body>
</html>
