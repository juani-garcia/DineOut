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

        <div class="row center">
            <a href="<c:url value ="/restaurants"/>"
               class="btn-large waves-effect waves-red white black-text lighten-1 center no-text-transform semibold rounded">
                ¡Explora todos los restaurantes!
            </a>
        </div>
    </div>
</div>


<div class="container">
    <div class="section">
        <div id="index-banner" class="rounded shadowed parallax-container parallax-container-large home_parallax">
            <a href="<c:url value ="/restaurant_picker"/>" class="row rounded shadowed white padding-15px home_parallax_card">
                <h5 class="center bold default_dark_text groovy">¿No sabes que comer?</h5>
                <p class="center default_dark_text">Dejanos elegir un restaurante para vos</p>
            </a>
            <div class="parallax white"><img src="<c:url value="/resources/media/background2.jpg"/>"
                                       alt=""></div>   <!-- Custom restaurant image -->
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
</body>
</html>
