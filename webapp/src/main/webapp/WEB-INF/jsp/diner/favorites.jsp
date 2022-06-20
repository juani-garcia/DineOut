<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Dine Out</title>
    <%@ include file="../header.jsp" %>
</head>
<body>
<%@ include file="../navbar.jsp" %>

<div class="section no-pad-bot">
    <div class="container">
        <h1 class="header center white-text bold"><spring:message code="diner.favorites.title"/></h1>
    </div>
</div>

<div class="container">
    <div class="flex_row">
        <c:if test="${favorites.size() == 0}">
            <div class="container">
                <div class="card card_wrapper padding-15px white">
                    <h2 class="header center default_light_text">
                        <spring:message code="diner.favorites.none_found"/>
                        <div class="center default_light_text underline margins_tb_30px">
                            <h6 class="grow_on_hover">
                                <a class="default_light_text" href="<c:url value="/restaurants"/>">
                                    <spring:message code="restaurant.reservation.explore"/>
                                </a>
                            </h6>
                        </div>
                    </h2>
                </div>
            </div>
        </c:if>
        <c:forEach items="${favorites}" var="favorite">

            <a href="<c:url value ="/restaurant/${favorite.restaurant.id}/view"/>"
               class="card horizontal card_wrapper grow_on_hover restaurant_card">
                <c:if test="${favorite.restaurant.image != null}">
                    <div class="card-image flex_center">
                        <c:url value="/image/${favorite.restaurant.image.id}" var="imagePath"/>
                        <img src="${imagePath}" class="scale_down rounded" alt=""/>
                    </div>
                </c:if>
                <div class="card-content default_dark_text flex_column">
                    <div class="flex_row width_100">
                        <h6 class="medium text_overflow_ellipsis"><c:out value="${favorite.restaurant.name}"/></h6>
                        <h6 class="medium text_overflow_ellipsis margin_left_auto">&#128205;<c:out
                                value="${favorite.restaurant.zone.name}"/></h6>
                    </div>
                    <p class="regular text_overflow_ellipsis width_100"><c:out value="${favorite.restaurant.detail}"/></p>
                    <c:if test="${favorite.restaurant.categories.size() != 0}">
                        <p class="light text_overflow_ellipsis width_70"><spring:message
                                code="home.restaurants.address"/>:
                            <c:out value="${favorite.restaurant.address}"/></p>
                    </c:if>
                    <div class="flex_row margin_t_auto width_100">
                        <c:if test="${favorite.restaurant.categories.size() != 0}">
                            <div class="flex_column width_70">
                                <p class="light text_overflow_ellipsis"><spring:message
                                        code="home.restaurants.categories"/>:</p>
                                <div class="flex_row_only scrollable_row width_100 z_index_9999 white_space_nowrap">
                                    <c:forEach items="${favorite.restaurant.categories}" var="category">
                                        <h6 class="card margins_lr_5px padding_4px shadowed_small z_index_9999">
                                            <spring:message code="${category.message}"/>
                                        </h6>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${favorite.restaurant.categories.size() == 0}">
                            <p class="light text_overflow_ellipsis margin_t_auto"><spring:message
                                    code="home.restaurants.address"/>:
                                <c:out value="${favorite.restaurant.address}"/></p>
                        </c:if>
                        <c:if test="${favorite.restaurant.rating != null && favorite.restaurant.rating != 0}">
                            <div class="margin_left_auto width_25 flex_row margin_t_auto">
                                <h3 class="medium text_overflow_ellipsis flex_row star_rating margin_tb_0px"
                                    id="${favorite.restaurant.rating}">
                                        <%-- generated via JS.--%>
                                </h3>
                                &nbsp;
                                <h6 class="center default_light_text margin_tb_0px">(<c:out
                                        value="${favorite.restaurant.ratingCount}"/>)</h6>
                            </div>
                        </c:if>
                        <c:if test="${!(favorite.restaurant.rating != null && favorite.restaurant.rating != 0)}">
                            <h3 class="medium text_overflow_ellipsis margin_left_auto flex_row">
                            </h3>
                        </c:if>
                    </div>
                </div>
            </a>
        </c:forEach>

    </div>
</div>
<c:if test="${pages > 1}">
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

    function defaultSelector(queryParam, options_id, select_id, params) {
        const categoryString = params.get(queryParam);
        if (categoryString != null && categoryString !== "") {
            const elOptions = document.getElementById(options_id).querySelectorAll('option');
            elOptions[0].removeAttribute('selected');
            let category = parseInt(categoryString) + 1; // category "0" in select is the "Select category"
            const elementOption = elOptions[category.toString()];
            elementOption.setAttribute('selected', 'selected');
            document.getElementById(select_id).children.item(0).children.item(0).value = elementOption.text;
        }
    }

    // Set up paginator
    document.addEventListener('DOMContentLoaded', function () {
        const paginator = document.getElementById("paginator");
        if (paginator === null) return;

        const params = new URLSearchParams(window.location.search);
        let pageNumber = params.get("page");
        if (pageNumber == null) pageNumber = "1";
        var pageNumberElem = document.getElementById("page_number_of_total");
        var pages = Math.ceil(<c:out value="${pages}"/>);
        pageNumberElem.textContent = "<spring:message code="paginator.text.main"/> " + pageNumber + " <spring:message code="paginator.text.of"/> " + pages;

        pageNumber = parseInt(pageNumber);

        var previousPagePaginator = document.getElementById("previous_page");
        var nextPagePaginator = document.getElementById("next_page");
        if (pageNumber === 1) {
            previousPagePaginator.className = "disabled default_dark_text";
        } else {
            previousPagePaginator.onclick = function () {
                pageNumber = pageNumber - 1;
                params.set("page", pageNumber.toString());
                previousPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
        }


        if (pageNumber === pages) {
            nextPagePaginator.className = "disabled default_dark_text";
        } else {
            nextPagePaginator.onclick = function () {
                pageNumber = pageNumber + 1;
                params.set("page", pageNumber.toString());
                nextPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
        }
    });
</script>