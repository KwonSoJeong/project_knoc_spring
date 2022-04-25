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
		<div class="title">멘토링 신고 내역</div>
		<table class="table">
			<colgroup>
				<col width="5%"/>
				<col width="15%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="10%"/>
				<col width="25%"/>
				<col width="10%"/>
			</colgroup>
		
			<tr>
				<th>번호</th>
				<th>신고한 ID</th>
				<th>신고 게시물</th>
				<th>신고된 ID</th>
				<th>접수일자</th>
				<th>신고사유</th>
				<th></th>
			</tr>	
			
			<c:forEach begin="1" end="12" varStatus="status">
			<tr>
			    <td>${status.count}</td>
			    <td>신고자</td>
			    <td><a href="#">게시물 제목</a></td>
			    <td>abcd123</td>
			    <td>2022.03.11</td>
			    <td>맘에안들어서</td>
			    <td><button class="btn green" type="submit">제재</button></td>
			</tr>
			</c:forEach>
			
		</table>
	
	
		<div class="container"  >
			<ul class="pagination justify-content-center"  >
			
				<li class='page-item <c:if test="${startPage <= bottomLine}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/report?pageNum=${startPage-bottomLine}">이전</a>
				</li>
		   
		   		<c:forEach var="i" begin="${startPage}" end="${endPage}">
				  <li class='page-item <c:if test="${i==pageInt}">  active </c:if>'>
				  	<a class="page-link" href="<%=request.getContextPath()%>/admin/report?pageNum=${i}"> ${i}</a>
				  </li>
				</c:forEach>
		
				<li class='page-item <c:if test="${endPage >= maxPage}"> disabled </c:if>'>
					<a class="page-link" href="<%=request.getContextPath()%>/admin/report?pageNum=${startPage+bottomLine}">다음</a>
				</li>
			</ul> 
		</div>
		
		
	</div>
</div>

</body>
</html>