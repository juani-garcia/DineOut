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
                <spring:message code="diner.reservations.title"/>
            </h1>
            <c:if test="${reservations.size() == 0}">
                <h2 class="header center default_light_text">No tienes ninguna reserva</h2>
            </c:if>
            <c:forEach items="${reservations}" var="reservation">
                <hr />
                <div class="card-content default_dark_text" style="display: flex; justify-content: flex-start;">
                    <h5 class="medium flex_column" style="margin-right: 10px"><b><c:out value="${reservation.restaurant.name}"/>: </b></h5>
                    <h5 class="medium flex_column">
                        <spring:message code="diner.reservation.detail"
                                        arguments="${reservation.amount}, ${reservation.dateString}, ${reservation.timeString}, ${reservation.restaurant.address}"/>
                    </h5>
                    <c:url value="/reservation/${reservation.reservationId}/delete" var="deleteUrl"/>
                    <form method="post" action="${deleteUrl}" style="margin: 0">
                        <button class="btn waves-effect waves-light btn-floating default_red"
                                type="submit" name="action" style="margin-left: auto">
                            <i class="material-icons left">delete</i>
                        </button>
                    </form>
                </div>
                <c:if test="${reservation.comments.length() > 0}">
                    <div class="card-content default_dark_text" style="display: flex; justify-content: flex-start; padding-top: 0" >
                        <h6 class="regular"><c:out value="${reservation.comments}"/></h6>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>