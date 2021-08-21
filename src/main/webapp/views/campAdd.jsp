<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="campAdd.title" var="title" />
<spring:message code="campAdd.name" var="name" />
<spring:message code="campAdd.code" var="code" />
<spring:message code="campAdd.signup" var="signUp" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp} | ${title} ${camp.manager.name}</title>
<spring:url value="/css/style.css" var="urlCss" />
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<h1>${title} ${camp.manager.name}</h1>
	
	<form:form method="POST" action="/summercamp/add/${camp.id}" modelAttribute="person">
		<table>
			<tbody>
				<tr>
					<td>${name}:</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>${code} 1:</td>
					<td><form:input path="code1" /></td>
					<td><form:errors path="code1" cssClass="error" /></td>
				</tr>
				<tr>
					<td>${code} 2:</td>
					<td><form:input path="code2" /></td>
					<td><form:errors path="code2" cssClass="error" /></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="${signUp}" />
	</form:form>
</body>
</html>