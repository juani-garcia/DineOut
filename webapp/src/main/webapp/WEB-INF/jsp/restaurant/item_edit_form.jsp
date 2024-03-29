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

<c:url value="/restaurant/item/${item.id}/edit" var="postPath"/>
<form:form id="item_form" modelAttribute="itemForm" action="${postPath}" method="post" enctype="multipart/form-data">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="restaurant.item.edit.form.header"/></h5>
                    </div>
                    <div class="row">
                        <form:label path="name" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.item.form.name"/>*</form:label>
                        <form:input type="text" path="name"/>
                        <form:errors path="name" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="detail" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.item.form.detail"/></form:label>
                        <form:input path="detail" type="text"/>
                        <form:errors path="detail" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row">
                        <form:label path="price" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.item.form.price"/>*</form:label>
                        <form:input path="price" type="number" step="0.01" min="0" id="decimalnumberonly"/>
                        <form:errors path="price" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="row input-field">
                        <form:label path="menuSectionId" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.item.form.menuSectionId"/></form:label>
                        <form:select path="menuSectionId">
                            <c:forEach items="${sections}" var="section">
                                <form:option value="${section.id}"><c:out value="${section.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="menuSectionId" element="p" cssClass="isa_error"/>
                    </div>
                    <div class="row">
                        <form:label path="image" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.item.form.image"/></form:label>
                        <form:input path="image" type="file"/>
                        <form:errors path="image" cssClass="isa_error" element="p"/>
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
                            <spring:message code="restaurant.item.edit.form.submit"/>
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
        <c:forEach items="${sections}" var="section">
        options.push("${section.name}");
        </c:forEach>
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems, options);
    });
</script>
</body>
</html>