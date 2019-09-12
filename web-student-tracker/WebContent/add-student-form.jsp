<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>學生資料-新增學生資料</title>
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
		<h3>新增學生資料</h3>

		<form action="StudentControllerServlet" method="post">
			<input type="hidden" name="command" value="ADD" />

			<table>
				<tbody>
					<tr>
						<td><label for="firs"> First Name:</label></td>
						<td><input id="firs" type="text" name="firstName" /></td>
					</tr>
					<tr>
						<td><label for="last">Last Name:</label></td>
						<td><input id="last" type="text" name="lastName" /></td>
					</tr>
					<tr>
						<td><label for="email">Email:</label></td>
						<td><input id="email" type="text" name="email" /></td>
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