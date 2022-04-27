<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Search</title>
</head>
<body>

<c:url value="/restaurants" var="getPath"/>
<form action="${getPath}" method="get">
    <input name="name" type="text" placeholder="Nombre" />
    <input name="category" type="text" placeholder="Category" list="categories"/>
    <datalist id="categories">
        <c:forEach items="${categories}" var="category">
            <option value="${category.name}"></option>
        </c:forEach>
        <option value=""></option>
    </datalist>

    <input name="shift" type="text" placeholder="Turno" list="shifts"/>
    <datalist id="shifts">
        <c:forEach items="${shifts}" var="shift">
            <option value="${shift.name}"></option>
        </c:forEach>
        <option value=""></option>
    </datalist>

    <input name="zone" type="text" placeholder="Zonas" list="zones"/>
    <datalist id="zones">
        <c:forEach items="${zones}" var="zone">
            <option value="${zone.name}"></option>
        </c:forEach>}
        <option value=""></option>
    </datalist>

    <input type="submit" />
</form>

</body>
</html>
