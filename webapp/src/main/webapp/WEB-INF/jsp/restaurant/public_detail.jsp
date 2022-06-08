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
<div class="flex_row">
    <div class="display_flex restaurant_detail_section_data">
        <div class="container">
            <div class="flex_column">
                <div class="card card_wrapper default_dark white-text same_width_elements">
                    <div class="card-content same_width_elements">
                        <h1 class="header center bold text_overflow_ellipsis flex_row flex_center">
                            <c:out value="${restaurant.name}"/>
                            <sec:authorize access="hasRole('${DINER}')">
                                <c:if test="${isUserFavorite}">
                                    <c:url value="/diner/favorite/${restaurant.id}/false" var="setFavorite"/>
                                    <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                                        <button class="btn-large waves-effect waves-light btn-floating default_red"
                                                type="submit"
                                                name="action">
                                            <i class="material-icons left">favorite</i>
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${!isUserFavorite}">
                                    <c:url value="/diner/favorite/${restaurant.id}/true" var="setFavorite"/>
                                    <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                                        <button class="btn-large waves-effect waves-light btn-floating default_red"
                                                type="submit"
                                                name="action">
                                            <i class="material-icons left">favorite_border</i>
                                        </button>
                                    </form>
                                </c:if>
                            </sec:authorize>
                        </h1>
                        <c:if test="${restaurant.rating != 0}">
                            <h3 class="center bold text_overflow_ellipsis flex_row flex_center" id="star_rating">
                                    <%-- generated via JS.--%>
                            </h3>
                        </c:if>

                    </div>
                    <c:if test="${restaurant.image != null}">
                        <div class="card-image">
                            <c:url value="/image/${restaurant.image.id}" var="imagePath"/>
                            <img src="${imagePath}" class="scale_down rounded" alt=""/>
                        </div>
                    </c:if>
                    <div class="card-content same_width_elements">
                        <div class="icon-block">
                            <h2 class="center text_overflow_ellipsis"><i class="material-icons">restaurant_menu</i></h2>
                            <h5 class="center text_overflow_ellipsis"><c:out value="${restaurant.detail}"/></h5>
                        </div>
                    </div>
                    <div class="card-content same_width_elements">
                        <h5 class="center text_overflow_ellipsis">&#128205;<c:out value="${restaurant.address}"/></h5>
                    </div>
                    <div class="card-content same_width_elements flex_center">
                        <h5 class="center text_overflow_ellipsis">
                            <spring:message code="restaurant.public_detail.time"/>:
                        </h5>
                        <c:if test="${shifts.size() == 0}">
                            <h6 class="center text_overflow_ellipsis"><spring:message
                                    code="restaurant.public_detail.all_day"/>
                            </h6>
                        </c:if>
                        <c:forEach items="${shifts}" var="shift">
                            <h6 class="center text_overflow_ellipsis">
                                <spring:message code="${shift.message}"/>
                                <c:out value="${shift.start}"/> a
                                <c:out value="${shift.end}"/></h6>
                        </c:forEach>
                    </div>
                    <sec:authorize access="hasRole('${DINER}')">
                        <div class="card-content same_width_elements">
                            <div class="container">
                                <div class="row center">
                                    <div class="flex_row">
                                        <a href="<c:url value ="/reserve/${restaurant.id}"/>"
                                           class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                                            <spring:message code="reservation.reservation.form.submit"/>
                                        </a>
                                        <a href="<c:url value ="/restaurant/${restaurant.id}/review"/>"
                                           class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded margin_l_20px">
                                            <spring:message code="restaurant.add_review"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <div class="card-content same_width_elements">
                            <div class="container">
                                <div class="row center">
                                    <a href="<c:url value ="/register"/>"
                                       class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                                        <spring:message code="reservation.reservation.form.register"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                </div>
                <c:if test="${reviews.size() != 0}">
                    <div class="card card_wrapper default_dark white-text same_width_elements">

                        <div class="card-content same_width_elements">
                            <h5><spring:message code="restaurant.add_review.reviews"/>:</h5>
                        </div>
                        <div class="card-content same_width_elements">
                            <c:forEach items="${reviews}" var="review">
                                <h5 class="semibold"><c:out value="${review.user.firstName}"/></h5>
                                <div class="flex_row">
                                    <h6 class="text_overflow_ellipsis width_75">
                                        <c:out value="${review.review}"/>
                                    </h6>
                                    <h3 class="medium text_overflow_ellipsis margin_left_auto flex_row star_rating margin_tb_auto"
                                        id="${review.rating}">
                                            <%-- generated via JS.--%>
                                    </h3>
                                </div>
                            </c:forEach>
                            <c:if test="${reviewPages > 1}">
                                <div class="container flex_center" id="paginator">
                                    <ul class="pagination padding-15px big">
                                        <li class="grow_on_hover2 white-text" id="previous_page"><a href="#!"><i
                                                class="material-icons">chevron_left</i></a></li>
                                        <li id="page_number_of_total" class="white-text regular"></li>
                                        <li class="grow_on_hover2 white-text" id="next_page"><a href="#!"><i
                                                class="material-icons">chevron_right</i></a></li>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <div class="restaurant_detail_section_menu padding-15px">
        <div class="section width_100">
            <div class="card menu_card">
                <div class="card-content black-text">
                    <h1 class="megabold groovy center">
                        <c:if test="${sections.size() == 0}">
                            <spring:message code="restaurant.public_detail.no_menu"/>.
                        </c:if>
                        <c:if test="${sections.size() != 0}">
                            <spring:message code="restaurant.public_detail.menu"/>:
                        </c:if>
                    </h1>
                    <c:forEach items="${sections}" var="section">
                        <h4 class="groovy bold"><c:out value="${section.name}"/></h4>
                        <c:if test="${section.menuItemList.size() == 0}">
                            <h6><spring:message code="restaurant.public_detail.no_items"/></h6>
                        </c:if>
                        <c:forEach items="${section.menuItemList}" var="item">
                            <hr/>
                            <div class="card horizontal item_card">
                                <c:if test="${item.image != null}">
                                    <div class="card-image">
                                        <c:url value="/image/${item.image.id}" var="imagePath"/>
                                        <img src="${imagePath}" class="scale_down rounded" alt=""/>
                                    </div>
                                </c:if>
                                <div class="card-content">
                                    <div class="section_item_container align_center">
                                        <h6 class="bold"><c:out value="${item.name}"/></h6>
                                        <p>$ <c:out value="${item.price}"/></p>
                                    </div>
                                    <p class="regular"><c:out value="${item.detail}"/></p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
