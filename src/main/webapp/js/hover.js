$(document).ready(function(){
	$("#tog1").hover(function(){
        $(this).children(".submenu").stop().slideToggle("fast");
    });
	$("#tog2").hover(function(){
        $(this).children(".submenu2").stop().slideToggle("fast");
    });
    $(".submenu").hover(function(){
        $(this).children(".submenu").stop().slideToggle("fast");
    });    
	$(".menu-toggle-btn").click(function() {
    	$(".hdiv").stop().slideToggle("fast");
	});

});