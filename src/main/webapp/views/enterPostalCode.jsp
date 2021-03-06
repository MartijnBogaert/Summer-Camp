<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="general.apply" var="apply" />
<spring:message code="enterPostalCode.title" var="title" />
<spring:message code="general.logout" var="logOut" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp}| ${title}</title>
<spring:url value="/css/style.css" var="urlCss" />
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<c:if test="${not empty signedUp}">
		<div class="message">${signedUp}</div>
	</c:if>

	<c:if test="${not empty booked}">
		<div class="error message">${booked}</div>
	</c:if>

	<h1>${title}</h1>

	<form:form method="POST" action="/summercamp"
		modelAttribute="postalCode">
		<table>
			<tbody>
				<tr>
					<td>${title}:</td>
					<td><form:input path="value" size="4" /></td>
					<td><form:errors path="value" cssClass="error" /></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="${apply}" />
	</form:form>
	
	<form method='POST' action='/logout'>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" value="${logOut}" />
	</form>
</body>
</html>