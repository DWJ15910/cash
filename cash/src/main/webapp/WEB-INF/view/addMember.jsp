<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<form method="post" action="${pageContext.request.contextPath}/addMember">
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="memberId"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="memberPw"></td>
			</tr>
		</table>
		<button type="submit">회원가입 전송</button>
	</form>
</body>
</html>