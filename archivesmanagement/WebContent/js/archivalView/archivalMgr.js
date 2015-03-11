var archivalMgr = {};
var typeid=null;
var fileId;

$(document).ready(function(){ 
	laoutheight();
	archivalMgr.loadPage();
	$("#wj").css("display","");	
	$("#da").css("display","");	
	$("#hd").css("display","");		
	$(window).resize(function(){
		$('#fileTable').datagrid({
			width:$(window).width()*0.6,
		});
	});
	
	$(function () {
        $('#win').window({
            onBeforeClose: function () { //当面板关闭之前触发的事件
               refreshFileTab();
               return true;
            }


        });
    });
});

archivalMgr.loadPage = function(){
	archivalMgr.loadTree();
	$.fn.zTree.getZTreeObj("archivalTree").expandAll(false);
};

/**
 * 初始化新增加开窗值
 */
function initaddopen(){
	initdatefun();
	initsaveposition();
	$('#d').dialog('open');
}


/**
 * 加载档案树
 */
archivalMgr.loadTree = function(){
	$("#archivalTree").empty();
		var t = $("#archivalTree");
		var zNodes = {};
			var settings = {
					view: {
						dblClickExpand: false,
						addHoverDom: addHoverDom,
						removeHoverDom: removeHoverDom,
						selectedMulti: false   //设置是否允许选中多个节点
					},
					edit: {
						enable: true,
						showRemoveBtn: showRemoveBtn,
						renameTitle: "编辑档案盒",
						removeTitle: "删除档案盒",
						showRenameBtn: showRenameBtn
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onClick: zTreeOnClick,
						beforeDrag: beforeDrag,
						beforeEditName: beforeEditName,
						beforeRemove: beforeRemove,
						onRemove: onRemove,
						onRename: onRename,
						beforeClick : zTreeBeforeClick
					}
				};
			
			$.ajax({
				type : "POST",
				url : "/archivesmanagement/ArchiveCateAction!loadCategoryTree.action",
				dataType : "json",
				async:false
			}).done(function(data){
				zNodes = data.treeList;
				});
			$.fn.zTree.init(t, settings, zNodes);
		

};



function zTreeBeforeClick(treeId, treeNode, clickFlag) {
	if(treeNode.isParent){
		var treeObj = $.fn.zTree.getZTreeObj("archivalTree");
		if(treeNode.clickico){
			treeNode.clickico = false;
		}else{
			treeObj.expandNode(treeNode, !treeNode.open, false, true);
		}
		return false;
	}
	return true;
};

function beforeDrag(treeId, treeNodes) {
	return false;
}

function beforeEditName(treeId, treeNode) {
		if(!treeNode.isParent){
			var archiveId = treeNode.id.split("-")[1];
			typeid=treeNode.id.split("-")[0];
			$.ajax({
				type : "get",
				url : "/archivesmanagement/ArchivalAction!loadArchive.action",
				dataType : "json",
				data:{
					'archive.archiveId':archiveId,
				},
				async:false
			}).done(function(data){
				initaddopen();
				eidtorarchive(data.archive);
				$.fn.zTree.getZTreeObj("archivalTree").selectNode(treeNode);
				});
		}
	
		return false;

}

function beforeRemove(treeId, treeNode) {

	var zTree = $.fn.zTree.getZTreeObj("archivalTree");
	zTree.selectNode(treeNode);
	return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}

function onRemove(e, treeId, treeNode) {
	
	//删除后的回调函数delArchiveFlie()
    var obj=treeNode.id.split('-');
	$.ajax({
		type : "post",
		url : "/archivesmanagement/ArchivalAction!delArchiveFlie.action",
		dataType : "json",
		data:{
			delarchiveId:obj[1],
		},
		async:false
	}).done(function(data){
		if(data.backJson!="00"){
			$.messager.alert('提示','档案下存在文件，先删除文件再重试！');
			archivalMgr.loadTree();
			return false;
		}
		archivalMgr.loadTree();
		document.getElementById("archiveShow").style.display="none";
		});
	
}

function onRename(e, treeId, treeNode, isCancel) {
	
	
}

function showRemoveBtn(treeId, treeNode) {
       return !treeNode.isParent;
	   //判断当节点处于第一个时不显示删除按钮
}

