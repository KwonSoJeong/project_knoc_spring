<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클래스 제목</title>
<script>
function favoriteCntUp() {
	// ajax를 이용하여 관심등록/해제 구현
    let httpreq = new XMLHttpRequest()
    httpreq.open("GET", "<%=request.getContextPath()%>/classes/classFavorite", true)
    httpreq.send()
    
    // callback
    httpreq.onreadystatechange = function() {
       
        if (httpreq.readyState == 4 && httpreq.status == 200) {
            let result = document.querySelector("#result")
            
            // responseText = status,favoriteCnt
            let arr = this.responseText.trim().split(",")
            let status = arr[0]
            let favoriteCnt = arr[1]
            let fav = document.querySelector("#fav")
            
            if (status == "login-null") {
                alert("관심 등록은 로그인 후 이용 가능합니다.")
            } else if (status == "favorite-Cnt-Up") {
                alert("관심 클래스로 추가되었습니다.")
                fav.innerHTML = "♥ " + favoriteCnt
            } else if (status == "favorite-Cnt-Down"){
                alert("관심 등록이 해제되었습니다.")
                fav.innerHTML = "♥ " + favoriteCnt
            }
        }
    }
}
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/style/classInfo.css">
</head>

<body style="padding-top: 70px;">
	<div class="ci-wrapper">
		<div class="class_header">
			<h2>${classone.title}</h2>
		</div>
		<div class="bottom-line"></div>


	<div class="class-wrapper">
	<div id="class-info">
	
	<div><img src="<%=request.getContextPath() %>/thumbnail/${classone.thumbnail}" width="825px" height="600px" style="border-radius: 15px;"></div>
	<div class="class-detail"><h2>${classone.title}</h2></div>
	<div class="class-detail">클래스 카테고리:&nbsp;${category}</div>
	<div class="class-detail">${classone.intro}</div>
	<div>
	<div class="class-detail" style="text-align: center;">목차</div>
	
	<c:forEach var="c" items="${contentList}">
	<div class="content-list">
	${contentNo}차시 | ${c.title } <br />
	<c:set var="contentNo" value="${contentNo+1}"/>
	</div>
	</c:forEach>
	
	</div>
	</div>
	
	<div id="class-register"> <!--<label>클래스 공유자 프로필</label>
	 <img src="<%=request.getContextPath() %>/profile/${profile}" width="110" height="90">
	<br> -->
	<div class="class-detail">클래스 공유자:&nbsp;${classone.lec_id}</div>
	<div id="price" class="class-detail">${classone.price}원</div>
	<div id="fav" class="class-detail">♥ ${classone.favorite}</div>
	
	<!-- <button id="class-bung2" type="submit" value="멘토링 신청">멘토링신청</button>  -->
	<button id="class-bung2" class="class-detail" type="button" onclick="location.href='<%=request.getContextPath()%>/classes/classRegister'">수강신청</button>
	<input id="class-bung22" class="class-detail" type="button" onclick="favoriteCntUp()" value="관심등록">
	</div>
	</div>

	</div>
</body>
</html>