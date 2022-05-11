<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="transparent" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="<c:url value="/"/>" class="brand-logo megabold">Dine Out <img style="height: 2.1rem" src="https://images.emojiterra.com/google/android-pie/512px/1f35c.png"></a>

        <ul class="right hide-on-small-and-down">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a href="<c:url value="/register"/>" class="restaurant-register light">
                    <spring:message code="navbar.register" />
                </a>
                </li>
                <li><a href="<c:url value="/login"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                    <spring:message code="navbar.login" />
                </a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li>
                    <div class="white-text">
                        <spring:message code="navbar.greeting" arguments="${user.firstName}" />
                    </div>
                </li>
                <% if (request.isUserInRole("DINER")) { %>
                <li>
                    <a href="<c:url value="/diner/reservations"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                <spring:message code="navbar.reservations" />
                            </p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/diner/favorites"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                <spring:message code="navbar.favorites" />
                            </p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/profile"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                <spring:message code="navbar.profile" />
                            </p>
                        </div>
                    </a>
                </li>
                <% } %>
                <% if (request.isUserInRole("RESTAURANT")) { %>
                <li>
                    <a href="<c:url value="/restaurant/reservations"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                <spring:message code="navbar.reservations" />
                            </p>
                        </div>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/profile"/>"
                       class="btn-small no-text-transform waves-effect waves-red white black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px">
                                <spring:message code="navbar.my_restaurant" />
                            </p>
                        </div>
                    </a>
                </li>
                <% } %>
                <li>
                    <a href="<c:url value="/logout"/>"
                       class="btn-small no-text-transform waves-effect waves-red default_red black-text bold">
                        <div class="logged-in-panel">
                            <p class="padding-5px white-text">
                                <spring:message code="navbar.logout" />
                            </p>
                        </div>
                    </a>
                </li>
            </c:if>
        </ul>
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"></a>
    </div>
</nav>