function showRenameBtn(treeId, treeNode) {
	return !treeNode.isParent; //判断当节点处于最后一个时不显示编辑按钮
}

function addHoverDom(treeId, treeNode) {
	if(treeNode.pId==null){
		return false;
	}
//	if(!treeNode.isParent){
//		return false;
//	}
	var sObjs = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtns_"+treeNode.tId).length>0) return;
	var addStrs = "<span class='button add' id='addBtns_" + treeNode.tId
		+ "' title='添加档案盒' onfocus='this.blur();'></span>";
	sObjs.after(addStrs);
	var btns = $("#addBtns_"+treeNode.tId);
	if (btns){ btns.bind("click", function(){
		typeid=treeNode.id;
		treeNode.clickico = true;
		addinit();
		initaddopen();
		
		/**
		 * 需求修改，不用自动生成档案盒号，改为用户手动维护
		 */
		//initDId();
	});
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#addBtns_"+treeNode.tId).unbind().remove();
}

/*初始化盒号*/
function initDId(){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalAction!initDid.action",
		dataType : "json",
		data:{
			'archivesCategoryId':typeid,
		},
		async:false
	}).done(function(data){
		$('#dnhhid').val(idformatter(data.dId));
		});
}

/**
 * 树节点点击事件
 * @param event 事件
 * @param treeId 所点击树ID
 * @param treeNode 所点击树对象
 */
function zTreeOnClick(event, treeId, treeNode) {
	
	if(!treeNode.isParent){
		var archiveId = treeNode.id.split("-")[1];
		$.ajax({
			type : "get",
			url : "/archivesmanagement/ArchivalAction!loadArchive.action",
			dataType : "json",
			data:{
				'archive.archiveId':archiveId,
			},
			async:false
		}).done(function(data){
			archivalMgr.archive = data.archive;
			showArchive(data);
			showFileTable(data);
			document.getElementById('treeboxshow').style.minHeight=$('#boxshow').height()+"px";
			//异步去计算当前档案的导航code
			calCategoryCode(archivalMgr.archive.archiveId);
			});
		
	}
	
};


function updatesuccess(id){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalAction!loadArchive.action",
		dataType : "json",
		data:{
			'archive.archiveId':id,
		},
		async:false
	}).done(function(data){
		archivalMgr.archive = data.archive;
		showArchive(data);
		showFileTable(data.fileList);
		document.getElementById('treeboxshow').style.minHeight=$('#boxshow').height()+"px";
		updatetreename(data.archive);
		});
}
	


/**
 * 展示档案内容
 * @param archive 档案对象
 */
function showArchive(data){
	$("#archiveName").text(data.archive.archiveName);
	$("#directoryId").text(data.archive.directoryId);
	$("#personInCharge").text(data.archive.personInCharge);
	$("#archivesLocation").text(data.archive.archivesLocation.locationStr);
	$("#writtenDate").text(JsonDateformat(data.archive.writtenDate));
	$("#fileYear").text(data.archive.archivedYear);
	$("#securityLevel").text(securityLType[data.archive.securityLevel]);
	//$("#securityLevel").combobox({data:constructArr(securityLType,archive.securityLevel)});
	$("#fileDate").text(JsonDateformat(data.archive.fileDate));
	$("#retentionPeriod").text(retentionPType[data.archive.retentionPeriod]);
	$("#storeWay").text(storeWayType[data.archive.storeWay]);
	$("#destoryed").text(destoryedType[data.archive.destoryed]);
	$("#archiveState").text(archiveStateType[data.archive.archiveState]);
	$("#archivesCategory").text(data.archive.archivesCategory);
	$("#remarks").text(data.archive.remarks);
	if(null != data.fileList){
		$("#fileTotle").text(data.fileList.length);
		$("#pageTotle").text(getFilePages(data.fileList));
	}else{
		$("#fileTotle").text("0");
		$("#pageTotle").text("0");
	}
	$("#archiveShow").css("display","");
}



