<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<h1>로그인</h1>
	<form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
		<sapn id="loginIdMsg"></sapn><br>
		<sapn id="loginPwMsg"></sapn>
		<table>
			<tr>
				<td>memberId</td>
				<td>
					<input type="text" name="memberId" id="loginId">
				</td>
			</tr>
			<tr>
				<td>memberPw</td>
				<td>
					<input type="password" name="memberPw" id="loginPw">
				</td>
			</tr>
		</table>
		<button type="button" id="loginBtn">로그인</button>
		<a href="${pageContext.request.contextPath}/addMember">회원가입</a>
	</form>
<script>
	$(document).ready(function(){
	// 로그인 클릭시 실행
		$('#loginBtn').click(function(){
			if($('#loginId').val().length == 0 || $('#loginPw').val().length == 0){
				if($('#loginId').val().length == 0) {
					console.log($('#loginId').val());
					$('#loginIdMsg').text('아이디를 입력해주세요');
				} else {
					$('#loginIdMsg').text('');
				}
			 
				if($('#loginPw').val().length == 0) {
					console.log($('#loginPw').val());
					$('#loginPwMsg').text('비밀번호를 입력해주세요');
				}else {
					$('#loginPwMsg').text('');
				}
				return;
			}
			$('#loginForm').submit();
		});
	});
</script>
</body>
</html>