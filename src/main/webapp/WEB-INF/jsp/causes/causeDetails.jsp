<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

    <h2>Cause Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><c:out value="${cause.name}"/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th>Budget target</th>
            <td><c:out value="${cause.budgetTarget} Euros"/></td>
        </tr>
        <tr>
            <th>Organization</th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
        	<th>Donations</th>
        	<td>
        	<c:forEach var="donation" items="${cause.donations}">
            	<c:out value="${donation.amount} Euros donados por ${donation.client} el dia ${donation.dateDonation}"/><br/>
            </c:forEach>
            </td>
        </tr>
    </table>
	<td>
        <spring:url value="/causes/{causeId}/donations/new" var="donationUrl">
        	<spring:param name="causeId" value="${cause.id}"/>
        </spring:url>
        <c:if test="${cause.isOpen}">
    	<a class="btn btn-default" href="${fn:escapeXml(donationUrl)}">Add Donation</a>
    	</c:if>
    </td>
</petclinic:layout>
