<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="addPrescriptionOrder.title"/></title>
</head>
<body class="bg-light">
<script type="text/javascript">
    <c:if test="${errorMessage != null}">
    alert(${errorMessage})
    </c:if>
</script>
<div class="container">
    <main>
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4" src="${pageContext.request.contextPath}/img/add.svg" alt="" width="72"
                 height="57">
            <h2><fmt:message key="addPrescriptionOrder.head"/></h2>
        </div>

        <div class="row g-3">
            <div class="col-md-7 col-lg-9">
                <h4 class="mb-3"><fmt:message key="addPrescriptionOrder.data"/></h4>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="add_prescription_order">
                    <div class="row g-3">

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="doctor" class="col-form-label"><fmt:message key="addPrescriptionOrder.doctor"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <select class="form-select" id="doctor" name="doctor">
                                    <c:forEach var="doctor" items="${doctorList}">
                                        <option value="${doctor.userId}">${doctor.name} ${doctor.surname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-5">
                                <h5>${noDoctorMessage}</h5>
                            </div>
                        </div>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drug" class="col-form-label"><fmt:message key="addPrescriptionOrder.drugName"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="drug" type="search" placeholder="<fmt:message key="addPrescriptionOrder.drugName.placeholder"/>" list="drugList" name="drugName" required>
                            </div>
                            <div class="col-sm-5">
                                <h5>${noDrugMessage}</h5>
                            </div>
                        </div>

                        <datalist id="drugList">
                            <c:forEach var="drug" items="${drugList}">
                                <option>${drug.drugName}</option>
                            </c:forEach>
                        </datalist>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drugAmount" class="col-form-label"><fmt:message
                                        key="addPrescriptionOrder.drugAmount"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="drugAmount" type="number" name="drugAmount" required>
                            </div>
                        </div>

                    </div>
                    ${addPrescriptionErrorMessage}
                    ${addPrescriptionErrorMessage=null}
                    <hr class="my-4">
                    <div class="row">

                        <div class="col-6">
                            <button class="w-100 btn btn-primary" type="submit"><fmt:message key="addPrescriptionOrder.submit"/></button>
                        </div>

                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
