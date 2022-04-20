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
<%@ include file="../user_detailed_navbar.jsp" %>

<h2 class="megabold center">Contanos mas de tu restaurante</h2>


<c:url value="/create_restaurant" var="postPath"/>
<form:form id="restaurant_form" modelAttribute="restaurantForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="register.restaurant.form.header"/></h5>
                    </div>
                    <div class="row">
                        <form:label path="name" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.name"/>*</form:label>
                        <form:input type="text" path="name"/>
                        <form:errors path="name" cssClass="isa_error" element="p"/>
                    </div>

                    <div class="row">
                        <form:label path="address" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.address"/>*</form:label>
                        <form:input path="address" type="text"/>
                        <form:errors path="address" cssClass="isa_error" element="p"/>
                    </div>

                    <div class="row">
                        <form:label path="email" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.email"/>*</form:label>
                        <form:input path="email" type="text"/>
                        <form:errors path="email" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="detail" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.detail"/>*</form:label>
                        <form:input path="detail" type="text"/>
                        <form:errors path="detail" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                                code="form.mandatory"/></h6>
                    </div>
                    <div class="row center">
                        <a type="submit" id="register-button"
                           class="btn-large waves-effect waves-red white black-text lighten-1"
                           href="javascript:{}"
                           onclick="document.getElementById('restaurant_form').submit();">Continua con el registro</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>

<%@ include file="../footer.jsp" %>
</body>
</html>