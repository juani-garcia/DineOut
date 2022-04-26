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

<!-- Main title -->
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message code="home.index.recommended.title"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>
</div>
<div class="separator default_red"></div>

<div class="container">
    <div class="row align_center">
        <div class="col">
            <c:if test="${restaurants.size() == 0}">
                <div class="container">
                    <div class="card card_wrapper padding-15px default_dark">
                        <h1 class="header center default_light_text">No hay restaurantes</h1>
                    </div>
                </div>
            </c:if>
            <c:if test="${restaurants.size() != 0}">
                <c:forEach items="${restaurants}" var="restaurant">

                    <a href="<c:url value ="/reserve/${restaurant.id}"/>">
                        <div class="card horizontal card_wrapper grow_on_hover">
                            <div class="card-image">
                                <img src="<c:url value="/resources/media/background1.jpg"/>" alt="rest_img" class="scale_down rounded" />
                            </div>
                            <div class="card-content default_dark_text">
                                <h4 class="medium"><c:out value="${restaurant.name}"/></h4>
                                <h6 class="regular"><c:out value="${restaurant.detail}"/></h6>
                                <p class="regular">&#128205;<c:out value="${restaurant.address}"/></p>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:if>

        </div>
    </div>
</div>
<%--<div class="row align_center">--%>
<%--    <ul class="pagination">--%>
<%--        <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>--%>
<%--        <li class="active"><a href="#!">1</a></li>--%>
<%--        <li class="waves-effect"><a href="#!">2</a></li>--%>
<%--        <li class="waves-effect"><a href="#!">3</a></li>--%>
<%--        <li class="waves-effect"><a href="#!">4</a></li>--%>
<%--        <li class="waves-effect"><a href="#!">5</a></li>--%>
<%--        <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>--%>
<%--    </ul>--%>
<%--</div>--%>

<%@ include file="../footer.jsp" %>
</body>
</html>
