<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>
	<br>test
	<br>test
	<br>test
	<br>test
	<br>test
	<br>test
	<br>test
	<br> 읽지 않은 알람 수 : ${unreadNoti }
	<br> 알람 리스트 :
	<br>
	<c:forEach var="noti" items="${notiList }">
		${noti.no }&nbsp;${noti.noti_Content }&nbsp;${noti.readChk }&nbsp;${noti.noti_Date }&nbsp;
		<c:if test="${noti.type==1 }"> <!-- type==1 버튼보임  type==0 버튼안보임 -->
		<a href="<%=request.getContextPath()%>/noti/accept?no=${noti.no}">수락</a> &nbsp; 
		<a href="<%=request.getContextPath()%>/noti/cancel?no=${noti.no}">거절</a>
		</c:if>
		<br>
	</c:forEach>
	<br>
	<a href="<%=request.getContextPath()%>/noti/allReadChk">allReadChk</a>
</body>
</html>