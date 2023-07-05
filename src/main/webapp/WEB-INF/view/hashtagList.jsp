<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>해쉬 태그 별 사용내역</h1>
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
	<c:if test="${startPage>10}">
		<a href="${pageContext.request.contextPath}/hashtagList?currentPage=${startPage-10}">이전</a>
	</c:if>
	<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
		<c:if test="${i<=endPage}">
			<a href="${pageContext.request.contextPath}/hashtagList?currentPage=${i}">${i}페이지</a>
		</c:if>
	</c:forEach>
	<c:if test="${endPage<lastPage}">
		<a href="${pageContext.request.contextPath}/hashtagList?currentPage=${startPage+10}">다음</a>
	</c:if>
</body>
</html>