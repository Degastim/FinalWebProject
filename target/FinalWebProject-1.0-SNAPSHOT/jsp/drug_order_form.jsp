<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="drugOrderForm.title"/></title>
</head>
<body class="bg-light">
<div class="container">
    <main>
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4"  src="${pageContext.request.contextPath}/img/add.svg" alt="" width="72" height="57">
            <h2><fmt:message key="drugOrderForm.head"/></h2>
        </div>

        <div class="row g-3">
            <div class="col-md-7 col-lg-9">
                <h4 class="mb-3"><fmt:message key="drugOrderForm.data"/></h4>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="drug_order">
                    <div class="row g-3">

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drugName" class="col-form-label"><fmt:message key="drugOrderForm.drugName"/>:</label>
                            </div>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="drugName" name="drugName" value="${drugName}" required>
                            </div>
                        </div>


                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drugAmount" class="col-form-label"><fmt:message key="drugOrderForm.amount"/>:</label>
                            </div>
                            <div class="col-sm-1">
                                <input type="number" class="form-control" id="drugAmount" name="drugAmount" required>
                            </div>
                        </div>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="dosage" class="col-form-label"><fmt:message key="drugOrderForm.dosage"/>%:</label>
                            </div>
                            <div class="col-sm-1">
                                <input type="number" class="form-control" id="dosage" name="dosage" value="${dosage}" required>
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">
                    <h5 style="color:red">${errorMessage}</h5>
                    ${errorMessage=null}
                    <div class="row">
                        <div class="col-6">
                            <button class="w-100 btn btn-primary" type="submit"><fmt:message key="drugOrderForm.submit.pay"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
