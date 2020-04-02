<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#dateDonation").datepicker({dateFormat: 'yy/mm/dd'});

            });
        </script>
    </jsp:attribute>
	<jsp:body>
	<h2>New Donation</h2>
	<form:form modelAttribute="donation" class="form-horizontal">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="client" />
			<petclinic:inputField label="Amount" name="amount" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Add donation</button>
			</div>
		</div>
	</form:form>
	</jsp:body>
</petclinic:layout>