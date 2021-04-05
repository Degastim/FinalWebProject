<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="/localization/messages"/>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
            crossorigin="anonymous"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <a class="navbar-brand h1" href="#"><img src="${pageContext.request.contextPath}/img/pharmacy.svg"
                                                     width="20" height="20" alt=""><fmt:message
                    key="header.brand.pharmacy"/></a>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <form method="GET" action="controller">
                        <input type="hidden" name="command" value="redirect_to_main">
                        <button class="nav-link btn btn-light border-0" aria-current="page"><img
                                src="${pageContext.request.contextPath}/img/pills.svg" alt="" width="20"
                                height="20"><fmt:message key="header.button.drugs"/></button>
                    </form>
                </li>
                <ctg:accessControl accessRole="PHARMACIST">
                    <li class="nav-item">
                        <form method="get" action="controller">
                            <input type="hidden" name="command" value="redirect_to_drug_table">
                            <button class="nav-link btn btn-light border-0"><fmt:message
                                    key="header.button.drugTable"/></button>
                        </form>
                    </li>
                </ctg:accessControl>
                <div class="nav-item dropdown">
                    <form method="POST" action="controller">
                        <input type="hidden" name="command" value="change_locale"/>
                        <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false"><img src="${pageContext.request.contextPath}/img/language.svg" width="20" height="20" alt=""><fmt:message key="header.language"/>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <button class="dropdown-item" type="submit" name="locale" value="en">English</button>
                            </li>
                            <li>
                                <button class="dropdown-item" type="submit" name="locale" value="ru">Русский</button>
                            </li>
                        </ul>
                    </form>
                </div>
            </ul>
        </div>
        <c:if test="${user==null}">
            <form method="GET" action="controller">
                <button type="submit" class="btn btn-primary" name="command" value="redirect_to_login"><fmt:message key="header.redirectToLogin"/></button>
                <button type="submit" class="btn btn-secondary" name="command" value="redirect_to_registration"><fmt:message key="header.redirectToRegistration"/></button>
            </form>
        </c:if>
        <ctg:userExist>
            <div class="btn-group">
                <form method="GET" action="controller">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false"><img class="mb-1" src="${pageContext.request.contextPath}/img/person.svg"><fmt:message key="header.personalArea"/></button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <c:choose>
                            <c:when test="${user.role=='DOCTOR'}">
                                <li>
                                    <h5 class="dropdown-item">${user.name} ${user.surname} (<fmt:message key="header.personalArea.userRole.doctor"/>)</h5>
                                </li>
                            </c:when>
                            <c:when test="${user.role=='PHARMACIST'}">
                                <li>
                                    <h5 class="dropdown-item">${user.name} ${user.surname} (<fmt:message key="header.personalArea.userRole.pharmacist"/>)</h5>
                                </li>
                                <li>
                                    <h5 class="dropdown-item"><fmt:message key="header.personalArea.account"/>:${user.amount}</h5>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <h5 class="dropdown-item">${user.name} ${user.surname} (<fmt:message key="header.personalArea.userRole.customer"/>)</h5>
                                </li>
                                <li>
                                    <h5 class="dropdown-item"><fmt:message key="header.personalArea.account"/>:${user.amount}</h5>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li>
                            <button class="dropdown-item" type="submit" name="command" value="redirect_to_change_password"><fmt:message key="header.personalArea.changePassword"/></button>
                        </li>
                        <ctg:accessControl accessRole="CUSTOMER">
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_customer_prescription_table"><fmt:message key="header.personalArea.prescriptions"/></button>
                            </li>
                        </ctg:accessControl>
                        <ctg:accessControl accessRole="CUSTOMER">
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_customer_order_table"><fmt:message key="header.personalArea.orders"/></button>
                            </li>
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_account_replenishment"><fmt:message key="header.personalArea.accountReplenishment"/></button>
                            </li>
                        </ctg:accessControl>
                        <ctg:accessControl accessRole="DOCTOR">
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_prescription_order_table"><fmt:message key="header.personalArea.prescriptionOrderTable"/></button>
                            </li>
                        </ctg:accessControl>
                        <ctg:accessControl accessRole="PHARMACIST">
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_pharmacist_drug_order_table"><fmt:message key="header.personalArea.pharmacistDrugOrderTable"/></button>
                            </li>
                            <li>
                                <button class="dropdown-item" type="submit" name="command" value="redirect_to_account_replenishment"><fmt:message key="header.personalArea.accountReplenishment"/></button>
                            </li>
                        </ctg:accessControl>
                        <li>
                            <button class="dropdown-item" type="submit" name="command" value="logout"><fmt:message key="header.logout"/></button>
                        </li>
                    </ul>
                </form>
            </div>
        </ctg:userExist>
    </div>
</nav>
</body>
</html>
