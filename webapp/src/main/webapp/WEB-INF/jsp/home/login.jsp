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
<div id="index-banner" class="align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><spring:message
                    code="home.login"/></h1>
        </div>
    </div>
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
                        <button type="submit" name="action"
                                class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                            <spring:message code="home.login"/>
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                    <div class="row center">
                        <div class="flex_row">
                            <h6>
                                <div class="grow_on_hover">
                                    <a class="thin underline default_dark_text" href="<c:url value="/forgot_my_password"/>">
                                        <spring:message code="home.forgot_password"/>
                                    </a>
                                </div>
                            </h6>
                            <h6 class="margin_left_auto flex_row">
                                <spring:message code="home.no_account"/>
                                <div class="grow_on_hover margins_lr_10px">
                                    <a class="thin underline default_dark_text" href="<c:url value="/register"/>">
                                        <spring:message code="home.register"/>
                                    </a>
                                </div>
                            </h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>


<%@ include file="../footer.jsp" %>
</body>
</html>

