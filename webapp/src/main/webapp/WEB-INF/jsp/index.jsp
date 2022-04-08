<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <title>Dine Out</title>
    <body>
        <h2>Hello <c:out value="${restaurant.name}"/>!</h2>
        <h4>The restaurant's id is <c:out value="${restaurant.id}"/></h4>
    </body>
</html>