<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="customerPrescriptionTable.title"/></title>
</head>
<body>
<c:choose>
    <c:when test="${prescriptionList.isEmpty()}">
        <fmt:message key="customerPrescriptionTable.error.noPrescription"/>
    </c:when>
    <c:otherwise>
        <form action="controller" method="POST">
            <input type="hidden" name="command" value="prescription_renewal">
            <table class="table table-dark table-hover table-bordered">
                <tr>
                    <td><fmt:message key="customerPrescriptionTable.column.doctorsName"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.doctorsSurname"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.drugName"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.amount"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.issueDate"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.endDate"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.act"/></td>
                    <td><fmt:message key="customerPrescriptionTable.column.status"/></td>
                </tr>
                <c:forEach var="prescription" items="${prescriptionList}">
                    <tr>
                        <td>${prescription.doctor.name}</td>
                        <td>${prescription.doctor.surname}</td>
                        <td>${prescription.drug.drugName}</td>
                        <td>${prescription.amount}</td>
                        <td>${prescription.issueDate}</td>
                        <td>${prescription.endDate}</td>
                        <td>
                <span class="d-inline-block" tabindex="0" data-bs-toggle="tooltip" title="<fmt:message key="customerPrescriptionTable.button.title"/>">
                    <button class="btn btn-info ${prescription.status!='APPROVED' ? 'disabled' : ''} " type="submit" name="prescriptionId" value="${prescription.id}"><fmt:message key="customerPrescriptionTable.column.submit"/></button>
                </span>
                        </td>
                        <c:choose>
                            <c:when test="${prescription.status=='PROCESSING'}">
                                <td>
                                    <button type="button" class="btn btn-warning" disabled><fmt:message key="customerPrescriptionTable.status.processing"/></button>
                                </td>
                            </c:when>
                            <c:when test="${prescription.status=='APPROVED'}">
                                <td>
                                    <button type="button" class="btn btn-success" disabled><fmt:message key="customerPrescriptionTable.status.approved"/></button>
                                </td>
                            </c:when>
                            <c:when test="${prescription.status=='REJECTED'}">
                                <td>
                                    <button type="button" class="btn btn-danger" disabled><fmt:message key="customerPrescriptionTable.status.rejected"/></button>
                                </td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </c:otherwise>
</c:choose>
<h5>${errorMessage}</h5>
${errorMessage=null}
<form action="controller" method="get">
    <input type="hidden" name="command" value="redirect_to_add_prescription_order">
    <button class="btn btn-success w-100" type="submit"><fmt:message key="customerPrescriptionTable.button.addPrescription"/></button>
</form>
</body>
</html>
