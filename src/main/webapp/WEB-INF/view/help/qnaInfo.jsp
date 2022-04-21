<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA Info</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/qnaInfo.css" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/swiper-bundle.min.css" />
<link href="/Project_KNOC/resource/style/main.css" rel='stylesheet' type='text/css' />
</head>
<body>

	<div class="layout-container">
		<div class="class_header" style="padding-top: 56px;">
			<div class="bottom-line">
				<h2 id="font">QnA</h2>
			</div>
		</div>
		<div class="wrapper">

			<main id="main">
				<section class="community-post-detail__section community-post-detail__post" data-id="473682">

					<div class="section__content">


						<div class="community-post-info" style="height: 400px; background-color: #F8F8F8; border-radius: 12px; margin-bottom: 30px;">

							<div class="community-post-info__header">
								<div class="header__title">
									<div style="display: flex; align-items: center; margin-bottom: 10px;">

										<c:if test="${commentCount==0 }">
											<div class="question__status-tag question__status-tag--recruited">미답변</div>
										</c:if>
										<c:if test="${commentCount>=1 }">
											<div class="question__status-tag question__status-tag--recruited" style="background-color: #37d3c0; border-color: #37d3c0;">답변완료</div>
										</c:if>

										<h1>${q.title }</h1>

										<input type="button" onclick="location.href='qnaList'" name="Update" class="question__status-tag question__status-tag--recruited" style="background-color: #da77f2; border-color: #da77f2; margin-left: 20px; margin-right: 0px;" value="목록으로" />
										<c:if test="${memid==q.writer }">

											<input type="button" onclick="location.href='qnaUpdate?qna_Id=${q.qna_Id}'" name="Update" class="question__status-tag question__status-tag--recruited" style="background-color: #4dabf7; border-color: #4dabf7; margin-left: 8px; margin-right: 0px;" value="수정" />
											<input type="button" onclick="location.href='qnaDeletePro?qna_Id=${q.qna_Id}'" name="Delete" class="question__status-tag question__status-tag--recruited" style="background-color: #ced4da; border-color: #ced4da; margin-left: 8px;" value="삭제" />
										</c:if>
									</div>
								</div>
								<div class="header__sub-title">
									<h6 class="user-name">
										<i class="fa fa-user">${q.writer }</i>
									</h6>
									<h6 class="user-name">
										<c:if test="${q.secret==1 }">&nbsp;</c:if>
										<c:if test="${q.secret==2 }">&nbsp;<i class="fa fa-lock"> 비밀글</i>
										</c:if>

									</h6>
									<span class="sub-title__created-at">&nbsp;· <i class="fa fa-pencil">${q.regDate }</i></span> <span class="sub-title__created-at">&nbsp;· <i class="fa fa-id-card-o">${q.qna_Id }</i></span>

								</div>
							</div>

							<div class="community-post-info__content">
								<p style="padding: 10px 0;">${q.content }</p>


							</div>


						</div>
					</div>
				</section>





				<section class="community-post-detail__section community-post-detail__answer">
					<div class="section__content">

						<div class="answer-info">

							<div class="community-post-info__content">
								<div class="content__body">
									<!--@@@@@ 주석때문에 오류난것이 있어서 몇개 지웠습니다 ㅠㅠ @@@@@-->

									<!-- 댓글이 있으면 댓글표시 -->
									<c:choose>
										<c:when test="${commentCount>=1}">
											<div class="answer__comment" data-id="175159">
												<div class="comment__index" style="background-color: #e9bd05; border-color: #e9bd05;">답변</div>

												<div class="comment__card">
													<div class="comment__header flex-row">
														<img class="comment__user-profile" src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" alt="eamon3481 프로필">
														<div class="flex-column">
															<div class="flex-row">
																<a href="/users/366575" class="comment__user-name">운영자 (KNOC 지기)<%-- ${comment.title } --%></a>
															</div>
														</div>
													</div>
													<div class="comment__body markdown-body">
														<p>${comment.content }</p>
														<div class="comment__features flex-row"></div>
													</div>
												</div>


												<%-- <div class="comment__re-comment">
											<div class="re-comment__header flex-row">
												<h4 class="re-comment__title">댓글</h4>
											</div>

											<div class="re-comment__body">
											
											
												<div class="re-comment flex-row" data-id="175161">
													<img class="re-comment__profile" src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" alt="">
													<div class="re-comment__content flex-column">
														<div class="re-comment__info flex-row">

															<button class="ac-button is-sm is-solid is-gray re-comment__writer ac-tag   ">
														<span class="ac-tag__hashtag">#&nbsp;</span><span class="ac-tag__name">글 작성자</span>
													</button>
															<h6 class="re-comment__user-name">${cl.title }</h6>
															<span class="re-comment__updated-at">·&nbsp;${cl.regDate }</span>
														</div>
														<div class="re-comment__body markdown-body">
															<p>${cl.content }&nbsp;</p>
														</div>
													</div>
												</div>

											</div>
										</div> --%>
											</div>
										</c:when>

										<%---- 어드민일 경우 댓글창이 보임 --%>
										<c:when test="${memid=='admin' }">
											<div class="answer__comment" data-id="new">
												<div class="comment__card comment__card--new">
													<form action="<%=request.getContextPath()%>/help/commentWritePro" method="post" id="" class="" role="form" onsubmit="">
														<div class="comment__header flex-row">
															<img class="comment__user-profile" src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" alt="">
															<div class="flex-column">
																<div class="flex-row">
																	<a href="/users/366575" class="comment__user-name">운영자 (KNOC 지기)<%-- ${comment.title } --%></a>
																</div>
																<!-- <textarea name="title" class="tinymce comment-editor" data-id="175159" placeholder="제목을 입력해주세요.." rows="1" style="color: transparent; text-shadow: 0 0 0 black;"></textarea> -->
															</div>
														</div>


														<textarea name="content" class="tinymce comment-editor" data-id="175159" placeholder="답글을 입력해주세요.." rows="10" style="padding: 10px;"></textarea>
														<input type="hidden" name="qna_Id" value="${q.qna_Id }">
														<div class="comment__body markdown-body" style="padding: 0;">
															<div class="comment__footer flex-row">
																<div class="flex-right">
																	<button type="submit" class="ac-button is-md is-solid is-primary e-post-comment ">답변 등록</button>
																</div>
															</div>
														</div>
													</form>

												</div>
											</div>
										</c:when>
										<%--댓글도 없고 어드민이 아닌경우 아무것도 표시 안함 --%>
										<c:otherwise></c:otherwise>
									</c:choose>

								</div>
							</div>
						</div>
				</section>
			</main>
</body>
</html>