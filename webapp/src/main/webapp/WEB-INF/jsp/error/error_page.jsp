<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title><c:out value="${code}"/> </title>
    <%@ include file="../header.jsp" %>
</head>
<body>

<div class="container margins_auto">
    <div class="section">
        <div class="rounded shadowed white">
            <div class="flex_center">
                <div class="section flex_center">
                    <div class="card default_red">
                        <div class="card-content">
                            <h1 class="white-text megabold">
                                <c:out value="${code}" /> -
                                <spring:message code="${title}"/>
                            </h1>
                        </div>
                    </div>
                </div>
                <h5><spring:message code="${hint}"/></h5>
                <h5><b><spring:message code="error.status"/>:</b> <c:out value="${code}"/></h5>
                <h5 id="error_uri"><b><spring:message code="error.uri"/>:</b>
                </h5>
                <div class="section flex_center flex_row">
                    <button onClick="history.back()"
                            class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1 ">
                        <i class="material-icons right">keyboard_backspace</i>
                        <spring:message code="error.back" />
                    </button>
                    <a href="<c:url value="/"/>" class="margin_l_20px">
                        <button class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                            <i class="material-icons right">home</i>
                            <spring:message code="error.home" />
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        let errorTag = document.getElementById("error_uri");
        errorTag.innerHTML = errorTag.innerHTML + " " + window.location.href;
    });
</script>
</body>
</html>