function showFileTable(data){
	var fileDescription = data.fileList;
	if(null == fileDescription){
		fileDescription = [];
	}
	$('#fileTable').datagrid({
		width:$(window).width()*0.59,
		height:$('#asd').height()*0.5,
		//sortName:'code',
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'fileCode',title:'文件编号',width:$(window).width()*0.6 * 0.12,align:'center'},
		    {field:'personInCharge',title:'责任者',width:$(window).width()*0.6 * 0.1,align:'center'},
		    {field:'fileName',title:'档案题名',width:$(window).width()*0.6 * 0.38,align:'center',formatter:function(value,row,index){
		    	return '<span style="float:left">'+value+'</span>';}},
		    {field:'writtenDate',title:'成文日期',width:$(window).width()*0.6 * 0.15,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
		    {field:'registrationTime',title:'登记日期',width:$(window).width()*0.6 * 0.15,align:'center',formatter:function(value,row,index){return JsonDateformat(value);}},
		    {field:'pages',title:'页数',width:$(window).width()*0.6 * 0.07,align:'center'},
		    {field:'remarks',title:'备注',width:$(window).width()*0.6 * 0.07,align:'center'}
		]],
		onDblClickRow:function(rowIndex, rowData){
			modifyFile(rowData.fileId);
		},
		rownumbers:true,
		singleSelect:false,
		autoRowHeight:true,
		pageSize:10,
		fitColumns:true,
		remoteSort:false,
		data:fileDescription
	});
	
}

/**
 * 打开打印目录窗体
 */
function printContent(){
	window.open('pdetail4.html','打印目录','alwaysRaised=yes,depended=yes,directories=no,height=600,Width=1000,location=no,menubar=no,scrollbars=yes');
}

function getFilePages(fileList){
	var totlePage=0;
	for(var i=0;i<fileList.length;i++){
		totlePage += fileList[i].pages; 
	}
	return totlePage;
}


function showFilePage(isCreate){
	$('#newFileForm').form('clear');
	//initAid();
	$('#w').window('open');
	$("#w").css("display","");
	$('#fileTabs').tabs('disableTab', 1);
	$('#fileTabs').tabs('select',0);
	fillFilePage();
	
}

function initAid(){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalFileAction!generateId.action",
		dataType : "json",
		data:{
			archiveId:archivalMgr.archive.archiveId,
		},
		async:false
	}).done(function(data){
		archivalMgr.archive.fileCode = data.fileCode;
		});
}

/**
 * 填充文件窗口
 * @param isCreate
 */
function fillFilePage(archiveFile){
	
	if(undefined != archiveFile){
		$("#new_fileName").val(archiveFile.fileName);
		$("#new_fileCode").val(archiveFile.fileCode);
		
		$("#new_fileState").combobox({data:constructArr(fileStateType,archiveFile.archived)});
		$("#new_securityLevel").combobox({data:constructArr(securityLType,archiveFile.securityLevel)});
		$("#new_storeWay").combobox({data:constructArr(storeWayType,archiveFile.storeWay)});
		$("#new_pages").val(archiveFile.pages);
		$("#new_remarks").val(archiveFile.remarks);
		$("#new_chargeInPerson").val(archiveFile.personInCharge);
		$("#new_fileId").val(archiveFile.fileId);
		//$('#new_code').val(idformatter(archiveFile.code));
		$('#new_code').val(archiveFile.code);


		
	}else{
		$("#new_pages").val("0");
		//$('#new_code').val(idformatter(archivalMgr.archive.fileCode));
		$("#new_fileState").combobox({data:constructArr(fileStateType,0)});
		$("#new_securityLevel").combobox({data:constructArr(securityLType,0)});
		$("#new_storeWay").combobox({data:constructArr(storeWayType,0)});
	}
	$("#new_categoryCode").val(archivalMgr.archive.archivesCategory.naviCode);
	$("#new_writtenDate").datebox({formatter:myformatter});
	$("#new_writtenDate").datebox('setValue',initdate());
	$('#new_regTime').datebox({formatter:myformatter});
	$('#new_regTime').datebox('setValue', initdate());
	$("#new_retentionP").combobox({data:constructArr(retentionPType,archivalMgr.archive.retentionPeriod)}).attr("disabled","true");
	
//	var val=$('#newFileForm').form('validate');
//	if(!val){
//		return false;
//	}
//	
}


/**
 * 保存文件
 */
