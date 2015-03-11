var isParent = true;
$(document).ready(function(){
	laoutheight();
	$.ajax({
		type : "POST",
		url : "/archivesmanagement/ArchiveLocationAction!loadEasylcation.action",
		dataType : "json",
		async:false
	}).done(function(data){
		var treeobj = [];
		treeobj[0] = data.easytreelist;		
		$('#tree_path_f').combotree({data:treeobj});
		$("#gjss").css("display","");
	});

	var node_path_f=$('#tree_path_f').combotree('tree').tree('getRoot');
	$("#tree_path_f").combotree('setValue',node_path_f.id);
	
	$.ajax({
		type : "POST",
		url : "/archivesmanagement/ArchiveCateAction!loadEasycategory.action",
		dataType : "json",
		async:false
	}).done(function(data){
		var treeobj = [];
		var treeobj1 = [];
		treeobj[0] = data.easytreelist;
		treeobj1[0] = data.easytreelist1;
		$('#tree_f').combotree('loadData',treeobj1);
	});


	$('#tree_f').combotree({
		onClick: function(node){
			isParent = node.parents;
		}
	});
	
	var node_f=$('#tree_f').combotree('tree').tree('getRoot');
	$("#tree_f").combotree('setValue',node_f.id);
	/*默认输入焦点*/
	$('#fileName').focus();
	/*回车事件*/
	$('#bd').keydown(function(e){ 
        var curKey = e.which; 
        if(curKey == 13){ 
            $('#search').click(); 
            return false; 
        } 
    });
	
	
	$('#as_files').keydown(function(e){ 
        var curKey = e.which; 
        if(curKey == 13){ 
            $('#fSearch').click(); 
            return false; 
        } 
    });
	/*监听窗口大小改变*/
	var resize_flag = false;
	var w = 0;
	$(window).resize(function(){
		resize_flag = !resize_flag;
		if(!resize_flag){
			window.setTimeout(function(){
				w = $('#navbar-example').width()*1;
				$('#dg').datagrid({
					width:w,
				});
			},300);			
		}
	});
	$('#dg').height($(window).height()*0.3);
});

var myview = $.extend({},$.fn.datagrid.defaults.view,{
	onAfterRender:function(target){
		$.fn.datagrid.defaults.view.onAfterRender.call(this,target);
		var opts = $(target).datagrid('options');
		var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
		vc.children('div.datagrid-empty').remove();
		if (!$(target).datagrid('getRows').length){
			var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
			d.css({
				position:'absolute',
				left:0,
				top:50,
				width:'100%',
				textAlign:'center'
			});
		}
	}
});

