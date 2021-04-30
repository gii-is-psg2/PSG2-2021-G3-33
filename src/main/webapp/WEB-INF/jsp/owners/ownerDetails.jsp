<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">

	<h2>
		<fmt:message key="ownersInfo" />
	</h2>


	<table class="table table-striped">
		<tr>
			<th><fmt:message key="name" /></th>
			<td><b><c:out value="${owner.firstName} ${owner.lastName}" /></b></td>
		</tr>
		<tr>
			<th><fmt:message key="address" /></th>
			<td><c:out value="${owner.address}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="city" /></th>
			<td><c:out value="${owner.city}" /></td>
		</tr>
		<tr>
			<th><fmt:message key="telephone" /></th>
			<td><c:out value="${owner.telephone}" /></td>
		</tr>
	</table>
	<sec:authorize access="hasAnyAuthority('admin')">
		<spring:url value="{ownerId}/edit" var="editUrl">
			<spring:param name="ownerId" value="${owner.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message
				key="editOwner" /></a>
		<spring:url value="/owners/{ownerId}/delete" var="deleteUrl">
			<spring:param name="ownerId" value="${owner.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default"><fmt:message
				key="deleteOwner" /></a>

		<spring:url value="{ownerId}/pets/new" var="addUrl">
			<spring:param name="ownerId" value="${owner.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message
				key="addPet" /></a>
	</sec:authorize>
	<br />
	<br />
	<br />
	<h2>
		<fmt:message key="petsAndVisits" />
	</h2>

	<table class="table table-striped">
		<c:forEach var="pet" items="${owner.pets}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>
							<fmt:message key="name" />
						</dt>
						<dd>
							<c:out value="${pet.name}" />
						</dd>
						<dt>
							<fmt:message key="birthDate" />
						</dt>
						<dd>
							<petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd" />
						</dd>
						<dt>
							<fmt:message key="type" />
						</dt>
						<dd>
							<c:out value="${pet.type.name}" />
						</dd>
						<dt>
							<fmt:message key="status" />
						</dt>
						<dd>
							<c:if test="${pet.status == false }">
								<fmt:message key="NoAdoption" />
							</c:if>
							<c:if test="${pet.status == true }">
								<fmt:message key="Adoption" />
							</c:if>	
						</dd>
						<sec:authorize access="hasAnyAuthority('owner')">
						<dd>
							<c:if test="${pet.status == false }">
								<div class="col-sm-offset-12 col-sm-4">
									<spring:url value="/owners/{ownerId}/pets/{petId}/{status}" var="statusUrl">
										<spring:param name="ownerId" value="${owner.id}"></spring:param>
										<spring:param name="petId" value="${pet.id}"></spring:param>
										<spring:param name="status" value="${true}"></spring:param>
									
									</spring:url>
									<a href="${fn:escapeXml(statusUrl)}" class="btn btn-default"><fmt:message key="inAdoption" /></a>
								</div>
							</c:if>
							<c:if test="${pet.status == true }">
								<div class="col-sm-offset-12 col-sm-4">
									<spring:url value="/owners/{ownerId}/pets/{petId}/{status}" var="statusUrl">
										<spring:param name="ownerId" value="${owner.id}"></spring:param>
										<spring:param name="petId" value="${pet.id}"></spring:param>
										<spring:param name="status" value="${false}"></spring:param>
									
									</spring:url>
									<a href="${fn:escapeXml(statusUrl)}" class="btn btn-default"><fmt:message key="cancelAdoption" /></a>
								</div>
							</c:if>
						</dd>
						<dd>
							<spring:url value="/adoptionRequests/{petId}"
									var="applicationsUrl">
									<spring:param name="petId" value="${pet.id}" />
								</spring:url> <a href="${fn:escapeXml(applicationsUrl)}"><fmt:message
										key="seeRequests" /></a>
						</dd>
						</sec:authorize>
					</dl>
				</td>
				<td valign="top">
					<table class="table-condensed">
						<thead>
							<tr>
								<th><fmt:message key="visitDate" /></th>
								<th><fmt:message key="description" /></th>
							</tr>
						</thead>
						<c:forEach var="visit" items="${pet.visits}">
							<tr>
								<td><petclinic:localDate date="${visit.date}"
										pattern="yyyy-MM-dd" /></td>
								<td><c:out value="${visit.description}" /></td>
								<td><spring:url
										value="/owners/{ownerId}/pets/{petId}/{visitId}/delete"
										var="visitUrl">
										<spring:param name="ownerId" value="${owner.id}" />
										<spring:param name="petId" value="${pet.id}" />
										<spring:param name="visitId" value="${visit.id}" />
									</spring:url> <a href="${fn:escapeXml(visitUrl)}"><fmt:message
											key="cancelledVisit" /></a></td>
							</tr>
						</c:forEach>
						<sec:authorize access="hasAnyAuthority('admin')">
						<tr>
							<td><spring:url value="/owners/{ownerId}/pets/{petId}/edit"
									var="petUrl">
									<spring:param name="ownerId" value="${owner.id}" />
									<spring:param name="petId" value="${pet.id}" />
								</spring:url> <a href="${fn:escapeXml(petUrl)}"><fmt:message
										key="editPet" /></a></td>
							<td><spring:url
									value="/owners/{ownerId}/pets/{petId}/delete" var="petUrl">
									<spring:param name="ownerId" value="${owner.id}" />
									<spring:param name="petId" value="${pet.id}" />
								</spring:url> <a href="${fn:escapeXml(petUrl)}"><fmt:message
										key="deletePet" /></a></td>
							<td><spring:url
									value="/owners/{ownerId}/pets/{petId}/visits/new"
									var="visitUrl">
									<spring:param name="ownerId" value="${owner.id}" />
									<spring:param name="petId" value="${pet.id}" />
								</spring:url> <a href="${fn:escapeXml(visitUrl)}"><fmt:message
										key="addVisit" /></a></td>
							<td><spring:url
									value="/owners/{ownerId}/pets/{petId}/rooms/new" var="roomsUrl">
									<spring:param name="ownerId" value="${owner.id}" />
									<spring:param name="petId" value="${pet.id}" />
								</spring:url> <a href="${fn:escapeXml(roomsUrl)}"><fmt:message
										key="addStay" /></a></td>
						</tr>
						</sec:authorize>
					</table>
				</td>
				<td style="verticla-align: top;">
					<table class="table-condensed">
						<thead>
							<tr>
								<th><fmt:message key="rooms" /></th>


							</tr>
						</thead>
						<c:forEach var="room" items="${pet.rooms}">
							<c:if test="${!room['new']}">

								<tr>
									<td><c:out value="${room.details}" /></td>
									<td><petclinic:localDate date="${room.startDate}"
											pattern="yyyy-MM-dd" /></td>
									<td><petclinic:localDate date="${room.endDate}"
											pattern="yyyy-MM-dd" /></td>
									<td><spring:url
											value="/owners/{ownerId}/pets/{petId}/rooms/{roomId}/delete"
											var="roomUrl">
											<spring:param name="ownerId" value="${owner.id}" />
											<spring:param name="petId" value="${pet.id}" />
											<spring:param name="roomId" value="${room.id}" />
										</spring:url> <a href="${fn:escapeXml(roomUrl)}"><fmt:message
												key="deleteStay" /></a></td>
								</tr>
							</c:if>
						</c:forEach>

					</table>
			</tr>

		</c:forEach>
	</table>
	<br />
	<br />
	<br />
	<h2>
		<fmt:message key="myAdoptionApplication" />
	</h2>
	<table class="table table-striped">
		<c:forEach var="application" items="${owner.applications}">
			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>
							<fmt:message key="name" />
						</dt>
						<dd>
							<c:out value="${application.pet.name}" />
						</dd>
						<dt>
							<fmt:message key="birthDate" />
						</dt>
						<dd>
							<petclinic:localDate date="${application.pet.birthDate}" pattern="yyyy-MM-dd" />
						</dd>
						<dt>
							<fmt:message key="type" />
						</dt>
						<dd>
							<c:out value="${application.pet.type.name}" />
						</dd>
						<dt>
							<fmt:message key="owner" />
						</dt>
						<dd>
							<c:out value="${application.pet.owner.firstName} ${application.pet.owner.lastName}" />
						</dd>
					</dl>
				</td>
				
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>
							<fmt:message key="description" />
						</dt>
						<dd>
							<c:out value="${application.description}" />
						</dd>
					</dl>
				</td>
				
				<td valign="middle">
					<spring:url value="/petsInAdoption/{petId}/{applicationId}/cancel" var="statusUrl">
						<spring:param name="petId" value="${application.pet.id}"></spring:param>
						<spring:param name="applicationId" value="${application.id}"></spring:param>
									
					</spring:url>
					<a href="${fn:escapeXml(statusUrl)}" class="btn btn-default"><fmt:message key="cancelApplication" /></a>
				</td>
			</tr>
		</c:forEach>
	</table>
</petclinic:layout>