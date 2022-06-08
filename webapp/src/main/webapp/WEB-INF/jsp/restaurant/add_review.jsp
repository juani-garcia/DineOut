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
<c:url value="/restaurant/${resId}/review" var="postPath"/>
<form:form id="restaurant_review_form" modelAttribute="restaurantReviewForm" action="${postPath}" method="post">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <div class="row">
                        <h5 class="white bold"><spring:message code="restaurant.add_review"/></h5>
                    </div>
                    <div class="row">
                        <form:label path="review" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.add_review.review"/></form:label>
                        <form:input type="text" path="review"/>
                        <form:errors path="review" cssClass="isa_error" element="p"/>
                    </div>
                    <div class="flex_row same_width_elements">
                    <div class="same_width_elements">
                        <form:label path="rating" cssClass="semibold label-text-size"><spring:message
                                code="restaurant.add_review.rating"/>*</form:label>
                        <form:input path="rating" type="number" id="numberonly" min="1" max="5"/>
                        <form:errors path="rating" cssClass="isa_error" element="p"/>
                    </div>
                    <h2 class="large text_overflow_ellipsis margin_left_auto flex_row star_rating margin_tb_auto same_width_elements"
                        id="stars">
                            <%-- generated via JS.--%>
                    </h2>
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
                            <spring:message code="restaurant.add_review"/>
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
    // Set up rating
    document.getElementById("numberonly").addEventListener('keyup', setStars);
    document.addEventListener('DOMContentLoaded', setStars);
    function setStars() {
        let ratings = document.getElementById("numberonly").value;
        let starRating = document.getElementById("stars");
        let stars = 0;
        if (ratings !== "") stars = parseInt(ratings);
        if (stars > 5) stars = 5;
        starRating.innerHTML = "";
        for (let i = 0; i < stars; i++) {
            starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_red_text three_rem">star</i>';
        }
        for (let i = 0; i < 5 - stars; i++) {
            starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_light_text three_rem">star</i>';
        }
    }
</script>
</body>
</html>