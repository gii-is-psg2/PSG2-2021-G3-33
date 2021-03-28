<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="owners">
	<h2><fmt:message key="owners"/></h2>

	<table id="ownersTable" class="table table-striped">
		<thead >
			<tr>
				<th style="width: 150px;"><fmt:message key="name"/></th>
				<th style="width: 200px;"><fmt:message key="address"/></th>
				<th><fmt:message key="city"/></th>
				<th style="width: 120px"><fmt:message key="telephone"/></th>
				<th><fmt:message key="pet"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${selections}" var="owner">
				<tr>
					<td><spring:url value="/owners/{ownerId}" var="ownerUrl">
							<spring:param name="ownerId" value="${owner.id}" />
						</spring:url> <a href="${fn:escapeXml(ownerUrl)}"><c:out
								value="${owner.firstName} ${owner.lastName}" /></a></td>
					<td><c:out value="${owner.address}" /></td>
					<td><c:out value="${owner.city}" /></td>
					<td><c:out value="${owner.telephone}" /></td>
					<td><c:forEach var="pet" items="${owner.pets}">
							<p><c:out value="${pet.name} " /></p>
						</c:forEach>
						<div class="col-sm-offset-12 col-sm-4" >
							<spring:url value="/owners/{ownerId}/delete" var="deleteUrl">
                				<spring:param name="ownerId" value="${owner.id}"></spring:param>
                			</spring:url>
                			<a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default">Eliminar</a>
            			</div></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
