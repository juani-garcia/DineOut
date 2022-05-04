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

<div class="container flex_center padding-15px">
    <div class="section flex_center width_100">
        <div class="card menu_card">
            <h1 class="megabold flex_center groovy">
                Reservas
            </h1>
            <c:if test="${reservations.size() == 0}">
                <h2 class="header center default_light_text">No tienes ninguna reserva</h2>
            </c:if>
            <c:forEach items="${reservations}" var="reservation">
                <div class="card-content default_dark_text">
                    <h4 class="medium"><c:out value="${reservation.restaurant.name}"/></h4>
                    <h5 class="medium"><c:out value="${reservation.amount}"/></h5>
                    <h6 class="regular"><c:out value="${reservation.dateTime}"/></h6>
                    <p class="regular"><c:out value="${reservation.comments}"/></p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>