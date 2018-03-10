<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Incomes</title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="/resources/js/main.js" type="text/javascript"></script>
    <script src="/resources/js/jquery.js" type="text/javascript"></script>

    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css"/>
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

<div class="parent section center">
    <c:url var="addAction" value="/income/add-income"/>

    <form:form action="${addAction}" commandName="income">

        <div class="table-title">Add income</div>
        <table class="table">
            <c:if test="${income.quantity!=0}">
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
                    <td>
                        <form:label path="date">
                            <spring:message text="Date"/>
                        </form:label>
                    </td>
                    <td>
                        <form:input path="date" readonly="true" size="8" disabled="true" cssClass="input-field"/>
                        <form:hidden path="date"/>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td>
                    <form:label path="quantity">
                        <spring:message text="Quantity"/>
                    </form:label>
                </td>
                <c:if test="${!empty error}">
                    <td class="validation-error">
                            ${error}
                    </td>
                    <hr class="line">
                </c:if>
                <td>
                    <form:input path="quantity" cssClass="input-field" required="required" min="1"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <c:if test="${income.quantity!=0}">
                        <input type="submit" class="submit-button"
                               value="<spring:message text="Edit income"/>"/>
                    </c:if>
                    <c:if test="${income.quantity==0}">
                        <input type="submit" class="submit-button"
                               value="<spring:message text="Add income"/>"/>
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

    <c:if test="${!empty listIncomes}">
        <div class="table-title">Incomes List</div>
        <table class="tg table">
            <tr>
                <th width="80">Income ID</th>
                <th width="80">Date</th>
                <th width="120">Quantity</th>
                <th width="60">Edit</th>
                <th width="60">Delete</th>
            </tr>
            <c:forEach items="${listIncomes}" var="income">
                <tr>
                    <td>${income.id}</td>
                    <td>${income.date}</td>
                    <td>${income.quantity}</td>
                    <td><a href="<c:url value='/edit-income/${income.id}' />">Edit</a></td>
                    <td><a href="<c:url value='/remove-income/${income.id}' />">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>