<script>
    // Set up rating
    document.addEventListener('DOMContentLoaded', function () {
        let ratings = document.getElementsByClassName("star_rating");
        if (ratings.length === 0) return;
        for (let starRating = ratings.item(0), i = 0; i < ratings.length; i++, starRating = ratings.item(i)) {
            for (let i = 0; i < starRating.id; i++) {
                starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_red_text">star</i>';
            }
            for (let i = 0; i < 5 - starRating.id; i++) {
                starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_light_text">star</i>';
            }
        }
    });

    // Set up rating
    document.addEventListener('DOMContentLoaded', function () {
        let starRating = document.getElementById("star_rating");
        if (starRating === null) return;
        for (let i = 0; i < <c:out value="${restaurant.rating}"/>; i++) {
            starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_red_text">star</i>';
        }

        for (let i = 0; i < 5 - <c:out value="${restaurant.rating}"/>; i++) {
            starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_light_text">star</i>';
        }
    });

    // Set up paginator
    document.addEventListener('DOMContentLoaded', function () {
        const paginator = document.getElementById("paginator");
        if (paginator === null) return;

        const params = new URLSearchParams(window.location.search);
        let pageNumber = params.get("review_page");
        if (pageNumber == null) pageNumber = "1";
        var pageNumberElem = document.getElementById("page_number_of_total");
        var pages = Math.ceil(<c:out value="${reviewPages}"/>);
        pageNumberElem.textContent = "<spring:message code="paginator.text.main"/> " + pageNumber + " <spring:message code="paginator.text.of"/> " + pages;

        pageNumber = parseInt(pageNumber);

        var previousPagePaginator = document.getElementById("previous_page");
        var nextPagePaginator = document.getElementById("next_page");
        if (pageNumber === 1) {
            previousPagePaginator.className = "disabled default_dark_text";
        } else {
            previousPagePaginator.onclick = function () {
                pageNumber = pageNumber - 1;
                params.set("review_page", pageNumber.toString());
                previousPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
        }


        if (pageNumber === pages) {
            nextPagePaginator.className = "disabled default_dark_text";
        } else {
            nextPagePaginator.onclick = function () {
                pageNumber = pageNumber + 1;
                params.set("review_page", pageNumber.toString());
                nextPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
        }
    });
</script>
</body>
</html>