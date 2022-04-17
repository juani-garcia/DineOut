<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="en">
<head>
    <title>Dine Out</title>
    <%@ include file="../header.jsp" %>
</head>
<body>
<%@ include file="../navbar.jsp" %>

<!-- Main title -->
<h2><spring:message code="home.register.body.title"/></h2>
<c:url value="/register" var="postUrl"/>
<form:form modelAttribute="registerForm" method="post" action="${postUrl}">
    <div>
        <form:errors path="username" element="p" cssClass="color: red"/>
        <form:label path="username"><spring:message code="home.register.form.username"/></form:label>
        <form:input type="text" path="username"/>
    </div>
    <div>
        <form:errors path="password" element="p" cssClass="color: red"/>
        <form:label path="password"><spring:message code="home.register.form.password"/></form:label>
        <form:input type="password" path="password"/>
    </div>
    <div>
        <input type="submit" value="<spring:message code="home.register.form.button"/>"/>
    </div>
</form:form>

<!-- Recommended -->
<div class="container">
    <div class="section">
        <div class="row align_center">
            <div class="col s6 offset-2">
                <div class="icon-block">
                    <h2 class="center brown-text"><i class="material-icons">group</i></h2>
                    <h5 class="center"><spring:message code="home.index.info.title" /></h5>
                    <p class="center light"><spring:message code="home.index.info.description" /></p>
                </div>
            </div>
            <div class="col s6 offset-2">
                <div class="icon-block">
                    <h2 class="center brown-text"><i class="material-icons">restaurant</i></h2>
                    <h5 class="center"><spring:message code="home.index.recommended.title" /></h5>

                    <p class="center light"><spring:message code="home.index.recommended.description" /></p>
                </div>
            </div>
        </div>

    </div>
</div>

<div id="restaurant-parallax-container" class="parallax-container">
    <div class="row align_center">
        <div class="col">
            <c:forEach items="${restaurants}" var="restaurant">

                <a href="<c:url value ="/reserve/${restaurant.id}"/>">
                    <div class="card card_wrapper">
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
<%@ include file="../footer.jsp" %>
</body>
</html>
