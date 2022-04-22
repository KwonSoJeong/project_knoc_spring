<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모델2 스터디 구합니다</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/studyInfo.css" />
</head>
<body>
	<div class="layout-container" style="padding-top: 34px;">

		<div class="class_header">

			<div class="bottom-line">
				<h2 id="font">스터디</h2>
			</div>
		</div>
		<div class="wrapper">
			<div id="article" class="content" role="main">
				<div class="nav" role="navigation">

					<!-- <div style="margin-top: 50px">
						<span style="font-size: 24px">스터디</span>
						<hr style="border: 0; margin-top: 4px; height: 3px; background: #b8e6e1" />
					</div> -->

				</div>

				<div class="panel panel-default clearfix fa-">
					<div class="panel-heading clearfix">
						<div class="avatar clearfix avatar-medium pull-left">
							<a href="#" class="avatar-photo"> <c:choose>
									<c:when test="${leaderProfile!=null}">
										<img src="<%=request.getContextPath() %>/profile/${leaderProfile}" />
									</c:when>
									<c:otherwise>
										<img src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" />
									</c:otherwise>
								</c:choose>
							</a>
							<div class="avatar-info">
								<a class="nickname" href="#" title="KNOC">${s. leader_Id}</a>
								<div class="date-created">
									<span class="timeago" title="2022-03-02 14:33:59">${s.regDate }</span>
								</div>
							</div>
						</div>
					</div>
					<div class="content-container clearfix">
						<div id="content-body" class="panel-body content-body pull-left">
							<form name="f" action="<%=request.getContextPath()%>/study/studyEntry" method="post">
								<div class="content-tags">
									<button type="submit" onclick="javascript: f.action='<%=request.getContextPath() %>/study/studyChangeProcess';" style="border: none;" class='<c:if test="${s.process==1 }">list-group-item-text item-tag label label-info'>
										<i class="fa fa-comments"></i>모집중
										</c:if>
										<c:if test="${s.process==2 }">list-group-item-text item-tag label label-gray'>모집완료</c:if>
									</button>
									<input type="hidden" name="study_Id" value="${s.study_Id }">

									<c:if test="${memid!=s.leader_Id }">
										<button type="submit" style="margin-left: 10px; display: inline; background-color: #ffa94d; border-color: #ffa94d; padding: 0.2em 0.6em 0.3em; font-size: 75%; font-weight: bold; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: 0.25em;">참가신청</button>
									</c:if>

									<c:if test="${memid==s.leader_Id }">
										<button type="button" onclick="location.href='studyUpdate?study_Id=${s.study_Id}'" style="margin-left: 10px; display: inline; background-color: #4dabf7; border-color: #4dabf7; padding: 0.2em 0.6em 0.3em; font-size: 75%; font-weight: bold; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: 0.25em;">수정</button>
										<button type="button" onclick="location.href='stydyDeletePro?study_Id=${s.study_Id}'" style="margin-left: 10px; display: inline; background-color: #ced4da; border-color: #ced4da; padding: 0.2em 0.6em 0.3em; font-size: 75%; font-weight: bold; line-height: 1; color: #fff; text-align: center; white-space: nowrap; vertical-align: baseline; border-radius: 0.25em;">삭제</button>
									</c:if>

									<h2 class="panel-title">${s.title }</h2>

									<hr />
									<article class="content-text" itemprop="articleBody">
										<p>
											${s.content }<br />
										</p>
									</article>
								</div>
							</form>
							<div class="content-function-cog share-btn-wrapper"></div>
						</div>
					</div>

					<div class="panel panel-default clearfix">
						<!-- List group -->
						<ul class="list-group">
							<li id="note-title" class="list-group-item note-title">
								<h3 class="panel-title">
									댓글 <span id="note-count">${count }</span>
								</h3>
							</li>

							<c:forEach items="${commentList }" var="c" varStatus="status">
								<li class="list-group-item note-item clearfix" id="note-1570494">
									<form action="/content/update/1570494" method="post" data-id="1570494" class="note-update-form">
										<input type="hidden" name="_method" value="PUT" id="_method" />
										<div class="content-body panel-body pull-left">
											<div class="avatar clearfix avatar-medium">
												<c:choose>
													<c:when test="${commentProfileList[status.index]!=null}">
														<span class="avatar-photo"><img src="<%=request.getContextPath() %>/profile/${commentProfileList[status.index] }" /></span>
													</c:when>
													<c:otherwise>
														<span class="avatar-photo"><img src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" /></span>
													</c:otherwise>
												</c:choose>
												<div class="avatar-info">
													<span class="nickname" title="김코딩">${c.writer }</span>
													<div class="date-created">
														<span class="timeago" title="2022-03-09T13:44:19">${c.regDate }</span>
													</div>
												</div>
											</div>
											<fieldset class="form">
												<article id="note-text-1570494" class="list-group-item-text note-text">
													<p>${c.content}</p>
												</article>
											</fieldset>
										</div>
									</form>
									<form action="/content/delete/1570494" method="post" id="note-delete-form-1570494">
										<input type="hidden" name="_csrf" value="567dc82c-ad24-4dfc-9f4c-9b5d76a725c5" /> <input type="hidden" name="_method" value="DELETE" id="_method" />
									</form>
								</li>
							</c:forEach>

							<li class="list-group-item note-form clearfix">
								<div class="panel-body">
									<h5 class="text-center">
										<c:choose>
											<c:when test="${memid==null}">
												<a href="<%=request.getContextPath()%>/member/login" class="link" style="color: blue;">로그인</a>을 하시면 댓글을 등록할 수 있습니다.</c:when>
											<c:otherwise>
												<form action="<%=request.getContextPath()%>/study/writeStudyCommentPro" method="post">
													<textarea name="content" id="summernote" rows="3" class="form-control input-block-level"></textarea>
													<input type="submit" name="create" class="create btn btn-success btn-wide pull-right" style="background-color: #37d3c0; border-color: #37d3c0; margin-top: 5px;" value="댓글등록" id="create" /> <input type="hidden" name="RefId" value="${s.study_Id}"> <input type="button" onclick="location.href='studyList'" name="create" class="create btn btn-success btn-wide pull-right" style="background-color: #37d3c0; border-color: #37d3c0; margin-top: 5px; margin-right: 10px;" value="목록으로" id="create" />

												</form>
											</c:otherwise>
										</c:choose>
									</h5>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
</body>
</html>