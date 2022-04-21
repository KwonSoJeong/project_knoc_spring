<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/style/classUpload.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
	
</head>
<script>
function thumbnail_upload() {
    let loca_left = window.screen.width/2 - 175
    let loca_top = window.screen.height/2 - 150
    const option = "width=350, height=300, left=" + loca_left + ", top=" + loca_top
    open("<%=request.getContextPath()%>/classes/thumbnailForm", "", option)
}
</script>

<script>
let i = 1
inputCnt = 1
function addContent() {
	if (inputCnt == 10) {
		alert('최대 10차시까지 등록할 수 있습니다.')
		return
	}
	
	// input name과 새로 생성될 input 영역에 새 번호를 부여하기 위해 i값을 1 증가
    i = i + 1
    const inputForm = document.querySelector(".form-group")
    const inputDiv = document.createElement('div')
    // 이후 삭제 시 id로 영역 찾을 수 있도록 번호를 붙여서 id 부여
    inputDiv.setAttribute("id", "newInput"+i)
    
    // 새로운 차시 제목 입력할 input
    const newTitle = document.createElement('input')
    newTitle.setAttribute("type", "text")
    newTitle.setAttribute("name", "contentTitle")
    newTitle.setAttribute("class", "form-control")
    
    // 새로운 차시 컨텐츠 파일 입력할 input
    const newFile = document.createElement('input')
    newFile.setAttribute("type", "file")
    newFile.setAttribute("name", "file"+i)
    newFile.setAttribute("class", "form-control")
    newFile.setAttribute("required", "required")
    
    // 줄바꿈, input 태그 제목 붙이기 위한 p태그 생성 : css로 margin:0 설정됨
    let addLabel1 = document.createElement('p')
    addLabel1.innerHTML = "<br><br>" + "제목을 입력하세요"
    let addLabel2 = document.createElement('p')
    addLabel2.innerHTML = "컨텐츠 파일을 입력하세요."
    
    let removeButton = document.createElement('button')
    removeButton.setAttribute("id", i)
    removeButton.setAttribute("type", "button")
    removeButton.setAttribute("onclick", "removeContent(this)")
    removeButton.innerHTML = "차시삭제"
    
    // 생성한 요소 전부 div에 붙이고 form태그 안에 추가
    inputDiv.appendChild(addLabel1)
    inputDiv.appendChild(newTitle)
    inputDiv.appendChild(addLabel2)
    inputDiv.appendChild(newFile)
    inputDiv.appendChild(removeButton)
    
    inputForm.appendChild(inputDiv)
    
    inputCnt = inputCnt + 1
}

function removeContent(obj) {
	// 1차시만 있을 때는 삭제할 수 없도록 알림창을 띄우고 삭제하지 않음
	if (inputCnt == 1) {
        alert('컨텐츠 없이 클래스를 등록할 수 없습니다.')
        return
    }
	
	const removeInput = document.getElementById("newInput"+obj.getAttribute('id'))
	removeInput.remove()
    
	// 전체 차시 카운트 1 감소
	inputCnt = inputCnt - 1
}
</script>

<body>
	<div class="mti-wrapper">

		<div class="mentor_header">
			<h2>클래스 개설</h2>
		</div>
		<div class="bottom-line"></div>
	
		<div id="upload-div">
		<div class="container">
			<form action="<%=request.getContextPath()%>/classes/classUploadPro" enctype="multipart/form-data" method="post" name="inputform">
				<br>
	            <label>썸네일</label>
	            <div id="blank_thumbnail">
	            <img id="picture" src=""  alt="썸네일 이미지" style="width:90%; height:100%;"/>
	            </div>
	            <input type="hidden" class="form-control" name="thumbnail">
	            <button id="tbtn" type="button" onclick="thumbnail_upload()">썸네일 등록</button>
	            
				<div id="upload-option">
	            <label>카테고리</label>
	            <select name="caterory_id">
	            <option value="category1">운동</option>
	            <option value="category2">크리에이티브</option>
	            <option value="category3">디자인</option>
	            <option value="category4">개발/프로그래밍</option>
	            <option value="category5">요리/베이킹</option>
	            <option value="category6">금융/재태크</option>
	            </select>
	            
	            <br>
	            <br>
	            <br>
	             <label>타입</label>
	             <input type="radio" name="type" value="1">영상
	             <input type="radio" name="type" value="2">글
	            </div>
	            
	            
	            <br><br>
	
				<div>
				<label>제목</label>
				<input type="text" name="title" class="form-control">
				</div>
				<br><br>
				
	            <div>
				<label>소개글</label>
				<textarea class="form-control" rows="10" cols="60" placeholder="소개글 작성" name="intro"></textarea>
				</div>
				<br><br>
				
				<div>
				<label>가격</label>
				<input type="text" name="price" class="form-control">
				</div>
				<br><br>
				
	           <div class="form-group">
					<label>컨텐츠 입력</label><br />
					제목을 입력하세요<input type="text" name="contentTitle" class="form-control">
					컨텐츠 파일을 입력하세요.<input type="file" name="file1" class="form-control" required="required">
					<button style="margin: 20px 0 10px 0;" id="1" type="button" onclick="removeContent(this)">차시 삭제</button>
				</div>
				<button type="button" onclick="addContent()">차시 추가</button>
				
				<div id="center" style="padding: 25px;">
				<button type="submit">등록하기</button>
				</div>
			</form>
		</div>
		</div>

	</div>
</body>
</html>