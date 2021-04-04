<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="vets">
	<h2>Modificar Veterinario</h2>
	<form:form modelAttribute="vet" class="form-horizontal"
		id="modify-vet-form">
		<div class="form-group has-feedback">
			<fmt:message var="firstName" key="firstName" />
			<fmt:message var="lastName" key="lastName" />
			<petclinic:inputField label="${firstName}" name="firstName" />
			<petclinic:inputField label="${lastName}" name="lastName" />
		</div>

		<div class="control-group">
			<c:set var="setSpecialties" value="${vet.specialties}" />
			<c:forEach items="${specialtiesTypes}" var="specialty">
				<c:if test="${fn:contains(setSpecialties, specialty)}">
					<c:set var="defecto" value="true" />
				</c:if>

				<c:choose>
					<c:when test="${defecto eq true}">
						<input type="checkbox" name="specialties" value="${specialty.id}"
							checked="checked" />
						<c:out value="${specialty.name}" />
						<br>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="specialties" value="${specialty.id}" />
						<c:out value="${specialty.name}" />
						<br>
					</c:otherwise>
				</c:choose>
				<c:set var="defecto" value="false" />
			</c:forEach>
		</div>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit">Actualizar
					Veterinario</button>
			</div>
		</div>
	</form:form>
</petclinic:layout>
