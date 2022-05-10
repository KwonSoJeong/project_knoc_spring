<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/member.css" />
</head>
<body>
<script>
if ('${param.msg}' === 'not_authorized') {
    alert('접근 권한이 없습니다.');
} else if ('${param.msg}' === 'need_login') {
    alert('로그인이 필요한 서비스입니다.');
}
</script>
<main>
        <div class="container">
            <div id="member-login" class="center-align">
                <h1 id="member-login-h1">로그인</h1>
                <form action="<%=request.getContextPath()%>/member/loginPro" autocomplete="off" method="post">
                    <input type="text" name="id" placeholder="아이디를 입력하세요." /> <br /> 
                    <input type="password" name="pwd" placeholder="비밀번호를 입력하세요." /> <br />
                    <button type="submit">로그인</button>
                </form>
                <a href="<%=request.getContextPath()%>/member/memberInput"><span>회원가입</span></a>
            </div>
        </div>
    </main>
</body>
</html>