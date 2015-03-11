var g_communityJson = null;
var treeId = "";
var dataGridId= "";
var treeObj = new Object();
$(function(){
	treeinit();
	$.ajax({
        type: 'get',
        url: '/propertybuildingmgmt/florBaseMgrAction_getComboboxData.action',
        dataType:"json",
        success: function (data) {
        	g_communityJson = data.getTCommunityJson;
        },
        error: function () {
            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
        }
    });
	
	$('#builCommunity').combotree({
		onClick: function(node){
			if(node.commno == null){
				return false;
			}
		}
	});
	//点击树节点是获取树节点下的所有楼栋信息
	$('#treeList').tree({
		onClick: function(node){
			if(node.endNode != 1){
				treeId = node.id;
			}else{
				treeId = "";
			}
			$.messager.progress({
			    text: '正在加载数据，请稍等...'
			});
			$.ajax({
				type : "get",
				url:"/propertybuildingmgmt/florBaseMgrAction_queryOnClickNodeGetList.action",
				dataType:"json",
				data : {
					treeNodeId : JSON.stringify(node.id)
				},
				success:function(data){
					$.messager.progress('close');
					$('#dg').datagrid('loadData', data.tbuListQuery);
				},
				error:function(){
					 $.messager.progress('close');
					 $.messager.alert("消息提示", "数据加载失败，请重新搜索！");	
				}
			});	
		}
	});
});


//获取列表
function getlist(){
	//楼栋区域JSON请求数据
	if(treeId != ""){
		$("#builCommunity").combotree("setValue",treeId);
		var record = $("#treeList").tree("getNode",treeId);
		var node = $('#treeList').tree('find',treeId);
		$('#treeList').tree('select',node.target);  
		$.ajax({
	        type: 'post',
	        url: '/propertybuildingmgmt/florBaseMgrAction_getTbuildDataToTreeid.action',
	        dataType:"json",
			data : {
				treeId : treeId
			},
	        success: function (data) {
	            $.messager.progress('close');
	            $('#dg').datagrid({"onLoadSuccess":function(data){
	            	for(var i=0;i<data.rows.length;i++){
	            		if(data.rows[i].builId == dataGridId){
	            			$(this).datagrid('selectRow',i);
	            			break;
	            		}
	            	}
	                
	            }}).datagrid("loadData",data.tbuList);
//	            $('#dg').datagrid("loadData",data.tbuList);
	        },
	        error: function () {
	            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
	        }
	    }); 
		return false;
	}
	$.ajax({
        type: 'get',
        url: '/propertybuildingmgmt/FloorTableAction_loadTreeListnode.action',
        dataType:"json",
        data:{flag:true},
        success: function (data) {
            $("#builCommunity").combotree({data:data.treelist});
            $("#builCommunity").combotree("setValue",data.treelist[0].id);
            $.messager.progress({
                text: '正在加载数据，请稍等...'
            });
        	$.ajax({
        	        type: 'post',
        	        url: '/propertybuildingmgmt/florBaseMgrAction_getTbuildingDataList.action',
        	        dataType:"json",
        	        success: function (data) {
        	            $.messager.progress('close');
        	            $('#dg').datagrid("loadData",data.tbuList);
        	        },
        	        error: function () {
        	            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
        	        }
        	    }); 
        },
        error: function () {
            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
        }
    });
	
}

function addOrUpdate(where){
	var iconStr = "";
	var titleStr = "";
	var getId = "";
	if($('#treeList').tree('getSelected')==null){
		$.messager.alert('提示','请选择区域');
		return false;
	}
	var treeNodeId = $('#treeList').tree('getSelected').id;
	if($('#treeList').tree('getSelected').build){
		treeId = treeNodeId;
	}
	if(where == "add"){
		$('#dg').datagrid('clearSelections');
		iconStr ='icon-application_add';
		titleStr = "新增";
	}
	if(where == "update"){
		iconStr ='icon-application_form_edit';
		titleStr = "修改";
		var record= $('#dg').datagrid('getSelections');
		if(record == null || record == ""){
			$.messager.alert("消息提示", "请选择您要修改的楼栋！");	
			return false;
		}else{
			getId = record[0].builId
		}
	}
	$('#win').window({
		title: titleStr,
		iconCls: iconStr,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		closable : false,
	    height:510,
		width: 980,
		modal: true,
	    content: '<iframe title="楼栋基础信息" width="960px" height="460px" boreder="0" frameborder=0  src="/propertybuildingmgmt/naviAction_floor_basic_mgr_addorupdata.action?getId='+getId+'"></iframe>'
	});
}

