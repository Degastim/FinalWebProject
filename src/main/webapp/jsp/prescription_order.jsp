<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="prescriptionOrder.title"/></title>
</head>
<body class="bg-light">
<div class="container">
    <main>
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4" src="${pageContext.request.contextPath}/img/add.svg" alt="" width="72"
                 height="57">
            <h2><fmt:message key="prescriptionOrder.head"/></h2>
        </div>

        <div class="row g-3">
            <div class="col-md-7 col-lg-9">
                <h4 class="mb-3"><fmt:message key="prescriptionOrder.data"/></h4>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="prescription_order_confirmation">
                    <input type="hidden" name="prescriptionId" value="${prescription.prescriptionId}">
                    <div class="row g-3">

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="customerName" class="col-form-label"><fmt:message key="prescriptionOrder.customerName"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="customerName" type="text" value="${prescription.customer.name}" readonly>
                            </div>
                        </div>


                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="customerSurname" class="col-form-label"><fmt:message key="prescriptionOrder.customerSurname"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="customerSurname" type="text" value="${prescription.customer.surname}" readonly>
                            </div>
                        </div>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="amount" class="col-form-label"><fmt:message key="prescriptionOrder.amount"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="amount" type="number" value="${prescription.amount}" readonly>
                            </div>
                        </div>
                        <h3><fmt:message key="prescriptionOrder.date"/></h3>
                            <div class="row g-2">
                                <div class="col-2">
                                    <label for="day" class="col-form-label"><fmt:message key="prescriptionOrder.date.day"/>:</label>
                                </div>
                                <div class="col-1">
                                    <input class="form-control" id="day" type="number" name="day" min="1" max="31" required></div>
                            </div>

                            <div class="row g-2">
                                <div class="col-2">
                                    <label for="month" class="col-form-label"><fmt:message key="prescriptionOrder.date.month"/>:</label>
                                </div>
                                <div class="col-1">
                                    <input class="form-control" id="month" type="number" name="month" min="1" max="12" required>
                                </div>
                            </div>

                            <div class="row g-2">
                                <div class="col-2">
                                    <label for="year" class="col-form-label"><fmt:message key="prescriptionOrder.date.year"/>:</label>
                                </div>
                                <div class="col-1">
                                    <input class="form-control" id="year" type="number" name="year" min="${currentYear}" max="${maxYear}" required>
                                </div>
                            </div>
                    </div>
                    <h5 style="color: red">${errorMessage}</h5>
                    ${errorMessage=null}
                    <hr class="my-4">
                    <div class="row">

                        <div class="col-6">
                            <button class="w-100 btn btn-primary" type="submit"><fmt:message key="prescriptionRequest.submit"/></button>
                        </div>

                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
