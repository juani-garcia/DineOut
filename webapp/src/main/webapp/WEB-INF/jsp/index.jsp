<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
    <body>
        <h2>Hello <c:out value="${restaurant.name}"/>!</h2>
        <h4>The user's id is <c:out value="${restaurant.id}"/></h4>
    </body>
</html>