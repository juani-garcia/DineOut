<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="default_dark" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="<c:url value="/"/>" class="brand-logo megabold">Dine Out &#127836;</a>

        <ul class="right hide-on-small-and-down">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a href="<c:url value="/register"/>" class="restaurant-register light">¡Registrate!</a>
                </li>
                <li><a href="<c:url value="/login"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">Iniciar
                    sesión</a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li>
                    <a href="<c:url value="/profile"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                ¡Hola <c:out value="${user.firstName}"/>!
                            </p>
                            <p>
                                <i class="material-icons">menu</i>
                            </p>
                        </div>
                    </a>
                </li>
            </c:if>
        </ul>

        <ul id="nav-mobile" class="sidenav">
            <li><a href="<c:url value="/test"/>"
                   class="btn-small no-text-transform card waves-effect waves-red white black-text bold">Iniciar
                sesión</a></li>
            <li><a href="<c:url value="/test"/>" class="restaurant-register">¡Registra tu restaurante!</a></li>
        </ul>
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
    </div>
</nav>