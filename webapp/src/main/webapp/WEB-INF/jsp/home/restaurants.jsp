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

<div class="section no-pad-bot">
    <div class="container">
        <h1 class="header center white-text bold"><spring:message code="home.index.recommended.title"/></h1>
    </div>
</div>

<div class="container">
    <div class="row">
        <%@include file="../search_bar.jsp" %>
    </div>
    <div class="flex_row">
        <c:if test="${restaurants.size() == 0}">
            <div class="container">
                <div class="card card_wrapper padding-15px default_dark">
                    <h1 class="header center default_light_text">
                        <spring:message code="home.restaurants.none_found"/>
                    </h1>
                </div>
            </div>
        </c:if>
        <c:forEach items="${restaurants}" var="restaurant">

            <a href="<c:url value ="/restaurant/view/${restaurant.id}"/>"
               class="card horizontal card_wrapper grow_on_hover restaurant_card">
                <div class="card-image flex_center">
                    <img src="<c:url value="/resources/media/background1.jpg"/>" alt="rest_img"
                         class="scale_down rounded"/>
                </div>
                <div class="card-content default_dark_text">
                    <div class="flex_row">
                        <h6 class="medium text_overflow_ellipsis"><c:out value="${restaurant.name}"/></h6>
                        <h6 class="medium text_overflow_ellipsis margin_left_auto">&#128205;<c:out
                                value="${restaurant.zone.name}"/></h6>
                    </div>
                    <p class="regular text_overflow_ellipsis"><c:out value="${restaurant.detail}"/></p>
                    <p class="light text_overflow_ellipsis"><spring:message code="home.restaurants.address" />: <c:out value="${restaurant.address}"/></p>
                </div>
            </a>
        </c:forEach>

    </div>
</div>
<c:if test="${pages > 1}">
    <div class="container flex_center" id="paginator">
        <ul class="pagination padding-15px big">
            <li class="grow_on_hover2 white-text" id="previous_page"><a href="#!"><i
                    class="material-icons">chevron_left</i></a></li>
            <li id="page_number_of_total" class="white-text regular"></li>
            <li class="grow_on_hover2 white-text" id="next_page"><a href="#!"><i
                    class="material-icons">chevron_right</i></a></li>
        </ul>
    </div>
</c:if>

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

    function clearSearchSelection() {
        const params = new URLSearchParams(window.location.search);

        params.set('category', '');
        var categoryOption = document.getElementById('category_select_options').value;
        var categoryOptions = document.getElementById('category_select_options').querySelectorAll('option');
        categoryOptions[categoryOption - -1].removeAttribute('selected');
        categoryOptions[0].setAttribute('selected', 'selected');
        document.getElementById('category_select').children.item(0).children.item(0).value = categoryOptions[0].text;

        params.set('zone', '');
        var zoneOption = document.getElementById('zone_select_options').value;
        var zoneOptions = document.getElementById('zone_select_options').querySelectorAll('option');
        zoneOptions[zoneOption - -1].removeAttribute('selected');
        zoneOptions[0].setAttribute('selected', 'selected');
        document.getElementById('zone_select').children.item(0).children.item(0).value = zoneOptions[0].text;

        params.set('shift', '');
        var shiftOption = document.getElementById('shift_select_options').value;
        var shiftOptions = document.getElementById('shift_select_options').querySelectorAll('option');
        shiftOptions[shiftOption - -1].removeAttribute('selected');
        shiftOptions[0].setAttribute('selected', 'selected');
        document.getElementById('shift_select').children.item(0).children.item(0).value = shiftOptions[0].text;

        document.getElementById("search_form").submit()
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

    // Set up paginator
    document.addEventListener('DOMContentLoaded', function () {
        const paginator = document.getElementById("paginator");
        if (paginator === null) return;

        const params = new URLSearchParams(window.location.search);
        let pageNumber = params.get("page");
        if (pageNumber == null) pageNumber = "1";
        var pageNumberElem = document.getElementById("page_number_of_total");
        var pages = Math.ceil(<c:out value="${pages}"/>);
        pageNumberElem.textContent = "Pagina " + pageNumber + " de " + pages;

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
        }


        if (pageNumber === pages) {
            nextPagePaginator.className = "disabled default_dark_text";
        } else {
            nextPagePaginator.onclick = function () {
                pageNumber = pageNumber + 1;
                params.set("page", pageNumber.toString());
                nextPagePaginator.children.item(0).attributes.getNamedItem("href").value = "?" + params;
            }
        }
    });
</script>
</body>
</html>
