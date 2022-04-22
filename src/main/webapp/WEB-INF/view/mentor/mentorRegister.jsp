<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/mentorRegister.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="mti-wrapper">

		<div class="mentor_header">
			<h2>멘토 등록</h2>
		</div>
		<div class="bottom-line"></div>
		
		<div id="con" class="container" style="padding: 35px 0;;">
	
			<form action="<%=request.getContextPath() %>/mentor/mentorRegisterPro" method="post">
			<!-- <form name="f" action="<%=request.getContextPath()%>" method="post"> -->
	
				<div class="form-group">
					<label>멘토링 주제</label>
					<textarea class="form-control" rows="3" cols="40" placeholder="멘토링 주제를 작성해 주세요." name="title"></textarea>
				</div> 
				<br>
				
	            <div class="form-group">
					<label>멘토 소개글</label>
					<textarea class="form-control" rows="5" cols="40" placeholder="멘토에 대해 작성해 주세요." name="intro"></textarea>
				</div> 
				<br>
				
				<div class="form-group">
					<label>멘토링 내용</label>
					<textarea class="form-control" rows="15" cols="60" placeholder="멘토링 내용에 대해 상세하게 적어주세요"  name="content"></textarea>
				</div>
				
				<div id="center" >
					<button type="submit" onclick="location.href='<%=request.getContextPath()%>/mentor/mentorRegister'" style="border-radius: 5px;">등록하기</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>