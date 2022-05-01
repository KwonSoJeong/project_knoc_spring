<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡMAINㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="<%=request.getContextPath() %>/js/click.js"></script>
<link href="<%=request.getContextPath() %>/resource/style/main.css" rel='stylesheet' type='text/css'/>
<script type="text/javascript">
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
</script>
</head>
<body>
	<!-- ㅡㅡㅡㅡㅡㅡㅡㅡ배너ㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
	<div class="carousel-bcc">
		<div class="container">
			<div id="demo" class="carousel slide" data-ride="carousel">
			  <ul id=carousel-indicators1 class="carousel-indicators">
			    <li data-target="#demo" data-slide-to="0" class="active"></li>
			    <li data-target="#demo" data-slide-to="1"></li>
			    <li data-target="#demo" data-slide-to="2"></li>
			  </ul>
			  <div class="carousel-inner">
			    <div class="carousel-item active">
			      <img src="<%=request.getContextPath() %>/resource/image/one.png" alt="one" width="1100" height="500">
			      <div class="carousel-caption">
			        <h3>Title</h3>
			        <p>content</p>
			      </div>   
			    </div>
			    <div class="carousel-item">
			      <img src="<%=request.getContextPath() %>/resource/image/two.png" alt="two" width="1100" height="500">
			      <div class="carousel-caption">
			        <h3>Title</h3>
			        <p>content</p>
			      </div>   
			    </div>
			    <div class="carousel-item">
			      <img src="<%=request.getContextPath() %>/resource/image/three.png" alt="three" width="1100" height="500">
			      <div class="carousel-caption">
			        <h3>Title</h3>
			        <p>content</p>
			      </div>   
			    </div>
			  </div>
			</div>
		</div>			
	</div>

	<!-- ㅡㅡㅡㅡㅡㅡㅡㅡ검색창ㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
	<div id="box-def" class="searchc1">
		<div id="box-def" class="searchcr">
			<form action="<%=request.getContextPath() %>/classes/classList" id="box-def" >
				<input id="search"  type="text" value="" placeholder="관심 있는 클래스를 입력해보세요." name="search_keyword">
				<button class="searchbtn" type="submit" >
					<img src="<%=request.getContextPath()%>/resource/image/search1.png">
				</button>
			</form>
		</div>
	</div>
	<div class="bor-bot"></div>
	
	<!-- ㅡㅡㅡㅡㅡㅡㅡㅡ신규강의ㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
	<div class="mnc-container"> 
		<div class="mnc-class">New Class</div>
		<div class="mnc-lcontainer">
		<c:forEach var="c" items="${newClassList}" varStatus="status">
			<div class="mnc-content">
				<div class="heart_img">
					<c:set var="class_id" value="${c.class_id}"/>
					<c:set var="doneLoop" value="false" />
					<c:forEach var="w" items="${wishList}">
					 <c:if test="${not doneLoop}">
					       <c:if test="${w.CLASS_ID.equals(class_id)}">
					           <c:set var="doneLoop" value="true"/>
					       </c:if>
					 </c:if>
					</c:forEach>
					
					<c:if test="${doneLoop==true}">
					   <button class="heartbtn" id="n${status.count}" type="button" onclick="favoriteCntUp('${class_id}', '${status.count}')">
                        <img src="<%=request.getContextPath()%>/resource/image/heart.png">
                        </button>
					</c:if>
					<c:if test="${doneLoop==false}">
					   <button class="noheartbtn" id="n${status.count}" type="button" onclick="favoriteCntUp('${class_id}', '${status.count}')">
                        <img src="<%=request.getContextPath()%>/resource/image/noheart.png">
                        </button>
					</c:if>

					<div class="mnc-thumbnail" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">
						<img src="<%=request.getContextPath()%>/thumbnail/${c.thumbnail}">
					</div>

				</div>
				<div class="mnc-creator" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.lec_id }</div>
				<div class="mnc-title" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.title }</div>
				<div id="fav${status.count}" class="mnc-heartcnt" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">♥ ${c.favorite }</div>
				<div class="mnc-bor-bot" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'"></div>
				<div class="mnc-price" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.price }원</div>
				
			</div>
			</c:forEach>
		</div>
	</div>

	<!-- ㅡㅡㅡㅡㅡㅡㅡㅡ추천강의ㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
	<div class="mnc-container"> 
		<div class="mnc-class">ONE PICK! Class</div>
		<div class="mnc-lcontainer">
		<c:set var="cnt" value="4"/>
		<c:forEach var="c" items="${favoriteClassList}">
			<div class="mnc-content">
				<div class="heart_img">
				    <c:set var="cnt" value="${cnt+1}"/>
					<c:set var="class_id" value="${c.class_id}"/>
                    <c:set var="doneLoop" value="false" />
                    <c:forEach var="w" items="${wishList}">
                     <c:if test="${not doneLoop}">
                           <c:if test="${w.CLASS_ID.equals(class_id)}">
                               <c:set var="doneLoop" value="true"/>
                           </c:if>
                     </c:if>
                    </c:forEach>
                    
                    <c:if test="${doneLoop==true}">
                       <button class="heartbtn" id="n${cnt}" type="button" onclick="favoriteCntUp('${class_id}', '${cnt}')">
                        <img src="<%=request.getContextPath()%>/resource/image/heart.png">
                        </button>
                    </c:if>
                    <c:if test="${doneLoop==false}">
                       <button class="noheartbtn" id="n${cnt}" type="button" onclick="favoriteCntUp('${class_id}', '${cnt}')">
                        <img src="<%=request.getContextPath()%>/resource/image/noheart.png">
                        </button>
                    </c:if>
					
					<div class="mnc-thumbnail" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">
						<img src="<%=request.getContextPath()%>/thumbnail/${c.thumbnail}">
					</div>

				</div>
				<div class="mnc-creator" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.lec_id }</div>
				<div class="mnc-title" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.title }</div>
				<div id="fav${cnt}" class="mnc-heartcnt" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">♥ ${c.favorite }</div>
				<div class="mnc-bor-bot" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'"></div>
				<div class="mnc-price" onclick="location.href='<%=request.getContextPath()%>/classes/classInfo?class_id=${c.class_id }'">${c.price }원</div>
			</div>
			</c:forEach>
		</div>
	</div>	
	
	<!-- ㅡㅡㅡㅡㅡㅡㅡㅡ채널톡ㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->	
	<div class="no m-cht">
		<img src="<%=request.getContextPath()%>/resource/image/talk.png">
	</div>

	<div class="ya1" style="display: none;">
		<div class="m-cht-box">
			<div class="m-cht-flex">
				<div id="back" onclick="nLoad()"><img src="<%=request.getContextPath()%>/resource/image/back.png"></div>
				<div id ="ti" class="m-cht-box-title">KNOC</div>
			</div>
			<div class="cht-bor-bot"></div>
			<div id="nload" class="m-cht-msg-win">
			  <c:choose>
                    <c:when test="${userId==null}">
                     <div class="m-cht-login-null">로그인 후 이용 가능합니다.</div>
                    </c:when>
                    <c:when test="${userId=='admin'}">
                     <c:forEach var="user" items="${groupList}">
                         <div class="m-cht-login-admin">
                            <input class="cl m-cht-qna-link" type="button" value="${user}님의 문의가 있습니다." onclick="adminChatLink('${user}')"/>
                         </div>
                     </c:forEach>
                    </c:when>
                    <c:otherwise>
                     <c:forEach var="webchat" items="${chatList}">
                         <c:if test="${webchat.userId.equals(userId)}">
                            <div class="right">
                                <div id="me">${webchat.message}</div>
                            </div>
                         </c:if>
                         <c:if test="${!webchat.userId.equals(userId)}">
                            <div class="left">
                                <div id="you">${webchat.message }</div>
                            </div>
                         </c:if>
                     </c:forEach>
                    </c:otherwise>
              </c:choose>
            
			</div>
			<div class="m-cht-input-win">
			    
			    <input class="m-cht-input-msg" type="text" placeholder="메세지를 적어주세요." />
			    <input class="m-cht-input-send" type="button" value="전송" onclick="sendText()" />
                <div class="m-cht-input-img">이미지를 끌어와서 전송해보세요.</div>
			</div>
		</div>
	</div>

	<div class="no2 m-cht-box-x" style="display: none;">-</div>
	
