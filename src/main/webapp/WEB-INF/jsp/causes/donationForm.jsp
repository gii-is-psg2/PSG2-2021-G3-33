<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="cause">

	

	<h2>
		<c:if test="${cause['new']}">Crear Causa</c:if>
	</h2>
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
	<h3 style="color: #ff5f5f;">Donado</h3>
	<h4>
		<c:out value="${cause.budgetCollected}" />
	</h4>
	<br />
	<h3 style="color: #eb3636;"><c:out value="${error}" /></h3>
	<form:form modelAttribute="cause" class="form-horizontal" id="add-cause-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Donacion"
				name="budgetCollected" />
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<a href="javascript:history.go(-1)" class="btn btn-default">Volver</a>
				<button class="btn btn-default" type="submit">Donar</button>
			</div>
		</div>
	</form:form>


	
</petclinic:layout>
