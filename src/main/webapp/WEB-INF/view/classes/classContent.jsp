<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡclassContentㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="<%=request.getContextPath() %>/js/click.js"></script>
<script type="text/javascript" src="../../js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/classContent.css">
</head>
<body>

	<div class="cc-wrapper">
		<div class="cc-just">
			<div class="cc-fsfc">${content.title}</div>
			<div class="cc-fsfc1">≡</div>
		</div>
		<div class="cc-bor-bot"></div>
	
		<div class="cc-justend">
			<div class="cc-content">
				<div id="vimeoWrap">
					<iframe frameborder="0" cols="" rows="" title="" src="<%=request.getContextPath() %>/contentfile/${content.file1}" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
				</div>
			</div>
			<!-- 컨텐츠 부분은 현재 테스트 용으로 class_Content 객체 toString 메소드 실행 -->
			<div class="cc-index">
				<div>목차</div>
				<ul id="cc-subnav">
				    <c:forEach var = "c" items = "${contentList }">
                    <li><a href="<%=request.getContextPath() %>/classes/classContent?content_id=${c.content_Id}">${contentNo}차시 | ${c.title}</a></li>
                    <c:set var= "contentNo" value="${contentNo+1}" ></c:set>
                    </c:forEach>
                    <!--  
					<li><a href="#">2. 봉지 뜯기</a></li>
					<li><a href="#">3. 과자 꺼내기</a></li>
					<li><a href="#">4. 과자 관찰하기</a></li>
					<li><a href="#">5. 입에 넣기</a></li>
					<li><a href="#">6. 씹기</a></li>
					<li><a href="#">7. 삼키기</a></li>
					-->
				</ul>
			</div>
	 	</div>
	</div>
                      
</body>
</html>