<script>
const msgArea = document.querySelector(".m-cht-msg-win")
const inputArea = document.querySelector(".m-cht-input-msg")
let groupId = '${groupId}'
const webSocket = new WebSocket('ws://#/<%=request.getContextPath()%>/groupchat')

webSocket.onopen = function(event) {onOpen(event)}
webSocket.onerror = function(event) {onError(event)}
webSocket.onmessage = function(event) {onMessage(event)}

function onOpen(event) {
    msgArea.innerHTML += "<p style='font-size: 13px; text-align: center;'>" + new Date() +"</p>"
    webSocket.send(groupId + ':${userId}:connected')
}

function onMessage(event) {
    let line = event.data
    let msg = JSON.parse(line)
    let filenameArr = [".jpg", ".png", ".gif", ".JPG", ".PNG", ".GIF"]
    let fileChk = event.data.split(":")[4].toLowerCase()
    console.log(fileChk)
    
    if (fileChk.includes('.jpg') || fileChk.includes('.png') || fileChk.includes('.gif') || fileChk.includes('.jpeg')) {
        let filename = event.data.split(":")[4]
        
        msgArea.innerHTML += "<div class='left'><div id='you'>"+ "<img src='<%=request.getContextPath()%>/chatimg/"+msg.message+"' width='200px'/>"  + "</div></div>" 
    } else {
        msgArea.innerHTML += "<div class='left'><div id='you'>" + msg.message + "</div></div>"
    }
    msgArea.scrollTop = msgArea.scrollHeight
}

