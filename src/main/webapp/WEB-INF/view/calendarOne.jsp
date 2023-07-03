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
			<th>사용자</th>
			<th>분류</th>
			<th>금액</th>
			<th>사용날짜</th>
			<th>메모</th>
		</tr>
		<c:forEach var="c" items="${list}">
        <tr>
            <td>${c.memberId}</td>
            <td>${c.category}</td>
            <td>${c.price}</td>
            <td>${c.cashbookDate}</td>
            <td>${c.memo}</td>
        </tr>
 	  </c:forEach>
	</table>
</body>
</html>