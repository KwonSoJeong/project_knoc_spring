<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script
    src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script
    src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
    src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<style>
*{
box-sizing: border-box;

}

#upload-col{
font-size: 24px; 
/*border-bottom : solid 2px #B8E6E1;
margin-left: 400px;
margin-right: 400px;*/
margin-top: 13vh;
}

#upload-h3{
border-bottom: solid 2px #B8E6E1;
margin-top: 80px;
margin-left: 240px;
margin-bottom: 12px;
width: 1450px;
}

button{
width : 100px;
height: 35px;
background-color: #B8E6E1;
font-size : 13px;
border: none;
text-align:center;
font-size: 15px;
margin-left: 30px;
}

#upload-div{
margin: 0 auto;
text-align: center;
}

#upload-thumbnail {
display: inline-block;
width : 300px;
}

#blank_thumbnail {
width: 400px;
height: 270px;
margin: 0 auto;
background-color: gray;
}

#upload-option {
margin-top: 50px;
}

p {
margin: 0;
}
</style>
  
</head>
<body>
<script>
function deleteChk() {
	if (confirm("삭제 후 복구가 불가능합니다. 진행하시겠습니까?")) {
		location.href="<%=request.getContextPath()%>/classes/classDeletePro?class_id="+'${classOne.class_id}';
	}
}

function thumbnail_upload() {
    let loca_left = window.screen.width/2 - 175
    let loca_top = window.screen.height/2 - 150
    const option = "width=350, height=300, left=" + loca_left + ", top=" + loca_top
    open("<%=request.getContextPath()%>/classes/thumbnailForm", "", option)
}
</script>  
<div id="upload-col">
    <h4 id="upload-h3">클래스 편집</h4>
    </div>
    
    <div id="upload-div">
    <div class="container" style="padding: 80px;">
        <form action="<%=request.getContextPath()%>/classes/classUpdatePro" enctype="multipart/form-data" method="post" name="inputform">
            <br>
            <label>썸네일</label>
            <div id="blank_thumbnail">
            <img id="picture" src="<%=request.getContextPath() %>/thumbnail/${classOne.thumbnail}" alt="썸네일 이미지" style="width:100%; height:100%;"/>
            </div>
            <input type="hidden" name="class_id" value="${classOne.class_id}" />
            <input type="hidden" name="thumbnail" value="${classOne.thumbnail}">
            <button type="button" onclick="thumbnail_upload()">썸네일 수정</button>
            
            <br><br>
            <div>
            <label>제목</label>
            <input type="text" name="title" class="form-control" value="${classOne.title}" readonly="readonly">
            </div>
            <br><br>
            
            <div>
            <label>소개글</label>
            <textarea class="form-control" rows="10" cols="60" placeholder="소개글 작성" name="intro" >${classOne.intro}</textarea>
            </div>
            <br><br>
            
            <div>
            <label>가격</label>
            <input type="text" name="price" class="form-control" value="${classOne.price}" />
            </div>
            <br><br>
            <label>컨텐츠 수정</label><br />
            <c:forEach var="content" items="${contentList}">
                <c:set var="no" value="${no+1}"/>
                <div class="form-group">
                    <input type="hidden" name="content_id${no}" value="${content.content_Id}" />
                    제목을 입력하세요<input type="text" name="contentTitle${no}" class="form-control" value="${content.title}">
                    컨텐츠 파일을 입력하세요.<input type="file" name="newFile${no}" class="form-control">
                    <input type="hidden" name="file${no}" value="${content.file1}"/>
                </div>
            </c:forEach>
            <div id="center" style="padding: 25px;">
            <button type="submit">수정</button>
            <button type="button" onclick="deleteChk()" >삭제</button>
            </div>
        </form>
    </div>
    </div>


</body>
</html>