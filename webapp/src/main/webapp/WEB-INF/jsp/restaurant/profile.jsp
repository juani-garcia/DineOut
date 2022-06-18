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
            <div class="card card_wrapper default_dark white-text same_width_elements">
                <div class="card-content same_width_elements">
                    <h1 class="header center bold text_overflow_ellipsis flex_row flex_center">
                        <c:out value="${restaurant.name}"/>
                        <c:url value="/restaurant/edit" var="editUrl"/>
                        <a class="btn-large waves-effect waves-light btn-floating default_red margin_l_20px"
                           href="${editUrl}">
                            <i class="material-icons left">edit</i>
                        </a>
                    </h1>

                    <c:if test="${restaurant.rating != 0}">
                        <h3 class="center bold text_overflow_ellipsis flex_row flex_center" id="star_rating">
                                <%-- generated via JS.--%>
                        </h3>
                    </c:if>
                </div>
                <c:if test="${restaurant.image != null}">
                    <div class="card-image flex_center">
                        <c:url value="/image/${restaurant.image.id}" var="imagePath"/>
                        <img data-enlargeable src="${imagePath}" class="scale_down rounded" alt=""/>
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
                <div class="card-content same_width_elements">
                    <div id="map" style="height: 400px"></div>
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

    <div class="restaurant_detail_section_menu padding-15px">
        <div class="section width_100">
            <div class="card menu_card">
                <div class="card-content black-text">
                    <h1 class="megabold groovy center">
                        <c:if test="${sections.size() == 0}">
                            <spring:message code="restaurant.detail.no_menu"/>.
                        </c:if>
                        <c:if test="${sections.size() != 0}">
                            <spring:message code="restaurant.public_detail.menu"/>:
                        </c:if>
                    </h1>
                    <div class="flex_row">
                        <a href="<c:url value="/restaurant/section"/>"
                           class="btn-large waves-effect waves-light default_dark white-text white-text no-text-transform">
                            <spring:message code="restaurant.profile.add_section"/>
                        </a>
                        <c:if test="${sections.size() != 0}">
                            <a href="<c:url value="/restaurant/item"/>"
                               class="btn-large waves-effect waves-light default_dark white-text white-text margin_left_auto no-text-transform">
                                <spring:message code="restaurant.profile.add_item"/>
                            </a>
                        </c:if>
                    </div>
                    <br><br>
                    <c:forEach items="${sections}" var="section">
                        <div class="flex_row">
                            <h4 class="groovy bold margin_right_auto"><c:out value="${section.name}"/></h4>
                            <c:if test="${section.ordering > 0}">
                                <c:url value="/restaurant/section/${section.id}/up" var="upUrl"/>
                                <form method="post" action="${upUrl}" class="margin_l_5px margin_r_5px">
                                    <button class="btn-large waves-effect waves-light btn-floating default_dark white-text"
                                            type="submit"
                                            name="action">
                                        <i class="material-icons left">arrow_upward</i>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${section.ordering < sections.size() - 1}">
                                <c:url value="/restaurant/section/${section.id}/down" var="downUrl"/>
                                <form method="post" action="${downUrl}" class="margin_l_5px margin_r_5px">
                                    <button class="btn-large waves-effect waves-light btn-floating default_dark white-text"
                                            type="submit"
                                            name="action">
                                        <i class="material-icons left">arrow_downward</i>
                                    </button>
                                </form>
                            </c:if>
                            <c:url value="/restaurant/section/${section.id}/edit" var="editUrl"/>
                            <a href="${editUrl}" class="margin_l_5px margin_r_5px">
                                <button class="btn-large waves-effect waves-light btn-floating default_dark white-text">
                                    <i class="material-icons left">edit</i>
                                </button>
                            </a>


                            <a class="btn-large waves-effect waves-light btn-floating default_red modal-trigger"
                               href="#delete_section_confirm_modal_${section.id}">
                                <i class="material-icons left">delete</i>
                            </a>
                            <div id="delete_section_confirm_modal_${section.id}" class="modal confirm_delete_modal_height">
                                <div class="modal-content">
                                    <h4 class="center">
                                        <spring:message code="restaurant.detail.delete_section"
                                                        arguments="${section.name}"/>
                                    </h4>
                                </div>
                                <div class="modal-footer">
                                    <div class="flex_row">
                                        <a class="modal-close waves-effect btn-flat grow_on_hover">
                                            <spring:message code="diner.reservation.back"/>
                                        </a>
                                        <c:url value="/restaurant/section/${section.id}/delete" var="deleteUrl"/>
                                        <form method="post" action="${deleteUrl}" class="margin_l_5px">
                                            <button class="modal-close waves-effect red-text btn-flat grow_on_hover"
                                                    type="submit" name="action">
                                                <spring:message code="diner.reservation.continue"/>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <c:if test="${section.menuItemList.size() == 0}">
                            <h6><spring:message code="restaurant.public_detail.no_items"/></h6>
                        </c:if>
                        <c:forEach items="${section.menuItemList}" var="item">
                            <hr/>
                            <div class="card horizontal item_card">
                                <c:if test="${item.image != null}">
                                    <div class="card-image">
                                        <c:url value="/image/${item.image.id}" var="imagePath"/>
                                        <img data-enlargeable src="${imagePath}" class="scale_down rounded" alt=""/>
                                    </div>
                                </c:if>
                                <div class="card-content">
                                    <div class="flex_row">
                                        <h6 class="bold"><c:out value="${item.name}"/></h6>
                                        <p class="margin_left_auto">$ <c:out value="${item.price}"/></p>
                                        <c:if test="${item.ordering > 0}">
                                            <c:url value="/restaurant/item/${item.id}/up" var="upUrl"/>
                                            <form method="post" action="${upUrl}" class="margin_l_5px margin_r_5px">
                                                <button class="btn-small waves-effect waves-light btn-floating default_dark white-text"
                                                        type="submit" name="action">
                                                    <i class="material-icons left">arrow_upward</i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${item.ordering < section.menuItemList.size() - 1}">
                                            <c:url value="/restaurant/item/${item.id}/down" var="downUrl"/>
                                            <form method="post" action="${downUrl}" class="margin_l_5px margin_r_5px">
                                                <button class="btn-small waves-effect waves-light btn-floating default_dark white-text"
                                                        type="submit" name="action">
                                                    <i class="material-icons left">arrow_downward</i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:url value="/restaurant/item/${item.id}/edit" var="editUrl"/>
                                        <a href="${editUrl}" class="margin_l_5px margin_r_5px">
                                            <button class="btn-small waves-effect waves-light btn-floating default_dark white-text">
                                                <i class="material-icons left">edit</i>
                                            </button>
                                        </a>
                                        <div class="margin_l_5px">
                                            <a class="btn-small waves-effect waves-light btn-floating default_red modal-trigger"
                                               href="#delete_item_confirm_modal_${item.id}">
                                                <i class="material-icons left">delete</i>
                                            </a>
                                            <div id="delete_item_confirm_modal_${item.id}"
                                                 class="modal confirm_delete_modal_height">
                                                <div class="modal-content">
                                                    <h4 class="center">
                                                        <spring:message code="restaurant.detail.delete_item"
                                                                        arguments="${item.name}"/>
                                                    </h4>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="flex_row">
                                                        <a class="modal-close waves-effect btn-flat grow_on_hover">
                                                            <spring:message code="diner.reservation.back"/>
                                                        </a>
                                                        <c:url value="/restaurant/item/${item.id}/delete"
                                                               var="deleteUrl"/>
                                                        <form method="post" action="${deleteUrl}" class="margin_l_5px">
                                                            <button class="modal-close waves-effect red-text btn-flat grow_on_hover"
                                                                    type="submit" name="action">
                                                                <spring:message code="diner.reservation.continue"/>
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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
<!-- Google maps api places -->
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBCNikN--hCj1MYvbCWEch4cTIh3JeicLQ&callback=initMap&v=weekly"
        defer
></script>
<script>
    $(document).ready(function () {
        $('.modal').modal();
    });

    function initMap() {
        if (document.getElementById('map') == null) return;

        let lat = <c:out value="${restaurant.lat}"/>;
        let lng = <c:out value="${restaurant.lng}"/>;
        if (document.getElementById('map') == null || lat === null) return;
        const myLatLng = {lat: lat, lng: lng};

        var map = new google.maps.Map(document.getElementById('map'), {
            center: myLatLng,
            zoom: 14
        });


        var bounds = new google.maps.LatLngBounds();

        var marker = new google.maps.Marker({
            position: myLatLng,
            map: map
        });

        const infowindow = new google.maps.InfoWindow({
            content: "<p class=\"black-text\">" + "<c:out value="${restaurant.address}"/>" + "</p>",
        });

        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });

    }

    document.addEventListener('DOMContentLoaded', initMap);


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
</script>
</body>
</html>