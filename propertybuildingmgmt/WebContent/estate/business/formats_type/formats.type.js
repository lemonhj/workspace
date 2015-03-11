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
		url:'propertybuildingmgmt/formatsType_loadFormatsTypes.action',
		async:false,
		dataType : "json",
		success:function(data){
			$('#dg').datagrid({
				data:data.listi,
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
			{field:'name',title:'类型名称',width:200},    
			{field:'color',title:'颜色',width:300,align:'center',
				formatter:function(value,row,index){
					return '<font style="font-size:40px;" color="'+value+'">■</font>';
				}
			}    
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
	}
	$('#win').window({
		title: titleStr,
		iconCls: iconStr,
		collapsible: false,
		minimizable: false,
		maximizable: false,
		resizable: false,
		draggable: false,
	    content: '<iframe width="502px" name="frameTab" height="200px" boreder="0" frameborder=0  src="/propertybuildingmgmt/naviAction_formats_type_addorupdata.action?where='+where+'&editId='+cid+'"></iframe>',
	});
}

//关闭窗口
function closeBtn(){
	$('#win').window('close');
}

//删除
function deleteBtn(){
	var selectFiles =  $('#dg').datagrid("getSelections");
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
							$('#dg').datagrid({data:data.listi});
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
							$('#dg').datagrid({data:data.listi});
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