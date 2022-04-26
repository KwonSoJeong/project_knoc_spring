<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스터디</title>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/studyList.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/swiper-bundle.min.css" />
<link href="<%=request.getContextPath() %>/resource/style/main.css" rel='stylesheet' type='text/css' />
</head>
<body>
	<div class="layout-container" style="padding-top: 34px;">

		<div class="class_header">
			<a class="create btn btn-success btn-wide pull-right" href="<%=request.getContextPath()%>/study/studyWrite" style="background-color: #37d3c0; border-color: #37d3c0;"><i class="fa fa-pencil"></i> 새 글 쓰기</a>
			<div class="bottom-line">
				<h2 id="font">스터디</h2>
			</div>
		</div>
		<div class="wrapper">
			<div id="list-article" class="content scaffold-list gathering-list" role="main">


				<div class="nav" role="navigation">

					<%-- <div style="margin-top: 56px">
						<span style="font-size: 24px">스터디</span> <a class="create btn btn-success btn-wide pull-right" href="<%=request.getContextPath()%>/study/studyWrite" style="background-color: #37d3c0; border-color: #37d3c0;"><i class="fa fa-pencil"></i> 새 글 쓰기 (👉클릭👈)</a>
						<hr style="border: 0; margin-top: 4px; height: 3px; background: #b8e6e1" />
					</div> --%>

					<form id="category-filter-form" name="category-filter-form" method="get" action="<%=request.getContextPath()%>/study/studyList">
						<div class="category-filter-wrapper">
							<div class="category-filter-query pull-right">
								<div class="input-group input-group-sm">
									<c:if test="${process!=null }"><input type="hidden" name="process" value="${process}"></c:if>
									<input type="search" name="keyword" id="search-field" class="form-control" placeholder="검색어" value="" /> <span class="input-group-btn">
										<button type="submit" class="btn btn-default">
											<i class="fa fa-search">검색</i>
										</button>
									</span>

								</div>
							</div>

							<ul class="list-sort pull-left">
								<li><a href="<%=request.getContextPath()%>/study/studyList?process=3" data-sort="id" data-order="desc" class='category-sort-link <c:if test="${process==3}">active</c:if>'>전체</a></li>
								<li><a href="<%=request.getContextPath()%>/study/studyList?process=1" data-sort="voteCount" data-order="desc" class='category-sort-link <c:if test="${process==1}">active</c:if>'>모집중</a></li>
								<li><a href="<%=request.getContextPath()%>/study/studyList?process=2" data-sort="noteCount" data-order="desc" class='category-sort-link <c:if test="${process==2}">active</c:if>'>모집완료</a></li>
							</ul>
							<!-- <input type="hidden" name="sort" id="category-sort-input" value="id" /> <input type="hidden" name="order" id="category-order-input" value="desc" /> -->
						</div>
					</form>
				</div>

				<c:if test="${(s.list!=0)}">
					<div class="okkys-choice">
						<div class="panel panel-default">
							<!-- Table -->
							<ul class="list-group">
								<c:forEach var="s" items="${list }" varStatus="status">
									<li class="list-group-item list-group-item-question list-group-no-note clearfix">
										<div class="list-title-wrapper clearfix">
											<div class="list-tag clearfix">
												<c:if test="${s.process==1}">
													<a href="<%=request.getContextPath()%>/study/studyList?process=1" class="list-group-item-text item-tag label label-info"><i class="fa fa-comments"></i>모집중</a>
												</c:if>
												<c:if test="${s.process==2}">
													<a href="<%=request.getContextPath()%>/study/studyList?process=2" class="list-group-item-text item-tag label label-gray">모집완료</a>
												</c:if>
											</div>

											<h5 class="list-group-item-heading list-group-item-evaluate">
												<a href="<%=request.getContextPath()%>/study/studyInfo?study_Id=${s.study_Id}" style="cursor: pointer;"> ${s.title} </a>
											</h5>
										</div>

										<div class="list-group-item-author clearfix">
											<div class="avatar clearfix avatar-list">
												<a href="#" class="avatar-photo"> <c:choose>
														<c:when test="${profileList[status.index]!=null}">
															<img src="<%=request.getContextPath() %>/profile/${profileList[status.index]}" />
														</c:when>
														<c:otherwise>
															<img src="//www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&s=40" />
														</c:otherwise>
													</c:choose>

												</a>
												<div class="avatar-info">
													<a class="nickname" href="#" title="KNOC">${s.leader_Id}</a>
													<div class="date-created">
														<span class="timeago" title="2022-03-02T14:33:59">${s.regDate}</span>
													</div>
												</div>
											</div>
										</div>
									</li>

								</c:forEach>
							</ul>
						</div>
						<!-- "javascript:void(0)" -->
						<div class="text-center">
							<ul class="pagination pagination-sm">
								<li class='prev <c:if test="${startPage <= bottomLine }"> disabled</c:if>'><a href="<c:if test="${startPage <= bottomLine }"> javascript:void(0)</c:if><%=request.getContextPath() %>/study/studyList?pageNum=${startPage-bottomLine}">«</a></li>
								<c:forEach var="i" begin="${startPage }" end="${endPage }">
									<li class='<c:if test="${i==pageInt }"> active </c:if>'><a href="<%=request.getContextPath()%>/study/studyList?pageNum=${i}">${i}</a></li>
								</c:forEach>
								<li class='next <c:if test="${endPage >= maxPage }"> disabled </c:if>'><a href="<c:if test="${endPage >= maxPage }"> javascript:void(0) </c:if><%=request.getContextPath() %>/study/studyList?pageNum=${startPage+bottomLine}">»</a></li>
							</ul>
						</div>
					</div>

				</c:if>
			</div>
		</div>
	</div>
</body>
</html>