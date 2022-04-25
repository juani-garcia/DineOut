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
<div id="index-banner" class="parallax-container parallax-container-small">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold">
                <c:if test="${empty restaurant}">No tenes restaurante</c:if>
                <c:if test="${not empty restaurant}"><c:out value="${restaurant.name}"/></c:if>
            </h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>


<div id="restaurant-parallax-container" class="parallax-container fill_space">
    <div class="row align_center">
        <a href="<c:url value="/restaurant/section"/>" class="waves-effect waves-light btn">Agregar seccion</a>
        <a href="<c:url value="/restaurant/item"/>" class="waves-effect waves-light btn">Agregar item</a>
    </div>
    <div class="row align_center">
        <div class="col">
            <c:forEach items="${sections}" var="section">
                 <div class="card card_wrapper">
                            <%--                <div class="card-image">--%>
                            <%--                    <img src="" alt="">--%>
                            <%--                </div>--%>
                        <div class="card-content black-text">
                            <h2><c:out value="${section.name}"/></h2>
                            <c:forEach items="${section.menuItemList}" var="item">
                                <p><c:out value="${item.name}"/></p>
                                <p><c:out value="${item.detail}"/></p>

                                <p><c:out value="${item.price}"/></p>
                            </c:forEach>
                        </div>
                    </div>
            </c:forEach>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="https://images.pexels.com/photos/1581384/pexels-photo-1581384.jpeg"/>"
                               alt=""></div>
</div>


<%@ include file="../footer.jsp" %>
</body>
</html>