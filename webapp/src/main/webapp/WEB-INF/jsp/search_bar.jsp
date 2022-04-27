<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="section">
        <div class="search_bar">
            <c:url value="/restaurants" var="getPath"/>
            <form action="${getPath}" method="get"
                  class="card horizontal card_wrapper white-text align_center same_width_elements_with_overflow"
                  id="search_form">
                <div class="card-content same_width_elements_with_overflow">
                    <label>
                        <spring:message code="search_bar.restaurant_name"/>:
                        <input name="name" type="text"/>
                    </label>
                </div>
                <div class="card-content same_width_elements_with_overflow">
                    <label id="category_select">
                        <spring:message code="search_bar.category"/>:
                        <select name="category">
                            <option value="" disabled selected><spring:message code="search_bar.category.placeholder"/>:
                            </option>
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.name}"><c:out value="${category.name}"/></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="card-content same_width_elements_with_overflow">
                    <label id="shift_select">
                        <spring:message code="search_bar.shift"/>:
                        <select name="shift">
                            <option value="" disabled selected><spring:message code="search_bar.shift.placeholder"/>:
                            </option>
                            <c:forEach items="${shifts}" var="shift">
                                <option value="${shift.name}"><c:out value="${shift.name}"/></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="card-content same_width_elements_with_overflow">
                    <label id="zone_select">
                        <spring:message code="search_bar.zone"/>:
                        <select name="zone">
                            <option value="" disabled selected><spring:message code="search_bar.zone.placeholder"/>:
                            </option>
                            <c:forEach items="${zones}" var="zone">
                                <option value="${zone.name}"><c:out value="${zone.name}"/></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <a class="card-content grow_on_hover2 black-text" type="submit" id="search-button"
                     onclick="document.getElementById('search_form').submit();"><i class="material-icons">search</i>
                </a>
            </form>
        </div>
    </div>
</div>


<%--<div class="row">--%>
<%--    <form:errors path="role" element="p" cssClass="isa_error"/>--%>
<%--    <div class="input-field role_selector">--%>
<%--        <label class="fill_space">--%>
<%--            <select name="role">--%>
<%--                <option value="" disabled selected><spring:message--%>
<%--                        code="home.register.form.roleSelector"/></option>--%>
<%--                <c:forEach items="${roleItems}" var="roleItem">--%>
<%--                    <option value="${roleItem}"><c:out value="${roleItem}"/></option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>
<%--        </label>--%>
<%--    </div>--%>
<%--</div>--%>