//删除
function  delectData(){
	var getRecord = $('#dg').datagrid('getSelections');
	
	if(getRecord.length >0){
		var treeNodeId = $('#treeList').tree('getSelected').id;
		$.messager.confirm("信息提示","删除后不可恢复，您确认想要删除所选的楼栋吗？",function(r){    
		    if (r){
		    	var arrs = "";
		        for(var i=0;i< getRecord.length; i++){
	        		if(i>0&&i<getRecord.length){
	        			arrs +=",'"+getRecord[i].builId+"'";
	        		}else{
	        			arrs +="'"+getRecord[i].builId+"'";
	        		}
		        }
		    	$.messager.progress({
		            text: '正在删除数据，请稍等...'
		        });
		    	$.ajax({
			        type: 'post',
			        url: '/propertybuildingmgmt/florBaseMgrAction_deleteTbuildingData.action?arrs='+arrs+"&treeNodeId="+treeNodeId,
			        dataType:"json",
			        success: function (data) {
			            $.messager.progress('close');
			            $.messager.alert("消息提示", "数据删除成功！");
			            if(data.tbuList.length<1){
			            	 $('#dg').datagrid('loadData', { total: 0, rows: [] });
			            }else{
			            	 $('#dg').datagrid("loadData",data.tbuList);
			            }
			        },
			        error: function () {
			            $.messager.alert("消息提示", "数据删除失败！");
			        }
			    });
		    }    
		});
	}else{
		$.messager.alert("消息提示", "请选择您要删除的楼栋！");	
		return false;		
	}
}
//关闭弹出的窗口
function closeWin(){
	$('#win').window("close");
}


//按条件进行查询
function queryData(){
	treeId = $("#builCommunity").combobox("getValue");
	$.messager.progress({
        text: '正在加载数据，请稍等...'
    });
	$('#dgformQuery').form('submit', {
		type:'post',
		url:"/propertybuildingmgmt/florBaseMgrAction_queryDataList.action",
		dataType : "json",
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if(isValid){
				return isValid;
			}
		},
		success:function(data){
			$.messager.progress('close');
			$('#winQuery').window('close');
			var result = JSON.parse(data);
			var node = $('#treeList').tree('find',treeId);
			$('#treeList').tree('select',node.target);
			$('#dg').datagrid('loadData', result.tbuListQuery);
		},
		error:function(){
			 $.messager.progress('close');
			 $.messager.alert("消息提示", "数据加载失败，请重新搜索！");	
		}
	});
}

//获取区域数据并填充到datagrid区域项中
function getDataValue(value,row,index){
	for(var oo in g_communityJson){
		if(value == g_communityJson[oo].commId)
			return g_communityJson[oo].commName
	}
}

//查询按钮功能实现
function queryWindosOpen(){
	dgformQuery.builName.value="";
	$('#winQuery').window({
		closed:false,
		title:'查询',
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		draggable: true,
		modal: true,
		shadow : true
	});
}

function treeinit(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadTreeListnode.action',
		data:{flag:true},
		success:function(data){
			$('#treeList').tree({
				data:data.treelist,
				animate:true,
				onLoadSuccess:function(node,data){
					//设置第一个节点高亮
//					$("#treeList li:eq(0)").find("div").addClass("tree-node-selected");
//		           var n = $("#treeList").tree("getSelected");  
//		           if(n!=null){
//		                $("#treeList").tree("select",n.target);    //相当于默认点击了一下第一个节点，执行onSelect方法  
//		           }  
				}
			});
		}
	});
}