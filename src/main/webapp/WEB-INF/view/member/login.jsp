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