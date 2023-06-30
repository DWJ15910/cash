<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>비밀번호 변경</h1>
	<form method="post" action="">
		<table>
			<tr>
				<th>기존비밀번호 입력</th>
				<td><input type="password" name="beforePw"></td>
			</tr>
			<tr>
				<th>변경할 비밀번호 입력</th>
				<td><input type="password" name="chPw1"></td>
			</tr>
			<tr>
				<th>변경할 비밀번호 재입력</th>
				<td><input type="password" name="chPw2"></td>
			</tr>
		</table>
		<button type="submit">비밀번호 변경</button>
	</form>
</body>
</html>