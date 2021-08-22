<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="login.title" var="title" />
<spring:message code="login.subtitle" var="subtitle" />
<spring:message code="login.username" var="username" />
<spring:message code="login.password" var="password" />
<spring:message code="login.login" var="logIn" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp} | ${title}</title>
<spring:url value="/css/style.css" var="urlCss" />
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<h1>${summerCamp} ${title}</h1>
	<h3>${subtitle}</h3>

	<c:if test="${not empty invalid}">
		<div class="error">${invalid}</div>
	</c:if>
	<c:if test="${not empty loggedOut}">
		<div class="msg">${loggedOut}</div>
	</c:if>

	<form method='POST' action='/login' >
		<table>
			<tr>
				<td>${username}:</td>
				<td><input type='text' name='username' /></td>
			</tr>
			<tr>
				<td>${password}:</td>
				<td><input type='password' name='password' /></td>
			</tr>
		</table>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" value="${logIn}" />
	</form>
</body>
</html>