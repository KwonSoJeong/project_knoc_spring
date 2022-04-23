<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡHEADERㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<!DOCTYPE html>
<html>
<title>KNOC</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<style>@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');</style>
<link href="<%=request.getContextPath() %>/resource/style/header.css" rel='stylesheet' type='text/css'/>
<link href="<%=request.getContextPath() %>/resource/style/footer.css" rel='stylesheet' type='text/css'/>
<script src="../js/hover.js"></script>
<sitemesh:write property='head'/>
<body>
<div id="wrapper">
    <div class="header">
        <nav class="hnav">
            <!-- logo -->
            <a class="navbar-brand1" href="<%=request.getContextPath() %>/classes/main">
                <img src="<%=request.getContextPath() %>/resource/image/logo.png">
            </a>
            <div class="hdiv">
            <!-- 좌측헤더 -->
            <ul class="hnavbar-nav drop" style="flex-direction: row;" >         
                <c:if test="${memid==null}">            
                    <li id="num1" class="nav-it">
                        <a href="<%=request.getContextPath() %>/classes/classList" id="fsfc" class="nav-link">클래스 ▼</a>
                        <ul id="subnav" class="submenu">
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category1">운동</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category2">크리에이티브</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category3">디자인</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category4">개발/프로그래밍</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category5">요리/베이킹</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category6">금융/재태크</a></li>
                        </ul>
                    </li>
                    <li class="nav-it">
                        <a href="<%=request.getContextPath() %>/mentor/mentorList" id="fsfc" class="nav-link">멘토링</a>
                    </li>
                    <li class="nav-it">
                        <a href="<%=request.getContextPath() %>/study/studyList" id="fsfc" class="nav-link">스터디</a>
                    </li>   
                </c:if>
        
                <c:if test="${memid!=null}">
                    <li id="num1" class="nav-it">
                        <a href="<%=request.getContextPath() %>/classes/classList" id="fsfc" class="nav-link">클래스 ▼</a>
                        <ul id="subnav" class="submenu">
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category1">운동</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category2">크리에이티브</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category3">디자인</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category4">개발/프로그래밍</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category5">요리/베이킹</a></li>
                            <li><a href="<%=request.getContextPath()%>/classes/classList?category_id=category6">금융/재태크</a></li>
                        </ul>
                    </li>
                    <li class="nav-it">
                        <a href="<%=request.getContextPath() %>/mentor/mentorList" id="fsfc" class="nav-link">멘토링</a>
                    </li>
                    <li class="nav-it">
                        <a href="<%=request.getContextPath() %>/study/studyList" id="fsfc" class="nav-link">스터디</a>
                    </li>
                    <li id="num2" class="nav-it">
                        <div id="fsfc" class="nav-link">지식공유참여</div>
                        <ul id="subnav" class="submenu">

                            <li><a href="<%=request.getContextPath() %>/mentor/mentorRegister">멘토 등록하기</a></li>
                            <li><a href="<%=request.getContextPath() %>/classes/classUpload">클래스 개설하기</a></li>

                        </ul>
                    </li>
                </c:if> 
            </ul>               
            <!-- 우측헤더 -->   
            <ul id="navbar-nav2" class="hnavbar-nav justify-content-end">           
                <c:if test="${memid==null}">
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/member/login">로그인</a>
                    </li>
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/member/memberInput">회원가입</a>
                    </li>
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/help/qnaList">고객센터</a>
                    </li>
                </c:if>
        
                <c:if test="${memid!=null}">                
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/member/logout">로그아웃</a>
                    </li>   
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/member/myPage">마이페이지</a>
                    </li>
                    <li class="nav-it">
                        <a id="fsfc" class="nav-link" href="<%=request.getContextPath() %>/help/qnaList">고객센터</a>
                    </li>               
                </c:if> 
            </ul>   
            </div>  
        </nav>
    </div>
    <span class="menu-toggle-btn">
            <span></span>
            <span></span>
            <span></span>
        </span> 
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡEDN HEADERㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->
<sitemesh:write property='body'/>
</div>
    <div id="footer" class="footer">
        <p>개인정보처리방침 &nbsp;&nbsp;이용약관</p>
        <p>COPYRIGHT © 2022 KNOC ALL RIGHTS RESERVED.</p>
    </div>
</body>
</html>
<!-- ㅡㅡㅡㅡㅡㅡㅡㅡEND FOOTERㅡㅡㅡㅡㅡㅡㅡㅡㅡ -->