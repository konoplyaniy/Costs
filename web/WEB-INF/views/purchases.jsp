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
<body onload="checkError()">

<div style="display: none">
    <form:form method="get" action="/synchronize-from-file">
        <button id="synchronize">click</button>
    </form:form>
</div>
<c:if test="${!empty ioException}">
    <div id="dateError" class="alert error-message">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Error! </strong>${ioException}
    </div>
</c:if>

<c:if test="${!empty newPurchasesCount}">
    <div id="dateError" class="alert success-message">
        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
        <strong>Success! </strong>${newPurchasesCount}
    </div>
</c:if>

<div class="parent">
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
        <button id="sync" onclick="sync()" class="button">Synchronize</button>
    </div>
    <div id="first-block">
        <div class="left section">
            <c:url var="addPurchase" value="/purchase/add-purchase"/>

            <form:form action="${addPurchase}" commandName="purchase">
                <div class="table-title">Add purchase</div>
                <table class="table">
                    <tr>
                        <c:if test="${purchase.price > 0}">
                            <th class="input-field-label">
                                <form:label path="id">
                                    <spring:message text="ID"/>
                                </form:label>
                            </th>
                            <th class="input-field-label">
                                <form:label path="date">
                                    <spring:message text="Date"/>
                                </form:label>
                            </th>
                        </c:if>
                        <th class="input-field-label">
                            <form:label path="price">
                                <spring:message text="Price"/>
                            </form:label>
                        </th>
                        <th class="input-field-label">
                            <form:label path="categoryByCategoryId" cssClass="input-field-label">
                                <spring:message text="Please select category"/>
                            </form:label>
                        </th>
                        <th class="input-field-label">
                            <form:label path="comment" cssClass="input-field-label">
                                <spring:message text="Comment"/>
                            </form:label>
                        </th>
                        <th></th>
                    </tr>
                    <tr>
                        <c:if test="${purchase.price>0}">
                            <td>
                                <form:input path="id" readonly="true" size="8" disabled="true"
                                            cssClass="input-field"/>
                                <form:hidden path="id"/>
                            </td>
                            <td>
                                <form:input path="date" readonly="true" size="8" disabled="true"
                                            cssClass="input-field"/>
                                <form:hidden path="date"/>
                            </td>
                        </c:if>
                        <td>
                            <form:input path="price" cssClass="input-field" type="number" min="1"/>
                        </td>
                        <td>
                            <form:select path="categoryByCategoryId" cssClass="dropdown" required="required">
                                <form:option value="">not selected</form:option>
                                <form:options items="${categoryList}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </td>
                        <td>
                            <form:input path="comment" cssClass="input-field"/>
                        </td>
                        <td>
                            <c:if test="${purchase.id != 0}">
                                <input type="submit" class="submit-button"
                                       value="<spring:message text="Edit purchase"/>"/>
                            </c:if>
                            <c:if test="${purchase.id == 0}">
                                <input type="submit" class="submit-button"
                                       value="<spring:message text="Add purchase"/>"/>
                            </c:if>
                        </td>
                    </tr>
                </table>
                <c:if test="${!empty successMSG}">
                    <div class="alert success-message">
                        <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                        <strong>Success!</strong>${successMSG}
                    </div>
                </c:if>
            </form:form>
        </div>
        <div class="right section">
            <div class="table-title">Money</div>
            <table class="tg table">
                <tr>
                    <th width="60">All income</th>
                    <th width="60">All purchase</th>
                    <th width="60">All money diff</th>
                    <th width="60">Month income</th>
                    <th width="60">Month purchase</th>
                    <th width="60">Month money diff</th>
                </tr>
                <tr>
                    <td class="income-price-text">+${allIncome}</td>
                    <td class="purchase-price-text">-${allPurchase}</td>
                    <td class="difference-price-text">${allMoneyDiff}</td>
                    <td class="income-price-text">+${monthIncome}</td>
                    <td class="purchase-price-text">-${montPurchase }</td>
                    <td class="difference-price-text">${monthMoneyDiff}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<hr class="line">
