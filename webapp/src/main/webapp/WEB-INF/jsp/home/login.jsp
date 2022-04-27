<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>Dine Out</title>
    <%@ include file="../header.jsp" %>
</head>
<body class="default_light">
<%@ include file="../navbar.jsp" %>
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message
                    code="home.login"/></h1>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>

<c:url value="/login" var="postUrl"/>
<form method="post" action="${postUrl}" id="login_form">
    <div class="container">
        <div class="section">
            <div class="row rounded shadowed white">
                <div class="col s6 offset-s3">
                    <c:if test="${param.error}">
                        <div class="isa_error">
                            <spring:message code="home.login.error"/>
                        </div>
                    </c:if>
                    <div class="row">
                        <label> <spring:message code="home.form.username"/> <input type="text" name="username"></label>
                    </div>
                    <div class="row">
                        <label> <spring:message code="home.form.password"/> <input type="password"
                                                                                   name="password"></label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="checkbox" id="remember" name="remember-me"/>
                            <span><spring:message code="home.login.form.rememberMe"/></span>
                        </label>
                    </div>
                    <div class="row center">
                        <a type="submit" id="register-button"
                           class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1"
                           href="javascript:{}"
                           onclick="document.getElementById('login_form').submit();"><spring:message
                                code="home.login"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>


<%@ include file="../footer.jsp" %>
</body>
</html>