function saveFile(){
	$('#newFileForm').form('submit', {
	    url:"/archivesmanagement/ArchivalFileAction!saveArchiveFile.action",
	    onSubmit: function(param){
	    	var val=$('#newFileForm').form('validate');
	    	if(!val){
	    		return false;
	    	}
	        param.archiveId = archivalMgr.archive.archiveId;
	       // param.fileCode=$("#new_code").val();
	    },
	    success:function(data){
	    	var data = JSON.parse(data);
	    	if(data.rep){
				$.messager.alert("提示","归档号重复！");
			}else{
				fileId = data.archiveFile.fileId;
				if(undefined != fileId){
					//$('#fileTabs').tabs('enableTab', 1);
					$.messager.alert('提示','保存成功！');
					//initAid();
					//$('#new_code').val(idformatter(archivalMgr.archive.fileCode));
					$("#new_fileName").val('');
					$("#new_code").val('');
					$("#new_chargeInPerson").val('');
					$("#new_fileCode").val('');
					$("#new_pages").val('');
					$('#fileTabs').tabs('disableTab', 1);
					reloaddatagrid();
					refreshPages(archivalMgr.archive.archiveId);
				}else{
					$.messager.alert('提示','保存失败！');
				}
			}
	    	
	    }
	});
}

/**
 * 删除文件
 */
function delFiles(){
	var selectFiles =  $('#fileTable').datagrid("getSelections");
	if(selectFiles.length > 0 ){
		var num = selectFiles.length;
		var delNums = "";
		for(var i=0;i<num;i++){
			delNums = delNums+","+selectFiles[i].fileId;
		}
		$.messager.confirm('提示','确定要删除'+num+'个文件吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "get",
					url : "/archivesmanagement/ArchivalFileAction!delArchiveFile.action",
					dataType : "json",
					data:{
						delNums:delNums
					},
					async:false
				}).done(function(data){
					fillFilePage(data.archiveFile);
					reloaddatagrid();
					refreshPages(archivalMgr.archive.archiveId);
					});
			}
		});
	}else{
		$.messager.alert('提示','请先选择需要删除的文件！');
	}
}

function cancelFilePage(){
	$('#newFileForm').form('clear');
	$('#w').window('close');
}



/**
 * 修改文件
 */
function modifyFile(fId){
	
	if(!fId){
		var selectFiles =  $('#fileTable').datagrid("getSelections");
		if(selectFiles.length !=1 ){
			 $.messager.alert('提示','请选择一个文件进行编辑！');
			 return;
			 }
		fileId = selectFiles[0].fileId;
	}else{
		fileId = fId;
	}
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalFileAction!loadArchiveFile.action",
		dataType : "json",
		data:{
			'archiveFile.fileId':fileId,
		},
		async:false
	}).done(function(data){
		fillFilePage(data.archiveFile);
		});
	$('#w').window('open');
	$('#fileTabs').tabs('select',0);
	$('#fileTabs').tabs('enableTab', 1);
	var val=$('#newFileForm').form('validate');
	if(!val){
		return false;
	}
	
}


/*
 * 加载开窗位置下拉列表
 * */
function initdate(){
	
	var date = new Date();
    var y = date.getFullYear();
    var mon = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + mon + '-' + d;
}


function initsaveposition(){
$.ajax({
	type : "POST",
	url : "/archivesmanagement/ArchiveLocationAction!loadEasylcation.action",
	dataType : "json",
	async:false
}).done(function(data){
	var treeobj = [];
	treeobj[0] = data.easytreelist;
	$('#dnwzid').combotree('loadData',treeobj);
	$('#dnmjid').combobox({data:constructArr(securityLType,0)});
	$('#dnbgqxid').combobox({data:constructArr(retentionPType,0)});
	$('#dncffsid').combobox({data:constructArr(storeWayType,0)});
	$('#dnxhid').combobox({data:constructArr(destoryedType,0)});
	$('#dnztid').combobox({data:constructArr(archiveStateType,0)});
	var widthtable=$('#tablewidth').width();
	document.getElementById("fileTable").style.width=widthtable+"px";
	});
}

/*
 * 保存或修改档案
 * */

