jQuery(document).ready(function($) {
	$('#openDiv').click(function(){
		$('.theme-popover-mask').fadeIn(100);
		$('.theme-popover').slideDown(200);
	});
	$('.theme-poptit .close').click(function(){
		$('.theme-popover-mask').fadeOut(100);
		$('.theme-popover').slideUp(200);
	});
	
	
	$('#openDiv').mouseover(function(){
		$('#openDiv').attr('src','common/images/login_btn_on.png');
	}).mouseout(function(){
		$('#openDiv').attr('src','common/images/login_btn_none.png');
	});
});
