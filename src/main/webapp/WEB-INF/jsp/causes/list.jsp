<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<h2>Causas</h2>

	<table id="causesTable" class="table table-striped">
		<thead>
			<tr>
				<th><fmt:message key="name" /></th>
				<th>Recaudado</th>
				<th>Objetivo</th>
				<c:if test="${not cause.isClosed}">
					<th></th>
					<th></th>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<sec:authorize access="hasAnyAuthority(admin)">
			<p>test</p>
		</sec:authorize>
			<c:forEach items="${list}" var="entry">
				<tr>
					<td><spring:url value="/causes/{causeId}" var="causeUrl">
							<spring:param name="causeId" value="${entry.id}" />
						</spring:url> <c:out value="${entry.name}" /></td>
					<td><c:out value="${entry.budgetCollected}" /></td>
					<td><c:out value="${entry.budgetTarget}" /></td>
					<td><spring:url value="/causes/{causeId}" var="detailsUrl">
							<spring:param name="causeId" value="${entry.id}" />
						</spring:url> <a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Detalles</a></td>
					<sec:authorize access="hasAnyAuthority('owner')">
						<c:if test="${!entry.isClosed }">
							<td><spring:url value="/causes/{causeId}/donate" var="detailsUrl">
								<spring:param name="causeId" value="${entry.id}" />
							</spring:url> <a href="${fn:escapeXml(detailsUrl)}" class="btn btn-default">Donar</a></td>	
						</c:if>
							
					
					</sec:authorize>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<table class="table-buttons">
		<tr>
			<td><a class="btn btn-default" style="margin-right: 10px"
				href='<spring:url value="/causes/new" htmlEscape="true"/>'>Crear
					Causa</a></td>
		</tr>
	</table>
</petclinic:layout>