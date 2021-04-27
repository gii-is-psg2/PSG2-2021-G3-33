<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="AdoptionApplication">
	<jsp:body>
		<h2>
			<fmt:message key="newPet"/>
		</h2>
		
		 <form:form modelAttribute="application"
                   class="form-horizontal">
                <input type="hidden" name="id" value="${application.id}"/>
                <input type="hidden" name="owner" value="${application.owner.id}"/>
                <input type="hidden" name="pet" value="${application.pet.id}"/>
                <div class="form-group has-feedback">
                	<div class="form-group">
                		<label class="col-sm-2 control-label"><fmt:message key="owner"/></label>
                    	<div class="col-sm-10">
                        	<c:out value="${application.pet.owner.firstName} ${application.pet.owner.lastName}"/>
                    	</div>
                	</div>
	               	<div class="form-group">
                		<label class="col-sm-2 control-label"><fmt:message key="name"/></label>
                    	<div class="col-sm-10">
                        	<c:out value="${application.pet.name}"/>
                    	</div>
                	</div>
                	<div class="form-group">
                		<label class="col-sm-2 control-label"><fmt:message key="birthDate"/></label>
                    	<div class="col-sm-10">
                        	<c:out value="${application.pet.birthDate}"/>
                    	</div>
                	</div>
                	<div class="form-group">
                		<label class="col-sm-2 control-label"><fmt:message key="type"/></label>
                    	<div class="col-sm-10">
                        	<c:out value="${application.pet.type}"/>
                    	</div>
                	</div>
                	<fmt:message var="description" key="description"/>
                	<petclinic:inputField label="${description}" name="description"/>
                	
                </div>
                <div class="form-group">
                	<div class="col-sm-offset-2 col-sm-10">
                		<button class="btn btn-default" type="submit"><fmt:message key="sendApplication"/></button>
                	</div>
                </div>
         </form:form>
	</jsp:body>
</petclinic:layout>