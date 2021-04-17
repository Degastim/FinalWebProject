<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="addPrescriptionOrder.title"/></title>
</head>
<body class="bg-light">
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
                <form action="controller" method="POST">
                    <input type="hidden" name="command" value="add_prescription_order">
                    <div class="row g-3">

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="doctor" class="col-form-label"><fmt:message
                                        key="addPrescriptionOrder.doctor"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <select class="form-select" id="doctor" name="doctor">
                                    <c:forEach var="doctor" items="${doctorList}">
                                        <option value="${doctor.id}">${doctor.name} ${doctor.surname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <c:if test="${doctorList.isEmpty()}">
                                <div class="col-sm-5">
                                    <fmt:message key="addPrescriptionOrder.error.noDoctor"/>
                                </div>
                            </c:if>
                        </div>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drugName" class="col-form-label"><fmt:message key="addPrescriptionOrder.drugName"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="drugName" type="search" placeholder="<fmt:message key="addPrescriptionOrder.drugName.placeholder"/>" list="drugList" name="drugName" required>
                            </div>

                            <c:if test="${drugList.isEmpty()}">
                                <div class="col-sm-5">
                                    <fmt:message key="addPrescriptionOrder.error.noDrug"/>
                                </div>
                            </c:if>
                        </div>

                        <datalist id="drugList">
                            <c:forEach var="drug" items="${drugList}">
                                <option>${drug.drugName}</option>
                            </c:forEach>
                        </datalist>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="dosage" class="col-form-label"><fmt:message
                                        key="addPrescriptionOrder.dosage"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="dosage" type="number" name="dosage" min="0" step="any" required>
                            </div>
                        </div>

                        <div class="row g-2">
                            <div class="col-sm-3">
                                <label for="drugAmount" class="col-form-label"><fmt:message key="addPrescriptionOrder.drugAmount"/>:</label>
                            </div>
                            <div class="col-sm-4">
                                <input class="form-control" id="drugAmount" type="number" name="drugAmount" min="0"
                                       required>
                            </div>
                        </div>

                    </div>
                    ${errorMessage}
                    ${errorMessage=null}
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
