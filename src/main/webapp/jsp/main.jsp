<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="main.title"/></title>
</head>
<body>
<div class="container my-5">
    <div class="row">
        <c:forEach var="drug" items="${cardsDrugList}" varStatus="drugCounter">
            <div class="col-sm-3">
                <form method="GET" action="controller">
                    <input type="hidden" name="command" value="redirect_to_drug_order_form">
                    <div class="card  h-100" style="width: 20rem">
                        <c:if test="${drug.drugPictureList.size()!=0}">
                            <div id="drugCard${drugCounter.count}" class="carousel carousel-dark slide" data-bs-ride="carousel">
                                <div class="carousel-indicators">
                                    <c:forEach var="picture" items="${drug.drugPictureList}" varStatus="counter">
                                        <button type="button" data-bs-target="#drugCard${drugCounter.count}" data-bs-slide-to="${counter.count-1}" class="${counter.count==1?'active':''}"></button>
                                    </c:forEach>
                                </div>
                                <div class="carousel-inner">
                                    <c:forEach var="picture" items="${drug.drugPictureList}" varStatus="counter">
                                        <div class="carousel-item ${counter.count==1?'active':''}">
                                            <img src="data:;base64,${picture.drugPicture}" class="card-img-top">
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#drugCard${drugCounter.count}" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#drugCard${drugCounter.count}" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                </button>
                            </div>
                        </c:if>
                        <div class="card-body text-center">
                            <h5 class="card-title">${drug.drugName}</h5>
                            <h5><a><fmt:message key="main.drugCard.footer.dosage"/>:${drug.dosage}</a></h5>
                            <p class="card-text">${drug.description}</p>
                        </div>
                        <div class="card-footer">
                            <input type="hidden" name="drugName" value="${drug.drugName}">
                            <input type="hidden" name="dosage" value="${drug.dosage}">
                            <button class="btn btn-primary me-4 ${user.role!='CUSTOMER'?'disabled':''}" type="submit"><fmt:message key="main.drugCard.footer.button.buy"/></button>
                            <a><fmt:message key="main.drugCard.footer.amount"/>:${drug.drugAmount}</a>
                            <a><fmt:message key="main.drugCard.footer.price"/>:${drug.dosage}</a>
                            <c:choose>
                                <c:when test="${drug.needPrescription==true}">
                                    <a><fmt:message key="main.drugCard.footer.prescription"/>:<fmt:message key="main.drugCard.footer.prescription.true"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a><fmt:message key="main.drugCard.footer.prescription"/>:<fmt:message key="main.drugCard.footer.prescription.false"/></a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </form>
            </div>
        </c:forEach>
    </div>
</div>
<h5 style="color: red">${errorMessage}</h5>
<div class="container my-5">
    <form method="GET" action="controller" name="paginationForm">
        <input type="hidden" name="command" value="pagination">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <button class="page-link" type="submit" name="paginationPage" value="previous"><fmt:message key="main.pagination.button.previous"/></button>
                </li>
                <c:forEach var="number" begin="${startPaginationPage}" end="${lastPaginationPage}">
                    <li class="page-item  ${currentPaginationPage == number?'active':''}">
                        <button class="page-link" type="submit" name="paginationPage" value="${number}">${number}</button>
                    </li>
                </c:forEach>
                <li class="page-item">
                    <button class="page-link" type="submit" name="paginationPage" value="next"><fmt:message key="main.pagination.button.next"/></button>
                </li>
            </ul>
        </nav>
    </form>
</div>
</body>
</html>
