<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="prescriptionOrderTable.title"/></title>
</head>
<body>
    <table class="table table-dark table-hover table-bordered">
        <tr>
            <td><fmt:message key="prescriptionOrderTable.column.id"/></td>
            <td><fmt:message key="prescriptionOrderTable.column.customerName"/></td>
            <td><fmt:message key="prescriptionOrderTable.column.customerSurname"/></td>
            <td><fmt:message key="prescriptionOrderTable.column.drugName"/></td>
            <td><fmt:message key="prescriptionOrderTable.column.amount"/></td>
            <td><fmt:message key="prescriptionOrderTable.column.act"/></td>
        </tr>
        <c:forEach var="prescription" items="${prescriptionList}">
            <tr>
                <td>${prescription.prescriptionId}</td>
                <td>${prescription.customer.name}</td>
                <td>${prescription.customer.surname}</td>
                <td>${prescription.drug.drugName}</td>
                <td>${prescription.amount}</td>
                <td>
                    <form action="controller" method="GET">
                        <input type="hidden"  >
                        <input type="hidden" name="prescriptionId" value="${prescription.prescriptionId}">
                        <button class="btn btn-primary" type="submit" name="command" value="redirect_to_prescription_order"><fmt:message key="prescriptionOrderTable.column.button.renewal"/></button>
                        <button class="btn btn-danger" type="submit" name="command" value="refusal_prescription_order"><fmt:message key="prescriptionOrderTable.column.button.renouncement"/></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
