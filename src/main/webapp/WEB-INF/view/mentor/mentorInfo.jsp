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

<!-- The Modal -->
<div class="modal" id="myModal" style="z-index: 1500;">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">신고</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <form action="<%=request.getContextPath()%>/mentor/report">
      <div class="modal-body">
      <div class="form-group">
        <label>신고 사유</label>
        <textarea name="reason" class="form-control" style="height: 180px;" placeholder="신고 사유를 입력해 주세요"></textarea>
        <input type="hidden" name="id" value="${memid }">
        <input type="hidden" name="report_Id" value="${mt.mentoring_Id}">
        </div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
      <button type="submit" class="btn btn-danger">신고하기</button>
        <button type="button" class="btn" style="background-color: #37d3c0; color: white;" data-dismiss="modal">닫기</button>
      </div>
		</form>
    </div>
  </div>
</div>

	<div class="mti-wrapper">
	
	
		<div class="mentor_header">
			<h2>멘토링</h2>
		</div>
		<div class="bottom-line"></div>



		<div class="mti-wrapper2">
			<div class="mti-intro">
				<div class="mti-lh">${mt.content}</div>
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
					<div class="mti-pro-name">MENTOR : ${mt.mentor_Id}</div>
					<div>${mt.intro}</div>	
				</div>
				
				<div>
					<form action="<%=request.getContextPath()%>/mentor/mentoringEntry" method="post">
						<input type="hidden" name="mentoring_Id" value="${mt.mentoring_Id }">
						<button id="mentro-bung2" type="submit">멘토링신청</button>
					</form>	
				</div>
				<c:if test="${memid==mt.mentor_Id }">
				<button id="mentro-bung2" onclick="location.href='mentorUpdate?mentoring_Id=${mt.mentoring_Id}'" type="button">수정</button>
				<button id="mentro-bung2" onclick="location.href='mentorDeletePro?mentoring_Id=${mt.mentoring_Id}'" type="button">삭제</button>
				</c:if>
				<button id="mentro-bung2" data-toggle="modal" data-target="#myModal" type="button">신고</button>
			</div>		
		</div>
	</div>
</body>
</html>