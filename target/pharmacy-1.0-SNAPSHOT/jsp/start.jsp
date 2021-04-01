<%@ include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="start.head"/></title>
    <style>
        .cover-container {
            max-width: 42em;
        }
    </style>
</head>
<body>

<div class="d-flex h-100 text-center text-dark bg-light my-auto">
<div class="cover-container d-flex w-100 h-50 p-3 mx-auto flex-column">
    <main class="px-3">
        <h1><fmt:message key="start.head"/></h1>
        <p class="lead"><fmt:message key="start.description"/></p>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="redirect_to_main">
        <p class="lead">
            <button class="btn btn-lg btn-dark fw-bold border-white bg-dark" ><fmt:message key="start.button"/></button>
        </p>
    </form>
    </main>


</div>

</div>

</body>
</html>
