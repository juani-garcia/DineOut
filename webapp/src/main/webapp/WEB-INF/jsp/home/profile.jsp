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
<%@ include file="../diner_detailed_navbar.jsp" %>

<!-- Main title -->

<div>
    <p><c:out value="${user.firstName}"/> </p>
</div>

<div>
    <h1>Tus reservas:</h1>
</div>


<%@ include file="../footer.jsp" %>
</body>
</html>