/*模糊查询*/
function searchFiles(){
	
	var fileName = $('#fileName').val();
	var searchType = $('#searchType').val();
	$.messager.progress({
	    text: '正在查询数据，请稍等...'
	});
	if(fileName.trim() != '' && fileName.trim() != null){
		if(searchType == '档案'){
			$.ajax({
				type:'post',
				url:'/archivesmanagement/ArchivalAction!searchArchive.action',
				dataType : "json",
				data:{
					'fileName':fileName,
				},
				async:false,
				success:function(data){
					$('#dg').datagrid({
						width:$('#navbar-example').width(),
						fitColumns:true,
						sortName:'directoryId',
						remoteSort:false,
						columns:[[
						    {field:'archive.archiveId',title:'档案盒号',width:$('#navbar-example').width() * 0.1,align:'center'},
						    {field:'directoryId',title:'盒号',width:$('#navbar-example').width() * 0.1,align:'center',sortable:true,formatter:function(value,row,index){return idformatter(value);},
						    	sorter:function(a,b){
						    		if(a>b){
						    			return 1;
						    		}else{
						    			return -1;
						    		}	
						    	}},
						    {field:'archiveName',title:'档案题名',width:$('#navbar-example').width() * 0.3,align:'center',formatter:function(value,row,index){return '<span style="float:left">'+value+'</span>';}},
						    {field:'locationStr',title:'保存位置',width:$('#navbar-example').width() * 0.3,align:'center'},
						    {field:'archiveState',title:'状态',width:$('#navbar-example').width() * 0.06,align:'center',formatter: function(value,row,index){return fileStateType[value];}},
						    {field:'fileDate',title:'归档时间',width:$('#navbar-example').width() * 0.225,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
						]]
					}).datagrid({
						data: data.list,
						nowrap:false,
						view:myview,
						emptyMsg: '没有符合此条件的档案存在!'
					});	
					
				}
			});
		}else if(searchType == '档案名'){
			$.ajax({
				type:'post',
				url:'/archivesmanagement/ArchivalFileAction!searchFiles.action',
				dataType : "json",
				data:{
					'fileName':fileName,
				},
				async:false,
				success:function(data){
					$('#dg').datagrid({
						width:$('#navbar-example').width(),
						fitColumns:true,
						sortName:'code',
						remoteSort:false,
						singleSelect:true,
						columns:[[
						    {field:'archive',title:'档案盒号',width:$('#navbar-example').width() * 0.1,align:'center',formatter:function(value,row,index){return value.directoryId;}},
						    {field:'code',title:'档案编号',width:$('#navbar-example').width() * 0.1,align:'center',sortable:true,formatter:function(value,row,index){return idformatter(value);},
						    	sorter:function(a,b){
							    		if(a>b){
							    			return 1;
							    		}else{
							    			return -1;
							    		}	
					    	}},
						    {field:'fileName',title:'档案题名',width:$('#navbar-example').width() * 0.3,align:'center',formatter:function(value,row,index){return '<span style="float:left">'+value+'</span>';}},
						    {field:'filePath',title:'保存位置',width:$('#navbar-example').width() * 0.2,align:'center'},
						    {field:'archived',title:'状态',width:$('#navbar-example').width() * 0.06,align:'center',formatter: function(value,row,index){return fileStateType[value];}},
						    {field:'registrationTime',title:'登记日期',width:$('#navbar-example').width() * 0.225,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
						]]
					}).datagrid({
						data: data.list,
						nowrap:false,
						view:myview,
						emptyMsg: '没有符合此条件的档案存在!'
					});
				}
			});
		}
	}
	$.messager.progress('close');
}

/*档案高级查询*/
function advancedSearch_a(){
	$('#as_archive').form('submit', {
		type:'post',
		url:'/archivesmanagement/ArchivalAction!advancedSearchArchive.action',
		dataType : "json",
		success:function(data){
			data = JSON.parse(data);
			$('#dg').datagrid({
				width:$('#navbar-example').width(),
				fitColumns:true,
				sortName:'directoryId',
				remoteSort:false,
				columns:[[
				    {field:'archive.directoryId',title:'档案盒号',width:$('#navbar-example').width() * 0.1,align:'center',formatter:function(value,row,index){return value.directoryId;}},
				    {field:'directoryId',title:'盒号',width:$('#navbar-example').width() * 0.1,align:'center',sortable:true,formatter:function(value,row,index){return idformatter(value);},
				    	sorter:function(a,b){
					    		if(a>b){
					    			return 1;
					    		}else{
					    			return -1;
					    		}	
			    	}},
				    {field:'archiveName',title:'档案题名',width:$('#navbar-example').width() * 0.3,align:'center',formatter:function(value,row,index){return '<span style="float:left">'+value+'</span>';}},
				    {field:'locationStr',title:'保存位置',width:$('#navbar-example').width() * 0.3,align:'center'},
				    {field:'archiveState',title:'状态',width:$('#navbar-example').width() * 0.06,align:'center',formatter: function(value,row,index){return fileStateType[value];}},
				    {field:'fileDate',title:'归档时间',width:$('#navbar-example').width() * 0.225,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
				]]
			}).datagrid({
				data: data.list,
				nowrap:false,
				view:myview,
				emptyMsg: '没有符合此条件的档案存在!'
			});
			$('#w').window('close');
		}
	});
}

/*档案高级查询*/
function advancedSearch_f(){
	$('#as_files').form('submit',{
		type:'post',
		url:'/archivesmanagement/ArchivalFileAction!advancedSearchArchive.action',
		dataType : "json",
		onSubmit:function(param){
			param.isParent = isParent;
		},
		success:function(data){
			data = JSON.parse(data);
			$('#dg').datagrid({
				width:$('#navbar-example').width(),
				fitColumns:true,
				sortName:'code',
				remoteSort:false,
				singleSelect:true,
				columns:[[
          			{field:'archive.archiveId',title:'档案盒号',width:$('#navbar-example').width() * 0.1,align:'center',formatter:function(value,row,index){return value.directoryId;}},
				    {field:'code',title:'档案编号',width:$('#navbar-example').width() * 0.1,align:'center',sortable:true,formatter:function(value,row,index){return idformatter(value);},
				    	sorter:function(a,b){
					    		if(a>b){
					    			return 1;
					    		}else{
					    			return -1;
					    		}	
			    	}},
				    {field:'fileName',title:'档案题名',width:$('#navbar-example').width() * 0.3,align:'center',formatter:function(value,row,index){return '<span style="float:left">'+value+'</span>';}},
				    {field:'filePath',title:'保存位置',width:$('#navbar-example').width() * 0.3,align:'center'},
				    {field:'archived',title:'状态',width:$('#navbar-example').width() * 0.06,align:'center',formatter: function(value,row,index){return fileStateType[value];}},
				    {field:'registrationTime',title:'登记日期',width:$('#navbar-example').width() * 0.225,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
				]]
			}).datagrid({
				data: data.list,
				nowrap:false,
				view:myview,
				emptyMsg: '没有符合此条件的档案存在!'
			});
			$('#w').window('close');
		}
	});
}


/*
 *自适应高度 
 * */
function laoutheight(){
	var itshow=$('#centershow').height();
	document.getElementById('contentshow').style.minHeight=itshow-223+"px";
	document.getElementById('contentshow').style.marginBottom="15px";
}