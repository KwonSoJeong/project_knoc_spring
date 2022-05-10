<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡReportㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title></title>
<link href="<%=request.getContextPath() %>/resource/style/admin/admin.css" rel='stylesheet' type='text/css'/>
</head>

<body>
<div class="wrapper">

	<div class="white">
		<div class="title">${subject} 신고 내역</div>
		<table class="table">
			<colgroup>
				<col width="9%"/>
				<col width="18%"/>
				<col width="22%"/>
				<col width="13%"/>
				<col width="28%"/>
				<col width="10%"/>
			</colgroup>
		
			<tr>
				<th>번호</th>
				<th>신고한 ID</th>
				<th>신고 게시물</th>
				<th>접수일자</th>
				<th>신고사유</th>
				<th></th>
			</tr>	
			
			<c:forEach var="report" items="${reportList}">
			<c:set var="reportID" value="${report.REPORT_ID}"/>
			<tr>
			    <td>${report.no}</td>
			    <td>${report.id}</td>
			    <c:if test="${fn:contains(reportID, 'mentoring')}">
			    <td><a href="<%=request.getContextPath()%>/mentor/mentorInfo?mentoring_Id=${reportID}">${report.title}</a></td>
			    </c:if>
			    <c:if test="${fn:contains(reportID, 'study')}">
                <td><a href="<%=request.getContextPath()%>/study/studyInfo?study_Id=${reportID}">${report.title}</a></td>
                </c:if>
			    <td>${report.regdate}</td>
			    <td>${report.reason}</td>
			    <td><button class="btn green" type="button" onclick="addSuspendedMember('${reportID}')">제재</button></td>
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
<script>
function addSuspendedMember(reportID) {
	location.href="<%=request.getContextPath()%>/admin/addSuspendedMember?report_id=" + reportID;
}
</script>
</body>
</html>