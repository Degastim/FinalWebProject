<%@ include file="header.jsp" %>
<%@ taglib prefix="ctg" uri="custom_tag" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="drugTable.title"/></title>
</head>
<body>
<ctg:drugTable/>
</body>
</html>
