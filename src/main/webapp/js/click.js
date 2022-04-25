$(document).ready(function(){
    $(".no").click(function(){
	        $(this).hide();
	        $(".ya1").fadeIn();
	        $(".no2").show();
	});

    $(".no2").click(function(){
	        $(".no").show();
	        $(".ya1").hide();
	        $(this).hide();
	});

	

	$("#back").click(function(){
          $(this).hide();
          $("#ti").css("margin", "20px 0px 0px 32px");
    });

	



	$(".n1").click(function(){
	        $(this).hide();
	        $(".y1").show();
	});

    $(".y1").click(function(){
	        $(".n1").show();
	        $(this).hide();
	});	

	$(".n2").click(function(){
	        $(this).hide();
	        $(".y2").show();
	});

    $(".y2").click(function(){
	        $(".n2").show();
	        $(this).hide();
	});		
	
	$(".n3").click(function(){
	        $(this).hide();
	        $(".y3").show();
	});

    $(".y3").click(function(){
	        $(".n3").show();
	        $(this).hide();
	});		
		
	$(".n4").click(function(){
	        $(this).hide();
	        $(".y4").show();
	});

    $(".y4").click(function(){
	        $(".n4").show();
	        $(this).hide();
	});	
	
					
	$(".cc-fsfc1").click(function(){
	        $(".cc-index").fadeToggle(100);
	});
});