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
		<div class="title">블랙리스트 목록</div>
		<table class="table">
			<colgroup>
				<col width="10%"/>
				<col width="15%"/>
				<col width="15%"/>
				<col width="28%"/>
				<col width="15%"/>
			</colgroup>
		
			<tr>
				<th>번호</th>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th></th>
			</tr>	
			
			<c:forEach var="member" items="${blackList}">
			<tr>
			    <td>0</td>
			    <td>${member.id}</td>
                <td>${member.name}</td>
                <td>${member.email}</td>
			    <td><button class="btn red" type="submit">등록 해제</button></td>
			</tr>
			</c:forEach>
			
		</table>
	
	
		<div class="container"  >
			<ul class="pagination justify-content-center"  >
			
				<li class='page-item <c:if test="${startPage <= bottomLine}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/blackList?pageNum=${startPage-bottomLine}">이전</a>
				</li>
		   
		   		<c:forEach var="i" begin="${startPage}" end="${endPage}">
				  <li class='page-item <c:if test="${i==pageInt}">  active </c:if>'>
				  	<a class="page-link" href="<%=request.getContextPath()%>/admin/blackList?pageNum=${i}"> ${i}</a>
				  </li>
				</c:forEach>
		
				<li class='page-item <c:if test="${endPage >= maxPage}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/blackList?pageNum=${startPage+bottomLine}">다음</a>
				</li>
			</ul> 
		</div>
		
		
	</div>
</div>

</body>
</html>