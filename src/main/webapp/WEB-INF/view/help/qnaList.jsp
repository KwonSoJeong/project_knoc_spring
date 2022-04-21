<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/qnaList.css" />
<link rel="stylesheet" href="/Project_KNOC/resource/style/swiper-bundle.min.css" />
<link href="/Project_KNOC/resource/style/main.css" rel='stylesheet' type='text/css' />
</head>
<script>
	if (document.getElementById("input_Check2").checked) {
		document.getElementById("input_Check1").disabled = true;
	}
</script>
<body>

	<!-- 모달창  -->
	<div class="modal fade" id="mo1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered ">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">문의하기</h5>
					<!-- data-dismiss="modal" 입려하여야 버튼클릭시 창닫김 -->
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<form action="<%=request.getContextPath()%>/help/qnaWritePro" method="post" id="" class="" role="form" onsubmit="">
					<!--body-->
					<div class="modal-body">

						<div class="row">
							<div class="col-12">
								<!-- 폼그룹은 태그안의 라벨봐 인풋창을 그룹으로 묶어줌 -->
								<div class="form-group">
									<label>제목</label>
									<!-- 폼컨트롤은 보더를 보기좋게 만든다. -->
									<input name="title" type="text" class="form-control">
								</div>
							</div>

							<div class="col-12">
								<div class="form-group">
									<label>공개 여부</label>
									<div class="form-check">
										<input name="secret" class="form-check-input" type="checkbox" value="2" id="input_Check2"> <label class="form-check-label" for="defaultCheck1"> 비밀글 </label> <input name="secret" type="hidden" value="1" id="input_Check1">
									</div>
								</div>
							</div>

							<div class="col-12">
								<div class="form-group">
									<label>내용</label>
									<textarea name="content" placeholder="질문을 적어주세요!" style="height: 180px;" class="form-control"></textarea>
								</div>
							</div>
						</div>

					</div>

					<div class="modal-footer">
						<button type="button" class="btn" data-dismiss="modal">닫기</button>
						<button type="submit" class="btn" style="background-color: #37d3c0; color: white;">저장하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>





	<div class="layout-container">
		<div class="class_header" style="padding-top: 56px;">
			<div class="bottom-line">
				<h2 id="font">QnA</h2>
			</div>
		</div>
		<!-- <div style="margin-top: 50px">
			<span style="font-size: 24px">QnA</span>
			<hr style="border: 0; margin-top: 4px; height: 3px; background: #b8e6e1" />
		</div> -->
		<div class="wrapper">

			<main id="main">
				<!-- 배너
				<section class="community-header">
					<div class="community-header__content">
						<h2 class="community-header__title">함께 성장할 스터디를 모집해보세요</h2>
						<p class="community-header__sub-title">강의 수강에서 더 나아가 함께 스터디까지!</p>
					</div>
				</section>
			-->



				<section class="community-body">
					<div class="community-body__content">

						<div class="search-filter">
							<!-- 탭
							<ul class="status">
								<li class="e-status active" data-status="">
									<button class="ac-button is-md is-text tab-button ">전체</button>
								</li>
								<li class="e-status " data-status="unrecruited">
									<button class="ac-button is-md is-text tab-button ">모집중</button>
								</li>
								<li class="e-status " data-status="recruited">
									<button class="ac-button is-md is-text tab-button ">모집완료</button>
								</li>
							</ul>
						-->

							<!-- 검색창
							<form class="search e-search">
								<div class="search-item">
									<div class="ac-input-with-item search-by-text ">
										<svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
									<path fill="#212529" fill-rule="evenodd" clip-rule="evenodd" d="M11.5 7c0 .59-.116 1.176-.343 1.722-.226.546-.557 1.042-.975 1.46-.418.418-.914.75-1.46.975-.546.226-1.131.343-1.722.343-.59 0-1.176-.117-1.722-.343-.546-.226-1.042-.557-1.46-.975-.418-.418-.75-.914-.975-1.46C2.616 8.176 2.5 7.591 2.5 7c0-1.194.474-2.338 1.318-3.182C4.662 2.974 5.807 2.5 7 2.5c1.193 0 2.338.474 3.182 1.318C11.026 4.662 11.5 5.806 11.5 7zm-.82 4.74c-1.206.936-2.723 1.377-4.242 1.234-1.52-.143-2.928-.86-3.937-2.005C1.49 9.825.956 8.34 1.004 6.813c.047-1.526.675-2.976 1.754-4.055 1.08-1.08 2.53-1.707 4.055-1.755 1.525-.047 3.012.488 4.156 1.498 1.145 1.01 1.862 2.417 2.005 3.937.143 1.52-.298 3.036-1.234 4.242l3.04 3.04c.074.069.133.151.174.243.04.092.063.192.065.292.001.101-.017.201-.055.294-.038.094-.094.179-.165.25-.071.071-.156.127-.25.165-.093.038-.193.056-.293.054-.101-.001-.2-.023-.293-.064-.091-.041-.174-.1-.243-.174l-3.04-3.04z"></path></svg>
										<input value="" data-kv="s" type="text" placeholder="관심 스터디를 검색해보세요!">
									</div>
									<button class="ac-button is-md is-solid is-primary search-form__search e-search-posts " style="min-width: 96px">검색</button>
								</div>
							</form>
						-->
						</div>

						<div class="question-list-container">
							<div class="posts-container-header">
								<!--sort 셀렉터(템플릿 이중 관리)-->
								<button type="button" data-toggle="modal" data-target="#mo1" class="ac-button is-md is-solid is-gray posts-container-header__button features__new-question e-new-question ">
									<span class="infd-icon"><svg width="16" height="16" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
									<path fill="#ffffff" fill-rule="evenodd" d="M11.508 1.451c.456-.455 1.195-.455 1.65 0l1.724 1.724c.456.456.456 1.194 0 1.65L13.02 6.687l-.008.008-7.219 6.89c-.132.126-.291.22-.466.273l-3.681 1.12c-.177.054-.369.006-.5-.124-.13-.131-.178-.323-.124-.5l1.115-3.666c.059-.192.166-.365.311-.504L9.651 3.31l1.857-1.858zM9.992 4.366l-6.854 6.542c-.02.02-.036.044-.044.072l-.843 2.769 2.785-.848c.025-.007.048-.02.067-.039l6.848-6.537-1.96-1.96zm2.675 1.26l1.508-1.508c.065-.065.065-.17 0-.236l-1.724-1.724c-.065-.065-.17-.065-.236 0l-1.508 1.509 1.96 1.96z" clip-rule="evenodd"></path></svg></span> <span class="posts-container-header__button-text">글쓰기</span>
								</button>
							</div>
							<ul class="question-list">
								<c:forEach items="${list }" var="list" varStatus="status">
									<li class="question-container"><a href="<%=request.getContextPath()%>/help/qnaInfo?qna_Id=${list.qna_Id }">
											<div class="question">
												<div class="question__info">
													<div class="question__title">
														<c:if test="${countList[status.index]==0}">
															<span class="question__status-tag question__status-tag--recruited">미답변</span>
														</c:if>
														<c:if test="${countList[status.index]>=1}">
															<span class="question__status-tag question__status-tag--recruited" style="background-color: #37d3c0;">답변완료</span>
														</c:if>

														<h3 class="title__text">
															<c:if test="${list.secret==1 }"> ${list.title } </c:if>
															<c:if test="${list.secret==2 }">
																<i class='fa fa-lock'>비밀글</i>
																<c:if test="${list.writer==memid || memid=='admin' }">${list.title }</c:if>
															</c:if>

															<span class="infd-icon title__icon"> </span>
														</h3>
													</div>
													<!-- <p class="question__body">N, K = map(int,input().split()) # N: 왕자, K: 탈락 숫자deq = deque([i+1 for i in range(N)])i = 1while len(...</p> -->
													<div class="question__tags"></div>
													<div class="question__info-footer">
														<i class="fa fa-user"> <c:if test="${list.secret==1 }">${list.writer }</c:if> <c:if test="${list.secret==2 }">
																<c:set var="secretWriter" value="${fn:substring(list.writer,2,6) }" />
													${fn:replace(list.writer,secretWriter , "****")}
													</c:if>
														</i> ${list.secret==1?"":"<i class='fa fa-lock'>비밀글</i>" } <i class="fa fa-pencil">${list.regDate}</i>
													</div>
												</div>
												<div class="question__additional-info">

														<c:if test="${countList[status.index]==0}">
																											<div class="question__comment">
															<span class="comment__count" style="font-size: 12px; font-weight: 500; color: #616568;">답변중</span>
																												</div>
														</c:if>
														<c:if test="${countList[status.index]>=1}">
																																									<div class="question__comment" style="background-color:#37d3c0; ">
															<span class="comment__count" style="font-size: 12px; font-weight: 900; background-color: #37d3c0; color: white;">답변OK</span>
																																											</div>
														</c:if>
														<!-- <span class="comment__description">답변</span> -->

													<!-- <button class="ac-button is-md is-text question__like e-like ">
													<svg width="16" xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 0 16 16">
												<path fill="#616568" d="M9.333 13.605c-.328.205-.602.365-.795.473-.102.057-.205.113-.308.168h-.002c-.143.074-.313.074-.456 0-.105-.054-.208-.11-.31-.168-.193-.108-.467-.268-.795-.473-.655-.41-1.53-1.007-2.408-1.754C2.534 10.382.667 8.22.667 5.676c0-2.308 1.886-4.01 3.824-4.01 1.529 0 2.763.818 3.509 2.07.746-1.252 1.98-2.07 3.509-2.07 1.938 0 3.824 1.702 3.824 4.01 0 2.545-1.867 4.706-3.592 6.175-.878.747-1.753 1.344-2.408 1.754z"></path></svg>
													0
												</button> -->
												</div>
											</div>

									</a></li>
								</c:forEach>

								<!-- <li class="question-container"><a href="/studies/472847">
										<div class="question">
											<div class="question__info">
												<div class="question__title">
													<span class="question__status-tag question__status-tag--recruited">QnA 글 ID</span>
													<h3 class="title__text">
														김영한님 JPA 강의 (Spring Data JPA, QueryDsl 등) 같이 공부하실 스터디원 모집합니다! <span class="infd-icon title__icon"> </span>
													</h3>
												</div>
												<p class="question__body">안녕하세요! &nbsp; 저희 스터디는 인프런의 김영한님 JPA 강의들을 바탕으로 공부하고 있는 온라인 스터디입니다. &nbsp; 현재 진도는 자바 ORM 표준 JPA 프로그래...</p>
												<div class="question__info-footer">
													<i class="fa fa-user"> 작성자</i> <i class="fa fa-pencil">등록일</i>
												</div>
											</div>
											<div class="question__additional-info">
												<div class="question__comment">
													<span class="comment__count">0</span> <span class="comment__description">답변</span>
												</div>
												<button class="ac-button is-md is-text question__like e-like ">
													<svg width="16" xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 0 16 16">
												<path fill="#616568" d="M9.333 13.605c-.328.205-.602.365-.795.473-.102.057-.205.113-.308.168h-.002c-.143.074-.313.074-.456 0-.105-.054-.208-.11-.31-.168-.193-.108-.467-.268-.795-.473-.655-.41-1.53-1.007-2.408-1.754C2.534 10.382.667 8.22.667 5.676c0-2.308 1.886-4.01 3.824-4.01 1.529 0 2.763.818 3.509 2.07.746-1.252 1.98-2.07 3.509-2.07 1.938 0 3.824 1.702 3.824 4.01 0 2.545-1.867 4.706-3.592 6.175-.878.747-1.753 1.344-2.408 1.754z"></path></svg>
													0
												</button>
											</div>
										</div>
								</a>
								</li>

								<li class="question-container"><a href="/studies/472657">
										<div class="question">
											<div class="question__info">
												<div class="question__title">
													<span class="question__status-tag question__status-tag--recruited">QnA 글 ID</span>
													<h3 class="title__text">
														코딩 테스트 준비 <span class="infd-icon title__icon"> </span>
													</h3>
												</div>
												<p class="question__body">코딩 테스트 준비를 위한 스터디 모집합니다!! &nbsp; 언어는 파이썬인데 스터디원들은 상관없을 것 같습니다. &nbsp; 진행 방식은 전 주에 정한 ...</p>
												<div class="question__info-footer">
													<i class="fa fa-user"> 작성자</i> <i class="fa fa-lock">비밀글</i> <i class="fa fa-pencil">등록일</i>
												</div>
											</div>
											<div class="question__additional-info">
												<div class="question__comment">
													<span class="comment__count">0</span> <span class="comment__description">답변</span>
												</div>

												<button class="ac-button is-md is-text question__like e-like ">
													<svg width="16" xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 0 16 16">
												<path fill="#616568" d="M9.333 13.605c-.328.205-.602.365-.795.473-.102.057-.205.113-.308.168h-.002c-.143.074-.313.074-.456 0-.105-.054-.208-.11-.31-.168-.193-.108-.467-.268-.795-.473-.655-.41-1.53-1.007-2.408-1.754C2.534 10.382.667 8.22.667 5.676c0-2.308 1.886-4.01 3.824-4.01 1.529 0 2.763.818 3.509 2.07.746-1.252 1.98-2.07 3.509-2.07 1.938 0 3.824 1.702 3.824 4.01 0 2.545-1.867 4.706-3.592 6.175-.878.747-1.753 1.344-2.408 1.754z"></path></svg>
													0
												</button>
											</div>
										</div>
								</a></li>
							</ul>
							
						</div> -->
								<!-- javascript:void(0) -->
								<nav class="pagination is-centered is-small" role="navagation" aria-label="pagination">
									<c:if test="${pageInt != startPage }">
										<a class="pagination-previous" href="<c:if test="${pageInt <= startPage}"> javascript:void(0)</c:if><%=request.getContextPath()%>/help/qnaList?pageNum=${pageInt-1}">이전 페이지</a>
									</c:if>
									<c:if test="${pageInt != maxPage }">
										<a class="pagination-next" href="<c:if test="${pageInt >= maxPage }"> javascript:void(0) </c:if><%=request.getContextPath()%>/help/qnaList?pageNum=${pageInt+1}">다음 페이지</a>
									</c:if>
									<ul class="pagination-list">
										<c:forEach var="i" begin="${startPage }" end="${endPage }">
											<li><a class='pagination-link <c:if test="${i==pageInt }">is-current</c:if>' href="<%=request.getContextPath()%>/help/qnaList?pageNum=${i}" aria-label="1 페이지로 이동">${i }</a></li>
										</c:forEach>
										<!-- <li><a class="pagination-link " href="?page=2" aria-label="2 페이지로 이동">2</a></li>
								<li><a class="pagination-link " href="?page=3" aria-label="3 페이지로 이동">3</a></li>
								<li><a class="pagination-link " href="?page=4" aria-label="4 페이지로 이동">4</a></li>
								<li><a class="pagination-link " href="?page=5" aria-label="5 페이지로 이동">5</a></li>
								<li><a class="pagination-link " href="?page=6" aria-label="6 페이지로 이동">6</a></li>
								<li><a class="pagination-link " href="?page=7" aria-label="7 페이지로 이동">7</a></li>
								<li><a class="pagination-link " href="?page=8" aria-label="8 페이지로 이동">8</a></li>
								<li><a class="pagination-link " href="?page=9" aria-label="9 페이지로 이동">9</a></li>
								<li><a class="pagination-link " href="?page=10" aria-label="10 페이지로 이동">10</a></li>
								<li><a class="pagination-link " href="?page=11" aria-label="다음 페이지 모음으로 이동">…</a></li>
								<li><a class="pagination-link " href="?page=26" aria-label="26 페이지로 이동">26</a></li> -->
									</ul>
								</nav>
						</div>
				</section>

			</main>
		</div>
	</div>
</body>
</html>