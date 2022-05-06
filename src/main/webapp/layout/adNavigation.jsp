<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡNavigationㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>admin</title></head>
<link href="<%=request.getContextPath() %>/resource/style/admin/adNavigation.css" rel='stylesheet' type='text/css'/>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath() %>/js/hover.js"></script>
<sitemesh:write property='head'/>
<body>


<div class="navi">
	<!-- logo -->
	<div class="logo_bar">
		<img class="logo" src="<%=request.getContextPath() %>/resource/image/Wlogo.png">
	</div>
	
	<div id="tog1" class="bar">
		<div class="menu">회원관리</div>
		<ul class="submenu">
			<li><a href="<%=request.getContextPath()%>/admin/memberList">회원정보 관리</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/blackList">블랙리스트 관리</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/suspendedList">제제 회원 관리</a></li>
		</ul>
	</div>
	<div id="tog2" class="bar">
		<div class="menu">신고관리</div>
		<ul class="submenu2">
			<li><a href="<%=request.getContextPath()%>/admin/report">멘토링 신고내역</a></li>
			<li><a href="<%=request.getContextPath()%>/admin/report">스터디 신고내역</a></li>
		</ul>
	</div>
</div>
<sitemesh:write property='body'/>

</body>
</html>