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
<%@ include file="../restaurant_detailed_navbar.jsp" %>

<h2 class="megabold center white-text"><spring:message code="register.restaurant.form.title"/></h2>


<c:url value="/restaurant/register" var="postPath"/>
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
                    <div class="row input-field">
                        <form:label path="zone" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.zone"/></form:label>
                        <form:select path="zone">
                            <c:forEach items="${zones}" var="zone">
                                <form:option value="${zone.name}"><c:out value="${zone.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="zone" element="p" cssClass="isa_error"/>
                    </div>
                    <c:if test="${duplicatedMail}">
                        <p class="isa_error">
                            <spring:message code="register.restaurant.form.duplicated_email" />
                        </p>
                    </c:if>
                    <div class="row">
                        <form:label path="email" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.email"/>*</form:label>
                        <form:input path="email" type="text"/>
                        <form:errors path="email" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="detail" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.detail"/></form:label>
                        <form:input path="detail" type="text"/>
                        <form:errors path="detail" cssClass="isa_error" element="p"/>
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message code="register.restaurant.form.detail.footnote"/></h6>
                    </div>
                    <div class="row">
                        <form:label path="categories" cssClass="semibold label-text-size">
                            <spring:message code="register.restaurant.form.categories"/>
                        </form:label>
                        <form:select multiple="true" path="categories">
                            <form:options items="${categoryList}" itemValue="id" itemLabel="name"/>
                        </form:select>
                        <form:errors path="categories" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                                code="form.mandatory"/></h6>
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

<%@ include file="../footer.jsp" %>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var options = [];
        <c:forEach items="${zones}" var="zone">
        options.push("${zone.name}");
        </c:forEach>
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems, options);
    });
</script>
</body>
</html>