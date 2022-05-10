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
		<div class="title">제제 회원 등록 내역</div>
		<table class="table">
			<colgroup>
				<col width="13%"/>
				<col width="23%"/>
				<col width="18%"/>
				<col width="18%"/>
				<col width="18%"/>
				<col width="10%"/>
			</colgroup>
		
			<tr>
				<th>번호</th>
				<th>ID</th>
				<th>등록일자</th>
				<th>해제(예정)일자</th>
				<th>누적횟수</th>
				<th></th>
			</tr>	
			
			<c:forEach var="member" items="${suspendedList}">
			<tr>
			    <td>${listNum}</td>
			    <c:set var="listNum" value="${listNum-1}"/>
			    <td>${member.id}</td>
			    <td>${member.regDate}</td>
			    <td>${member.dueDate}</td>
			    <td>${member.accCnt}</td>
			    <td>
			    <c:if test="${member.status == 'Y' && now > member.dueDate}">
			    <button class="btn red" type="submit" onclick="updateStatus('${member.id}')">등록 해제</button>
			    </c:if></td>
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
<script>
function updateStatus(id) {
    location.href="<%=request.getContextPath()%>/admin/updateStatus?reportedID=" + id;
}
</script>
</body>
</html>