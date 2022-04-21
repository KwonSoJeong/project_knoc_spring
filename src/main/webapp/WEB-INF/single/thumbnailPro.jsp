<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
const img = opener.document.querySelector('#picture')
img.src="<%=request.getContextPath()%>/thumbnail/${filename}"
opener.document.inputform.thumbnail.value="${filename}"
self.close()
</script>
</body>
</html>