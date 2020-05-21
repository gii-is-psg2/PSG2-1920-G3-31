<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>Causes</h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Organization</th>
            <th>Budget target</th>
            <th>Total donations</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cause.causeList}" var="cause">
            <tr>
                <td>
                    <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}"><c:out value="${cause.name}"/></a>
                </td>
                <td>
                	<c:out value="${cause.organization}"/>
                </td>
                <td>
                	<c:out value="${cause.budgetTarget} Euros"/>
                </td>
                <td>
                	<c:out value="${cause.donationAmount} Euros"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'>Create Cause</a>
</petclinic:layout>
