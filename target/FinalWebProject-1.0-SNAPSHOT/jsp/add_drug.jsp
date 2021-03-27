<%@ include file="header.jsp" %>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="addDrug.title"/></title>
</head>
<body class="bg-light">
<div class="container">
    <main>
        <div class="py-5 text-center">
            <img class="d-block mx-auto mb-4" src="${pageContext.request.contextPath}/img/add.svg" alt="" width="72"
                 height="57">
            <h2><fmt:message key="addDrug.head"/></h2>
        </div>

        <div class="row g-3">
            <div class="col-md-7 col-lg-9">
                <h4 class="mb-3"><fmt:message key="addDrug.data"/></h4>
                <form action="controller" method="post">
                    <div class="row g-3">

                        <div class="row g-2">

                            <div class="col-sm-3">
                                <label for="drugName" class="col-form-label"><fmt:message key="addDrug.drugName"/>:</label>
                            </div>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="drugName" name="drugName" value="${editDrug.drugName}" required>
                            </div>

                        </div>


                        <div class="row g-2">

                            <div class="col-sm-3">
                                <label for="drugAmount" class="col-form-label"><fmt:message key="addDrug.drugAmount"/>:</label>
                            </div>
                            <div class="col-sm-6">
                                <input type="number" class="form-control" id="drugAmount" name="drugAmount" value="${editDrug.amount}" min="0" required>
                            </div>

                        </div>

                        <div class="col-12">
                            <label for="drugDescription" class="form-label"><fmt:message key="addDrug.drugDescription"/>:</label>
                            <div class="input-group">
                                <textarea class="form-control" id="drugDescription" name="drugDescription" rows="3" required>{editDrug.description}</textarea>
                            </div>
                        </div>

                    </div>

                    <hr class="my-4">

                    <h5><fmt:message key="addDrug.radioButton.title"/>:</h5>

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="needPrescription" id="flexRadioDefault1" value="true" ${editDrug.needPrescription==true?'checked':''}>
                        <label class="form-check-label" for="flexRadioDefault1"><fmt:message key="addDrug.radioButton.true"/></label>
                    </div>

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="needPrescription" id="flexRadioDefault2" value="false" ${editDrug.needPrescription!=true?'checked':''}>
                        <label class="form-check-label" for="flexRadioDefault2"><fmt:message key="addDrug.radioButton.false"/></label>
                    </div>

                    <hr class="my-4">
                    <h5 style="color: red">${errorMessage}</h5>
                    ${errorMessage=null}
                    <div class="row">
                        <div class="col-6">
                            <button class="w-100 btn btn-primary" type="submit" name="command" value="add_drug"><fmt:message key="addDrug.submit"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>
</body>
</html>
