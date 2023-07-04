<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>캐쉬북 추가</h1>
	<form method="post" action="${pageContext.request.contextPath}/addCashbook">
		<h1>${targetYear}-${targetMonth+1}-${targetDate}</h1>
		<input type="hidden" name="date" value="${targetYear}-${targetMonth+1}-${targetDate}">
		<table>
			<tr>
				<th>분류</th>
				<td>
					<select name ="category">
						<option value="지출">지출</option>
						<option value="수입">수입</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>금액</th>
				<td><input type="number" name="price"></td>
			</tr>
			<tr>
				<th>메모</th>
				<td>
					<input type ="text" name="memo">
				</td>
			</tr>
		</table>
		<button type="submit">추가하기</button>
	</form>
</body>
</html>