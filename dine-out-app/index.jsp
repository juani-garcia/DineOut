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

<!-- Main title -->
<div class="section no-pad-bot">
    <div class="container">
        <h1 class="header center white-text bold">Search, book, and enjoy!</h1>
        <h5 class="header center white-text bold">The solution to dining out</h5>

        <div class="row">
        </div>
    </div>
</div>


<div class="container">
    <div class="section">
        <div id="index-banner" class="rounded shadowed parallax-container parallax-container-large home_parallax">
            <a href="<c:url value ="/restaurant_picker"/>"
               class="row rounded shadowed white padding-15px home_parallax_card waves-effect waves-red grow_on_hover">
                <h5 class="center bold default_dark_text groovy">Don't know what to eat?</h5>
                <p class="center default_dark_text">Let us pick a restaurant just for you</p>
            </a>
            <div class="parallax white"><img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fauspost.com.au%2Fcontent%2Fdam%2Fcorp%2Ftravel-essentials%2Fdestination-guides%2Fitaly%2Fphoto-antipasto-platter-italian-food.jpg&f=1&nofb=1&ipt=cc2063ce7b90b2d5022d4e421d5c5abb61858571a618b14967941247bd91a1d1&ipo=images"
                                             alt=""></div>   <!-- Custom restaurant image -->
        </div>
    </div>
</div>

</body>
</html>
