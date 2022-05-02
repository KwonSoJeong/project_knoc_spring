<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡReportㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title></title></head>
<link href="<%=request.getContextPath() %>/resource/style/admin/admin.css" rel='stylesheet' type='text/css'/>

<body>
<div class="wrapper">

	<div class="white">
		<div class="flex">
			<div class="title">전체 회원 목록</div>
			<form action="">
				<input class="form-control form-content" type="text" placeholder="아이디로 검색" name="#">
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
			
			<c:forEach begin="1" end="12" varStatus="status">
			<tr>
			    <td>${status.count}</td>
			    <td>에이비씨12</td>
			    <td>홍길동</td>
			    <td>gildong@gmail.com</td>
			    <td>N</td>
			    <td><button class="btn1 green" type="submit">블랙리스트 등록</button></td>
			    <td><button class="btn red" type="submit">정보 삭제</button></td>
			</tr>
			</c:forEach>
			
		</table>
	
	
		<div class="container"  >
			<ul class="pagination justify-content-center"  >
			
				<li class='page-item <c:if test="${startPage <= bottomLine}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/suspendedList?pageNum=${startPage-bottomLine}">이전</a>
				</li>
		   
		   		<c:forEach var="i" begin="${startPage}" end="${endPage}">
				  <li class='page-item <c:if test="${i==pageInt}">  active </c:if>'>
				  	<a class="page-link" href="<%=request.getContextPath()%>/admin/suspendedList?pageNum=${i}"> ${i}</a>
				  </li>
				</c:forEach>
		
				<li class='page-item <c:if test="${endPage >= maxPage}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/suspendedList?pageNum=${startPage+bottomLine}">다음</a>
				</li>
			</ul> 
		</div>
		
		
	</div>
</div>

</body>
</html>