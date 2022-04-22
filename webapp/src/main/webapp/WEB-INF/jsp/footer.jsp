<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="page-footer default_red">
    <%--    <div class="container">--%>
    <%--        <div class="section">--%>
    <%--            <div class="row rounded shadowed white">--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text"><spring:message code="home.index.info.title"/></h5>
                <p class="light white-text"><spring:message code="home.index.info.description"/></p>


            </div>
            <%--            <div class="col l3 s12">--%>
            <%--                <h5 class="white-text">Lorem ipsum</h5>--%>
            <%--                <ul>--%>
            <%--                    <li><a class="white-text" href="#!">Link 1</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 2</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 3</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 4</a></li>--%>
            <%--                </ul>--%>
            <%--            </div>--%>
            <%--            <div class="col l3 s12">--%>
            <%--                <h5 class="white-text">Lorem ipsum</h5>--%>
            <%--                <ul>--%>
            <%--                    <li><a class="white-text" href="#!">Link 1</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 2</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 3</a></li>--%>
            <%--                    <li><a class="white-text" href="#!">Link 4</a></li>--%>
            <%--                </ul>--%>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            <spring:message code="webapp.footer.project"/>
            <text class="bold"><spring:message code="company.name"/>
            </text>
        </div>
    </div>
</footer>

<!-- Scripts-->
<script src="<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/materialize.js"/>"></script>
<script src="<c:url value="/resources/js/init.js"/>"></script>