function saveOrupdateArchive(){
	$('#archivalidform').form('submit',{
		url : '/archivesmanagement/ArchivalAction!savaArchiveFlie.action',
		onSubmit:function(param){
			var val=$('#archivalidform').form('validate');
			if(!val){
				return false;
			}
			param.archivLocationId=dnwzidselect();
			param.archivesCategoryId=typeid;
		},
		success:function(data){
			var returndata = JSON.parse(data);
			if(returndata.didRep){
				$.messager.alert('提示','盒号重复！');
			}else{
				$.messager.alert('提示','保存成功！');
				archivalMgr.loadTree();				
				var ztree = $.fn.zTree.getZTreeObj("archivalTree");
				var newNodeId = returndata.archive.archivesCategory.categoryId + "-" + returndata.archive.archiveId;
				var newNode = ztree.getNodeByParam('id',newNodeId);
				ztree.selectNode(newNode,false);
				document.getElementById('treeboxshow').style.maxHeight=$('#boxshow').height()+"px";
				showArchive(returndata);
				//showFileTable(returndata);
				$('#d').dialog('close');
			}
			
		}	
	});
}

/*
 * 选择树形节点触发select
 * */
function dnwzidselect(){
	var t = $('#dnwzid').combotree('tree');	// 获取树对象
	var n = t.tree('getSelected');	
	return n.id;
}
/**
 * 文件上传
 */
function fileUpload(){
//		$.messager.progress({ title:'Please waiting',  
//	        msg:'Loading data...'  
//	    });  
//		$("#uploadFile").form('submit', {
//			url:"/archivesmanagement/ArchivalFileAction!uploadFile.action",
//			onSubmit: function(param){
//				param.fileId = fileId;
//			},
//			success:function(data){
//				$.messager.progress('close');
//				var archiveFile = JSON.parse(data).archiveFile;
//				if(null == archiveFile || undefined == archiveFile){
//					$.messager.alert('提示','上传失败！请点击浏览，选择需要上传的文件！');
//				}else{
//					$.messager.alert('提示','上传成功！');
//					refreshFileTab();
//				}
//			}
//		});
	

	$('#win').window({content:'<iframe scrolling="auto" id="openIframe" frameborder="0"  src="/archivesmanagement/upload.jsp" style="width:100%;height:100%;"></iframe>'}).window('open');
	//$('#win').window({ width:600, height:400,href:'upload.jsp'});
	
}

/**
 * 删除上传的文件
 */
function delUploadFile(){
	var selected =  $('#storeFile').datagrid("getSelected");
	if(selected){
		$.messager.confirm('提示','确定要删除此个文件吗？',function(isDel){
			if(isDel){
				$.ajax({
					type : "get",
					url : "/archivesmanagement/StoreFileAction!delStoreFile.action",
					dataType : "json",
					data:{
						delNum:selected.storeFileId
					},
					async:false
				}).done(function(data){
					if(data.operateState){
						$.messager.alert('提示','删除成功！');
						
						//刷新文件列表
						refreshFileTab();
					}else{
						$.messager.alert('提示','删除失败！');
					}
					});
			}
		});
	}else{
		$.messager.alert('提示','请先选择要删除的文件！');
	}
}

/**
 * 下载存储文件
 */
function downloadSF(){
	var selected =  $('#storeFile').datagrid("getSelected");
	if(selected){
		window.location.href="/archivesmanagement/DownloadAction/DownloadAction.action"+"?fileName="+selected.filePath;
	}else{
		$.messager.alert('提示','请先选择要下载的文件！');
	}
	
}

/**
 * 导出Excel
 */
function exportExcel(){
	window.location.href="/archivesmanagement/Excel.action"+"?archiveId="+archivalMgr.archive.archiveId;
}



/*
 * 初始化时间
 * */
function initdatefun(){
	$('#dncjrqid').datebox({
		formatter:myformatter
	});
	$('#dngdrqid').datebox({
		formatter:myformatter
	});
	$('#archivalidform').form('enableValidation');
	
	$('#newFileForm').form('enableValidation');
}

/*
 * 修改开窗
 * */
function eidtorarchive(obj){
	$('#archivalidform').form('clear');
	$('#dnid').val(obj.archiveId);
	$('#dnname').val(obj.archiveName);
	$('#dnhhid').val(obj.directoryId);
	$('#dnzrid').val(obj.personInCharge);
	$('#dnwzid').combotree('setValue', obj.archivesLocation.locationId);
	$('#dncjrqid').datebox('setValue', obj.writtenDate);
	$('#dngdndid').val(obj.archivedYear);
	$('#dnmjid').combobox('select',obj.securityLevel);
	$('#dngdrqid').datebox('setValue',obj.fileDate);
	$('#dnbgqxid').combobox('select',obj.retentionPeriod);
	$('#dncffsid').combobox('select',obj.storeWay);
	$('#dnxhid').combobox('select',obj.destoryed);
	$('#dnztid').combobox('select',obj.destoryed);
	$('#dnremark').val(obj.remarks);
	var val=$('#archivalidform').form('validate');
	if(!val){
		return false;
	}

}

