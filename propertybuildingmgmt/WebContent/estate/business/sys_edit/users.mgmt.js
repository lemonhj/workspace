//获取当前屏幕的宽高
var clientWidth = $(window).width();
var clientHeight =  $(window).height();


$(function(){
	gridList();
	datainitUsers();
});

//初始化类型信息表
function datainitUsers(){
	$.ajax({
		type:'post',
		url:'propertybuildingmgmt/UserAuthorizeAction_loadAllUsers.action',
		async:false,
		dataType : "json",
		success:function(data){
			$('#dg').datagrid({
				data:data.allUsers
			});
		}
	});
}




//创建easyui-datagrid
function gridList(){
	$('#dg').datagrid({
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
		    {field:'suPassword',hidden:true},
		    {field:'suId',title:'用户ID',width:200,align:'center'},    
			{field:'suNick',title:'用户昵称',width:300,align:'center'},    
			{field:'suUsername',title:'登陆用户名',width:300,align:'center'},    
			{field:'roles',title:'角色',width:300,align:'center',formatter:function(value,rowData,index){
				var name="";
				for(var i in value){
					if(name!="")name+=","
					name += value[i].roleName;
				}
				return name;
			}} 
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
function addOrUp(where,fid){
	//选择条目的Id,点击新增按钮时为空
	var cid = '';
	var iconStr = "";
	var titleStr = "";
	//点击新增按钮
	if(where == "add"){
		iconStr ='icon-application_add';
		titleStr = "新增";
		document.getElementById("usersunameid").value="";
		document.getElementById("usernickid").value="";
		//document.getElementById("userpasswordid").disabled=false;
		loadrolestree();
	}
	//点击修改按钮
	if(where == "update"){
		if(!fid){
			var selectFiles =  $('#dg').datagrid("getSelections");
			if(selectFiles.length != 1){
				 $.messager.alert('提示','请选择一个类型进行修改！');
				 return;
			}
			cid = selectFiles[0].id;
		}else{
			cid = fid;
		}
		iconStr ='icon-application_form_edit';
		titleStr = "修改";
		document.getElementById("useridid").value=selectFiles[0].suId;
		document.getElementById("usersunameid").value=selectFiles[0].suUsername;
		document.getElementById("usernickid").value=selectFiles[0].suNick;
		document.getElementById("userpasswordid").value=selectFiles[0].suPassword;
		//document.getElementById("userpasswordid").disabled=true;
		loadrolestreeBYupdate(selectFiles[0].suId);
	}
	

	$('#userwin').dialog('open');
}

//关闭窗口
function closeBtn(){
	$('#userwin').dialog('close');
}


//初始化密码
function initpassword(){
	document.getElementById("userpasswordid").value="123456";
	return false;
}




/*
 * 角色加载
 * 
 */
 function loadrolestree(){
	 
	 $.ajax({
			type:'post',
			url:'propertybuildingmgmt/SysRolesAction_loadRolesTree.action',
			async:false,
			dataType : "json",
			success:function(data){
				$('#roletree').tree({
					data:data.roleTreeList
				});
				$('#hasroletree').tree('reload',null);
			}
		}); 
 }
 
 /*
  * 修改开窗
  * */
 
 function loadrolestreeBYupdate(id){
	 $.ajax({
			type:'post',
			url:'propertybuildingmgmt/SysRolesAction_loadRolesByUpdate.action',
			async:false,
			data:{
				userid:id
			},
			success:function(data){

				$('#roletree').tree({
					data:data.roleTreeList
				});
				$('#hasroletree').tree('loadData',data.userhasroles);
			}
		}); 
 }
 /*
  * 左移
  * */
 function leftmove(){
	 var node=$('#hasroletree').tree('getChecked');
	 if(node.length==0){
		 $.messager.alert('提示','请选择你已经获取的权限');
		 return false;
	 }
	     var list=new Array();
	     var obj=null;
	     for(var i=0;i<node.length;i++){
	     obj=new Object();
	     obj.id=node[i].id;
	     obj.text=node[i].text;
	     list.push(obj);
	     $('#hasroletree').tree('remove',node[i].target);
	     }
	     
     $('#roletree').tree('append',{
    	 parent:null,
    	 data:list
     });	 
 }
 
 /*
  *右移 
  * */
 function rightmove(){
	 var node=$('#roletree').tree('getChecked');
	 if(node.length==0){
		 $.messager.alert('提示','请选择权限');
		 return false;
	 }
     var list=new Array();
     var obj=null;
     for(var i=0;i<node.length;i++){
     obj=new Object();
     obj.id=node[i].id;
     obj.text=node[i].text;
     list.push(obj);
     $('#roletree').tree('remove',node[i].target);
     }

     
     $('#hasroletree').tree('append',{
    	 parent:null,
    	 data:list
     });	 	 
 }
 
 /*
  * 选择复选框树状态
  * */
 function onchecktree(node, checked){
	 $('#roletree').tree('select',node.target);
 }
 function onchecktreeother(node, checked){
	 $('#hasroletree').tree('select',node.target);
 }
 
 /*
  * 点击树状态
  * */
function onClicktree(node){
	 $('#roletree').tree('check',node.target);
}

function onClicktreeother(node){
	 $('#hasroletree').tree('check',node.target);
}


/*
 * 保存
 * */
function saverole(){
	var node=$('#hasroletree').tree('getRoots');
	if(node.length!=0){
		$('#userform').form('submit', {    
		    url:'propertybuildingmgmt/UserAuthorizeAction_addUser.action',    
		    onSubmit: function(param){    
		        param.rolestostr = JSON.stringify(node);   
		    },    
		    success:function(data){ 
		    	datainitUsers();
		    	$('#userwin').dialog('close');  
		    }    
		});  
	}
}

/*
 * 删除
 * */
//function deluser(){
//	var selectUsers =  $('#dg').datagrid("getSelections");
//	var strdelid = "";
//	for(var i in selectUsers){
//		if(strdelid!="")strdelid+=",";
//		strdelid+=selectUsers[i].suId;
//	}
//
//	if(selectUsers.length!=0){
//		$.messager.confirm('提示','确定要删除'+selectUsers.length+'个用户吗？',function(isDel){
//			if(isDel){
//				$.ajax({
//					type : "post",
//					url : "propertybuildingmgmt/UserAuthorizeAction_deleteUsers.action",
//					data:{
//						deluserid:strdelid
//					},
//					success:function(data){
//						datainitUsers();
//					}
//				});
//			}
//		});
//	}else{
//		$.messager.alert('提示','请先选择需要删除的用户！');
//	}
//}

/*
 * 删除
 * */
function deluser(){
	var selectUsers =  $('#dg').datagrid("getSelections");
	if(selectUsers.length==1){
		var strdelid = selectUsers[0].suId;
		$.messager.confirm('提示','确定要删除此用户吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "post",
					url : "propertybuildingmgmt/UserAuthorizeAction_deleteUser.action",
					data:{
						"user.suId":strdelid
					},
					success:function(data){
						datainitUsers();
					}
				});
			}
		});
	}else{
		$.messager.alert('提示','请选择一个需要删除的用户！');
	}
}


