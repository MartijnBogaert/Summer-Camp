<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="general.summercamp" var="summerCamp" />
<spring:message code="campsOverview.title" var="title" />
<spring:message code="campsOverview.manager" var="manager" />
<spring:message code="campsOverview.postalcode" var="postalCode" />
<spring:message code="campsOverview.max" var="max" />
<spring:message code="campsOverview.errors.nocamps" var="noCamps" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${summerCamp}| ${title}</title>
<spring:url value="/css/style.css" var="urlCss" />
<link rel="stylesheet" href="${urlCss}" type="text/css" />
</head>
<body>
	<h1>${summerCamp}${title}</h1>

	<c:choose>
		<c:when test="${camps != null}">
			<table>
				<thead>
					<tr>
						<th>${manager}</th>
						<th>${postalCode}</th>
						<th>${max}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${camps}" var="camp">
						<tr>
							<td>${camp.manager.name}</td>
							<td>${camp.postalCode}</td>
							<td>${camp.maxChildren}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<span class="error">${noCamps}</span>
		</c:otherwise>
	</c:choose>
</body>
</html>