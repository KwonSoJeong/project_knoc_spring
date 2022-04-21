<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡmyPageㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/myPage.css">
</head>
<body>

	<div class="mp-wrapper">
		<div class="mp-fsfc">마이페이지</div>
		<div class="mp-bor-bot"></div>


	
	
		<div class="mp-margin">
			<div class="mp-lcontainer">
			
				<div class="mp-thumbnail">
					<div class="mp-cdtitle">나의 프로필</div>
					<div class="mp-profile2">
						<div class="mp-profimg"> <img src="<%=request.getContextPath() %>/profile/${member.profile}" onerror="this.src='<%=request.getContextPath() %>/resource/image/profile.png'"> </div>
						<div class="mp-procomt">${member.id }님 <br> 안녕하세요!</div>
					</div>
					<div class="mp-just">
						<a class="mp-bottom_right" href="<%=request.getContextPath() %>/member/memberDelete">회원탈퇴</a>	
						|
						<a class="mp-bottom_right" href="<%=request.getContextPath() %>/member/memberUpdate">프로필 수정하기</a>					
					</div>
				</div>
				<span></span>
				<div class="mp-thumbnail">
					<div class="mp-cdtitle">나의 클래스</div>
					<div class="mp-cljust">
						<div class="mp-clbox">
							<ul>
							<c:forEach var="c" items="${classList}">
							     <c:if test="${c.type==1}">
							     <li><a href="<%=request.getContextPath()%>/classes/classUpdate?class_id=${c.member_study_id}">${c.title}</a></li>
							     </c:if>
							</c:forEach>
							</ul>
						</div>	
						<div class="mp-justf">
							<div class="cur" onclick="#">관리</div>
						</div>					
					</div>				
				</div>
				<span></span>
				<div class="mp-thumbnail">
					<div class="mp-cdtitle">수강중인 클래스</div>	
					<div class="mp-cljust">
						<div class="mp-clbox">
							<ul>
							<c:forEach var="c" items="${classList}">
                                <c:if test="${c.type==2}">
                                <li><a href="<%=request.getContextPath()%>/classes/classContent?class_id=${c.member_study_id}">${c.title}</a></li>
                                 </c:if>
                            </c:forEach>
							</ul>
						</div>	
						<div class="mp-justf">
							<div class="cur" onclick="#">바로가기</div>
						</div>					
					</div>									
				</div>
			</div>
			
			
			<div class="mp"></div>
			
			
			<div class="mp-lcontainer">

				<div class="mp-thumbnail">
					<div class="mp-cdtitle">관심 클래스</div>		
					<div class="mp-cljust">
						<div class="mp-clbox">
							<ul>
							<c:forEach var="w" items="${wishList}">
                                <li><a href="<%=request.getContextPath()%>/classes/classInfo?class_id=${w.CLASS_ID}">${w.TITLE}</a></li>
                            </c:forEach> 
							</ul>
						</div>					
					</div>		
				</div>
				<span></span>
				<div class="mp-thumbnail">
					<div class="mp-cdtitle">멘토링 현황</div>	
					<div class="mp-cljust">
						<div class="mp-clbox">
							<ul>
							<c:forEach var="m" items="${mentoringList}">
                            <li><a href="<%=request.getContextPath()%>/mentor/mentorInfo?mentoring_Id=${m.member_study_id}">${m.title}</a></li>
                            </c:forEach>
							</ul>
						</div>					
					</div>			
				</div>
				<span></span>
				<div class="mp-thumbnail">
					<div class="mp-cdtitle">스터디 현황</div>			
					<div class="mp-cljust">
						<div class="mp-clbox">
							<ul> 
							<c:forEach var="s" items="${studyList}">
                            <li><a href="<%=request.getContextPath()%>/study/studyInfo?study_Id=${s.member_study_id}">${s.title}</a></li>
                            </c:forEach>
							</ul>
						</div>					
					</div>	
				</div>


			</div>
		</div>
	</div>
</body>
</html>