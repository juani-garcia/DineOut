<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <%@ include file="header.jsp" %>
    <title>Not Found</title>
</head>
<body>

<%@ include file="navbar.jsp" %>
<button class="btn waves-effect waves-light" onClick="history.back()">Regresar</button>
<h1>No encontramos el resturante</h1>
<br/>
<p><b>Codigo de error:</b> <c:out value="${pageContext.errorData.statusCode}"/></p>
<p><b>Pedido URI:</b> <c:out value="${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}"/></p>
<br/>

<%@ include file="footer.jsp" %>
</body>

</html>