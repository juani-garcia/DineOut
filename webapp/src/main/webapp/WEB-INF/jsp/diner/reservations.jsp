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
        <div class="bold grow_on_hover">
            <c:if test="${past}">
                <c:url value="/diner/reservations" var="toggleUrl" />
                <a href="${toggleUrl}" class = "white-text">
                    <spring:message code="diner.show_future" />
                </a>
            </c:if>
            <c:if test="${!past}">
                <c:url value="/diner/reservations?past=true" var="toggleUrl" />
                <a href="${toggleUrl}" class="white-text">
                    <spring:message code="diner.show_past" />
                </a>
            </c:if>
        </div>
        <div class="card menu_card">
            <h1 class="megabold flex_center groovy">
                <c:if test="${past}" >
                    <spring:message code="diner.past_reservations.title"/>
                </c:if>
                <c:if test="${!past}" >
                    <spring:message code="diner.future_reservations.title"/>
                </c:if>
            </h1>
            <c:if test="${reservations.size() == 0}">
                <h2 class="header center default_light_text">No tienes ninguna reserva</h2>
            </c:if>
            <c:forEach items="${reservations}" var="reservation">
                <hr/>
                <div class="card-content default_dark_text flex_column">
                    <div class="flex_row">
                        <a href="<c:url value ="/restaurant/view/${reservation.restaurant.id}"/>" class="grow_on_hover default_dark_text underline">
                            <h5 class="medium"><b><c:out
                                    value="${reservation.restaurant.name}"/>: </b></h5>
                        </a>
                        <div class="margin_left_auto flex_row">
                                <%--                            <div class="margins_lr_5px">--%>
                                <%--                                <c:url value="/reservation/${reservation.reservationId}/delete" var="deleteUrl"/>--%>
                                <%--                                <form method="post" action="${deleteUrl}">--%>
                                <%--                                    <button class="btn-large waves-effect waves-light btn-floating default_dark"--%>
                                <%--                                            type="submit" name="action">--%>
                                <%--                                        <i class="material-icons left">create</i>--%>
                                <%--                                    </button>--%>
                                <%--                                </form>--%>
                                <%--                            </div>--%>
                            <c:if test="${!past}">
                                <div class="margins_lr_5px">
                                    <a class="btn-large waves-effect waves-light btn-floating default_red modal-trigger"
                                            href="#delete_confirm_modal">
                                        <i class="material-icons left">delete</i>
                                    </a>
                                    <div id="delete_confirm_modal" class="modal">
                                        <div class="modal-content">
                                            <h4>
                                                <spring:message code="diner.reservation.confirmation"
                                                arguments="${reservation.restaurant.name}"/>
                                            </h4>
                                        </div>
                                        <div class="modal-footer">
                                            <div class="flex_row">
                                                <a class="modal-close waves-effect btn-flat">
                                                    <spring:message code="diner.reservation.back" />
                                                </a>
                                                <form method="post"
                                                      action="<c:url value="/reservation/${reservation.reservationId}/delete"/>">
                                                    <button class="modal-close waves-effect red-text btn-flat"
                                                            type="submit" name="action">
                                                        <spring:message code="diner.reservation.continue" />
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <h5 class="medium">
                        <spring:message code="diner.reservation.detail"
                                        arguments="${reservation.amount}, ${reservation.dateString}, ${reservation.timeString}, ${reservation.restaurant.address}"/>
                    </h5>
                    <br>
                    <c:if test="${reservation.comments.length() > 0}">
                        <h6 class="regular"><spring:message code="diner.reservation.comments"/> <c:out
                                value="${reservation.comments}"/></h6>
                    </c:if>
                    <c:if test="${!reservation.isConfirmed}">
                        <h6>
                            <spring:message code="diner.reservation.pending"/>
                        </h6>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
<script>
    $(document).ready(function(){
        $('.modal').modal();
    });

</script>
</body>
</html>