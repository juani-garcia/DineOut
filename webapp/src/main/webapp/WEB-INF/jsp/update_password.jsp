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
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message
                    code="home.forgot_password"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>

<c:url value="/save_password" var="postPath"/>
<form:form id="new_password_form" modelAttribute="newPasswordForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <form:errors path="password" element="p" cssClass="isa_error"/>
                        <form:label path="password"><spring:message code="password.recovery.newPassword"/>*</form:label>
                        <form:input type="password" path="password"/>
                    </div>
                    <div class="row">
                        <form:errors path="confirmPassword" element="p" cssClass="isa_error"/>
                        <form:label path="confirmPassword"><spring:message
                                code="password.recovery.confirmPassword"/>*</form:label>
                        <form:input type="password" path="confirmPassword"/>
                    </div>
                    <div class="display_hidden">
                        <form:label path="token" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.email"/>*</form:label>
                        <form:input path="token" type="text"/>
                        <form:errors path="token" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row center">
                        <button type="submit" name="action"
                                class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                            <spring:message code="register.restaurant.form.continue"/>
                            <i class="material-icons right">send</i>
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

