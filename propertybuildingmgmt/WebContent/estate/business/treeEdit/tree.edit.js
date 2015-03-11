$(function(){
	treeinit();
	
});

function treeinit(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadTreeListnode.action',
		data:{flag:true},
		success:function(data){
			$('#tt').tree({
				data:data.treelist,
				animate:true,
			});
		}
	});
	
}

//新增窗口
function add(id){
	//清空错误提示
	$('#errorTips')[0].innerHTML = '';
	$('#tname').val('');
	var selectNode =  $('#tt').tree('getSelected'); 
	
	if(!id){
		if(selectNode == null){
			$.messager.alert('提示','请选择区域进行增加！');
			return;
		}
		id = selectNode.id;
		$('#parentName').val(selectNode.text);
	}
	
	//隐藏修改按钮
	$('#saveBtn').css('display','inline');
	$('#editBtn').css('display','none');
	
	//开窗
	$('#win').window({
		closed:false,
		title:'新增',
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		draggable: false,
	});
}

//新增区域
function addInfo(){
	//获取当前选取的节点
	var selectNode =  $('#tt').tree('getSelected'); 
	
	//非空验证
	if($('#tname').val() == ""){
		$('#errorTips')[0].innerHTML = '区域名不能为空!';
		return;
	}
	
	var re = /^[\w\u4e00-\u9fa5]+$/gi;
	if (!re.test($('#tname').val())) {  
		$('#errorTips')[0].innerHTML = '名称中不能包含特殊字符';
		return;
	}
	
	//宏立城下不能新建楼盘
	if(selectNode.id == 2){
		$.messager.alert('提示',selectNode.text+'下不能新建区域!');
		return;
	}
	
	
	//判断区域下是否有重名的区域存在
	for(var a in selectNode.children){
		if($('#tname').val() == selectNode.children[a].text){
			$('#errorTips')[0].innerHTML = '区域已存在！';
//			$.messager.alert('提示','区域已存在！');
			return;
		}
	}
	$.messager.progress({
        text: '正在新增区域，请稍等...'
    });
	//提交新增请求
	$.ajax({
		url:'/propertybuildingmgmt/FloorTableAction_addTcInfo.action',
		type:'post',
		data:{name:$('#tname').val(),tpid:selectNode.id}, 
		dataType:'json',
		success:function(data){
			$.messager.progress('close');
			if(data.flag){
				$.messager.alert('提示','区域增加成功！','',function(r){
					treeinit();
					$('#win').window('close');
				});
			}else{
				$.messager.alert('提示','区域增加失败,请重新添加!');
			}
		},
		error:function(data){
			$.messager.progress('close');
		}
	});
}

//修改窗口
function edit(id){
	//清空错误提示
	$('#errorTips')[0].innerHTML = '';
	var selectNode =  $('#tt').tree('getSelected'); 
	if(!id){
		if(selectNode == null){
			$.messager.alert('提示','请选择区域进行修改！');
			return;
		}
		$('#tname').val(selectNode.text);
		id = selectNode.id;
		$('#parentName').val($('#tt').tree('getParent',selectNode.target).text);
	}
	//隐藏保存按钮
	$('#saveBtn').css('display','none');
	$('#editBtn').css('display','inline');
	//开窗
	$('#win').window({
		closed:false,
		title:'新增',
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		draggable: false,
	});
}

//修改区域
function editInfo(){
	//获取当前选取的节点
	var selectNode =  $('#tt').tree('getSelected'); 
	
	//非空验证
	if($('#tname').val() == ""){
		$('#errorTips')[0].innerHTML = '区域名不能为空!';
		return;
	}
	
	var re = /^[\w\u4e00-\u9fa5]+$/gi;
	if (!re.test($('#tname').val())) {  
		$('#errorTips')[0].innerHTML = '名称中不能包含特殊字符';
		return;
	}
	
	//判断所选区域是否是自己
	
	//判断所选区域所属区域下是否有重名的区域存在
	for(var a in $('#tt').tree('getParent',selectNode.target).children){
		if($('#tname').val() == $('#tt').tree('getParent',selectNode.target).children[a].text){
			$('#errorTips')[0].innerHTML = '区域已存在！';
			return;
		}
	}
	$.messager.progress({
        text: '正在修改区域，请稍等...'
    });
	//提交修改请求
	$.ajax({
		url:'/propertybuildingmgmt/FloorTableAction_editTcInfo.action',
		type:'post',
		data:{name:$('#tname').val(),tid:selectNode.id},
		dataType:'json',
		success:function(data){
			$.messager.progress('close');
			if(data.flag){
				$.messager.alert('提示','区域修改成功！','',function(r){
					treeinit();
					$('#win').window('close');
				});
			}else{
				$.messager.alert('提示','区域修改失败,请重新修改!');
			}
		},
		error:function(data){
			$.messager.progress('close');
		}
	});
}

//删除区域
function deleteBtn(id){
	//获取所选区域
	var selectNode =  $('#tt').tree('getSelected'); 
	
	if(!id){
		if(selectNode == null){
			$.messager.alert('提示','请选择区域进行删除！');
			return;
		}
		id = selectNode.id;
	}
	//判断所选区域下是否有区域存在
	
	if(	selectNode.endNode == 1){
		$.messager.alert('提示',selectNode.text+'不能删除!');
		return;
	}
	
	if(selectNode.children != 0){
		$.messager.alert('提示',selectNode.text+'下有区域存在，请先删除'+selectNode.text+'下的所有区域！');
		return;
	}else{
		//提交删除请求
		$.messager.confirm('提示','是否要删除'+selectNode.text,function(r){
			if(r){
				$.messager.progress({
			        text: '正在删除区域，请稍等...'
			    });
				$.ajax({
					url:'/propertybuildingmgmt/FloorTableAction_deleteTcInfo.action',
					type:'post',
					dataType:'json',
					data:{tid:id},
					success:function(data){
						$.messager.progress('close');
						//data.code 是一个返回状态;0代表区域下游楼栋存在;1代表删除成功;
						if(data.code == '0'){
							$.messager.alert('提示',selectNode.text+'下有楼栋存在，请先删除'+selectNode.text+'下的所有楼栋！');
							return;
						}else if (data.code == '1'){
							$.messager.alert('提示','删除成功!','',function(r){
								treeinit();
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
}