function sendText() {
    msgArea.innerHTML += "<div class='right'><div id='me'>" + inputArea.value + "</div></div>"
    webSocket.send(groupId + ':${userId}:' + inputArea.value)
    msgArea.scrollTop = msgArea.scrollHeight
    inputArea.value = ""
}

function init() {
    let imgArea = document.querySelector(".m-cht-input-img")
    
    imgArea.ondragenter = (event) => {
        event.preventDefault()
        event.stopPropagation()
        
        imgArea.classList.add("focus")
    }
    
    imgArea.ondragleave = (event) => {
        event.preventDefault()
        event.stopPropagation()
        
        imgArea.classList.remove("focus")
    }
    
    imgArea.ondragover = (event) => {
        event.preventDefault()
        event.stopPropagation()
    }
    
    imgArea.ondrop = (event) => {
        event.preventDefault()
        event.stopPropagation()
        
        imgArea.classList.remove("focus")
        
        imgUpload(event.dataTransfer.files)
    }
}

function imgUpload(files) {
    let inputImg = new FormData()
    inputImg.append("file", files[0])
    inputImg.append("groupId", groupId)
    inputImg.append("userId", '${userId}')
    
    let httpreq = new XMLHttpRequest()
    
    httpreq.open("POST", "/project_knoc_spring/classes/imgUpload", true)
    
    httpreq.send(inputImg)
    
    httpreq.onload = function(e) {
        if (httpreq.status == 200) {
            sendImg(httpreq.responseText.trim())
        } else {
            alert("error")
        }
    }
}

function sendImg(filename) {
    msgArea.innerHTML += "<div class='right'><div id='me'>"
           + "<img src='/project_knoc_spring/chatimg/" + filename + "' width='200px'/>"
           + "</div></div>"
           
    webSocket.send(groupId + ':${userId}:'+filename)
    msgArea.scrollTop = msgArea.scrollHeight
}

function adminChatLink(user){
	document.getElementById("back").style.display = "block";
	document.getElementById("ti").style.margin = '20px 0px 0px 0px';
	
    let httpreq = new XMLHttpRequest()
    let param = "?groupId="+encodeURIComponent(user)

    let url = "/project_knoc_spring/classes/adminChat" 
    httpreq.open("GET", "/project_knoc_spring/classes/adminChat"+param, true)
    
    httpreq.send()
    
    httpreq.onload = function(e) {
        if (httpreq.status == 200) {
            msgArea.innerHTML = this.responseText.trim()
            msgArea.innerHTML += "<p style='font-size: 13px; text-align: center;'>" + new Date() +"</p>"
            
            
        }
    }
    
    groupId = user
    webSocket.send(groupId + ':${userId}:connected')
    
}

init()

function nLoad(){  
      $("#nload").load(window.location.href + " #nload");
}
</script>	
</body>
</html>
