<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="changePassword.title"/></title>
</head>
<body class="bg-light">
<div class="container">
    <main>
        <form action="controller" method="POST">
            <input type="hidden" name="command" value="change_password">
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4"  src="${pageContext.request.contextPath}/img/exchange.svg" alt="" width="72" height="57">
            <h2><fmt:message key="changePassword.head"/></h2>
        </div>

        <div class="row g-3">
            <div class="col-md-7 col-lg-9">
                <h4 class="mb-3"><fmt:message key="changePassword.data"/></h4>
                <form action="controller" method="POST">
                    <div class="row g-3">

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="oldPassword" class="col-form-label"><fmt:message key="changePassword.oldPassword"/>:</label>
                            </div>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
                            </div>
                        </div>


                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="newPassword" class="col-form-label"><fmt:message key="changePassword.newPassword"/>:</label>
                            </div>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <div>
                        <h5>${passwordChangeResult}</h5>
                        ${passwordChangeResult=null}
                        <button type="submit" class="btn btn-primary"><fmt:message key="changePassword.submit"/></button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
