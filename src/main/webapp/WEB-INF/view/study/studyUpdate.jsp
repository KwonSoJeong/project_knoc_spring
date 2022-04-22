<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 글 쓰기</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/studyWrite.css" />
</head>
<body>
		<div class="layout-container" style="padding-top: 34px;">
	
		<div class="class_header"  >
			
			<div class="bottom-line">
				<h2 id="font">게시글 수정</h2> 
			</div>
		</div>
		<div class="wrapper">
			<div id="article-create" class="content" role="main">
				<div class="content-header">

					<!-- <div style="margin-top: 50px">
						<span style="font-size: 24px">새 글 쓰기</span>
						<hr style="border: 0; margin-top: 4px; height: 3px; background: #b8e6e1" />
					</div> -->

				</div>

				<div class="panel panel-default clearfix">
					<div class="panel-heading clearfix">
						<div class="avatar clearfix avatar-medium pull-left">
							<a href="/user/info/138252" class="avatar-photo">
							
							<c:choose>
							<c:when test="${leaderProfile!=null}"><img src="<%=request.getContextPath() %>/profile/${leaderProfile}" /></c:when>
							<c:otherwise><img src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" /></c:otherwise>
							</c:choose>
							
							</a>
							<div class="avatar-info">
								<a class="nickname" href="/user/info/138252" title="KNOC">${s.leader_Id}</a>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<form action="<%=request.getContextPath()%>/study/studyUpdatePro" method="post" id="article-form" class="article-form" role="form" onsubmit="return postForm()">
							<fieldset class="form">
								<input type="hidden" name="_csrf" value="ffedcba6-5eb9-48c8-8889-5f7ed435b5ba" />

								<div class="form-group has-feedback">
									<div>
										<input type="text" name="title" required="" value="${s.title }" placeholder="제목을 입력해 주세요." class="form-control" id="title" />
									</div>
								</div>

								<div class="form-group has-feedback">
									<textarea name="content" id="summernote" rows="20" class="form-control input-block-level">${s.content }</textarea>
								</div>
								<input type="hidden" name="textType" value="HTML" id="textType" />
								<input type="hidden" name="study_Id" value="${s.study_Id }">
								<div class="nav" role="navigation">
									<fieldset class="buttons">
										<a href="<%=request.getContextPath() %>/study/studyInfo" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')">취소</a> <input type="submit" name="create" class="create btn btn-success btn-wide pull-right" style="background-color: #37d3c0; border-color: #37d3c0;" action="create" value="수정" id="create" />
									</fieldset>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>