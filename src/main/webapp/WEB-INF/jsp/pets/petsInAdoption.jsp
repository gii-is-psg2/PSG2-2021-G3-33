<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	

<petclinic:layout pageName="pets">
	<h2>
		<fmt:message key="petsInAdoptionList" />
	</h2>
	
	<table id="petsInAdoptionTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Nombre</th>
				<th style="width: 200px;">Fecha de nacimiento</th>
				<th style="width: 200px;">Tipo de mascota</th>
				<th style="width: 200px">Dueño</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pets}" var="pet">
				<tr>
					<td><c:out value="${pet.name}" /></td>
					<td><c:out value="${pet.birthDate}" /></td>
					<td><c:out value="${pet.type.name}" /></td>
					<td><c:out value="${pet.owner.firstName} ${pet.owner.lastName }" />
						<sec:authorize access="hasAnyAuthority('owner')">
						<c:if test="${pet.owner.id != owner.id }">
							<div class="col-sm-offset-12 col-sm-4">
								<spring:url value="/petsInAdoption/adopt={petId}" var="adoptUrl">
									<spring:param name="petId" value="${pet.id}"></spring:param>
								</spring:url>
								<a href="${fn:escapeXml(adoptUrl)}" class="btn btn-default"><fmt:message
										key="adopt" /></a>
							</div>
						</c:if>
						
						</sec:authorize>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>