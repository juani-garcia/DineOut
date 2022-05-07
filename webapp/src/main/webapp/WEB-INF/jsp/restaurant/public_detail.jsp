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
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold text_overflow_ellipsis flex_row flex_center"><c:out value="${restaurant.name}"/>
                <% if (request.isUserInRole("DINER")) { %>
                <c:if test="${isUserFavorite}">
                    <c:url value="/diner/set_favorite/${restaurant.id}/false" var="setFavorite"/>
                    <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                        <button class="btn-large waves-effect waves-light btn-floating default_red" type="submit"
                                name="action">
                            <i class="material-icons left">favorite</i>
                        </button>
                    </form>
                </c:if>
                <c:if test="${!isUserFavorite}">
                    <c:url value="/diner/set_favorite/${restaurant.id}/true" var="setFavorite"/>
                    <form method="post" action="${setFavorite}" class="cero_height margin_left_20px">
                        <button class="btn-large waves-effect waves-light btn-floating default_red" type="submit"
                                name="action">
                            <i class="material-icons left">favorite_border</i>
                        </button>
                    </form>
                </c:if>
                <% } %>
            </h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background2.jpg"/>"
                               alt=""></div>
</div>

<div class="flex_row">
    <div class="restaurant_detail_section_menu flex_center padding-15px">
        <div class="menu_title_card flex_center align_center rounded shadowed padding-15px">
            <h1 class="megabold groovy center">
                <c:if test="${sections.size() == 0}">
                    <spring:message code="restaurant.public_detail.no_menu"/>.
                </c:if>
                <c:if test="${sections.size() != 0}">
                    <spring:message code="restaurant.public_detail.menu"/>:
                </c:if>
            </h1>
        </div>
        <c:if test="${sections.size() != 0}">
            <div class="section flex_center width_100">
                <div class="card menu_card">
                    <div class="card-content black-text">
                        <c:forEach items="${sections}" var="section">
                            <h2 class="bold"><c:out value="${section.name}"/></h2>
                            <c:if test="${section.menuItemList.size() == 0}">
                                <h4><spring:message code="restaurant.public_detail.no_items"/></h4>
                            </c:if>
                            <c:forEach items="${section.menuItemList}" var="item">
                                <div class="card horizontal item_card">
                                    <c:if test="${item.imageId > 0}">
                                        <div class="card-image">
                                            <c:url value="/image/${item.imageId}" var="imagePath"/>
                                            <img src="${imagePath}" class="scale_down rounded" alt=""/>
                                        </div>
                                    </c:if>
                                    <div class="card-content">
                                        <div class="section_item_container align_center">
                                            <h4><c:out value="${item.name}"/></h4>
                                            <p>$ <c:out value="${item.price}"/></p>
                                        </div>
                                        <hr/>
                                        <p><c:out value="${item.detail}"/></p>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
    <div class="display_flex restaurant_detail_section_data flex_center">
        <div class="container">
            <div class="card card_wrapper default_dark white-text align_center same_width_elements">
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
                    <h5 class="center text_overflow_ellipsis">Horarios:</h5>
                    <c:if test="${shifts.size() == 0}">
                        <h6 class="center text_overflow_ellipsis">Las 24hs.</h6>
                    </c:if>
                    <c:forEach items="${shifts}" var="shift">
                        <h6 class="center text_overflow_ellipsis"><spring:message code="${shift.message}"/> <c:out
                                value="${shift.start}"/> a
                            <c:out
                                    value="${shift.end}"/></h6>
                    </c:forEach>
                </div>
                <div class="card-content same_width_elements">
                    <div class="container">
                        <div class="row center">
                            <a href="<c:url value ="/reserve/${restaurant.id}"/>"
                               class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                                ¡Reserva!
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>