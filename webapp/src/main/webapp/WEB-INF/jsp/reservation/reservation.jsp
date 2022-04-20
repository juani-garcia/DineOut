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
<div id="index-banner" class="parallax-container parallax-container-small">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><c:out value="${restaurant.name}"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>

<div class="container">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row default_dark rounded shadowed">
            <div class="align_center align_space_between">
                <div class="icon-block">
                    <h2 class="center white-text"><i class="material-icons">restaurant_menu</i></h2>
                    <h5 class="center white-text semibold"><c:out value="${restaurant.detail}"/></h5>
                </div>
                    <h5 class="center white-text">&#128205;<c:out value="${restaurant.address}"/></h5>
                    <h5 class="center white-text">Horarios: </h5>
            </div>
        </div>
    </div>
</div>

<c:url value="/create/${resId}" var="postPath"/>
<form:form id="reservation_form" modelAttribute="reservationForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="reservation.reservation.form.header"/></h5>
                    </div>
                    <div class="row">
                        <form:label path="mail" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.mail"/>*</form:label>
                        <form:input type="text" path="mail"/>
                        <form:errors path="mail" cssClass="isa_error" element="p"/>
                    </div>

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
                           href="javascript:{}" onclick="document.getElementById('reservation_form').submit();">¡Reservá!</a>
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