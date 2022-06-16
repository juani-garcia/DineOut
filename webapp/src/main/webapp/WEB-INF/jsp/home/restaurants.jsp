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

<div class="container">
    <div class="row">
        <%@include file="../search_bar.jsp" %>
    </div>
    <div class="flex_row">
        <c:if test="${restaurants.content.size() == 0}">
            <div class="container">
                <div class="card card_wrapper padding-15px default_dark">
                    <h1 class="header center default_light_text">
                        <spring:message code="home.restaurants.none_found"/>
                    </h1>
                </div>
            </div>
        </c:if>
        <c:forEach items="${restaurants.content}" var="restaurant">

            <a href="<c:url value ="/restaurant/${restaurant.id}/view"/>"
               class="card horizontal card_wrapper grow_on_hover restaurant_card">
                <c:if test="${restaurant.image != null}">
                    <div class="card-image flex_center">
                        <c:url value="/image/${restaurant.image.id}" var="imagePath"/>
                        <img src="${imagePath}" class="scale_down rounded" alt=""/>
                    </div>
                </c:if>
                <div class="card-content default_dark_text flex_column">
                    <div class="flex_row">
                        <h6 class="medium text_overflow_ellipsis"><c:out value="${restaurant.name}"/></h6>
                        <h6 class="medium text_overflow_ellipsis margin_left_auto">&#128205;<c:out
                                value="${restaurant.zone.name}"/></h6>
                    </div>
                    <p class="regular text_overflow_ellipsis"><c:out value="${restaurant.detail}"/></p>
                    <c:if test="${restaurant.categories.size() != 0}">
                        <p class="light text_overflow_ellipsis width_70"><spring:message
                                code="home.restaurants.address"/>:
                            <c:out value="${restaurant.address}"/></p>
                    </c:if>
                    <div class="flex_row margin_t_auto">
                        <c:if test="${restaurant.categories.size() != 0}">
                            <div class="flex_column width_70">
                                <p class="light text_overflow_ellipsis"><spring:message
                                        code="home.restaurants.categories"/>:</p>
                                <div class="flex_row_only scrollable_row width_100 z_index_9999">
                                    <c:forEach items="${restaurant.categories}" var="category">
                                        <h6 class="card margins_lr_5px padding_4px grow_on_hover shadowed_small z_index_9999"
                                            onclick="searchForCategory(${category.id})">
                                            <spring:message code="${category.message}"/>
                                        </h6>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${restaurant.categories.size() == 0}">
                            <p class="light text_overflow_ellipsis margin_t_auto"><spring:message
                                    code="home.restaurants.address"/>:
                                <c:out value="${restaurant.address}"/></p>
                        </c:if>
                        <c:if test="${restaurant.rating != null && restaurant.rating != 0}">
                            <div class="margin_left_auto width_25 flex_row margin_t_auto">
                                <h3 class="medium text_overflow_ellipsis flex_row star_rating margin_tb_0px"
                                    id="${restaurant.rating}">
                                        <%-- generated via JS.--%>
                                </h3>
                                &nbsp;
                                <h6 class="center default_light_text margin_tb_0px">(<c:out
                                        value="${restaurant.ratingCount}"/>)</h6>
                            </div>
                        </c:if>
                        <c:if test="${!(restaurant.rating != null && restaurant.rating != 0)}">
                            <h3 class="medium text_overflow_ellipsis margin_left_auto flex_row">
                            </h3>
                        </c:if>
                    </div>
                </div>
            </a>
        </c:forEach>

    </div>
</div>
<c:if test="${restaurants.pageCount > 1}">
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
    function searchForCategory(catId) {
        let catSearch = '<c:url value ="/restaurants?category="/>';
        window.location.href = catSearch + catId;
    }

    // Set up rating
    document.addEventListener('DOMContentLoaded', function () {
        let ratings = document.getElementsByClassName("star_rating");
        if (ratings.length === 0) return;
        for (let starRating = ratings.item(0), i = 0; i < ratings.length; i++, starRating = ratings.item(i)) {
            for (let i = 0; i < starRating.id; i++) {
                starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_red_text">star</i>';
            }
            for (let i = 0; i < 5 - starRating.id; i++) {
                starRating.innerHTML = starRating.innerHTML + '<i class="material-icons default_light_text">star</i>';
            }
        }
    });

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

        params.set('name', '');
        document.getElementById('name_filter_input').value = "";

        params.set('category', '');
        var categoryOption = document.getElementById('category_select_options').value;
        var categoryOptions = document.getElementById('category_select_options').querySelectorAll('option');
        categoryOptions[categoryOption - -1].removeAttribute('selected');
        categoryOptions[0].setAttribute('selected', 'selected');
        document.getElementById(document.getElementById('category_select').children.item(0).children.item(0).getAttribute("data-target")).childNodes.forEach(function (el) {
            el.setAttribute("class", "")
        })
        document.getElementById(document.getElementById('category_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).setAttribute("class", "selected");
        document.getElementById(document.getElementById('category_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).click()
        document.getElementById('category_select').children.item(0).children.item(0).value = categoryOptions[0].text;

        params.set('zone', '');
        var zoneOption = document.getElementById('zone_select_options').value;
        var zoneOptions = document.getElementById('zone_select_options').querySelectorAll('option');
        zoneOptions[zoneOption - -1].removeAttribute('selected');
        zoneOptions[0].setAttribute('selected', 'selected');
        document.getElementById(document.getElementById('zone_select').children.item(0).children.item(0).getAttribute("data-target")).childNodes.forEach(function (el) {
            el.setAttribute("class", "")
        })
        document.getElementById(document.getElementById('zone_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).setAttribute("class", "selected");
        document.getElementById(document.getElementById('zone_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).click()
        document.getElementById('zone_select').children.item(0).children.item(0).value = zoneOptions[0].text;

        params.set('shift', '');
        var shiftOption = document.getElementById('shift_select_options').value;
        var shiftOptions = document.getElementById('shift_select_options').querySelectorAll('option');
        shiftOptions[shiftOption - -1].removeAttribute('selected');
        shiftOptions[0].setAttribute('selected', 'selected');
        document.getElementById(document.getElementById('shift_select').children.item(0).children.item(0).getAttribute("data-target")).childNodes.forEach(function (el) {
            el.setAttribute("class", "")
        })
        document.getElementById(document.getElementById('shift_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).setAttribute("class", "selected");
        document.getElementById(document.getElementById('shift_select').children.item(0).children.item(0).getAttribute("data-target")).children.item(0).click()
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
        var pages = Math.ceil(<c:out value="${restaurants.pageCount}"/>);
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
