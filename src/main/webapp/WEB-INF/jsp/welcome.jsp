<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->

<petclinic:layout pageName="home">
	<h2 style="text-align: center;">
		<fmt:message key="welcome" />
	</h2>
	<spring:url
		value="https://img.playbuzz.com/image/upload/ar_1.3333333333333333,c_crop/q_auto:good,f_auto,fl_lossy,w_640,c_limit,dpr_1/v1526036072/es9lmmyujeoi4vzntpf3.jpg"
		htmlEscape="true" var="petsImage" />
	<div style="text-align: center;" class="centerthis">
		<div class="flex-container">
			<div class="flex-item">
				<img style="align: center;" class="imagencita" width="30%"
					src="${petsImage}" />
			</div>
		</div>
	</div>
</petclinic:layout>
