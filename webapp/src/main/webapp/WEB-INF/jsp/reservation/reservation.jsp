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
            <div class="row rounded shadowed white width_75">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="reservation.reservation.form.header"/></h5>
                    </div>
                    <div class="row">
                        <form:label path="amount" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.amount"/>*</form:label>
                        <form:input path="amount" type="number" id="numberonly" min="0"/>
                        <form:errors path="amount" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="date" cssClass="semibold label-text-size">
                            <spring:message code="reservation.reservation.form.date" />
                        </form:label>
                        <form:input path="date" type="date"/>
                        <form:errors path="date" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row">
                        <form:label path="time" cssClass="semibold label-text-size">
                            <spring:message code="reservation.reservation.form.time"/>
                         </form:label>
                        <form:select path="time">
                            <c:forEach items="${times}" var="time">
                                <form:option value="${time}">
                                    <c:out value="${time}"/>
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="time" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row">
                        <form:label path="comments" cssClass="semibold label-text-size"><spring:message
                                code="reservation.reservation.form.comments"/></form:label>
                        <form:input path="comments" type="text"/>
                        <form:errors path="comments" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <h6 class="semibold label-text-size grey-text text-lighten-1">
                            <spring:message code="form.mandatory"/></h6>
                    </div>
                    <div class="row center flex_row">
                        <a id="back-button"
                           class="btn-large waves-effect waves-red default_red white-text lighten-1 no-text-transform"
                           href="javascript:{}" onclick="history.back();">
                            <spring:message code="reservation.reservation.form.back"/>
                        </a>
                        <button type="submit" id="register-button"
                                class="btn-large waves-effect waves-red green white-text lighten-1 margin_l_20px no-text-transform"
                                onclick="document.getElementById('reservation_form').submit();">
                            <spring:message code="reservation.reservation.form.submit"/>
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
        <c:forEach items="${times}" var="time">
        options.push("${time}");
        </c:forEach>
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems, options);
    });
</script>
</body>
</html>