<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vets">
	<h2>Veterinarians</h2>

	<table id="vetsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Specialties</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vets.vetList}" var="vet">
				<tr>
					<td><c:out value="${vet.firstName} ${vet.lastName}" /></td>
					<td><c:forEach var="specialty" items="${vet.specialties}">
							<c:out value="${specialty.name} " />
						</c:forEach> <c:if test="${vet.nrOfSpecialties == 0}">none</c:if></td>
					<td><a href="/vets/${vet.id}/edit">Modificar</i>
					</a>
        <div class="col-sm-offset-9 col-sm-10" >
							<spring:url value="/vets/{vetId}/delete" var="deleteUrl">
                				<spring:param name="vetId" value="${vet.id}"></spring:param>
                			</spring:url>
                			<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar</a>
            			</div>
        </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<table class="table-buttons">
		<tr>
			<td>
				<form action="vets/new">
					<input type="submit" value="Create Vet" />
				</form>
			</td>
		</tr>
	</table>
</petclinic:layout>
