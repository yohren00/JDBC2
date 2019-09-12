<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
    <%@ page import="java.util.*,com.len.web.jdbc.*" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>�ǥ͸��-���M�ǥ͸��</title>
<link type="text/css" rel="stylesheet" href="css/style.css" >
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>�ǥ͸��</h2>
		</div>
	</div>
	

	<div id="container">
		<div id="content">
			<h2>���M�ǥ͸��</h2>
		
		<form action="StudentControllerServlet" method="post">
			<input type="hidden" name="command" value="SEARCH" />
			�ǥͦW��: <input type="text" name="theSearchName" /> 
				<input type="submit" value="���M" class="add-student-button" />
			
			
			<table>
				<tr>
					<th>First Name</th>
					
					<th>Last Name</th>
					
					<th>Email</th>
					
					<th>action</th>
				</tr>
				
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
				
				<!--update�覡-->
				<c:url var="tempLink" value="StudentControllerServlet">
				
					<c:param name="command" value="LOAD"/>
					
					<c:param name="studentId" value="${tempStudent.id}"/>
					
				</c:url>
				
				<!--delete�覡-->
				<c:url var="tempLink2" value="StudentControllerServlet">
				
					<c:param name="command" value="DELETE"/>
					
					<c:param name="studentId" value="${tempStudent.id}"/>
					
				</c:url>
				
				
				
				<tr>
				
					<td>${tempStudent.firstName }</td>
					
					<td>${tempStudent.lastName }</td>
					
					<td>${tempStudent.email }</td>
					
					<td>
					
					<a href="${tempLink}">Update</a>
					|
					<a href="${tempLink2}" onclick="if(!(confirm('�O�_�T�w�n�R��?')))return false">Delete</a>
					
					</td>
				</tr>

				</c:forEach>

			</table>
</form>
		<p>
			<a href="StudentControllerServlet">��^�ؿ�</a>
		</p>
		</div>
	</div>
</body>
</html>