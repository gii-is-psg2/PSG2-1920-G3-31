<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#startDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#finishDate").datepicker({dateFormat: 'yy/mm/dd'});

            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${booking['new']}">New </c:if>Booking</h2>

        <b>Pet</b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Start Date</th>
                <th>Finish Date</th>
                <th>Type</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${booking.pet.name}"/></td>
                <td><c:out value="${booking.pet.type.name}"/></td>
                <td><c:out value="${booking.pet.owner.firstName} ${booking.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="StarDate" name="startDate"/>
                <petclinic:inputField label="FinishDate" name="finishDate"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${booking.pet.id}"/>
                    <button class="btn btn-default" type="submit">Save Booking</button>
                </div>
            </div>
        </form:form>

        <b>Previous Bookings</b>
        <table class="table table-striped">
            <tr>
                <th>Start Date</th>
                <th>Finish Date</th>
            </tr>
            <c:forEach var="booking" items="${booking.pet.bookings}">
                <c:if test="${!booking['new']}">
                    <jsp:useBean id="now" class="java.util.Date" />
                    <fmt:parseDate value="${booking.finishDate}" var="parsedFinishDate" pattern="yyyy-MM-dd" />
                    <c:if test="${parsedFinishDate.time lt now.time}">
                    <tr>
                        <td><petclinic:localDate date="${booking.startDate}" pattern="yyyy/MM/dd"/></td>
                        <td><petclinic:localDate date="${booking.finishDate}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    </c:if>
                </c:if>
            </c:forEach>
        </table>
        <b>Next Bookings</b>
        <table class="table table-striped">
            <tr>
                <th>Start Date</th>
                <th>Finish Date</th>
            </tr>
            <c:forEach var="booking" items="${booking.pet.bookings}">
                <c:if test="${!booking['new']}">
                    <fmt:parseDate value="${booking.startDate}" var="parsedStartDate" pattern="yyyy-MM-dd" />
                    <c:if test="${parsedStartDate.time gt now.time}">
                        <tr>
                            <td><petclinic:localDate date="${booking.startDate}" pattern="yyyy/MM/dd"/></td>
                            <td><petclinic:localDate date="${booking.finishDate}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                    </c:if>
                </c:if>
            </c:forEach>
        </table>
       
    </jsp:body>

</petclinic:layout>
