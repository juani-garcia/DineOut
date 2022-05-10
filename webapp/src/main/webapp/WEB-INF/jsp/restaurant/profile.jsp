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
                        <a class="btn-large waves-effect waves-light btn-floating default_red"
                           href="${editUrl}">
                            <i class="material-icons left">edit</i>
                        </a>
                    </h1>
                </div>
                <div class="card-image">
                    <c:url value="/image/${item.imageId}" var="imagePath"/>
                    <img src="<c:url value="/resources/media/background1.jpg"/>" class="scale_down rounded" alt=""/>
                </div>
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
            </div>
        </div>
    </div>

    <div class="restaurant_detail_section_menu padding-15px">
        <div class="section width_100">
            <div class="card menu_card">
                <div class="card-content black-text">
                    <h1 class="megabold groovy center">
                        <c:if test="${sections.size() == 0}">
                            <spring:message code="restaurant.public_detail.no_menu"/>. <!-- TODO add custom message -->
                        </c:if>
                        <c:if test="${sections.size() != 0}">
                            <spring:message code="restaurant.public_detail.menu"/>:
                        </c:if>
                    </h1>
                    <div class="flex_row">
                        <a href="<c:url value="/restaurant/section"/>"
                           class="btn-large waves-effect waves-light default_blue white-text no-text-transform">
                            <spring:message code="restaurant.profile.add_section"/>
                        </a>
                        <a href="<c:url value="/restaurant/item"/>"
                           class="btn-large waves-effect waves-light default_blue white-text margin_left_auto no-text-transform">
                            <spring:message code="restaurant.profile.add_item"/>
                        </a>
                    </div>
                    <br><br>
                    <c:forEach items="${sections}" var="section">
                        <div class="flex_row">
                            <h4 class="groovy bold margin_right_auto"><c:out value="${section.name}"/></h4>
                            <c:if test="${section.ordering > 1}">
                                <c:url value="/restaurant/section/${section.id}/up" var="upUrl"/>
                                <form method="post" action="${upUrl}" class="margin_l_5px margin_r_5px">
                                    <button class="btn-large waves-effect waves-light btn-floating default_blue"
                                            type="submit"
                                            name="action">
                                        <i class="material-icons left">arrow_upward</i>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${section.ordering < sections.size()}">
                                <c:url value="/restaurant/section/${section.id}/down" var="downUrl"/>
                                <form method="post" action="${downUrl}" class="margin_l_5px margin_r_5px">
                                    <button class="btn-large waves-effect waves-light btn-floating default_blue" type="submit"
                                            name="action">
                                        <i class="material-icons left">arrow_downward</i>
                                    </button>
                                </form>
                            </c:if>
                            <c:url value="/restaurant/section/${section.id}/edit" var="editUrl"/>
                            <a href="${editUrl}" class="margin_l_5px margin_r_5px">
                                <button class="btn-large waves-effect waves-light btn-floating default_yellow">
                                    <i class="material-icons left">edit</i>
                                </button>
                            </a>
                            <c:url value="/restaurant/section/${section.id}/delete" var="deleteUrl"/>
                            <form method="post" action="${deleteUrl}" class="margin_l_5px">
                                <button class="btn-large waves-effect waves-light btn-floating default_red"
                                        type="submit" name="action">
                                    <i class="material-icons left">delete</i>
                                </button>
                            </form>
                        </div>
                        <c:if test="${section.menuItemList.size() == 0}">
                            <h6><spring:message code="restaurant.public_detail.no_items"/></h6>  <!-- TODO: custom message -->
                        </c:if>
                        <c:forEach items="${section.menuItemList}" var="item">
                            <hr/>
                            <div class="card horizontal item_card">
                                <c:if test="${item.imageId > 0}">
                                    <div class="card-image">
                                        <c:url value="/image/${item.imageId}" var="imagePath"/>
                                        <img src="${imagePath}" class="scale_down rounded" alt=""/>
                                    </div>
                                </c:if>
                                <div class="card-content">
                                    <div class="flex_row">
                                        <h6 class="bold"><c:out value="${item.name}"/></h6>
                                        <p class="margin_left_auto">$ <c:out value="${item.price}"/></p>
                                        <c:if test="${item.ordering > 1}">
                                            <c:url value="/restaurant/item/${item.id}/up" var="upUrl"/>
                                            <form method="post" action="${upUrl}" class="margin_l_5px margin_r_5px">
                                                <button class="btn-small waves-effect waves-light btn-floating default_blue"
                                                        type="submit" name="action">
                                                    <i class="material-icons left">arrow_upward</i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:if test="${item.ordering < section.menuItemList.size()}">
                                            <c:url value="/restaurant/item/${item.id}/down" var="downUrl"/>
                                            <form method="post" action="${downUrl}" class="margin_l_5px margin_r_5px">
                                                <button class="btn-small waves-effect waves-light btn-floating default_blue"
                                                        type="submit" name="action">
                                                    <i class="material-icons left">arrow_downward</i>
                                                </button>
                                            </form>
                                        </c:if>
                                        <c:url value="/restaurant/item/${item.id}/edit" var="editUrl"/>
                                        <a href="${editUrl}" class="margin_l_5px margin_r_5px">
                                            <button class="btn-small waves-effect waves-light btn-floating default_yellow">
                                                <i class="material-icons left">edit</i>
                                            </button>
                                        </a>
                                        <c:url value="/restaurant/item/${item.id}/delete" var="deleteUrl"/>
                                        <form method="post" action="${deleteUrl}" class="margin_l_5px">
                                            <button class="btn-small waves-effect waves-light btn-floating default_red"
                                                    type="submit" name="action">
                                                <i class="material-icons left">delete</i>
                                            </button>
                                        </form>
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