<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dine Out</title>
    <link href="${pageContext.request.contextPath}/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
    <body>
        <h2 class="back">Hello <c:out value="${restaurant.name}"/>!</h2>
        <h4>The restaurant's id is <c:out value="${restaurant.id}"/></h4>
    </body>
</html>