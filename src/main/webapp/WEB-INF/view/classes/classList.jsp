<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/classList.css">
<meta charset="UTF-8">
</head>
<body>
	<div class="cl-wrapper">
		<div class="class_header">
			<h2 id ="font">클래스</h2>
			<form class="mtl-form" action="<%=request.getContextPath()%>/classes/classList">
				<input class="form-control form-content" type="text" placeholder="검색하기" name="search_keyword">
				<button id="cl-btn" type="submit">검색</button>
			</form>	
		</div>
		<div class="bottom-line"></div>
		<div class="main_content">
			<div class="categori quick_menu">
				<ul id="cl-subnav" class="submenu">
				    <li><a href="<%=request.getContextPath()%>/classes/classList">전체</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category1">운동</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category2">크리에이티브</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category3">디자인</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category4">개발/프로그래밍</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category5">요리/베이킹</a></li>
					<li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category6">금융/재태크</a></li>
				</ul>			
			</div>
			
		<section>
			<div class="class_List"> 
				<div>
				    <c:forEach var="c" items="${classList}" varStatus="status">
					<div class="box">
							
						<div class="heart_img">
							
<%-- 						<!-- doneLoop변경조건문 -->
							<c:set var="class_id" value="${c.class_id}"/>
							<c:set var="doneLoop" value="false" />

							<c:forEach var="w" items="${wishList}">
							 <c:if test="${not doneLoop}">
							       <c:if test="${w.CLASS_ID.equals(class_id)}">
							           <c:set var="doneLoop" value="true"/>
							       </c:if>
							 </c:if>
							</c:forEach>
						<!-- doneLoop변경조건문 끝-->
							
						<!-- true일때 관심버튼 꽉찬하트 -->
							<c:if test="${doneLoop==true}">
							   <button class="heartbtn" id="n${status.count}" type="button" onclick="favoriteCntUp('${class_id}', '${status.count}')">
		                        <img src="<%=request.getContextPath()%>/resource/image/heart.png">
		                       </button>
							</c:if>
						<!-- false일때 관심버튼 빈하트 -->
							<c:if test="${doneLoop==false}">
							   <button class="noheartbtn" id="n${status.count}" type="button" onclick="favoriteCntUp('${class_id}', '${status.count}')">
		                        <img src="<%=request.getContextPath()%>/resource/image/noheart.png">
		                       </button>
							</c:if> --%>
						
							<div class="cc-cc">
								<img src="<%=request.getContextPath()%>/thumbnail/${c.thumbnail}" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'" style="width: 280px; height: 215px;">
							</div>
						</div>
	
						<div class="cc-creator" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.lec_id }</div>					
						<div class="cc-title" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.title }</div>
						<div id="fav${status.count}" class="cc-heartcnt" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">♥ ${c.favorite }</div>
						<div class="cc-bor-bot" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'"></div>
						<div class="cc-price" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.price }원</div>
					</div>	
					</c:forEach> 
				</div>						
			</div>
			<!-- classList 테스트용 코드입니다! <c:forEach var = "c" items="${classList}">${c}</c:forEach>-->
		</section>	

		</div>
	</div>

<script>

function favoriteCntUp(class_id, cnt) {
	console.log(class_id)
	console.log(cnt)
	// ajax를 이용하여 관심등록/해제 구현
    let httpreq = new XMLHttpRequest()
	let param = "?class_id=" + encodeURIComponent(class_id)
    httpreq.open("GET", "<%=request.getContextPath()%>/classes/classFavorite"+param, true)
    httpreq.send()
    
    // callback
    httpreq.onreadystatechange = function() {
       
        if (httpreq.readyState == 4 && httpreq.status == 200) {
            let result = document.querySelector("#result")
            let heartButton = document.querySelector("#n" + cnt)
            // responseText = status,favoriteCnt
            let arr = this.responseText.trim().split(",")
            let status = arr[0]
            let favoriteCnt = arr[1]
            let fav = document.querySelector("#fav" + cnt)
            
            if (status == "login-null") {
                alert("관심 등록은 로그인 후 이용 가능합니다.")
            } else if (status == "favorite-Cnt-Up") {
                alert("관심 클래스로 추가되었습니다.")
                fav.innerHTML = "♥ " + favoriteCnt
                heartButton.innerHTML = "<img src='<%=request.getContextPath()%>/resource/image/heart.png'>"
            } else if (status == "favorite-Cnt-Down"){
                alert("관심 등록이 해제되었습니다.")
                fav.innerHTML = "♥ " + favoriteCnt
                heartButton.innerHTML = "<img src='<%=request.getContextPath()%>/resource/image/noheart.png'>"
            }
        }
    }
}

/*무한스크롤*/
var loading = false;    //중복실행여부 확인 변수
let pageInt = 1;


