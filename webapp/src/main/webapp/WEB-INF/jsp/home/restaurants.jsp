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
    <div class="row">
        <%@include file="../search_bar.jsp" %>
    </div>
    <div class="flex_column">
        <c:if test="${restaurants.size() == 0}">
            <div class="container">
                <div class="card card_wrapper padding-15px default_dark">
                    <h1 class="header center default_light_text">
                        <spring:message code="home.restaurants.none_found"/>
                    </h1>
                </div>
            </div>
        </c:if>
        <c:if test="${restaurants.size() != 0}">
            <c:forEach items="${restaurants}" var="restaurant">

                <a href="<c:url value ="/restaurant/view/${restaurant.id}"/>"
                   class="card horizontal card_wrapper grow_on_hover restaurant_card">
                    <div class="card-image flex_center">
                        <img src="<c:url value="/resources/media/background1.jpg"/>" alt="rest_img"
                             class="scale_down rounded"/>
                    </div>
                    <div class="card-content default_dark_text">
                        <h6 class="medium text_overflow_ellipsis"><c:out value="${restaurant.name}"/></h6>
                        <p class="regular text_overflow_ellipsis"><c:out value="${restaurant.detail}"/></p>
                        <p class="light text_overflow_ellipsis">&#128205;<c:out value="${restaurant.address}"/></p>
                    </div>
                </a>
            </c:forEach>
        </c:if>

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
<script>
    function defaultSelector(queryParam, options_id, select_id, params) {
        const categoryString = params.get(queryParam);
        if (categoryString != null && categoryString !== "") {
            const elOptions = document.getElementById(options_id).querySelectorAll('option');
            elOptions[0].removeAttribute('selected');
            let category = parseInt(categoryString) + 1; // category "0" in select is the "Select category"
            const elementOption = elOptions[category.toString()];
            elementOption.setAttribute('selected', 'selected');
            document.getElementById(select_id).children.item(0).children.item(0).value = elementOption.text;
        }
    }

    // Set up all selectors
    document.addEventListener('DOMContentLoaded', function () {
        const params = new URLSearchParams(window.location.search);

        var categoryElems = document.getElementById("category_select").querySelectorAll('select');
        var categoryInstances = M.FormSelect.init(categoryElems);
        defaultSelector("category", "category_select_options", "category_select", params)


        var shiftElems = document.getElementById("shift_select").querySelectorAll('select');
        var shiftInstances = M.FormSelect.init(shiftElems);
        defaultSelector("shift", "shift_select_options", "shift_select", params)


        var zoneElems = document.getElementById("zone_select").querySelectorAll('select');
        var zoneInstances = M.FormSelect.init(zoneElems);
        defaultSelector("zone", "zone_select_options", "zone_select", params)

        const name = params.get("name");
        if (name != null && name !== "") {
            const nameInput = document.getElementById("name_filter_input");
            nameInput.value = name;
        }
    });

        var shift_options = [];
        <c:forEach items="${shifts}" var="shift">
        shift_options.push("${shift.message}");
        </c:forEach>
        var shift_elems = document.getElementById("shift_select").querySelectorAll('select');
        var shift_instances = M.FormSelect.init(shift_elems, shift_options);

        var zone_options = [];
        <c:forEach items="${zones}" var="zone">
        zone_options.push("${zone.name}");
        </c:forEach>
        var zone_elems = document.getElementById("zone_select").querySelectorAll('select');
        var zone_instances = M.FormSelect.init(zone_elems, zone_options);
    });
</script>
</body>
</html>
