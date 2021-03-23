<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
        <div class="col-md-12">
            <spring:url value="https://img.playbuzz.com/image/upload/ar_1.3333333333333333,c_crop/q_auto:good,f_auto,fl_lossy,w_640,c_limit,dpr_1/v1526036072/es9lmmyujeoi4vzntpf3.jpg" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" width="30%" src="${petsImage}"/>
        </div>
    </div>
</petclinic:layout>
