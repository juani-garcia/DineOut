<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Dine Out</title>
    <link rel="icon" href="https://images.emojiterra.com/google/android-pie/512px/1f35c.png" type="image/x-icon">

    <!-- CSS  -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:100" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/materialize.css" type="text/css" rel="stylesheet"
          media="screen,projection"/>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" type="text/css" rel="stylesheet"
          media="screen,projection"/>
</head>
<body>
<nav class="default_dark" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="#" class="brand-logo megabold">Dine Out &#127836;</a>
        <ul class="right hide-on-small-and-down">
            <li><a href="#" class="restaurant-register light">¡Registra tu restaurante!</a></li>
        </ul>

        <ul id="nav-mobile" class="sidenav">
            <li><a href="#" class="restaurant-register">¡Registra tu restaurante!</a></li>
        </ul>
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
    </div>
</nav>

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
                    <h5 class="center">Restaurantes recomendados</h5>

                    <p class="center light">Esta es una seleccion de algunos de los mejores restaurantes de Buenos
                        Aires.</p>
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
                <div class="card-content black-text">
                    <p>I am a very simple card. I am good at containing small bits of information. I am convenient
                        because I require little markup to use effectively.</p>
                </div>
            </div>
        </div>
    </div>
    <div class="parallax"><img src="https://images.pexels.com/photos/315755/pexels-photo-315755.jpeg"
                               alt="Unsplashed background img 1"></div>
</div>


<footer class="page-footer default_red">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Lorem ipsum</h5>
                <p class="grey-text text-lighten-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>


            </div>
            <div class="col l3 s12">
                <h5 class="white-text">Lorem ipsum</h5>
                <ul>
                    <li><a class="white-text" href="#!">Link 1</a></li>
                    <li><a class="white-text" href="#!">Link 2</a></li>
                    <li><a class="white-text" href="#!">Link 3</a></li>
                    <li><a class="white-text" href="#!">Link 4</a></li>
                </ul>
            </div>
            <div class="col l3 s12">
                <h5 class="white-text">Lorem ipsum</h5>
                <ul>
                    <li><a class="white-text" href="#!">Link 1</a></li>
                    <li><a class="white-text" href="#!">Link 2</a></li>
                    <li><a class="white-text" href="#!">Link 3</a></li>
                    <li><a class="white-text" href="#!">Link 4</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            Un proyecto de
            <text class="bold">Graphene
                <text>.
        </div>
    </div>
</footer>


<!--  Scripts-->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/materialize.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/init.js"></script>

</body>
</html>
