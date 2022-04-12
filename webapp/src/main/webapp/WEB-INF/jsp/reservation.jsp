<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><c:out value="${restaurant.name}"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="${pageContext.request.contextPath}/resources/media/background1.jpg"
                               alt="Unsplashed background img 1"></div>   <!-- Custom restaurant image -->
</div>

<div class="container">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row default_dark rounded">
            <div class="col s6 offset-s3">
                <div class="icon-block">
                    <h2 class="center white-text"><i class="material-icons">restaurant_menu</i></h2>
                    <h5 class="center white-text"><c:out value="${restaurant.detail}"/></h5>
                </div>
            </div>
        </div>
    </div>
</div>

<c:url value="/create/${resId}" var="postPath"/>
<form:form id="reservation_form" modelAttribute="reservationForm" action="${postPath}" method="post">
    <div class="row">
        <div class="col s6 offset-s3">
            <h2 class="white bold center">Completa el formulario para finalizar la reserva:</h2>
            <div class="row">
                <form:label path="mail">Mail: </form:label>
                <form:input type="text" path="mail"/>
                <form:errors path="mail" cssClass="isa_error" element="p"/>
            </div>

            <div class="row">
                <form:label path="amount">Amount: </form:label>
                <form:input path="amount" type="number"/>
                <form:errors path="amount" cssClass="isa_error" element="p"/>
            </div>

            <div class="row">
                <form:label path="dateTime">Date and time: </form:label>
                <form:input path="dateTime" type="datetime-local"/>
                <form:errors path="dateTime" cssClass="isa_error" element="p"/>
            </div>

            <div class="row">
                <form:label path="comments">Comments: </form:label>
                <form:input path="comments" type="text"/>
                <form:errors path="comments" cssClass="isa_error" element="p"/>
            </div>
        </div>
    </div>

    <%--    <div>--%>
    <%--        <input type="submit" value="Register!"/>--%>
    <%--    </div>--%>

    <div class="row center">
            <%--        TODO: ask if this is ok--%>
        <a type="submit" id="register-button" class="btn-large waves-effect waves-red white black-text lighten-1"
           href="javascript:{}" onclick="document.getElementById('reservation_form').submit();">¡Reservá!</a>
    </div>

</form:form>
<%@ include file="footer.jsp" %>
</body>
</html>