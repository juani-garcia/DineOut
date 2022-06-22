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

<c:url value="/diner/edit" var="postUrl"/>
<form:form modelAttribute="userProfileEditForm" method="post" action="${postUrl}" id="user_profile_edit_form">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h4><spring:message code="diner.profile.edit"/></h4>
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
                    <div class="row center flex_row">
                        <a id="back-button"
                           class="btn-large waves-effect waves-red default_red white-text lighten-1 no-text-transform"
                           href="javascript:{}" onclick="history.back();">
                            <spring:message code="restaurant.edit.form.back"/>
                        </a>
                        <button type="submit" name="action"
                                class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1 margin_left_auto">
                            <spring:message code="register.restaurant.form.update"/>
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>


<%@ include file="../footer.jsp" %>
</body>
</html>
