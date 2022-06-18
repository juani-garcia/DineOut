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


<c:url value="/restaurant/edit" var="postPath"/>
<form:form id="restaurant_form" modelAttribute="restaurantForm" action="${postPath}" method="post"
           enctype="multipart/form-data">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="register.restaurant.edit.form.title"/></h5>
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
                        <form:input path="address" type="text" id="pac-input"/>
                        <form:errors path="address" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="container flex_column">
                        <div id="map" style="height: 400px"></div>
                    </div>
                    <div class="row input-field">
                        <form:label path="zone" cssClass="semibold label-text-size display_hidden"/>
                        <form:input type="text" path="zone" id="zone_input" cssClass="display_hidden"/>
                        <form:errors path="zone" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row input-field display_hidden">
                        <form:label path="lat" cssClass="semibold label-text-size"/>
                        <form:input type="number" path="lat" step="0.000001" min="-90" max="90" id="lat_input"/>
                        <form:errors path="lat" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row input-field display_hidden">
                        <form:label path="lng" cssClass="semibold label-text-size"/>
                        <form:input type="number" path="lng" step="0.000001" min="-180" max="180" id="lng_input"/>
                        <form:errors path="lng" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row">
                        <form:label path="email" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.email"/>*</form:label>
                        <form:input path="email" type="text"/>
                        <form:errors path="email" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="image" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.image"/></form:label>
                        <form:input path="image" type="file"/>
                        <form:errors path="image" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="detail" cssClass="semibold label-text-size"><spring:message
                                code="register.restaurant.form.detail"/></form:label>
                        <form:input path="detail" type="text"/>
                        <form:errors path="detail" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row" id="category_select_options">
                        <form:label path="categories" cssClass="semibold label-text-size">
                            <spring:message code="register.restaurant.form.categories"/>
                        </form:label>
                        <form:select multiple="true" path="categories">
                            <c:forEach items="${categories}" var="category">
                                <form:option value="${category.id}"><spring:message
                                        code="${category.message}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="categories" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row" id="shift_select_options">
                        <form:label path="shifts" cssClass="semibold label-text-size">
                            <spring:message code="register.restaurant.form.shifts"/>
                        </form:label>
                        <form:select multiple="true" path="shifts">
                            <c:forEach items="${shifts}" var="shift">
                                <form:option value="${shift.id}"><spring:message code="${shift.message}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="shifts" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                                code="form.mandatory"/></h6>
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

<!-- Google maps api places -->
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCNikN--hCj1MYvbCWEch4cTIh3JeicLQ&callback=initAutocomplete&libraries=places&v=weekly"
        defer
></script>
<%@ include file="../footer.jsp" %>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // var zoneElems = document.getElementById('zone_select_options').querySelectorAll('select');
        // var zoneInstances = M.FormSelect.init(zoneElems);

        var categoryElems = document.getElementById('category_select_options').querySelectorAll('select');
        var categoryInstances = M.FormSelect.init(categoryElems);

        var shiftElems = document.getElementById('shift_select_options').querySelectorAll('select');
        var shiftInstances = M.FormSelect.init(shiftElems);
    });
</script>
</body>
</html>