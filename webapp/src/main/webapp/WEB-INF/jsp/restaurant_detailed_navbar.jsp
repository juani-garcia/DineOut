<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="white user_nav_options" role="navigation">
    <div class="container align_space_between">
        <a onClick="history.back()" class="semibold black-text padding-5px grow_on_hover">
            <spring:message code="navbar.back"/>
        </a>
        <a href="<c:url value="/logout"/>" class="semibold black-text padding-5px grow_on_hover">
            <spring:message code="navbar.logout" />
        </a>
    </div>
</div>