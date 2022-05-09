<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡReportㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title></title>
<link href="<%=request.getContextPath() %>/resource/style/admin/admin.css" rel='stylesheet' type='text/css'/>
</head>


<body>
<div class="wrapper">

	<div class="white">
		<div class="flex">
			<div class="title">전체 회원 목록</div>
			<form action="<%=request.getContextPath()%>/admin/memberList">
				<input class="form-control form-content" type="text" placeholder="아이디로 검색" name="keyword">
			</form>
		</div>
		
		<table class="table">
			<colgroup>
				<col width="10%"/>
				<col width="20%"/>
				<col width="10%"/>
				<col width="24%"/>
				<col width="10%"/>
				<col width="16%"/>
				<col width="10%"/>
			</colgroup>
		
			<tr>
				<th>번호</th>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>BlackList</th>
				<th></th>
				<th></th>
			</tr>	
			
			<c:forEach var="member" items="${memberList}">
			<tr>
			    <td>${memberNum}</td>
			    <c:set var="memberNum" value="${memberNum-1 }"/>
			    <td>${member.id}</td>
			    <td>${member.name}</td>
			    <td>${member.email}</td>
			    <td>${member.blacklist}</td>
			    <td><button class="btn1 green" type="button" onclick="addBlackList('${member.id}')">블랙리스트 등록</button></td>
			    <td><button class="btn red" type="button" onclick="deleteMember('${member.id}')">정보 삭제</button></td>
			</tr>
			</c:forEach>
			
		</table>
	
	
		<div class="container"  >
			<ul class="pagination justify-content-center"  >
			
				<li class='page-item <c:if test="${startPage <= bottomLine}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/memberList?pageNum=${startPage-bottomLine}">이전</a>
				</li>
		   
		   		<c:forEach var="i" begin="${startPage}" end="${endPage}">
				  <li class='page-item <c:if test="${i==pageInt}">  active </c:if>'>
				  	<a class="page-link" href="<%=request.getContextPath()%>/admin/memberList?pageNum=${i}"> ${i}</a>
				  </li>
				</c:forEach>
		
				<li class='page-item <c:if test="${endPage >= maxPage}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/memberList?pageNum=${startPage+bottomLine}">다음</a>
				</li>
			</ul> 
		</div>
		
		
	</div>
</div>
<script>
function addBlackList(id) {
	location.href="<%=request.getContextPath()%>/admin/addBlackList?member_id=" + id;
}

function deleteMember(id) {
    location.href="<%=request.getContextPath()%>/admin/deleteMember?member_id=" + id;
}
</script>
</body>
</html>