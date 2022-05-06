<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/style/notiList.css">
<link href="<%=request.getContextPath()%>/resource/style/main.css" rel='stylesheet' type='text/css' />
<meta charset="UTF-8">
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
<title>test</title>
</head>
<body>

	<div class="cl-wrapper">
		<div class="class_header" style="padding-top: 40px; justify-content: center;">
			<h2 id="font">알림 목록</h2>

		</div>
		<div class="main_content" style="margin-top: 70px;">

			<section>
				<div style="background-color: #f8f9fa; padding: 10px 0">
					<div style="display: flex; justify-content: space-between; margin: 0 18px;">
						<span>읽지 않은 알람 수 : ${unreadNoti }</span> <span><a href="<%=request.getContextPath()%>/noti/allReadChk"><i class="fa fa-check-circle"></i>&nbsp;모두 읽음 표시</a></span>
					</div>
				</div>

				<c:forEach var="noti" items="${notiList}">
					<div class="payment-container">
						<div class="payment-thumbnail">
							<div class="box">
								<c:choose>
									<c:when test="${noti.type==1 }">
										<i class="fa fa-circle" style="color: #41e8d5" aria-hidden="true"></i>
									</c:when>
									<c:when test="${noti.type==0 && noti.readChk == 1 }">
										<i class="fa fa-circle" style="color: #41e8d5" aria-hidden="true"></i>
									</c:when>
									<c:when test="${noti.type==0 }">
										<i class="fa fa-circle" style="color: #cacaca" aria-hidden="true"></i>
									</c:when>

								</c:choose>

							</div>
						</div>
						<div>
							<c:choose>
								<c:when test="${noti.type==1 }">
									<div class="payment-title" style="font-weight: bold;">${noti.noti_Content }</div>
								</c:when>

								<c:when test="${noti.type==0 && noti.readChk == 1 }">
									<div class="payment-title" style="font-weight: bold;" >${noti.noti_Content }</div>
								</c:when>

								<c:when test="${noti.type==0 }">
									<div class="payment-title">${noti.noti_Content }</div>
								</c:when>

							</c:choose>
						</div>
						<div>
							<div class="payment-price">

								<c:if test="${noti.type==1 }">
									<!-- type==1 버튼보임  type==0 버튼안보임 -->
									<a style="background-color: #65b5ac; padding: 10px 20px; border-radius: 10px; color: whitesmoke;" href="<%=request.getContextPath()%>/noti/accept?no=${noti.no}">수락</a> &nbsp; 
									<a style="background-color: #ff7d6f; padding: 10px 20px; border-radius: 10px; color: whitesmoke;" href="<%=request.getContextPath()%>/noti/cancel?no=${noti.no}">거절</a>
								</c:if>

								<c:if test="${noti.type==0 && noti.readChk == 1 }">
									<!-- type==1 버튼보임  type==0 버튼안보임 -->
									<a style="background-color: #41e8d5; padding: 10px 20px; border-radius: 10px; color: whitesmoke;" href="<%=request.getContextPath()%>/noti/readChk?no=${noti.no}">확인</a> &nbsp; 
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</section>

		</div>
	</div>


</body>
</html>