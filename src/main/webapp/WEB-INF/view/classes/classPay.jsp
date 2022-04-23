<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/style/classPay.css">
<meta charset="UTF-8">
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
</head>
<body>
	<div class="cl-wrapper">
		<div class="class_header" style="padding-top: 40px; justify-content: center;">
			<h2 id="font">클래스 결제</h2>

		</div>
		<!-- <div class="bottom-line"></div> -->
		<div class="main_content" style="margin-top: 70px;">

			<section>

				<span style="font-size: 24px;">결제 클래스 정보</span>
				<div class="payment-container">
					<div class="payment-thumbnail">
						<div class="box">

							<div class="heart_img">
								<div class="cc-cc">
									<img src="/Project_KNOC/thumbnail/" onclick="location.href='/Project_KNOC/classes/classInfo?class_id='" style="width: 280px; height: 215px;">
								</div>
							</div>
						</div>
					</div>

					<div>
						<div class="payment-title">클래스 제목(왼쪽 정렬)</div>
					</div>
					<div>
						<div class="payment-price">55,000원</div>
					</div>
				</div>

				<div class="payment-btn-wrapper">
					<button class="payment-btn" onclick="requestPay()">결제하기</button>
				</div>

			</section>

		</div>
	</div>
	<script>
		var IMP = window.IMP; // 생략 가능
		IMP.init("{가맹점 식별코드}"); // 예: imp00000000

		function requestPay() {
			// IMP.request_pay(param, callback) 결제창 호출
			IMP.request_pay({ // param
				pg : "html5_inicis",
				pay_method : "card",
				merchant_uid : "ORD20180131-0000011",
				name : "클래스 제목",
				amount : 100,
				buyer_email : "example@example.com",
				buyer_name : "user",
				buyer_tel : "010-1234-5678",
				buyer_addr : "서울특별시 강남구 신사동",
				buyer_postcode : "01181"
			}, function(rsp) { // callback
				if (rsp.success) {
					// 결제 성공 시 로직
				} else {
					// 결제 실패 시 로직
				}
			});
		}
	</script>
</body>
</html>