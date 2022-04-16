<%--
  Created by IntelliJ IDEA.
  User: jerobrave
  Date: 4/10/22
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="default_dark" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="<c:url value="/"/>" class="brand-logo megabold">Dine Out &#127836;</a>
        <%--        <!-- TODO: add isloggedin validation-->--%>
        <%--        <c:if test="isloggedin">--%>

        <%--        </c:if>--%>
        <%--        <c:if test="">--%>

        <%--        </c:if>--%>

        <ul class="right hide-on-small-and-down">
            <li><a href="<c:url value="/test"/>" class="restaurant-register light">¡Registra tu restaurante!</a></li>
            <li><a href="<c:url value="/test"/>" class="btn-small waves-effect waves-red white black-text bold">Iniciar
                sesión</a></li>
            <li>
                <a href="<c:url value="/test"/>" class="btn-small waves-effect waves-red default_red black-text bold">
                    <div class="logged-in-panel">
                        <p class="padding-5px">
                            Hola xxxx
                        </p>
                        <p>
                            <i class="material-icons">menu</i>
                        </p>
                    </div>
                </a>
            </li>
        </ul>

        <ul id="nav-mobile" class="sidenav">
            <li><a href="<c:url value="/test"/>" class="btn-small card waves-effect waves-red white black-text bold">Iniciar
                sesión</a></li>
            <li><a href="<c:url value="/test"/>" class="restaurant-register">¡Registra tu restaurante!</a></li>
        </ul>
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
    </div>
</nav>