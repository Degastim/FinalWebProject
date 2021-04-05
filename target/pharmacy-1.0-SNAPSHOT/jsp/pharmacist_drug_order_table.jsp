<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="pharmacist_drug_order_table.title"/></title>
</head>
<body>
<table class="table table-dark table-hover table-bordered">
    <tr>
        <td><fmt:message key="pharmacist_drug_order_table.column.customerName"/></td>
        <td><fmt:message key="pharmacist_drug_order_table.column.customerSurname"/></td>
        <td><fmt:message key="pharmacist_drug_order_table.column.drugName"/></td>
        <td><fmt:message key="pharmacist_drug_order_table.column.drugsNumber"/></td>
        <td><fmt:message key="customer_drug_order_table.column.act"/></td>
    </tr>
    <c:forEach var="drugOrder" items="${drugOrderList}">
        <tr>
            <td>${drugOrder.customer.name}</td>
            <td>${drugOrder.customer.surname}</td>
            <td>${drugOrder.drug.drugName}</td>
            <td>${drugOrder.drugsNumber}</td>
            <td>
                <form action="controller" method="GET">
                    <input type="hidden" name="drugOrderId" value="${drugOrder.id}">
                    <button class="btn btn-primary" type="submit" name="command" value="confirm_drug_order">
                        <fmt:message key="customer_drug_order_table.column.act.confirm"/></button>
                    <button class="btn btn-danger" type="submit" name="command" value="refuse_drug_order"><fmt:message key="customer_drug_order_table.column.act.refuse"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h5>${errorMessage}</h5>
${errorMessage=null}
</body>
</html>
