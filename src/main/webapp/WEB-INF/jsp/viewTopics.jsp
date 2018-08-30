<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 align="center">Topic Details</h1>
				<jstl:if test="${requestScope.allTopic.size() > 0 }">
					<table align="center">
						<tr>
							
							<th>Topic ID</th>
							<th>Topic Name</th>
							<th>Topic Description</th>
						</tr>
						<jstl:forEach var="topic" items="${requestScope.allTopic}">
							<tr>
								<td>${topic.id}</td>
								<td>${topic.name}</td>
								<td>${topic.description}</td>
							</tr>
						</jstl:forEach>
					</table>
				</jstl:if>
				<jstl:if test="${requestScope.allTopic.size() == 0 }">
					<h4 align="center">No Topics Found!</h4>
				</jstl:if>
</body>
</html>