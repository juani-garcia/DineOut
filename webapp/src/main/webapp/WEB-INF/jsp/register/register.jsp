<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dine Out</title>
    <%@ include file="../header.jsp" %>
</head>
<body class="default_light">
<%@ include file="../navbar.jsp" %>
<div id="index-banner" class="align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message code="home.register"/></h1>
        </div>
    </div>
</div>

<c:url value="/register" var="postUrl"/>
<form:form modelAttribute="registerForm" method="post" action="${postUrl}" id="register_form">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h4><spring:message code="home.register.body.title"/></h4>
                    </div>
                    <div class="row">
                        <form:errors path="firstName" element="p" cssClass="isa_error"/>
                        <form:label path="firstName"><spring:message code="home.form.firstname"/></form:label>
                        <form:input type="text" path="firstName"/>
                    </div>
                    <div class="row">
                        <form:errors path="lastName" element="p" cssClass="isa_error"/>
                        <form:label path="lastName"><spring:message code="home.form.lastname"/></form:label>
                        <form:input type="text" path="lastName"/>
                    </div>
                    <div class="row">
                        <form:errors path="username" element="p" cssClass="isa_error"/>
                        <form:label path="username"><spring:message code="home.form.username"/></form:label>
                        <form:input type="text" path="username"/>
                    </div>
                    <div class="row">
                        <form:errors path="password" element="p" cssClass="isa_error"/>
                        <form:label path="password"><spring:message code="home.form.password"/></form:label>
                        <form:input type="password" path="password"/>
                    </div>
                    <div class="row">
                        <form:errors path="confirmPassword" element="p" cssClass="isa_error"/>
                        <form:label path="confirmPassword"><spring:message
                                code="home.register.form.confirmPassword"/></form:label>
                        <form:input type="password" path="confirmPassword"/>
                    </div>
                    <div class="row"> <%-- TODO: modify to accept form:checkbox --%>
                        <label>
                            <input type="checkbox" id="isRestaurant" name="isRestaurant"/>
                            <span><spring:message code="home.register.form.roleSelector"/></span>
                        </label>
                    </div>
                    <div class="row center">
                        <button type="submit" name="action"
                                class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                            <spring:message code="home.register.form.button"/>
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                    <div class="row center">
                        <div class="flex_row">
                            <h6 class="flex_row">
                                <spring:message code="home.has_account"/>
                                <div class="grow_on_hover margins_lr_10px">
                                    <a class="thin underline default_dark_text" href="<c:url value="/login"/>">
                                        <spring:message code="home.login"/>
                                    </a>
                                </div>
                            </h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>


<%@ include file="../footer.jsp" %>
</body>
</html>
