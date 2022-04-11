<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold">¡Busca, reserva y disfruta!</h1>
            <div class="row center">
                <h5 class="header col s12 medium">La solucion a tus comidas afuera</h5>
            </div>
        </div>
    </div>
    <div class="parallax"><img src="${pageContext.request.contextPath}/resources/media/background1.jpg"
                               alt="Unsplashed background img 1"></div>
</div>


<div class="container">
    <div class="section">

        <!--   Icon Section   -->
        <div class="row">
            <div class="col s6 offset-s3">
                <div class="icon-block">
                    <h2 class="center brown-text"><i class="material-icons">group</i></h2>
                    <h5 class="center">Por el momento no hay restaurantes disponibles.</h5>
                    <%--                    <h5 class="center">Restaurantes recomendados</h5>--%>

                    <%--                    <p class="center light">Esta es una seleccion de algunos de los mejores restaurantes de Buenos--%>
                    <%--                        Aires.</p>--%>
                </div>
            </div>
        </div>

    </div>
</div>

<div id="restaurant-parallax-container" class="parallax-container">
    <div class="row">
        <div class="col s6 offset-s3">
            <div class="card">
                <%--                <div class="card-image">--%>
                <%--                    <img src="" alt="">--%>
                <%--                </div>--%>
                <%--                <div class="card-content black-text">--%>
                <%--                    <p>I am a very simple card. I am good at containing small bits of information. I am convenient--%>
                <%--                        because I require little markup to use effectively.</p>--%>
                <%--                </div>--%>
            </div>
        </div>
    </div>
    <div class="parallax"><img src="https://images.pexels.com/photos/315755/pexels-photo-315755.jpeg"
                               alt="Unsplashed background img 1"></div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
