<%@ include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="login.title"/></title>
</head>
<body class="text-center">
<div class="row">
    <div class="col-md-4 mx-auto my-5">
        <form method="POST" action="controller">
            <input type="hidden" name="command" value="login"/>
            <img class="mb-4" src="${pageContext.request.contextPath}/img/people_circle.svg" alt="" width="72"
                 height="57">

            <div class="row mb-4">
                <label for="inputEmail" class="col-sm-3 col-form-label"><fmt:message key="login.email"/>:</label>
                <div class="col-sm-9">
                    <input type="email" class="form-control" id="inputEmail" name="email"
                           placeholder="<fmt:message key="login.email"/>">
                </div>
            </div>

            <div class="row mb-4">
                <label for="inputPassword" class="col-sm-3 col-form-label"><fmt:message key="login.password"/>:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="inputPassword" name="password"
                           placeholder="<fmt:message key="login.password"/>">
                </div>
                <br>
                <h5 style="color: red">${errorMessage}</h5>
                ${errorMessage=null}
                <br>
            </div>
            <button class="w-100 btn btn-md btn-primary" type="submit"><fmt:message key="login.button.submit"/></button>
        </form>
        <form method="GET" action="controller">
            <button class="w-100 btn btn-md btn-success" type="submit" name="command" value="redirect_to_registration">
                <fmt:message key="login.button.redirectToRegistration"/></button>
        </form>
    </div>
</div>
</body>
</html>