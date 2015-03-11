var current_user_type = '';
$(function(){
	$.ajax({
		url:'/propertybuildingmgmt/UserPermissionAction_loadUserType.action',
		type:'post',
		async:false,
		success:function(data){
			current_user_type = data.usertype;
		}
	});
});