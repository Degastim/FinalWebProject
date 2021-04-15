<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="customer_drug_order_table.title"/></title>
</head>
<body>
<c:choose>
    <c:when test="${drugOrderList.isEmpty()}">
        <fmt:message key="customer_drug_order_table.message.noDrugOrdersFound"/>
    </c:when>
    <c:otherwise>
        <form action="controller" method="POST">
            <input type="hidden" name="command" value="prescription_renewal">
            <table class="table table-dark table-hover table-bordered">
                <tr>
                    <td><fmt:message key="customer_drug_order_table.column.drugName"/></td>
                    <td><fmt:message key="customer_drug_order_table.column.drugDosage"/></td>
                    <td><fmt:message key="customer_drug_order_table.column.drugsNumber"/></td>
                    <td><fmt:message key="customer_drug_order_table.column.drugOrderStatus.status"/></td>
                </tr>
                <c:forEach var="drugOrder" items="${drugOrderList}">
                    <tr>
                        <td>${drugOrder.drug.drugName}</td>
                        <td>${drugOrder.drug.dosage}</td>
                        <td>${drugOrder.drugsNumber}</td>
                        <td>
                            <c:choose>
                                <c:when test="${drugOrder.status=='PROCESSING'}">
                                    <button class="btn btn-info disabled" disabled><fmt:message key="customer_drug_order_table.column.drugOrderStatus.processing"/></button>
                                </c:when>
                                <c:when test="${drugOrder.status=='APPROVED'}">
                                    <button class="btn btn-success" disabled><fmt:message key="customer_drug_order_table.column.drugOrderStatus.approved"/></button>
                                </c:when>
                                <c:when test="${drugOrder.status=='REJECTED'}">
                                    <button class="btn btn-danger" disabled><fmt:message key="customer_drug_order_table.column.drugOrderStatus.rejected"/></button>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
