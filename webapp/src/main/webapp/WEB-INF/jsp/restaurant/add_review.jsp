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
<c:url value="/restaurant/review/${resId}" var="postPath"/>
<form:form id="restaurant_review_form" modelAttribute="restaurantReviewForm" action="${postPath}" method="post">
  <div class="container">
    <div class="section">
      <div class="row rounded shadowed white">
        <div class="col s6 offset-s3">
          <div class="row">
            <h5 class="white bold"><spring:message code="restaurant.section.form.header"/></h5>
          </div>
          <div class="row">
            <form:label path="review" cssClass="semibold label-text-size"><spring:message
                    code="restaurant.section.form.name"/>*</form:label>
            <form:input type="text" path="review"/>
            <form:errors path="review" cssClass="isa_error" element="p"/>
          </div>
          <div class="row">
            <form:label path="rating" cssClass="semibold label-text-size"><spring:message
                    code="restaurant.section.form.name"/>*</form:label>
            <form:input path="rating" type="number" id="numberonly" min="0" max="5"/>
            <form:errors path="rating" cssClass="isa_error" element="p"/>
          </div>
          <div class="row">
            <h6 class="semibold label-text-size grey-text text-lighten-1"><spring:message
                    code="form.mandatory"/></h6>
          </div>
          <div class="row center">
            <button type="submit" name="action"
                    class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
              <spring:message code="restaurant.section.form.submit"/>
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