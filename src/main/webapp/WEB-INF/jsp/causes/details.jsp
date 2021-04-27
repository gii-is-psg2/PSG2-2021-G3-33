<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="cause">

	<h2>Detalles</h2>
	<br />
	<h3 style="color: #ff5f5f;">
		<fmt:message key="name" />
	</h3>
	<h4>
		<c:out value="${cause.name}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">
		<fmt:message key="description" />
	</h3>
	<h4>
		<c:out value="${cause.description}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Objetivo</h3>
	<h4>
		<c:out value="${cause.budgetTarget}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Organización</h3>
	<h4>
		<c:out value="${cause.organization}" />
	</h4>
	<br />
	<h3 style="color: #ff5f5f;">Estado</h3>
	<h4>
		<c:if test="${cause.isClosed }">
			Causa cerrada
		</c:if>
	</h4>

	<h4>
		<c:if test="${!cause.isClosed }">
			Causa abierta
		</c:if>
	</h4>
	<br />
</petclinic:layout>