function next_load(pageInt){
	
	$.ajax({
	        type:"GET",
	        url:"/project_knoc_spring/classes/classList2?pageInt="+pageInt,
	        data : '',
	        dataType : "json",
	        success: function(classList){
	        	let c = classList;
	        	/* console.log(classList); */
	        	
	        	if(classList.length > 1){                		
	        		var Loopdiv = document.createElement("div");				
	        		var thumdiv = document.createElement("div");      					
					var firstdiv = document.createElement("div");					
					var seconddiv = document.createElement("div");       					
					var addContent = document.createElement("div");					
					let count = 13; 
					let doneLoop = false;//
					
					for (let cls of classList) {
						<%-- for(let w of wishList){
							doneLoop = false;
							if(doneLoop==false){
								if(w.CLASS_ID==cls.class_id){
										doneLoop = true;
								}
							}
						}	
						
						if(doneLoop==true){
							Loopdiv.innerHTML ='<button class="heartbtn" id="n' +count+ '" type="button" onclick="favoriteCntUp(' + "'" + cls.class_id+ "', '" + count + "'" + ')"><img src="<%=request.getContextPath()%>/resource/image/heart.png"></button>'
						}else{
							Loopdiv.innerHTML ='<button class="noheartbtn" id="n' +count+ '" type="button" onclick="favoriteCntUp(' + "'" + cls.class_id+ "', '" + count + "'" + ')"><img src="<%=request.getContextPath()%>/resource/image/noheart.png"></button>'
						} --%>
						
						//네
						thumdiv.innerHTML ='<div class="cc-cc"><img src="<%=request.getContextPath()%>/thumbnail/'+cls.thumbnail+'" onclick="location.href='+"'<%=request.getContextPath()%>/classes/classInfo?class_id=" +cls.class_id+ "'"  +'" style="width: 280px; height: 215px;"></div>' 						
						
						firstdiv.innerHTML ='<div class="heart_img">' +thumdiv.innerHTML+  '</div>' 						  						
					    seconddiv.innerHTML = '<div class="cc-title" onclick="location.href=' + "'<%=request.getContextPath()%>/classes/classInfo?class_id="+cls.class_id+"'" + '">' + cls.title + '</div><div id="fav'  +count+ '" class="cc-heartcnt" onclick="location.href=' + "'<%=request.getContextPath()%>/classes/classInfo?class_id=" + cls.class_id+"'" + '">♥' + cls.favorite+ '</div><div class="cc-bor-bot" onclick="location.href='+"'<%=request.getContextPath()%>/classes/classInfo?class_id="+cls.class_id+"'"+'"></div><div class="cc-price" onclick="location.href='+"'<%=request.getContextPath()%>/classes/classInfo?class_id="+cls.class_id+"'"+'">'+cls.price+'원</div>'
						
					    addContent.innerHTML += '<div class="box">'	+ firstdiv.innerHTML + seconddiv.innerHTML + '</div>'
						count++;
						
					}
					document.querySelector('.class_List').appendChild(addContent);			
		            loading = false;    //실행 가능 상태로 변경
	        	}
	        }
	        ,error: function(request,status,error) {
	        	alert(" cmessage = " + request.responseText + " error = " + error); // 실패 시
	        }
	    });
}

function pageplus(pageInt){
     alert(pageInt);
	 $.ajax({
         type:"GET", 
         url:"classes/classList?pageInt="+pageInt,
         data : null,
         dataType : "text",
         success: function(result){console.log("success!")}
         ,error: function(request,status,error) {
         	alert("pcode = "+ request.status + " message = " + request.responseText + " error = " + error);
         }
     });	
}

$(window).scroll(function(){
   	if($(window).scrollTop()+200>=$(document).height() - $(window).height())
   	{
        if(!loading)//실행 가능
        {
            loading = true;//실행 불가능 상태로 변경
            next_load(++pageInt); 
        }
   	}
});






/* 스크롤바 */
$(function(){
    var $win = $(window);
    var top = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.
 
    /*사용자 설정 값 시작*/
    var speed          = "fast";     // 따라다닐 속도 : "slow", "normal", or "fast" or numeric(단위:msec)
    var easing         = 'swing'; // 따라다니는 방법 기본 두가지 linear, swing
    var $layer         = $('.quick_menu'); // 레이어 셀렉팅
    var layerTopOffset = "10px";   // 레이어 높이 상한선, 단위:px
    $layer.css('position', 'absolute');
    /*사용자 설정 값 끝*/
 
    // 스크롤 바를 내린 상태에서 리프레시 했을 경우를 위해
    if (top > 0 )
        $win.scrollTop(layerTopOffset+top);
    else
        $win.scrollTop(0);
 
    //스크롤이벤트가 발생하면
    $(window).scroll(function(){
        yPosition = $win.scrollTop() + 80;
        if (yPosition < 0)
        {
            yPosition = 0;
        }
        $layer.animate({"top":yPosition }, {duration:speed, easing:easing, queue:false});
    });
});


</script>	
</body>
</html>