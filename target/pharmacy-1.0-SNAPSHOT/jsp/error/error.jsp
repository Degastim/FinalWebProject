<%@ include file="/jsp/header.jsp" %>
<html>
<head>
    <title><fmt:message key="error.title"</title>
</head>
<body>
<fmt:message key="error.request1"/> ${pageContext.errorData.requestURI} <fmt:message key="error.request2"/>
<br/>
<fmt:message key="error.servlet"/> : ${pageContext.errorData.servletName}
<br/>
<fmt:message key="error.statusCode"/> : ${pageContext.errorData.statusCode}
<br/>
<fmt:message key="error.exception"/> : ${pageContext.errorData.throwable}
</body>
</html>