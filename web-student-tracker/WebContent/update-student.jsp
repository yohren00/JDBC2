<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>學生資料-修改學生資料</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>學生資料</h2>
		</div>
	</div>

	<div id="container">
		<h3>修改學生資料</h3>

		<form action="StudentControllerServlet" method="post">
		
			<input type="hidden" name="command" value="UPDATE" />
			
			<input type="hidden" name="studentId" value="${STUDENT_LOAD.id}" />
			
			<table>
				<tbody>

					<tr>
						<td><label for="first"> First Name:</label></td>
						<td><input id="first" type="text" name="firstName"
									value="${STUDENT_LOAD.firstName}" /></td>
					</tr>

					<tr>
						<td><label for="last"> Last Name:</label></td>
						<td><input id="last" type="text" name="lastName" 
									value="${STUDENT_LOAD.lastName}" /></td>
					</tr>

					<tr>
						<td><label for="email"> Email:</label></td>
						<td><input id="email" type="text" name="email" 
									value="${STUDENT_LOAD.email}"/></td>
									
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="確認" class="save" /></td>
					</tr>

				</tbody>
			</table>
		</form>
		<p>
			<a href="StudentControllerServlet">返回目錄</a>
		</p>
	</div>

</body>
</html>