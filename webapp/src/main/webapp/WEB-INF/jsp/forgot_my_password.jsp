<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Dine Out</title>
    <%@ include file="./header.jsp" %>
</head>
<body class="default_light">
<%@ include file="./navbar.jsp" %>
<div id="index-banner" class="align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message
                    code="home.forgot_password"/></h1>
        </div>
    </div>
</div>

<c:url value="/reset_password" var="postPath"/>
<form:form id="password_recovery_form" modelAttribute="passwordRecoveryForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <form:errors path="username" element="p" cssClass="isa_error"/>
                        <form:label path="username"><spring:message code="password.recovery.username"/>*</form:label>
                        <form:input type="text" path="username"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                                code="password.recovery.sendEmail"/></h6>
                    </div>
                    <div class="row center">
                        <button type="submit" name="action"
                                class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                            <spring:message code="password.recovery.continue"/>
                            <i class="material-icons right">send</i>  <!-- TODO: add floating message -->
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>


<%@ include file="./footer.jsp" %>
</body>
</html>

