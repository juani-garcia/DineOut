<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Dine Out</title>
    <%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="navbar.jsp" %>

<!-- Main title -->
<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message code="webapp.index.header.title" /></h1>
            <div class="row center">
                <h5 class="header col s12 medium"><spring:message code="webapp.index.header.catchphrase" /></h5>
            </div>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>
</div>

<!-- Recommended -->
<div class="container">
    <div class="section">
        <div class="row">
            <div class="col s6 offset-2">
                <div class="icon-block">
                    <h2 class="center brown-text"><i class="material-icons">restaurant</i></h2>
                    <h5 class="center"><spring:message code="webapp.index.recommended.title" /></h5>
                    <p class="center light"><spring:message code="webapp.index.recommended.description" /></p>
                </div>
            </div>
            <div class="col s6 offset-2">
                <div class="icon-block">
                    <h2 class="center brown-text"><i class="material-icons">restaurant</i></h2>
                    <h5 class="center"><spring:message code="webapp.index.recommended.title" /></h5>

                    <p class="center light"><spring:message code="webapp.index.recommended.description" /></p>
                </div>
            </div>
        </div>

    </div>
</div>

<div id="restaurant-parallax-container" class="parallax-container">
    <div class="row">
        <div class="col s6 offset-s3">
            <c:forEach items="${restaurants}" var="restaurant">

                <a href="<c:url value ="/reserve/${restaurant.id}"/>">
                    <div class="card">
                            <%--                <div class="card-image">--%>
                            <%--                    <img src="" alt="">--%>
                            <%--                </div>--%>
                        <div class="card-content black-text">
                            <h2><c:out value="${restaurant.name}"/></h2>
                            <p><c:out value="${restaurant.detail}"/></p>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="https://images.pexels.com/photos/1581384/pexels-photo-1581384.jpeg"/>"
                               alt=""></div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
