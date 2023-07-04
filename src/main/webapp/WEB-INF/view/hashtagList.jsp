<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>날짜</th>
			<th>분류</th>
			<th>금액</th>
			<th>메모</th>
			<th>해쉬태그</th>
		</tr>
		<c:forEach var="m" items="${hashList}">
			<tr>
				<th>${m.cashbookDate}</th>
				<th>${m.category}</th>
				<th>${m.price}</th>
				<th>${m.memo}</th>
				<th>#${m.word}</th>
			</tr>
		</c:forEach>
	</table>
</body>
</html>