/*
 * 初始化新增开窗
 * */
function addinit(){
	$('#archivalidform').form('clear');
//	$('#dncjrqid').datebox('setValue', obj.writtenDate);
	$('#dngdndid').val(new Date().getFullYear());
	$('#dnmjid').combobox('select',0);
//	$('#dngdrqid').datebox('setValue',obj.fileDate);
	$('#dnbgqxid').combobox('select',0);
	$('#dncffsid').combobox('select',0);
	$('#dnxhid').combobox('select',0);
	$('#dnztid').combobox('select',0);
	var val=$('#archivalidform').form('validate');
	if(!val){
		return false;
	}
}


function refreshFileTab(){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/StoreFileAction!loadStoreFile.action",
		dataType : "json",
		async:true,
		data:{
			'fileId':fileId,
		},
	}).done(function(data){
		$('#storeFile').datagrid({
			data:data.asfList});
	});
}

var objdatagrid=null;
function openbelongArchive(){
	objdatagrid=$('#fileTable').datagrid('getSelections');
	if(objdatagrid.length>0){
		$('#belongArchive').dialog('open');
		var objtree=$('#belongArchiveTree');
		var ztreenodes;
		var settingstree = {
				view: {
					dblClickExpand: false,
					selectedMulti: false   //设置是否允许选中多个节点
				},
				edit: {
					enable: false,
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick:onclickztree
				}
			};
		
		$.ajax({
			type : "POST",
			url : "/archivesmanagement/ArchiveCateAction!loadCategoryTree.action",
			dataType : "json",
			async:false
		}).done(function(data){
			ztreenodes = data.treeList;
			});
		$.fn.zTree.init(objtree, settingstree, ztreenodes);
	}else{
	$.messager.alert('提示','请选择要换挡的文件');
	}
}

var belongtoid=null;
function onclickztree(event, treeId, treeNode){
	if(!treeNode.isParent){
		 var itemid=treeNode.id.split('-')[1];
	     belongtoid=itemid;
	}else{
		$.messager.alert('提示','此节点不是档案节点，请重新选择！','',function(r){
			belongtoid=null;
		});
	}
}

function savebelongtoarchive(){
	var strid=null;
	var codeNums = null;
	if(belongtoid==null){
		$.messager.alert('提示','请选择档案节点！','',function(r){
			return false;
		});
	}else if(belongtoid == archivalMgr.archive.archiveId){
		$.messager.alert('提示','此文件已经在该档案下！','',function(r){
			return false;
		});
	}
	else{
		for(var i=0;i<objdatagrid.length;i++){
			if(strid!=null)strid+=",";
			strid+=objdatagrid[i].fileId;
			if(codeNums!=null)codeNums+=",";
			codeNums += objdatagrid[i].code;
		}
		$.ajax({
			type : "POST",
			url : "/archivesmanagement/ArchivalFileAction!updateArchiveFilebelong.action",
			data:{
				delNums:strid,
				archiveId:belongtoid,
				codeNums:codeNums
			},
			dataType : "json",
			async:false
		}).done(function(data){
			if(data.codeNums){
				$.messager.alert('提示','待转档文件'+data.codeNums+'编号在目标档案中已存在，请先修改档案编号后转换！');
			}
			strid=null;
			belongtoid=null;
			$('#belongArchive').dialog('close');
			reloaddatagrid();
			});
	}
}


function reloaddatagrid(){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalAction!loadArchive.action",
		dataType : "json",
		data:{
			'archive.archiveId':archivalMgr.archive.archiveId,
		},
		async:false
	}).done(function(data){
		showFileTable(data);
		});
}


/*
 *自适应高度 
 * */
