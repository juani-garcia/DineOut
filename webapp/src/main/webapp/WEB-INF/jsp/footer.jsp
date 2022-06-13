<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer class="page-footer transparent">
    <div class="footer-copyright transparent">
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
    if (document.getElementById('numberonly') !== null) {
        document.getElementById('numberonly').addEventListener('keydown', function(e) {
            var key   = e.keyCode ? e.keyCode : e.which;
            if (!( [8, 9, 13, 27, 46, 110].indexOf(key) !== -1 ||  // Remove 190 -> "."
                (key === 65 && ( e.ctrlKey || e.metaKey  ) ) ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57 && !(e.shiftKey || e.altKey)) ||
                (key >= 96 && key <= 105)
            )) e.preventDefault();
        });
    }
    if (document.getElementById('decimalnumberonly') !== null) {
        document.getElementById('decimalnumberonly').addEventListener('keydown', function(e) {
            var key   = e.keyCode ? e.keyCode : e.which;
            if (!( [8, 9, 13, 27, 46, 110, 190].indexOf(key) !== -1 ||  // Allow 190 -> "."
                (key === 65 && ( e.ctrlKey || e.metaKey  ) ) ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57 && !(e.shiftKey || e.altKey)) ||
                (key >= 96 && key <= 105)
            )) e.preventDefault();
        });
    }

    // Handle image enlargement
    // Retrived from: https://stackoverflow.com/questions/18545077/image-fullscreen-on-click
    $('img[data-enlargeable]').addClass('img-enlargeable').click(function() {
        var src = $(this).attr('src');
        var modal;

        function removeModal() {
            modal.remove();
            $('body').off('keyup.modal-close');
        }
        modal = $('<div>').css({
            background: 'RGBA(0,0,0,.5) url(' + src + ') no-repeat center',
            backgroundSize: 'contain',
            width: '100%',
            height: '100%',
            position: 'fixed',
            zIndex: '10000',
            top: '0',
            left: '0',
            cursor: 'zoom-out'
        }).click(function() {
            removeModal();
        }).appendTo('body');
        //handling ESC
        $('body').on('keyup.modal-close', function(e) {
            if (e.key === 'Escape') {
                removeModal();
            }
        });
    });
</script>