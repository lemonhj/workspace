$(function(){
	menuTreeinit();
	
});

function menuTreeinit(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/SysMenuAction_loadTreeListnode.action',
		data:{flag:true},
		success:function(data){
			$('#menuTree').tree({
				data:data.menuTreeList,
				animate:true,
			});
		}
	});
	
}