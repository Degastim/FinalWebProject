<%@ include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="account_replenishment.title"/><</title>
</head>
<body class="bg-light">
<div class="position-absolute top-50 start-50 translate-middle">
    <form action="controller" method="POST">
        <input type="hidden" name="command" value="account_replenishment">
        <label for="amount"><fmt:message key="account_replenishment.title"/></label>
        <input type="number" name="amount" id="amount" min="0" step="any">
        <button class="btn btn-primary"><fmt:message key="account_replenishment.button"/></button>
    </form>
</div>
<h5 style="color: green">${informationMessage}</h5>
${informationMessage=null}
</body>
</html>
