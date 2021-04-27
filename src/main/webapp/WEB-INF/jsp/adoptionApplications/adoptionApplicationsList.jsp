<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<petclinic:layout pageName="ApplicationRequests">
	<h2>
		<fmt:message key="RequestsFor" /> <c:out value="${pet.name}"></c:out>
	</h2>
	
	<table class="table table-striped">
		<c:forEach var="application" items="${pet.applications}">
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
							<fmt:message key="applicant" />
						</dt>
						<dd>
							<c:out value="${application.owner.firstName} ${application.owner.lastName}" />
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
				<!--  Cambiar cuando implemente los dos controladores(uno para cancelar[que borre solo una solicitud] y el otro
				para aceptar[que borre todas las solicitudes y cambie el dueño a la mascota[) -->
					<spring:url value="/adoptionRequests/{petId}/{applicationId}/accept" var="acceptUrl">
						<spring:param name="petId" value="${application.pet.id}"></spring:param>
						<spring:param name="applicationId" value="${application.id}"></spring:param>
									
					</spring:url>
					<a href="${fn:escapeXml(acceptUrl)}" class="btn btn-default"><fmt:message key="accept" /></a>
					
					<spring:url value="/adoptionRequests/{petId}/{applicationId}/reject" var="rejectUrl">
						<spring:param name="petId" value="${application.pet.id}"></spring:param>
						<spring:param name="applicationId" value="${application.id}"></spring:param>
									
					</spring:url>
					<a href="${fn:escapeXml(rejectUrl)}" class="btn btn-default"><fmt:message key="reject" /></a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
</petclinic:layout>