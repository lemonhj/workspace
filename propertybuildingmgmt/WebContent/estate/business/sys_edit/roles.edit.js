//获取当前屏幕的宽高
var clientWidth = $(window).width();
var clientHeight =  $(window).height();


$(function(){
	gridList();
	datainit();
});

//初始化类型信息表
function datainit(){
	$.ajax({
		type:'post',
		url:'propertybuildingmgmt/SysRolesAction_loadAllRoles.action',
		async:false,
		dataType : "json",
		success:function(data){
			$('#rolesTable').datagrid({
				data:data.allRoles
			});
			$('#menuTreeBox').tree({
				data:data.menuTreeList,
				animate:true
			});
		}
	});
	
}

//创建easyui-datagrid
function gridList(){
	$('#rolesTable').datagrid({
		singleSelect: false,
		remoteSort:false,
		rownumbers:true,
		height:clientHeight*0.84,
//		sortName:'id',
		columns:[[  
		    {field:'ck',checkbox:true},
//			{field:'id',title:'类型序号',width:200,sortable:true,
//		    	sorter:function(a,b){
//		    		if(a>b){return 1;}else{return -1;}
//		    	}
//			},    
		    {field:'id',title:'角色ID',width:200,align:'center'},    
			{field:'roleName',title:'角色名称',width:300,align:'center'},    
			{field:'roleValue',title:'角色名值',width:300,align:'center'},    
			{field:'roleDesc',title:'角色描述',width:300,align:'center'} 
	    ]],
	    onDblClickRow:function(rowIndex, rowData){
	    	addOrUp('update',rowData.id);
		}
	});

}

//获取页面传递的值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

//新增或修改
function addOrUpRole(where,fid){
	//选择条目的Id,点击新增按钮时为空
	var rid = '';
	var iconStr = "";
	var titleStr = "";
	//点击新增按钮
	if(where == "add"){
		iconStr ='icon-application_add';
		titleStr = "新增";
	}
	//点击修改按钮
	if(where == "update"){
		if(!fid){
			var selectFiles =  $('#rolesTable').datagrid("getSelections");
			if(selectFiles.length != 1){
				 $.messager.alert('提示','请选择一个类型进行修改！');
				 return;
			}
			rid = selectFiles[0].id;
		}else{
			rid = fid;
		}
		iconStr ='icon-application_form_edit';
		titleStr = "修改";
	}
	$('#rolewin').window({
		title: titleStr,
		iconCls: iconStr,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: true,
		draggable: true,
		width:500,
		height:400,
	    content: '<iframe width="502px" name="frameTab" height="400px" boreder="0" frameborder=0  src="/propertybuildingmgmt/naviAction_addOrUpdateRoles.action?where='+where+'&editId='+rid+'"></iframe>',
	});
}

function fillRoleWin(role){
	$("#roleName").val(role.roleName);
	$("#roleDesc").val(role.roleDesc);
	$("#roleId").val(role.id);
	
	var menus = role.sysMenus;
	for(var i=0;i<menus.length;i++){
		if(menus[i].menuUrl){
			var node = $('#menuTreeBox').tree('find', menus[i].menuId);
			$('#menuTreeBox').tree('check', node.target);
		}
	}
}

function saveORUpdateRole(){
	
	var menuTree = $('#menuTreeBox').tree('getChecked');
	var menusStr = "";
	for (i in menuTree){
		menusStr = menusStr + menuTree[i].id +",";
	}
	
	$('#roleForm').form('submit', {
	    url:"/propertybuildingmgmt/SysRolesAction_saveOrUpdateRole.action",
	    onSubmit: function(param){
	    	var val=$('#rightForm').form('validate');
	    	if(!val){
	    		return false;
	    	}
	    	param.menuList = menusStr;
	    },
	    success:function(data){
	    	if(undefined != data){
	    		var data = JSON.parse(data);
				$.messager.confirm('提示','保存成功！',function(r){
				    if (r){
				        backBtn();
				        window.parent.datainit();
				    }
				});
			}else{
				$.messager.alert('提示','保存失败！');
			}
	    }
	});
	
};


function backBtn(){
	parent.closeBtn();
}

function fillData(rid){
	$.ajax({
		type:'get',
		url:'propertybuildingmgmt/SysRolesAction!loadRole.action',
		async:false,
		dataType : "json",
		data:{
			'role.id':rid,
		},
		success:function(data){
			fillRoleWin(data.role);
		}
	});
	}
//关闭窗口
function closeBtn(){
	$('#rolewin').window('close');
}

/**
 * 删除多个角色
 */
function delRoles(){
	var selectRoles =  $('#rolesTable').datagrid("getSelections");
	if(selectRoles.length > 0 ){
		var num = selectRoles.length;
		var delNums = "";
		for(var i=0;i<num;i++){
			delNums = delNums+","+selectRoles[i].id;
		}
		$.messager.confirm('提示','确定要删除'+num+'个角色吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "get",
					url : "/propertybuildingmgmt/SysRolesAction_deleteRoles.action",
					dataType : "json",
					data:{
						delNums:delNums
					},
					async:false
				}).done(function(data){
					datainit();
					});
			}
		});
	}else{
		$.messager.alert('提示','请先选择需要删除的角色！');
	}
}

/**
 * 删除角色
 */
function delRole(){
	var selectRoles =  $('#rolesTable').datagrid("getSelections");
	if(selectRoles.length == 1 ){
		var delId = selectRoles[0].id;
		$.messager.confirm('提示','确定要删除这个角色吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "get",
					url : "/propertybuildingmgmt/SysRolesAction_deleteRole.action",
					dataType : "json",
					data:{
						"role.id":delId
					},
					async:false
				}).done(function(data){
					if(data.error == "error"){
						$.messager.alert('提示','您所删除的角色有用户关联，请先解除关联后删除！');
					}
					datainit();
				});
			}
		});
	}else{
		$.messager.alert('提示','请先选择一个需要删除的角色！');
	}
}