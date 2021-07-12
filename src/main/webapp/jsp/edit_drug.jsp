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
                <input type="hidden" name="drugId" value="${editDrug.id}">
                <div class="row g-3">

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="drugName" class="col-form-label"><fmt:message key="editDrug.drugName"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="drugName" name="drugName"
                                   value="${editDrug.drugName}" required >
                        </div>
                    </div>


                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="drugAmount" class="col-form-label"><fmt:message
                                    key="editDrug.drugAmount"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="drugAmount" name="drugAmount"
                                   value="${editDrug.drugAmount}" min="0" required>
                        </div>
                    </div>

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="dosage" class="col-form-label"><fmt:message key="editDrug.drugDosage"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="dosage" name="dosage"
                                   value="${editDrug.dosage}" min="0" step="any" required>
                        </div>
                    </div>

                    <div class="row g-2">
                        <div class="col-sm-3">
                            <label for="price" class="col-form-label"><fmt:message key="editDrug.drugPrice"/>:</label>
                        </div>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" id="price" name="price" step="any"
                                   value="${editDrug.price}" min="0" required>
                        </div>
                    </div>

                    <div class="col-12">
                        <label for="drugDescription" class="form-label"><fmt:message
                                key="editDrug.drugDescription"/>:</label>
                        <div class="input-group">
                            <textarea class="form-control" id="drugDescription" name="drugDescription" rows="3"
                                      required>${editDrug.description} </textarea>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <h5><fmt:message key="editDrug.radioButton.title"/>:</h5>
                <div class="form-check">
                    <label class="form-check-label" for="radioButton1"><fmt:message
                            key="editDrug.radioButton.true"/></label>
                    <input class="form-check-input" type="radio" name="needPrescription" id="radioButton1"
                           value="true" ${editDrug.needPrescription==true?'checked':''}>
                </div>
                <div class="form-check">
                    <label class="form-check-label" for="radioButton2"><fmt:message
                            key="editDrug.radioButton.false"/></label>
                    <input class="form-check-input" type="radio" name="needPrescription" id="radioButton2"
                           value="false" ${editDrug.needPrescription!=true?'checked':''}>
                </div>

                <hr class="my-4">
                <h5 style="color: red">${errorMessage}</h5>
                ${errorMessage=null}
                <div class="row">
                    <div class="col-6">
                        <button class="w-100 btn btn-primary" type="submit" name="command" value="edit_drug">
                            <fmt:message key="editDrug.submit"/></button>
                    </div>

                    <div class="col-6">
                        <button type="submit" class="w-100 btn btn-danger" name="command" value="delete_drug">
                            <fmt:message key="editDrug.delete"/></button>
                    </div>
                </div>
            </form>
            <div class="mb-4">
                <form action="upload" enctype="multipart/form-data" method="POST">
                    <input type="hidden" name="drugId" value="${editDrug.id}">
                    <label for="formFile" class="form-label"><fmt:message key="editDrug.uploadFile.label"/>:</label>
                    <input class="form-control form-control-sm" type="file" name="picture" id="formFile" height="130">
                    <div class="my-2">
                        <input type="submit" value="<fmt:message key="editDrug.uploadFile.submit"/>">
                    </div>
                </form>
            </div>
            <c:choose>
                <c:when test="${editDrug.drugPictureList.isEmpty()}">
                    <h2><fmt:message key="editDrug.informationMessage.noDrugPictureList"/></h2>
                </c:when>
                <c:otherwise>
                    <form action="controller" method="POST">
                        <table class="table table-striped">
                            <tr>
                                <td><h5><fmt:message key="editDrug.table.count"/></h5></td>
                                <td><h5><fmt:message key="editDrug.table.images"/></h5></td>
                                <td><h5><fmt:message key="editDrug.table.act"/></h5></td>
                            </tr>
                            <c:forEach var="picture" items="${editDrug.drugPictureList}" varStatus="counter">
                                <input type="hidden" name="drugPictureId" value="${picture.id}">
                                <tr>
                                    <td class="">${counter.count}</td>
                                    <td><img src="data:;base64,${picture.drugPicture}" width="150" height="150"></td>
                                    <td>
                                        <button type="submit" class="w-100 btn btn-danger" name="command"
                                                value="delete_drug_picture"><fmt:message
                                                key="editDrug.delete"/></button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </form>
                </c:otherwise>
            </c:choose>
            <hr class="my-4">
        </div>
    </div>
</div>
</body>
</html>
