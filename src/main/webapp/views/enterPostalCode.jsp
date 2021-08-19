<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="general.apply" var="apply" />
<spring:message code="enterPostalCode.title" var="title" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp} | ${title}</title>
<spring:url value="/css/style.css" var="urlCss" />
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<h1>${title}</h1>

	<form:form method="POST" action="summercamp" modelAttribute="postalCode">
		<table>
			<tbody>
				<tr>
					<td>${title}:</td>
					<td><form:input path="value" size="4" /></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value=${apply} />
	</form:form>
</body>
</html>