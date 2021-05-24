<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<petclinic:layout pageName="webStatus">

    <c:choose>
    <c:when test="${health == 'UP'}">
      <h2>
     	<button type="button" class="btn btn-success">
        <fmt:message key="healthUp"/>
		</button>
    <fmt:message key="messageHealthUP"/></h2>
    </c:when>
    <c:otherwise>
      <h2>
    	<button type="button" class="btn btn-danger">
        <fmt:message key="healthDown"/>
      </button>
      <fmt:message key="messageHealthUP"/></h2>
    </c:otherwise>
    </c:choose>
     
</petclinic:layout>