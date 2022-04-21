<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/member.css" />
<script>
function pwdChk(inputform) {
	if (inputform.pwd.value == inputform.newpwd.value) {
		alert("새로운 비밀번호를 입력하세요.")
		inputform.newpwd.focus()
		
		return false
	}
	
	if (inputform.newpwd.value != inputform.newpwdchk.value) {
		alert("새로운 비밀번호를 다시 확인하세요.")
		inputform.newpwdchk.focus()
		
		return false
	}
	
}
</script>
</head>
<body>
    <main>
        <div class="container">
            <div id="member-input" class="center-align">
            <h1 id="member-input-h1">비밀번호 수정</h1>
                <form action="<%=request.getContextPath() %>/member/passwordPro" autocomplete="off" method="post" name="inputform" onsubmit="return pwdChk(this)">
                <div id="member-input-password">
                    <label for="user-pwd">기존 비밀번호</label>
                    <input id="user-pwd" type="password" name="pwd" required="required"/> <br />
                    <label for="new-pwd">새 비밀번호</label>
                    <input id="new-pwd" type="password" name="newpwd" required="required"/> <br />
                    <label for="pwd-chk">비밀번호 확인</label>
                    <input id="pwd-chk" type="password" name="newpwdchk" required="required"/> <br />
                </div>
                <div id="member-input-submit">
                    <button type="submit">비밀번호 수정</button>
                </div>
                </form>
                
            </div>
        </div>
    </main>
</body>
</html>