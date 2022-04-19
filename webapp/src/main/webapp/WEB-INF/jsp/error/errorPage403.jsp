<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="en">
<head>
    <title>403 - Acceso denegado</title>
    <%@ include file="../header.jsp" %>
</head>
<body>
<%@ include file="../navbar.jsp" %>

<button class="btn waves-effect waves-light" onClick="history.back()">Regresar</button>
<h1>403 - Acceso denegado</h1>
<br/>
<p><b>Codigo de error:</b> <c:out value="${pageContext.errorData.statusCode}"/></p>
<p><b>Pedido URI:</b> <c:out value="${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}"/></p>
<br/>

<%@ include file="../footer.jsp" %>
</body>
</html>
