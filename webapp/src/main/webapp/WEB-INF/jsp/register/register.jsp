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
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold">¡Registra tu restaurante!</h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
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
                    <c:if test="${duplicatedUsername}">
                        <p class="isa_error">
                            <spring:message code="home.form.duplicated_username" />
                        </p>
                    </c:if>
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
                    <div class="row input-field">
                        <form:label path="role"><spring:message
                                code="home.register.form.roleSelector"/></form:label>
                        <form:select path="role" items="${roleItems}" size="${roleItems.size()}"/>
                        <form:errors path="role" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row center">
                        <a type="submit" id="register-button"
                           class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1"
                           href="javascript:{}"
                           onclick="document.getElementById('register_form').submit();"><spring:message
                                code="home.register.form.button"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>


<%@ include file="../footer.jsp" %>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var options = [];
        <c:forEach items="${roleItems}" var="roleItem">
        options.push("${roleItem}");
        </c:forEach>
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems, options);
    });
</script>
</body>
</html>
