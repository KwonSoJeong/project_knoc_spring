<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:forEach var="webchat" items="${chatList}">
	<c:if test="${!webchat.userId.equals(groupId)}">
		<div class="right">
			<div id="me">${webchat.message}</div>
		</div>
	</c:if>
	<c:if test="${webchat.userId.equals(groupId)}">
		<div class="left">
			<div id="you">${webchat.message }</div>
		</div>
	</c:if>
</c:forEach>