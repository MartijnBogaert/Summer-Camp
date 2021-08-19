<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="campsOverview.title" var="title" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp} | ${title}</title>
</head>
<body>
	<h1>${summerCamp} ${title}</h1>
</body>
</html>