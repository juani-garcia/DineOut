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
                        <% if (request.isUserInRole("DINER")) { %>
                        <c:if test="${isUserFavorite}">
                            <c:url value="/diner/set_favorite/${restaurant.id}/false" var="setFavorite"/>
                            <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                                <button class="btn-large waves-effect waves-light btn-floating default_red"
                                        type="submit"
                                        name="action">
                                    <i class="material-icons left">favorite</i>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${!isUserFavorite}">
                            <c:url value="/diner/set_favorite/${restaurant.id}/true" var="setFavorite"/>
                            <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                                <button class="btn-large waves-effect waves-light btn-floating default_red"
                                        type="submit"
                                        name="action">
                                    <i class="material-icons left">favorite_border</i>
                                </button>
                            </form>
                        </c:if>
                        <% } %>
                    </h1>
                    <h3 class="center bold text_overflow_ellipsis flex_row flex_center"><i class="material-icons default_red_text left">favorite</i><c:out
                            value="${restaurant.favCount}"/></h3>
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
                <% if (request.isUserInRole("DINER")) { %>
                <div class="card-content same_width_elements">
                    <div class="container">
                        <div class="row center">
                            <a href="<c:url value ="/reserve/${restaurant.id}"/>"
                               class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                                <spring:message code="reservation.reservation.form.submit"/>
                            </a>
                        </div>
                    </div>
                </div>
                <% } %>
                <% if (!request.isUserInRole("RESTAURANT") && !request.isUserInRole("DINER")) { %>
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
                <% } %>
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
</body>
</html>