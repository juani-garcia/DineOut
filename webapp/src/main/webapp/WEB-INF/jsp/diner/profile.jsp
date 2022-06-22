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

<div class="container flex_center padding-15px">
    <div class="section flex_center width_100">
        <div class="card menu_card">
            <h1 class="header center megabold text_overflow_ellipsis flex_row flex_center groovy">
                <spring:message code="diner.profile.title" />
                <c:url value="/diner/edit" var="editUrl"/>
                <a class="btn-large waves-effect waves-light btn-floating default_red margin_l_20px"
                   href="${editUrl}">
                    <i class="material-icons left white-text webkit_fill_white">edit</i>
                </a>
            </h1>

            <h4 class="bold flex_center"> <spring:message code="home.form.username"/></h4>
            <h5 class="flex_center"><c:out value="${user.username}"/></h5>
            <hr>
            <h4 class="bold flex_center"> <spring:message code="home.form.firstname"/></h4>
            <h5 class="flex_center"><c:out value="${user.firstName}"/></h5>
            <hr>
            <h4 class="bold flex_center"> <spring:message code="home.form.lastname"/></h4>
            <h5 class="flex_center"><c:out value="${user.lastName}"/></h5>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>