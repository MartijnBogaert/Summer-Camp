<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Summer Camp | Postal Code</title>
</head>
<body>
	<h1>Postal Code</h1>

	<form:form method="POST" action="summercamp" modelAttribute="postalCode">
		<table>
			<tr>Postal Code:</tr>
			<tr><form:input path="value" size="4" /></tr>
		</table>
		<input type="submit" value="OK" />
	</form:form>
</body>
</html>