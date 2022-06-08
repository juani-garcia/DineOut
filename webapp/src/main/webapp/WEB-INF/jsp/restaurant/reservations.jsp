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
                <c:if test="${past}">
                    <spring:message code="restaurant.reservations.past_title"/>
                </c:if>
                <c:if test="${!past}">
                    <spring:message code="restaurant.reservations.future_title"/>
                </c:if>
            </h1>
            <div class="flex_center">
                <h6 class="grow_on_hover default_dark_text bold underline">
                    <c:if test="${past}">
                        <c:url value="/restaurant/reservations" var="toggleUrl"/>
                        <a href="${toggleUrl}" class="default_dark_text">
                            <spring:message code="restaurant.reservations.show_future"/>
                        </a>
                    </c:if>
                    <c:if test="${!past}">
                        <c:url value="/restaurant/reservations?past=true" var="toggleUrl"/>
                        <a href="${toggleUrl}" class="default_dark_text">
                            <spring:message code="restaurant.reservations.show_past"/>
                        </a>
                    </c:if>
                </h6>
            </div>
            <c:if test="${reservations.size() == 0}">
                <h2 class="header center default_light_text">
                    <spring:message code="restaurant.reservation.no_reservations"/>
                </h2>
            </c:if>
            <c:forEach items="${reservations}" var="reservation">
                <hr/>
                <div class="card-content default_dark_text flex_column">
                    <div class="flex_row">
                        <h5 class="medium"><b>
                            <c:out value="${reservation.owner.firstName}"/>
                            <c:out value="${reservation.owner.lastName}"/>:
                        </b></h5>
                        <div class="margin_left_auto flex_row">
                            <c:if test="${reservation.isConfirmable}">
                                <div class="margins_lr_5px">
                                    <form method="post"
                                          action="<c:url value="/reservation/${reservation.id}/confirm"/>">
                                        <button class="btn-large waves-effect waves-light btn-floating green modal-trigger"
                                                type="submit" name="action">
                                            <i class="material-icons left">check</i>
                                        </button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${!past}">
                                <div class="margins_lr_5px">
                                    <a class="btn-large waves-effect waves-light btn-floating default_red modal-trigger"
                                       href="#delete_confirm_modal_${reservation.id}">
                                        <i class="material-icons left">delete</i>
                                    </a>
                                    <div id="delete_confirm_modal_${reservation.id}"
                                         class="modal confirm_delete_modal_height">
                                        <div class="modal-content">
                                            <h4 class="center">
                                                <spring:message code="restaurant.reservation.confirmation"
                                                 arguments="${reservation.owner.firstName}, ${reservation.owner.lastName}"/>
                                            </h4>
                                        </div>
                                        <div class="modal-footer">
                                            <div class="flex_row">
                                                <a class="modal-close waves-effect btn-flat">
                                                    <spring:message code="restaurant.reservation.back"/>
                                                </a>
                                                <form method="post"
                                                      action="<c:url value="/reservation/${reservation.id}/delete"/>">
                                                    <button class="modal-close waves-effect red-text btn-flat"
                                                            type="submit" name="action">
                                                        <spring:message code="restaurant.reservation.continue"/>
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
                        <spring:message code="restaurant.reservation.detail"
                                        arguments="${reservation.amount}, ${reservation.dateString}, ${reservation.timeString}"/>
                    </h5>
                    <br>
                    <c:if test="${reservation.comments.length() > 0}">
                        <h6 class="regular"><spring:message code="diner.reservation.comments"/> <c:out
                                value="${reservation.comments}"/></h6>
                    </c:if>
                    <c:if test="${!reservation.isConfirmed && reservation.isValidTime}">
                        <h6 class="isa_warning">
                            <spring:message code="restaurant.reservation.pending"/>
                        </h6>
                    </c:if>
                    <c:if test="${reservation.isConfirmed && reservation.isValidTime}">
                        <h6 class="isa_success">
                            <spring:message code="restaurant.reservation.approved"/>
                        </h6>
                    </c:if>
                </div>
            </c:forEach>
            <c:if test="${pages > 1}">
                <div class="container flex_center" id="paginator">
                    <ul class="pagination padding-15px big">
                        <li class="grow_on_hover2 default_dark_text" id="previous_page"><a href="#!"><i
                                class="material-icons" id="prev_page_chevron">chevron_left</i></a></li>
                        <li id="page_number_of_total" class="default_dark_text regular"></li>
                        <li class="grow_on_hover2 default_dark_text" id="next_page"><a href="#!"><i
                                class="material-icons" id="next_page_chevron">chevron_right</i></a></li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
<script>
    $(document).ready(function () {
        $('.modal').modal();
    });

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
            var prevPageChevron = document.getElementById("prev_page_chevron");
            prevPageChevron.className = "material-icons black-text";
        }
        if (pageNumber === pages) {
            nextPagePaginator.className = "disabled default_dark_text";
        } else {
            nextPagePaginator.onclick = function () {
                pageNumber = pageNumber + 1;
                params.set("page", pageNumber.toString());
                nextPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
            var nextPageChevron = document.getElementById("next_page_chevron");
            nextPageChevron.className = "material-icons black-text";
        }
    });
</script>
</body>
</html>