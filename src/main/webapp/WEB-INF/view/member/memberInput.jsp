<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/member.css" />
<script>
let idChkOk = false
let pwdChkOk = false

function pic_upload() {
	let loca_left = window.screen.width/2 - 175
    let loca_top = window.screen.height/2 - 150
	const option = "width=350, height=300, left=" + loca_left + ", top=" + loca_top
	open("<%=request.getContextPath()%>/member/pictureForm", "", option)
}

function idChk() {
    const id = document.inputform.id.value
    let result = document.querySelector("#input-id-chk")
    
    if(id.length < 8) {
        result.style.color = "red"
        
    } else {
        let httpreq = new XMLHttpRequest()
        let param = "?id="+encodeURIComponent(id)
        httpreq.open("GET", "<%=request.getContextPath()%>/member/idChk"+param, true)
        httpreq.send()
        
        httpreq.onreadystatechange = function()  {   
            if (httpreq.readyState == 4 && httpreq.status == 200) {
        
                let chk = this.responseText.trim()
                if (chk == "alreadyExistId") {
                    result.innerHTML = "이미 사용중인 아이디입니다."
                    result.style.color = "red"
                } else if (chk == "ok") {
                    result.innerHTML = "사용 가능한 아이디입니다."
                    result.style.color = "green"
                    idChkOk = true
                }
            }
        }
    }
}

function pwdChk() {
    const pwd = document.inputform.pwd.value
    let result = document.querySelector("#input-pwd-chk")
    
    if(pwd.length < 8) {
        result.style.color = "red"
        
    } else {
        let httpreq = new XMLHttpRequest()
        let param = "?pwd="+encodeURIComponent(pwd)
        httpreq.open("GET", "<%=request.getContextPath()%>/member/pwdChk"+param, true)
        httpreq.send()
        
        httpreq.onreadystatechange = function()  {   
            if (httpreq.readyState == 4 && httpreq.status == 200) {
        
                let chk = this.responseText.trim()
                if (chk == "false") {
                    result.innerHTML = "영문, 숫자, 특수문자를 모두 포함시켜 주세요."
                    result.style.color = "red"
                } else if (chk == "true") {
                    result.innerHTML = "사용 가능한 비밀번호 입니다."
                    result.style.color = "green"
                    pwdChkOk = true
                }
            }
        }
    }
}


function submitChk() {
    if (idChkOk == true && pwdChkOk == true) {
        return true
    } else {
        alert("아이디와 비밀번호를 다시 확인해주세요.")
        return false
    }
}
</script>


</head>
<body>
	<main id =minput-main>
		<div id="member-input" class="center-align">
            <h1 id="member-input-h1">회원가입</h1>
                <form action="<%=request.getContextPath()%>/member/memberInputPro" autocomplete="off" method="post" name="inputform" onsubmit="return submitChk()">
                <div id="member-input-profile">
                    <img id="picture" src="<%=request.getContextPath() %>/resource/image/profile.jpg" alt="프로필이미지" /> <br />
                    <input type="hidden" name="profile" />
                    <button type="button" onclick="pic_upload()">사진 등록</button>
                </div>
                <div id="member-input-info">
                    
                    <label for="user-id">아이디</label>
                    <div class="user-input">
                    <div id="input-id-chk">8자 이상 입력해주세요.</div>
                    <input id="user-id" type="text" name="id" onkeyup="idChk()" required="required" style="margin-top: 0;"/> <br />
                    </div>
                    <label for="user-pwd">비밀번호</label>
                    <div class="user-input">
                    <div id="input-pwd-chk">영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.</div>
                    <input id="user-pwd" type="password" name="pwd" onkeyup="pwdChk()" required="required" style="margin-top: 0;"/> <br />
                    </div>
                    <label for="user-name">이름</label>
                    <input id="user-name" type="text" name="name" required="required" style="margin-top: 20px;"/> <br />
                    <label for="user-email">이메일</label>
                    <input id="user-email" type="email" name="email" required="required"/> <br />
                    <label for="user-tel">전화번호</label>
                    <input id="user-tel" type="tel" name="tel" required="required"/> 
                </div>
                <div id="member-input-submit">
                    <button type="submit">회원가입</button>
                </div>
                </form>
                
		</div>
	</main>
</body>
</html>