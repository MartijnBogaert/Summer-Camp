<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="403.title" var="title" />
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
	<h1>${title}</h1>

	<form method='POST' action='/logout'>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" value="${logOut}" />
	</form>
</body>
</html>