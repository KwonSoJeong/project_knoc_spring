<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/member.css" />
<script>
function pic_upload() {
    let loca_left = window.screen.width/2 - 175
    let loca_top = window.screen.height/2 - 150
    const option = "width=350, height=300, left=" + loca_left + ", top=" + loca_top
    open("<%=request.getContextPath()%>/member/pictureForm", "", option)
}
</script>
</head>
<body>
    <main>
        <div class="container">
            <div id="member-input" class="center-align">
            <h1 id="member-input-h1">회원정보수정</h1>
                <form action="<%=request.getContextPath() %>/member/memberUpdatePro" autocomplete="off" method="post" name="inputform">
                <div id="member-input-profile">
                    <!-- 프로필 사진을 등록하지 않은 회원의 경우 이미지 란이 비어있지 않도록 기본 프로필 사진 보이게 함-->
                    <img id="picture" src="<c:choose><c:when test="${member.profile == null}"><%=request.getContextPath() %>/resource/image/profile.jpg</c:when><c:otherwise><%=request.getContextPath()%>/profile/${member.profile}</c:otherwise></c:choose>" alt="프로필이미지" /> <br />
                    <input type="hidden" name="profile" value="${member.profile }"/>
                    <button type="button" onclick="pic_upload()">사진 변경</button>
                </div>
                <div id="member-input-info">
                    <label for="user-id">아이디</label>
                    <input id="user-id" type="text" name="id" value="${member.id}" readonly="readonly"/> <br />
                    <label for="user-pwd">비밀번호</label>
                    <input id="user-pwd" type="password" name="pwd" /> <br />
                    <label for="user-name">이름</label>
                    <input id="user-name" type="text" name="name" value="${member.name}"/> <br />
                    <label for="user-email">이메일</label>
                    <input id="user-email" type="email" name="email" value="${member.email}" /> <br />
                    <label for="user-tel">전화번호</label>
                    <input id="user-tel" type="tel" name="tel" value="${member.tel}"/> 
                </div>
                <div id="member-input-submit">
                    <button type="submit">회원정보수정</button>
                    <button type="button" onclick="location.href='<%=request.getContextPath()%>/member/password'">비밀번호수정</button>
                </div>
                </form>
                
            </div>
        </div>
    </main>
</body>
</html>