<div id="second-block" class="parent">

    <div class="right section">
        <button id="show-hide-search" class="submit-button" onclick="showHideForm()">Show search form
        </button>
        <div id="search-purchases-form" style="display: none">
            <div class="table-title">Search purchases</div>
            <c:url var="search" value="/purchases/search-purchase-between"/>
            <form:form action="${search}" method="get">
                <table class="table">
                    <tr>
                        <th class="input-field-label">
                            <label for="startDate">Start date</label>
                        </th>
                        <th class="input-field-label">
                            <label for="startDate">End date</label>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <input id="startDate" title="startDate" type="text" placeholder="dd.mm.yyyy"
                                   name="startDate"
                                   class="input-field" required>
                        </td>
                        <td>
                            <input id="endDate" title="endDate" type="text" placeholder="dd.mm.yyyy"
                                   name="endDate"
                                   class="input-field" required>
                        </td>
                        <td>
                            <button type="submit" class="submit-button">search
                            </button>
                        </td>
                    </tr>
                </table>
            </form:form>
            <c:if test="${!empty dataParseError}">
                <div id="dateError" class="alert error-message">
                    <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    <strong>Error! </strong>${dataParseError}
                </div>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${empty listPurchases}">
                <div class="table-title">You haven't any purchases today</div>
            </c:when>

            <c:otherwise>
                <div class="table-title">Today purchases list</div>
                <table class="tg table">
                    <tr>
                        <th width="80">Purchase ID</th>
                        <th width="120">Date</th>
                        <th width="60">Category</th>
                        <th width="60">Price</th>
                        <th width="60">Comment</th>
                        <th width="60">Edit</th>
                        <th width="60">Delete</th>
                    </tr>
                    <c:forEach items="${listPurchases}" var="purchase">
                        <tr>
                            <td>${purchase.id}</td>
                            <td>${purchase.date}</td>
                            <td>${purchase.categoryByCategoryId.name}</td>
                            <td>${purchase.price}</td>
                            <td>${purchase.comment}</td>
                            <td><a href="<c:url value='/edit-purchase/${purchase.id}' />">Edit</a></td>
                            <td><a href="<c:url value='/remove-purchase/${purchase.id}' />">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="left section">
        <%--todo change logic for this - possibly need add statisctic to other controller--%>
        <c:if test="${!empty listPurchases and purchase.id ==0}">
            <div class="table-title">Statistic for today</div>
            <table class="tg table">
                <tr>
                    <th width="60">Max</th>
                    <th width="60">Min</th>
                    <th width="60">Average</th>
                    <th width="60">Total</th>
                </tr>
                <tr>
                    <td>${maxPurchase}</td>
                    <td>${minPurchase}</td>
                    <td>${averagePurchase}</td>
                    <td>${allPurchases}</td>
                </tr>
            </table>
        </c:if>
        <div id="purchases-between">
            <c:if test="${!empty listPurchasesBetween}">
                <hr class="line">
                <div class="table-title">Purchases between: ${startDate} - ${endDate}</div>
                <table class="tg table">
                    <tr>
                        <th width="80">Purchase ID</th>
                        <th width="120">Date</th>
                        <th width="60">Category</th>
                        <th width="60">Price</th>
                        <th width="60">Comment</th>
                        <th width="60">Edit</th>
                        <th width="60">Delete</th>
                    </tr>
                    <c:forEach items="${listPurchasesBetween}" var="purchase">
                        <tr>
                            <td>${purchase.id}</td>
                            <td>${purchase.date}</td>
                            <td>${purchase.categoryByCategoryId.name}</td>
                            <td>${purchase.price}</td>
                            <td>${purchase.comment}</td>
                            <td><a href="<c:url value='/edit-purchase/${purchase.id}' />">Edit</a></td>
                            <td><a href="<c:url value='/remove-purchase/${purchase.id}' />">Delete</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>
</div>
<div class="parent">
    <%--todo change logic for this - possibly need add graphs to other controller--%>
    <c:if test="${purchase.id ==0}">
        <hr class="line">
        <div class="left section">
            <img class=diagram alt="Can't load graph =(" src=<c:url value="${lineGraph}"/>/>
        </div>
        <div class="right section">
            <img class=diagram alt="Can't load graph =(" src=${categoryGraph}/>
        </div>
    </c:if>
</div>

</body>
</html>
