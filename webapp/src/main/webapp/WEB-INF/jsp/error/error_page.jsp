<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <title>404 Pagina no encontrada</title>
    <%@ include file="../header.jsp" %>
</head>
<body>
<%@ include file="../navbar.jsp" %>

<div class="container margins_auto">
    <div class="section">
        <div class="rounded shadowed white">
            <div class="flex_center">
                <div class="section flex_center">
                    <div class="card default_red">
                        <div class="card-content">
                            <h1>¡Oops! <c:out value="${error.title}"/></h1>
                        </div>
                    </div>
                </div>
                <h5><c:out value="${error.recomendation}"/></h5>
                <h5><b>Codigo de error:</b> <c:out value="${pageContext.errorData.statusCode}"/></h5>
                <h5><b>Pedido URI:</b> <c:out
                        value="${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}"/>
                </h5>
                <div class="section flex_center">
                    <button onClick="history.back()"
                            class="btn-large no-text-transform waves-effect waves-red white black-text lighten-1">
                        <i class="material-icons right">keyboard_backspace</i>
                        Regresar
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
</body>
</html>
