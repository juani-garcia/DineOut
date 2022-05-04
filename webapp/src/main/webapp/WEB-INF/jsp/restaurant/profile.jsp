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

<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold">
                <c:out value="${restaurant.name}"/>
            </h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>

<div class="container flex_center padding-15px">
    <div class="menu_title_card flex_center align_center rounded shadowed">
        <h1 class="megabold flex_center groovy">Menu:</h1>
    </div>
    <div class="section flex_center width_100">
        <div class="card menu_card">
            <div class="center-align padding-15px">
                <a href="<c:url value="/restaurant/section"/>"
                   class="btn-large waves-effect waves-light default_blue white-text">Agregar seccion</a>
                <a href="<c:url value="/restaurant/item"/>"
                   class="btn-large waves-effect waves-light default_blue white-text">Agregar item</a>
            </div>
            <div class="card-content black-text">
                <c:forEach items="${sections}" var="section">
                    <%--                <div class="card-image">--%>
                    <%--                    <img src="" alt="">--%>
                    <%--                </div>--%>
                    <div class="row" style="display: flex; align-items: center">
                        <c:if test="${section.ordering > 1}">
                            <c:url value="/restaurant/section/${section.id}/up" var="upUrl"/>
                            <form method="post" action="${upUrl}" style="margin: 0">
                                <button class="btn waves-effect waves-light btn-floating default_blue" type="submit" name="action">
                                    <i class="material-icons left">arrow_upward</i>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${section.ordering < sections.size()}">
                            <c:url value="/restaurant/section/${section.id}/down" var="downUrl"/>
                            <form method="post" action="${downUrl}" style="margin: 0">
                                <button class="btn waves-effect waves-light btn-floating default_blue" type="submit" name="action">
                                    <i class="material-icons left">arrow_downward</i>
                                </button>
                            </form>
                        </c:if>
                        <c:url value="/restaurant/section/${section.id}/delete" var="deleteUrl"/>
                        <form method="post" action="${deleteUrl}" style="margin: 0">
                            <button class="btn waves-effect waves-light btn-floating default_red"
                                    type="submit" name="action">
                                <i class="material-icons left">delete</i>
                            </button>
                        </form>
                        <h2 class="bold">
                            <c:out value="${section.name}"/>
                        </h2>
                    </div>
                    <c:forEach items="${section.menuItemList}" var="item">
                        <div class="section_item_container align_center">
                            <div class="row" style="display:flex; margin: 0; align-items: center">
                                <c:if test="${item.ordering > 1}">
                                    <c:url value="/restaurant/item/${item.id}/up" var="upUrl"/>
                                    <form method="post" action="${upUrl}" style="margin: 0">
                                        <button class="btn waves-effect waves-light btn-floating default_blue"
                                                type="submit" name="action">
                                            <i class="material-icons left">arrow_upward</i>
                                        </button>
                                    </form>
                                </c:if>
                                <c:if test="${item.ordering < section.menuItemList.size()}">
                                    <c:url value="/restaurant/item/${item.id}/down" var="downUrl"/>
                                    <form method="post" action="${downUrl}" style="margin: 0">
                                        <button class="btn waves-effect waves-light btn-floating default_blue"
                                                type="submit" name="action">
                                            <i class="material-icons left">arrow_downward</i>
                                        </button>
                                    </form>
                                </c:if>
                                <c:url value="/restaurant/item/${item.id}/delete" var="deleteUrl"/>
                                <form method="post" action="${deleteUrl}" style="margin: 0">
                                    <button class="btn waves-effect waves-light btn-floating default_red"
                                            type="submit" name="action">
                                        <i class="material-icons left">delete</i>
                                    </button>
                                </form>
                                <h4><c:out value="${item.name}"/></h4>
                            </div>
                            <p>$ <c:out value="${item.price}"/></p>
                        </div>
                        <hr>
                        <p><c:out value="${item.detail}"/></p>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<%@ include file="../footer.jsp" %>
</body>
</html>