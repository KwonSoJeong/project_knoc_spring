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
            <div id="member-input" class="center-align">
            <h1 id="member-input-h1">회원탈퇴</h1>
                <form action="<%=request.getContextPath() %>/member/memberDeletePro" autocomplete="off" method="post" >
                <div id="member-input-password">
                    <label for="user-id">아이디</label>
                    <input id="user-id" type="text" name="id" value="${member.id }" readonly="readonly"/> <br />
                    <label for="user-pwd">비밀번호</label>
                    <input id="user-pwd" type="password" name="pwd" required="required"/> <br />
                </div>
                <div id="member-input-submit">
                    <button type="submit">회원탈퇴</button>
                </div>
                </form>
                
            </div>
        </div>
    </main>
</body>
</html>