<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <div class="section">
        <div class="search_bar flex_center">
            <div class="card flex_column">
                <c:url value="/restaurants" var="getPath"/>
                <form action="${getPath}" method="get"
                      class="card horizontal card_wrapper white-text align_center same_width_elements_with_overflow no_border no_bottom_margin"
                      id="search_form">
                    <div class="card-content same_width_elements_with_overflow">
                        <label>
                            <spring:message code="search_bar.restaurant_name"/>:
                            <input name="name" type="text" id="name_filter_input"
                                   placeholder="Atuel, Citadino, BudaGreen, ..."/>
                        </label>
                    </div>
                    <div class="card-content same_width_elements_with_overflow">
                        <label id="category_select">
                            <spring:message code="search_bar.category"/>:
                            <select name="category" id="category_select_options">
                                <option value="" selected><spring:message code="search_bar.category.placeholder"/>
                                </option>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.idString}"><spring:message
                                            code="${category.message}"/></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="card-content same_width_elements_with_overflow">
                        <label id="shift_select">
                            <spring:message code="search_bar.shift"/>:
                            <select name="shift" id="shift_select_options">
                                <option value="" selected><spring:message code="search_bar.shift.placeholder"/>
                                </option>
                                <c:forEach items="${shifts}" var="shift">
                                    <option value="${shift.idString}"><spring:message code="${shift.message}"/></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="card-content same_width_elements_with_overflow">
                        <label id="zone_select">
                            <spring:message code="search_bar.zone"/>:
                            <select name="zone" id="zone_select_options">
                                <option value="" selected><spring:message code="search_bar.zone.placeholder"/>
                                </option>
                                <c:forEach items="${zones}" var="zone">
                                    <option value="${zone.idString}"><c:out value="${zone.name}"/></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="card-content">
                        <label id="search_submit">
                            <button class="btn-large waves-effect waves-red grow_on_hover btn-floating white"
                                    type="submit">
                                <i class="material-icons default_dark_text font_2rem">search</i>
                            </button>
                        </label>
                    </div>
                </form>
                <div class="flex_center margin_b_5px">
                    <a onclick="clearSearchSelection()"
                       class="grow_on_hover default_dark_text thin underline black-text flex_center">
                        <spring:message code="search_bar.clear_filters"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
