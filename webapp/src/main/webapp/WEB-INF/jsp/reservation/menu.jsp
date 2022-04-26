<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dine Out</title>
    <%@ include file="../header.jsp" %>
</head>
<body class="default_light">
<%@ include file="../navbar.jsp" %>
<div id="index-banner" class="parallax-container parallax-container-small align_center">
    <div class="section no-pad-bot">
        <div class="container">
            <h1 class="header center white-text bold"><c:out value="${restaurant.name}"/></h1>
            <h4 class="header center white-text bold"><c:out value="${restaurant.detail}"/></h4>
        </div>
    </div>
    <div class="parallax"><img src="<c:url value="/resources/media/background1.jpg"/>"
                               alt=""></div>   <!-- Custom restaurant image -->
</div>


<div class="container">
    <div class="section">
            <div class="align_center">
                <div class="col">
                    <div class="card menu_card">
                        <c:forEach items="${sections}" var="section">
                            <%--                <div class="card-image">--%>
                            <%--                    <img src="" alt="">--%>
                            <%--                </div>--%>
                            <div class="card-content black-text">
                                <h2><c:out value="${section.name}"/></h2>
                                <c:forEach items="${section.menuItemList}" var="item">
                                    <p><c:out value="${item.name}"/></p>
                                    <p><c:out value="${item.detail}"/></p>

                                    <p><c:out value="${item.price}"/></p>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
</body>
</html>