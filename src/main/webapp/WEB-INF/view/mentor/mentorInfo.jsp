<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/style/mentorInfo.css">
</head>
<body>
	<div class="mti-wrapper">
	
	
		<div class="mentor_header">
			<h2>멘토링</h2>
		</div>
		<div class="bottom-line"></div>



		<div class="mti-wrapper2">
			<div class="mti-intro">
				<div class="mti-lh">${m.content}</div>
			</div>
			
			
			<div class="mti-wrapper3">
				<div class="mti-profile"> 
					<div class="mti-pro-img">
						<c:choose>
							<%--프로필 사진이 없으면 기본 프로필사진, 있으면 설정한 프로필 사진 --%>
							<c:when test="${profile!=null }"><img src="<%=request.getContextPath() %>/profile/${profile}" onerror="this.src='<%=request.getContextPath() %>/resource/image/profile.png'"></c:when>
							<c:otherwise><img src="<%=request.getContextPath() %>/resource/image/profile.png" width="110" height="90"></c:otherwise>
						</c:choose>
					</div>	
					<div class="mti-pro-name">MENTOR : ${m.mentor_Id}</div>
					<div>${m.intro}</div>	
				</div>
				
				<div>
					<form action="<%=request.getContextPath()%>/mentor/mentoringEntry" method="post">
						<input type="hidden" name="mentoringId" value="${m.mentoring_Id }">
						<button id="mentro-bung2" type="submit">멘토링신청</button>
					</form>	
				</div>
				<c:if test="${memid==m.mentor_Id }">
				<button id="mentro-bung2" onclick="location.href='mentorUpdate?mentoring_Id=${m.mentoring_Id}'" type="button">수정</button>
				<button id="mentro-bung2" onclick="location.href='mentorDeletePro?mentoring_Id=${m.mentoring_Id}'" type="button">삭제</button>
				</c:if>
			</div>		
		</div>
	</div>
</body>
</html>