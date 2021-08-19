<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Summer Camp | Postal Code</title>
<spring:url value="/css/style.css" var="urlCss"/>
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<h1>Postal Code</h1>

	<form:form method="POST" action="summercamp" modelAttribute="postalCode">
		<table>
			<tbody>
				<tr>
					<td>Postal Code:</td>
					<td><form:input path="value" size="4" /></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="Apply" />
	</form:form>
</body>
</html>