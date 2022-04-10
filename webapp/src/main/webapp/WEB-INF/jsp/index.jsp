<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <title>Dine Out</title>
    <body>
        <h2>All restaurants</h2>
        <c:forEach items="${restaurants}" var="restaurant">
            <a href="<c:url value="/reserve/${restaurant.id}"/>" >
                <h4><c:out value="${restaurant.name}"/></h4>
            </a>
        </c:forEach>
    </body>
</html>