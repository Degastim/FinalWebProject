<%@ include file="header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="registration.title"/></title>
</head>
<body class="text-center">
<div class="row">
    <div class="col-md-4 mx-auto my-5">
        <form class="was-validated" method="POST" action="controller">
            <input type="hidden" name="command" value="registration"/>
            <img class="mb-4" src="${pageContext.request.contextPath}/img/people_circle.svg" alt="" width="72"
                 height="57">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="registration.button.submit"/></h1>

            <div>
                <label for="name" class="form-label"><fmt:message key="registration.label.name"/>:</label>
                <input type="text" id="name" class="form-control" name="name"
                       placeholder="<fmt:message key="registration.label.name"/>" required
                       pattern="[A-Za-zА-Яа-яЁё]{1,45}" autofocus>
                <div class="invalid-feedback">
                    <fmt:message key="registration.feedback.name"/>
                </div>
            </div>

            <div class="my-2">
                <label for="surname" class="form-label"><fmt:message key="registration.label.surname"/>:</label>
                <input type="text" id="surname" class="form-control" name="surname"
                       placeholder="<fmt:message key="registration.label.surname"/>" required
                       pattern="[A-Za-zА-Яа-яЁё]{1,45}">
                <div class="invalid-feedback">
                    <fmt:message key="registration.feedback.surname"/>
                </div>
            </div>

            <div class="my-2">
                <label for="email" class="form-label"><fmt:message key="registration.label.email"/>:</label>
                <input type="email" id="email" class="form-control" name="email"
                       placeholder="<fmt:message key="registration.label.email"/>" required
                       pattern="^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$">
                <div class="invalid-feedback">
                    <fmt:message key="registration.feedback.email"/>
                </div>
            </div>

            <div class="my-2">
                <label for="password" class="form-label"><fmt:message key="registration.label.password"/>:</label>
                <input type="password" id="password" class="form-control"
                       placeholder="<fmt:message key="registration.label.password"/>" name="password" required
                       pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15}$">
                <div class="invalid-feedback">
                    <fmt:message key="registration.feedback.password"/>
                </div>
            </div>
            <div class="form-check">
                <label class="form-check-label" for="customer"><fmt:message key="registration.checkbox.customer"/></label>
                <input class="form-check-input" type="radio" name="userRole" value="customer" id="customer" required>
            </div>
            <div class="form-check">
                <label class="form-check-label" for="doctor"><fmt:message key="registration.checkbox.doctor"/></label>
                <input class="form-check-input" type="radio" name="userRole" value="doctor" id="doctor" required>
            </div>
            <br>
            ${errorMessage}
            ${errorMessage=null}
            <br>
            <div>
                <button class="w-100 btn btn-md btn-primary" type="submit"><fmt:message key="registration.button.submit"/></button>
            </div>
            <div>
                <button class="w-100 btn btn-md btn-success" type="submit"><fmt:message key="registration.button.redirectToLogin"/></button>
            </div>
        </form>
    </div>
</div>
</div>
</body>
</html>
