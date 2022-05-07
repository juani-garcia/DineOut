<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="page-footer transparent">
    <div class="footer-copyright default_dark">
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
<script>
    // Retrieved from: https://stackoverflow.com/questions/18156824/restricting-an-input-box-to-only-numbers-0-9#18156861
    document.getElementById('numberonly').addEventListener('keydown', function(e) {
        var key   = e.keyCode ? e.keyCode : e.which;
        console.log(key);
        if (!( [8, 9, 13, 27, 46, 110].indexOf(key) !== -1 ||  // Remove 190 -> "."
            (key === 65 && ( e.ctrlKey || e.metaKey  ) ) ||
            (key >= 35 && key <= 40) ||
            (key >= 48 && key <= 57 && !(e.shiftKey || e.altKey)) ||
            (key >= 96 && key <= 105)
        )) e.preventDefault();
    });
</script>