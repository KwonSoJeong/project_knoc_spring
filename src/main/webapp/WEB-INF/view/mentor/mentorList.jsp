<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/mentorlist.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" integrity="sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==" crossorigin="anonymous" referrerpolicy="no-referrer" />

</head>
<body>
	<div class="ml-wrapper">
		<div class="mentor_header">
			<h2>멘토링</h2>
			<form class="ml-form" action="<%=request.getContextPath()%>/mentor/mentorList">
				<input class="form-control form-content" type="text" placeholder="검색하기" name="keyword">
				<button id="ml-btn" type="submit">검색</button>
			</form>	
		</div>
		<div class="bottom-line"></div>
		
		
		<div class="dot-wrapper">
			<c:forEach var="m" items="${mt}" varStatus="status">
				<div class="mentor-dot">
					<div class="ml-fx">
						<div class="ml-fx2">
							<%-- 프로필사진이 없으면 기본 프로필사진으로 설정 --%>
							<c:if test="${profile[status.index]!=null }"><img src="<%=request.getContextPath() %>/profile/${profile[status.index]}" width="100" height="80" id="pic" onerror="this.src='<%=request.getContextPath() %>/resource/image/profile.png'"></c:if>
							<c:if test="${profile[status.index]==null }"><img src="<%=request.getContextPath() %>/resource/image/profile.png" width="100" height="80" id="pic"></c:if>
							
							<div class="ml-topic">${m.title}</div>
							<div class="ml-pro-name">by. ${m.mentor_Id}</div>	
							
							<c:if test="${ratingavg[status.index]>=0.5}">
							<div class="rating">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]==5}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<5 && ratingavg[status.index]>=4.5}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<4.5 && ratingavg[status.index]>=4}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<4 && ratingavg[status.index]>=3.5}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<3.5 && ratingavg[status.index]>=3}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<3 && ratingavg[status.index]>=2.5}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<2.5 && ratingavg[status.index]>=2}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<2 && ratingavg[status.index]>=1.5}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<1.5 && ratingavg[status.index]>=1}">checked</c:if> onclick="return(false);">
								<input type="radio" name="rating${status.count}" <c:if test="${ratingavg[status.index]<1}">checked</c:if> onclick="return(false);">
							</div>
							</c:if>
							
						</div>
						<div class="ml-btn">
							<form action="<%=request.getContextPath() %>/mentor/mentorInfo?mentoring_Id=${m.mentoring_Id}" method="post">
								<button class="ml-bung2" type="submit">내용보기</button>
							</form>	
						</div>						
					</div>	
					
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>