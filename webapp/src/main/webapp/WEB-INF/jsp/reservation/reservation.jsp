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

<c:url value="/create/${resId}" var="postPath"/>
<form:form id="reservation_form" modelAttribute="reservationForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="reservation.reservation.form.header"/></h5>
                    </div>
                    <c:if test="${formSuccess == true}">
                        <div class="isa_success">
                            Se realizo la reserva exitosamente!
                        </div>
                    </c:if>
                    <div class="row">
                        <form:label path="amount" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.amount"/>*</form:label>
                        <form:input path="amount" type="number"/>
                        <form:errors path="amount" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="dateTime" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.date_and_time"/>*</form:label>
                        <form:input path="dateTime" type="datetime-local"/>
                        <form:errors path="dateTime" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="comments" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.comments"/></form:label>
                        <form:input path="comments" type="text"/>
                        <form:errors path="comments" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                                code="form.mandatory"/></h6>
                    </div>
                    <div class="row center">
                        <a type="submit" id="register-button"
                           class="btn-large waves-effect waves-red white black-text lighten-1"
                           href="javascript:{}" onclick="document.getElementById('reservation_form').submit();">
                            <spring:message code="reservation.reservation.form.submit" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--    <div>--%>
    <%--        <input type="submit" value="Register!"/>--%>
    <%--    </div>--%>


</form:form>
<%@ include file="../footer.jsp" %>
</body>
</html>