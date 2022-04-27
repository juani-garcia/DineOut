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
            <h1 class="header center white-text bold text_overflow_ellipsis"><c:out value="${restaurant.name}"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>

<div class="container">
    <div class="section restaurant_detail_flex_container">
        <!--   Icon Section   -->
        <div class="card horizontal card_wrapper default_dark white-text align_center same_width_elements">
            <div class="card-content same_width_elements">
                <div class="icon-block">
                    <h2 class="center"><i class="material-icons">restaurant_menu</i></h2>
                    <h5 class="center"><c:out value="${restaurant.detail}"/></h5>
                </div>
            </div>
            <div class="card-content same_width_elements">
                <h5 class="center">&#128205;<c:out value="${restaurant.address}"/></h5>
            </div>
            <div class="card-content same_width_elements">
                <h5 class="center">Horarios: </h5>
                <c:forEach items="${shifts}" var="shift">
                    <h6><c:out value="${shift}"/></h6>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<div class="container flex_center padding-15px">
    <div class="menu_title_card flex_center align_center rounded shadowed padding-15px">
        <h1 class="megabold flex_center groovy">
            <c:if test="${sections.size() == 0}">
                <spring:message code="restaurant.public_detail.no_menu"/>.
            </c:if>
            <c:if test="${sections.size() != 0}">
                <spring:message code="restaurant.public_detail.menu"/>:
            </c:if>
        </h1>
    </div>
    <div class="section flex_center width_100">
        <div class="card menu_card">
            <div class="card-content black-text">
                <c:forEach items="${sections}" var="section">
                    <h2 class="bold"><c:out value="${section.name}"/></h2>
                    <c:forEach items="${section.menuItemList}" var="item">
                        <div class="section_item_container align_center">
                            <h4><c:out value="${item.name}"/></h4>
                            <p>$ <c:out value="${item.price}"/></p>
                        </div>
                        <hr>
                        <p><c:out value="${item.detail}"/></p>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<div class="section no-pad-bot">
    <div class="container">
        <div class="row center scale_up">
            <a href="<c:url value ="/reserve/${restaurant.id}"/>"
               class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                ¡Reserva!
            </a>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>