<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>member One</h1>
	<table>
		<tr>
			<th>아이디</th>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<!-- ${member.memberPw} -->
				****
			</td>
		</tr>
	</table>
	<a href="${pageContext.request.contextPath}/modifyMember">비밀번호변경</a>	
	<a href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>	
</body>
</html>