function laoutheight(){
	var itshow=$('#centershow').height();
	document.getElementById('contentshow').style.maxHeight=itshow-223+"px";
	document.getElementById('boxshow').style.minHeight=itshow-250+"px";
	document.getElementById('contentshow').style.marginBottom="15px";
	document.getElementById('treeboxshow').style.maxHeight=itshow-250+"px";
	document.getElementById('treeboxshow').style.minHeight=itshow-250+"px";
}


/*
 * 修改成功后修改树名称
 * */
function updatetreename(node){
	var obj=$.fn.zTree.getZTreeObj("archivalTree").getSelectedNodes();
	obj[0].name=node.directoryId;
	$.fn.zTree.getZTreeObj("archivalTree").updateNode(obj[0]);
}


//异步去计算当前档案的导航code
function calCategoryCode(archiveId){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalAction!createCategoryCode.action",
		dataType : "json",
		data:{
			"archive.archiveId":archiveId,
		},
		async:true
	}).done(function(data){
		archivalMgr.archive = data.archive;
		$("#naviCode").text(archivalMgr.archive.archivesCategory.naviCode);
	});
}

//异步刷新页数和文件数
function refreshPages(archiveId){
	$.ajax({
		type : "get",
		url : "/archivesmanagement/ArchivalAction!loadArchive.action",
		dataType : "json",
		data:{
			"archive.archiveId":archiveId,
		},
		async:true
	}).done(function(data){
		if(null != data.fileList){
			$("#fileTotle").text(data.fileList.length);
			$("#pageTotle").text(getFilePages(data.fileList));
		}else{
			$("#fileTotle").text("0");
			$("#pageTotle").text("0");
		}
	});
}

/**
 * 初始化上传控件
 */
function initUpload(){
	 $("#uploadify").uploadify({
	        //开启调试
	        'debug' : false,
	        //是否自动上传
	        'auto':false,
	        //超时时间
	        'successTimeout':99999,
	        //附带值
	        'formData':{
	            'userid':'用户id',
	            'username':'用户名',
	            'rnd':'加密密文'
	        },
	        //flash
	        'swf': "uploadify.swf",
	        //不执行默认的onSelect事件
	        'overrideEvents' : ['onDialogClose'],
	        
	        //文件选择后的容器ID
	        'queueID':'fileQueue',
	        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
	        'fileObjName':'fileName',
	        //上传处理程序
	        'uploader':'/archivesmanagement/uploadFile.action',
	        //浏览按钮的背景图片路径
	       // 'buttonImage':'${pageContext.request.contextPath}/js/jquery.uploadify/uploadify-cancel.png',
	        //浏览按钮的宽度
	        'width':'100',
	        //浏览按钮的高度
	        'height':'32',
	        //expressInstall.swf文件的路径。
	        //'expressInstall':'expressInstall.swf',
	        //在浏览窗口底部的文件类型下拉菜单中显示的文本
	        //'fileTypeDesc':'支持的格式：',
	        //允许上传的文件后缀
	       // 'fileTypeExts':'*.jpg;*.jpge;*.gif;*.png',
	        //上传文件的大小限制
	        'fileSizeLimit':'10000MB',
	        'buttonText' : '选择文件',//浏览按钮
	        //上传数量
	        'queueSizeLimit' : 25,
	        //每次更新上载的文件的进展
	        'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
	             //有时候上传进度什么想自己个性化控制，可以利用这个方法
	             //使用方法见官方说明
	        },
	        //选择上传文件后调用
	        'onSelect' : function(file) {
	        },
	        //返回一个错误，选择文件的时候触发
	        'onSelectError':function(file, errorCode, errorMsg){
	            switch(errorCode) {
	                case -100:
	                    alert("上传的文件数量已经超出系统限制的"+$('#uploadify').uploadify('settings','queueSizeLimit')+"个文件！");
	                    break;
	                case -110:
	                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#uploadify').uploadify('settings','fileSizeLimit')+"大小！");
	                    break;
	                case -120:
	                    alert("文件 ["+file.name+"] 大小异常！");
	                    break;
	                case -130:
	                    alert("文件 ["+file.name+"] 类型不正确！");
	                    break;
	            }
	        },
	        //检测FLASH失败调用
	        'onFallback':function(){
	            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
	        },
	        //上传到服务器，服务器返回相应信息到data里
	        'onUploadSuccess':function(file, data, response){
	        	//$('#uploadify').uploadify('upload');
	        	//alert(data);
	        }
	    });
}






