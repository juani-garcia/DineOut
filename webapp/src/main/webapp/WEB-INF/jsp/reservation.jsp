<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <title>Reserve</title>
</head>
<body>
<h2>Reserve</h2>
<c:url value="/create" var="postPath"/>

<form:form modelAttribute="reservationForm" action="${postPath}" method="post">
    <div>
        <form:label  path="mail" >Mail: </form:label>
        <form:input type="text"  path="mail" />
        <form:errors path="mail" cssClass="formError" element="p"/>
    </div>

    <div>
        <form:label path="amount">Amount: </form:label>
        <form:input path="amount" type="number" />
        <form:errors path="amount" cssClass="formError" element="p"/>
    </div>

    <div>
        <form:label path="date" >Date: </form:label>
        <form:input path="date" type="text" />
        <form:errors path="date" cssClass="formError" element="p"/>
    </div>

    <div>
        <form:label path="time">Time: </form:label>
        <form:input path="time" type="text" />
        <form:errors path="time" cssClass="formError" element="p"/>
    </div>

    <div>
        <form:label path="comments" >Comments: </form:label>
        <form:input path="comments" type="text" />
        <form:errors path="comments" cssClass="formError" element="p"/>
    </div>

    <div>
        <input type="submit" value="Register!"/>
    </div>

</form:form>
</body>
</html>