//获取当前屏幕的宽高
var clientWidth = $(window).width();
var clientHeight =  $(window).height();

//初始化类型信息表
function datainit(){
	$.ajax({
		type:'post',
		url:'propertybuildingmgmt/SysRightsAction_loadAllRights.action',
		async:false,
		dataType : "json",
		success:function(data){
			$('#rightsList').datagrid({
				data:data.allRights,
			});
		}
	});
	
}

//创建easyui-datagrid
function gridList(){
	$('#rightsList').datagrid({
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
			{field:'id',title:'权限ID',width:200,align:'center'},    
			{field:'rightName',title:'权限名称',width:300,align:'center'},    
			{field:'rightUrl',title:'权限Url',width:300,align:'center'},    
			{field:'rightCode',title:'权限码',width:300,align:'center'},    
			{field:'rightDesc',title:'权限描述',width:300,align:'center'}    
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
	}
	//点击修改按钮
	if(where == "update"){
		if(!fid){
			var selectFiles =  $('#rightsList').datagrid("getSelections");
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
	}
	$('#rightwin').window({
		title: titleStr,
		iconCls: iconStr,
		width:400,
		height:400,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		draggable: true,
	    content: '<iframe scrolling="no" height="100%" name="frameTab"  boreder="0" frameborder=0  src="/propertybuildingmgmt/naviAction_addOrUpdateRights.action?where='+where+'&editId='+cid+'"></iframe>',
	});
}	

function fillData(cid){
	$.ajax({
		type:'post',
		url:'propertybuildingmgmt/SysRightsAction_loadAllRights.action',
		async:false,
		dataType : "json",
		data:{
			archiveId:archivalMgr.archive.archiveId,
		},
		success:function(data){
		}
	});
}

function saveORUpdateRight(){
	$('#rightForm').form('submit', {
	    url:"/propertybuildingmgmt/SysRightsAction_saveOrUpdateRight.action",
	    onSubmit: function(param){
	    	var val=$('#rightForm').form('validate');
	    	if(!val){
	    		return false;
	    	}
	    },
	    success:function(data){
	    	if(undefined != data){
				$.messager.alert('提示','保存成功！');
				backBtn();
			}else{
				$.messager.alert('提示','保存失败！');
			}
	    }
	});
	datainit();
}

/**
 * 删除权限
 */
function delRights(){
	var selectRights =  $('#rightsList').datagrid("getSelections");
	if(selectRights.length > 0 ){
		var num = selectRights.length;
		var delNums = "";
		for(var i=0;i<num;i++){
			delNums = delNums+","+selectRights[i].id;
		}
		$.messager.confirm('提示','确定要删除'+num+'个权限吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "get",
					url : "/propertybuildingmgmt/SysRightsAction_deleteRights.action",
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
		$.messager.alert('提示','请先选择需要删除的权限！');
	}
}


//关闭窗口
function closeBtn(){
	$('#rightwin').window('close');
}

//删除
function deleteBtn(){
	var selectFiles =  $('#rightsList').datagrid("getSelections");
	if(selectFiles.length == 0 ){
		 $.messager.alert('提示','请选择一个类型进行删除！');
		 return;
	}
	$.messager.confirm('提示','确定删除选择的'+selectFiles.length+'条类型？',function(r){
		if(r){
			$.messager.progress({
		        text: '正在删除数据，请稍等...'
		    });
			var temp = "";
			for(var i = 0;i<selectFiles.length;i++){
				temp += ","+selectFiles[i].id;
			}
			$.ajax({
				type:'post',
				url:'propertybuildingmgmt/formatsType_deleteTypes.action',
				async:false,
				dataType : "json",
				data:{delectIds:temp},
				success:function(data){
					$.messager.progress('close');
					if(data.cdNames.length == 0){
						$.messager.alert('提示','成功删除'+data.count+'条类型','',function(r){
							$('#rightsList').datagrid({data:data.listi});
						});
					}else{
						var str = '';
						for(var i=0;i<data.cdNames.length;i++){
							if(i == data.cdNames.length -1){
								str += data.cdNames[i];
							}else{
								str += data.cdNames[i] + ',';
							}
						}
						$.messager.alert('提示','删除类型失败!有'+str+'类型的公司存在,无法删除!','',function(r){
							$('#rightsList').datagrid({data:data.listi});
						});
					}
				},
				error:function(data){
					$.messager.progress('close');
				}
			});
		}
	});
}

function backBtn(){
	parent.closeBtn();
}