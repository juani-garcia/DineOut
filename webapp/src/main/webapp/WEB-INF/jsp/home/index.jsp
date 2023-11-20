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
<div class="section no-pad-bot">
    <div class="container">
        <h1 class="header center white-text bold"><spring:message code="home.index.header.title"/></h1>
        <h5 class="header center white-text bold"><spring:message code="home.index.header.catchphrase"/></h5>

        <div class="row">
            <%@include file="../search_bar.jsp" %>
        </div>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <div class="container shadowed rounded default_dark">
                <div class="row center">
                    <h4 class="white-text bold"><spring:message code="home.index.info.title"/></h4>
                    <h6 class="light white-text"><spring:message code="home.index.info.description"/></h6>
                </div>
            </div>
        </c:if>
    </div>
</div>


<div class="container">
    <div class="section">
        <div id="index-banner" class="rounded shadowed parallax-container parallax-container-large home_parallax">
            <a href="<c:url value ="/restaurant_picker"/>"
               class="row rounded shadowed white padding-15px home_parallax_card waves-effect waves-red grow_on_hover">
                <h5 class="center bold default_dark_text groovy"><spring:message
                        code="home.index.picker.question"/></h5>
                <p class="center default_dark_text"><spring:message code="home.index.picker.answer"/></p>
            </a>
            <div class="parallax white"><img src="<c:url value="/public/static/background2.jpg"/>"
                                             alt=""></div>   <!-- Custom restaurant image -->
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
<script>
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
    }

    document.addEventListener('DOMContentLoaded', function () {
        // Add category, zone and shift options for search.
        var category_options = [];
        <c:forEach items="${categories}" var="category">
        category_options.push("${category.message}");
        </c:forEach>
        var category_elems = document.getElementById("category_select").querySelectorAll('select');
        var category_instances = M.FormSelect.init(category_elems, category_options);

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
