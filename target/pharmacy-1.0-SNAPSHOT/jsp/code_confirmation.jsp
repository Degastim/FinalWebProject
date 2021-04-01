<%@ include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="codeConfirmation.title"/><</title>
</head>
<body class="bg-light">
<div class="position-absolute top-50 start-50 translate-middle">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="code_confirmation">
        <label for="verification_code"><fmt:message key="codeConfirmation.label"/></label>
        <input type="number" name="verification_code" id="verification_code">
    </form>
</div>
<h5 style="color: red">${errorMessage}</h5>
${errorMessage=null}
</body>
</html>
