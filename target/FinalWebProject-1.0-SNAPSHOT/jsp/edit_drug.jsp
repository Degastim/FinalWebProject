<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="editDrug.title"/></title>
</head>
<body class="bg-light">
<div class="container">
    <div class="py-5 text-center">
        <img class="d-block mx-auto mb-4" src="${pageContext.request.contextPath}/img/exchange.svg" alt="" width="72"
             height="57">
        <h2><fmt:message key="editDrug.head"/></h2>
    </div>

    <div class="row g-3">
        <div class="col-md-7 col-lg-9">
            <h4 class="mb-3"><fmt:message key="editDrug.data"/></h4>
            <form action="controller" method="post">
                <input type="hidden" name="drugId" value="${editDrug.drugId}">
                <div class="row g-3">

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="drugName" class="col-form-label"><fmt:message key="editDrug.drugName"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="drugName" name="drugName"
                                   value="${editDrug.drugName}" required>
                        </div>
                    </div>


                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="drugAmount" class="col-form-label"><fmt:message
                                    key="editDrug.drugAmount"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="drugAmount" name="drugAmount"
                                   value="${editDrug.amount}" required>
                        </div>
                    </div>

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="dosage" class="col-form-label"><fmt:message key="editDrug.drugDosage"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="dosage" name="dosage"
                                   value="${editDrug.dosage}" required>
                        </div>
                    </div>

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="price" class="col-form-label"><fmt:message key="editDrug.drugPrice"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="price" name="price" value="${editDrug.price}"
                                   required>
                        </div>
                    </div>

                    <div class="col-12">
                        <label for="drugDescription" class="form-label"><fmt:message key="editDrug.drugDescription"/>:</label>
                        <div class="input-group">
                            <textarea class="form-control" id="drugDescription" name="drugDescription" rows="3" required>${editDrug.description}</textarea>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <h5><fmt:message key="editDrug.radioButton.title"/>:</h5>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="needPrescription" id="flexRadioDefault1" value="true" ${editDrug.needPrescription==true?'checked':''}>
                    <label class="form-check-label" for="flexRadioDefault1"><fmt:message key="editDrug.radioButton.true"/></label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="needPrescription" id="flexRadioDefault2" value="false" ${editDrug.needPrescription!=true?'checked':''}>
                    <label class="form-check-label" for="flexRadioDefault2"><fmt:message key="editDrug.radioButton.false"/></label>
                </div>

                <hr class="my-4">

                <div class="row">
                    <div class="col-6">
                        <button class="w-100 btn btn-primary" type="submit" name="command" value="edit_drug"><fmt:message key="editDrug.submit"/></button>
                    </div>

                    <div class="col-6">
                        <button type="submit" class="w-100 btn btn-danger" name="command" value="delete_drug"><fmt:message key="editDrug.delete"/></button>
                    </div>
                </div>
            </form>
            <div class="mb-4">
                <form action="upload" enctype="multipart/form-data" method="POST">
                    <input type="hidden" name="drugId" value="${editDrug.drugId}">
                    <label for="formFile" class="form-label"><fmt:message key="editDrug.uploadFile.label"/>:</label>
                    <input class="form-control form-control-sm" type="file" name="picture" id="formFile" height="130">
                    <div class="my-2">
                        <input type="submit" value="<fmt:message key="editDrug.uploadFile.submit"/>">
                    </div>
                </form>
            </div>
            <table class="table table-striped">
                <tr>
                    <td><h5><fmt:message key="editDrug.table.id"/></h5></td>
                    <td><h5><fmt:message key="editDrug.table.images"/></h5></td>
                </tr>
                <c:forEach var="image" items="${editDrug.images}" varStatus="counter">
                    <tr>
                        <td class="">${counter.count}</td>
                        <td><img src="data:;base64,${image}" width="150" height="150"></td>
                    </tr>
                </c:forEach>
            </table>
            <hr class="my-4">
        </div>
    </div>
</div>
</body>
</html>
