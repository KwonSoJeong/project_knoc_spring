<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/mentorManage.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" integrity="sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<title>Insert title here</title>

<script>
$(document).on('click','#msiId', function () {
	var hidden = document.getElementById("mentoring_Id");
    hidden.value = $(this).attr("value")
});
</script>

</head>
<body>
	<div class="mmg-wrapper">
	
		<div class="mmg_header"><h2>멘토링 관리</h2></div>
		<div class="bottom-line"></div>

		<div class="mp-margin">
		
			<div class="green_box">
				<div class="title">진행중인 멘토링</div>
				<table class="table">
					<colgroup>
						<col width="70%"/>
						<col width="15%"/>
						<col width="15%"/>
					</colgroup>
					<c:forEach items="${mentoringList }" var="m" varStatus="status">
					<c:if test="${m.type==1 }">
					<tr>
					    <td id="align"><a href="<%=request.getContextPath()%>/mentor/mentorInfo?mentoring_Id=${m.member_study_id}">${m.title }</a></td>
					    <td><button class="btn green" onclick="location.href='mentorUpdate?mentoring_Id=${m.member_study_id}'" type="button">수정하기</button></td>
					    <td><button class="btn red" onclick="location.href='mentorDeletePro?mentoring_Id=${m.member_study_id}'" type="button">삭제하기</button></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
			</div>
			
			<div class="mp"></div>
			
			<div class="green_box">
				<div class="title">참가중인 멘토링</div>
				<table class="table">
					<colgroup>
						<col width="80%"/>
						<col width="20%"/>
					</colgroup>
					
					<c:forEach items="${mentoringList }" var="m" varStatus="status">
					<c:if test="${m.type==2 }">
					<tr>
					    <td id="align"><a href="<%=request.getContextPath()%>/mentor/mentorInfo?mentoring_Id=${m.member_study_id}">${m.title }</a></td>
					    <td><button id="msiId" value="${m.member_study_id}" class="btn red" type="button" data-toggle="modal" data-target="#myModal">종료하기</button></td>
					</tr>
					</c:if>
					</c:forEach>
				</table>
			</div>
			
		</div>
	</div>
	
	
	<!-- The Modal -->
	<div class="modal" id="myModal" style="z-index: 1500;">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">멘토링 평가하기</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
		  <!-- Modal body -->
		  <form action="<%=request.getContextPath()%>/mentor/rating">
		      <div class="modal-body">
				<div class="rating">
					<input type="radio" name="rating" value="5">
					<input type="radio" name="rating" value="4.5">
					<input type="radio" name="rating" value="4">
					<input type="radio" name="rating" value="3.5">
					<input type="radio" name="rating" value="3">
					<input type="radio" name="rating" value="2.5">
					<input type="radio" name="rating" value="2">
					<input type="radio" name="rating" value="1.5">
					<input type="radio" name="rating" value="1">
					<input type="radio" name="rating" value="0.5">
					
			        <input id="mentoring_Id" type="hidden" name="mentoring_Id" value="${m.member_study_id}">
				</div>
		      </div>
		
			  <!-- Modal footer -->
		      <div class="modal-footer">
		      	<button type="submit" class="btn" style="background-color: #37d3c0; color: white;">별점등록</button>
		      </div>
		  </form>
	    </div>
	  </div>
	</div>
	
</body>
</html>