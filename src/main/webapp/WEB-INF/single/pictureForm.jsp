<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Profile</title>
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

body {
    font-family: 'Noto Sans KR';
}

h1 {
    font-size: 24px;
    border-bottom: 2px solid #B8E6E1;
    font-weight: normal;
}

button {
    width: 150px;
    height: 30px;
    cursor: pointer;
    border-radius: 3%;
    border: 2px solid #B8E6E1;
    background-color : #B8E6E1;
}

#profile-input {
    text-align: center;
}

input, button {
    margin-top: 50px;
    font-family: 'Noto Sans KR';
    font-size: 16px;
}

input {
    padding-left: 70px;
}
</style>
</head>
<body>
<h1>프로필 사진 선택</h1>
<div id="profile-input">
<form action="<%=request.getContextPath()%>/member/picturePro" method="post" enctype="multipart/form-data">
    <input id="user-profile" type="file" name="profile"> <br />
    <button type="submit">저장</button>
</form>
</div>
</body>
</html>