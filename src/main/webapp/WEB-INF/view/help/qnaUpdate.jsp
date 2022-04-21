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
<script>
	if (document.getElementById("input_Check2").checked) {
		document.getElementById("input_Check1").disabled = true;
	}
</script>
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


						<div class="community-post-info">

							<div class="community-post-info__header">
								<div class="answer__comment" data-id="new">
									<div class="comment__card comment__card--new">
										<form action="<%=request.getContextPath()%>/help/qnaUpdatePro" method="post" id="" class="" role="form" onsubmit="">
											<div class="comment__header flex-row">

												<div class="flex-column">

													<textarea name="title" class="tinymce comment-editor" data-id="175159" placeholder="제목을 입력해주세요.." rows="1" style="padding: 10px; width: 630px;">${q.title }</textarea>
												</div>
											</div>


											<textarea name="content" class="tinymce comment-editor" data-id="175159" placeholder="내용을 입력해주세요.." rows="10" style="padding: 10px; width: 630px;">${q.content }</textarea>
											<input type="hidden" name="qna_Id" value="${q.qna_Id }">
											<div class="comment__body markdown-body" style="padding: 0;">
												<div class="comment__footer flex-row">
													<div class="flex-right">
														<div class="form-check" style="margin-right: 10px">
															<input name="secret" class="form-check-input" type="checkbox" value="2" id="input_Check2"> <label class="form-check-label" for="defaultCheck1"> 비밀글 </label> <input name="secret" type="hidden" value="1" id="input_Check1">
														</div>
														<button type="submit" class="ac-button is-md is-solid is-primary e-post-comment ">수정</button>
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</main>
</body>
</html>