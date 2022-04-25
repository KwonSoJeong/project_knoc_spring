$(document).ready(function(){
    $("#num1").hover(function(){
        $(this).children(".submenu").stop().slidetoggle("fast");
    });
    $(".submenu").hover(function(){
        $(this).children(".submenu").stop().slidetoggle("fast");
    });    
	$(".menu-toggle-btn").click(function() {
    	$(".hdiv").stop().slideToggle("fast");
	});

});