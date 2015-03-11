// JavaScript Document
	var settingsss = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,   //设置是否允许选中多个节点
				showIcon:true
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				renameTitle: "编辑",
				removeTitle: "删除",
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				onRemove: onRemove,
				onRename: onRename
			}
		};

	    var bzw=null;

		

		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function beforeEditName(treeId, treeNode) {
            bzw="editor";
				GlobalTreeid=treeId;
				GlobaltreeNode=treeNode;
				$('#danopenadd').dialog({
					title:'修改档案分类',
					iconCls:'icon-edit'	
				});
				openaddwindow();

				return false;

		}
		
		function beforeRemove(treeId, treeNode) {

			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		
		function onRemove(e, treeId, treeNode) {
			//删除后的回调函数

			$.ajax({
				type : "POST",
				url : "/archivesmanagement/InitZtreeAction!selectHaveArchives.action",
				data:{
					delId:treeNode.id
				},
				dataType : "json",
				async:false	
			}).done(function(data){
                   if(data.backJson!="00"){
                	   if(data.backJson=="02"){
                		   $.messager.alert('提示','类型下存在档案，请删除档案后再试');
                		   loadtree(); 
                		   return false;
                	   }
                   }else{
                	   delArchive(treeNode.id); 
                   }
				 console.log(data);
				});
		}
		
		function delArchive(id){
			$.ajax({
				type : "POST",
				url : "/archivesmanagement/InitZtreeAction!delTreeNode.action",
				data:{
					delId:id
					  },
				dataType : "json",
				async:false,
				
			}).done(function(data){
				if(data.backJson!="00"){
					 $.messager.alert('提示','删除失败,请刷新后重试');
					 return false;
				}
				 loadtree();
			});
		}
		
		
	
		
		function onRename(e, treeId, treeNode, isCancel) {
			
			
		}
		
		function showRemoveBtn(treeId, treeNode) {
			if(treeNode.pId=="0" || treeNode.pId==null){
				return false;
			}
			var childrenNodes=treeNode.children;
			if(childrenNodes==null || childrenNodes==""){
				return true;
			}else{
				return false;
			}
			
			
			   //判断当节点处于第一个时不显示删除按钮
		}
		
		function showRenameBtn(treeId, treeNode) {
			if(treeNode.pId=="0" || treeNode.pId==null){
				return false;
			}
			
			var childrenNodes=treeNode.children;
			if(childrenNodes==null || childrenNodes==""){
				return true;
			}else{
				return true;
			}
		}



		var newCountss = 1;
		var GlobalTreeid=null;
		var GlobaltreeNode=null;
		function addHoverDom(treeId, treeNode) {
			
			var sObjs = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtns_"+treeNode.tId).length>0) return;
			var addStrs = "<span class='button add' id='addBtns_" + treeNode.tId
				+ "' title='添加' onfocus='this.blur();'></span>";
			sObjs.after(addStrs);
			var btns = $("#addBtns_"+treeNode.tId);
			if (btns){ btns.bind("click", function(){
				bzw="adder";
				GlobalTreeid=treeId;
				GlobaltreeNode=treeNode;
				$('#danopenadd').dialog({
					title:'新增档案分类',
					iconCls:'icon-add'	
				});
				openaddwindow();
			});
			}
		}
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtns_"+treeNode.tId).unbind().remove();
		}
		
		var zNodes = {};
		$(document).ready(function(){
			laoutheight();
			loadtree();
			$("#xzda").css("display","");
		});
		
	var loadtree=function(){	
		$.ajax({
			type : "POST",
			url : "/archivesmanagement/InitZtreeAction!loadarchivalCategories.action",
			dataType : "json",
			async:false
		}).done(function(data){
			zNodes =data.treeList;
			$.fn.zTree.init($("#treeDemo"), settingsss, zNodes);
			});
	}
		/*
		 * 新增同级节点以及下级节点开窗
		 * */
		function openaddwindow(){
			$('#addarchives').form('clear');
			if(bzw=="adder"){
			addopenname.dateshow.value=initdate();
			addopenname.parentname.value=GlobaltreeNode.name;
			}else if(bzw=="editor"){
				addopenname.typename.value=GlobaltreeNode.name;
				addopenname.code.value=GlobaltreeNode.code;
				addopenname.dateshow.value=JsonDateformat(GlobaltreeNode.createTime);
				if(GlobaltreeNode.getParentNode()==null){}else{
				addopenname.parentname.value=GlobaltreeNode.getParentNode().name;}
			}
			$('#danopenadd').dialog('open');
		}
		
		
		function JsonDateformat(obj){
			var objdate=new Date(obj);
			return myformatter(objdate);
		}

		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
		
		/*
		 * 初始化时间格式
		 * */
		function initdate(){
            var date = new Date();
            var y = date.getFullYear();
            var mon = date.getMonth() + 1;
            var d = date.getDate();
            return y + '-' + mon + '-' + d;
		}
		
		/*
		 * 新增节点及修改节点保存
		 * */
		
		function addsave(){
			if(bzw=="adder"){
				if(addopenname.typename.value=="" || addopenname.typename.value==null){
					$.messager.alert('提示','类别名称不能为空！','',function(){
						addopenname.typename.focus();
					});
					return false;
				}
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var pId = GlobaltreeNode.id;
			var name = addopenname.typename.value;
			var code = addopenname.code.value;
			var nodes_type=zTree.getNodesByParam("name",name,GlobaltreeNode);
			if(nodes_type.length>0){
				$.messager.alert('提示','已存在同名分类，请勿重复！');
				return false;
			}
			$.ajax({
				type : "POST",
				url : "/archivesmanagement/InitZtreeAction!addTreeNode.action",
				data:{
					parentId : pId,
				    name : name,
				    createtime:addopenname.dateshow.value,
				    code:code
					  },
				dataType : "json",
				async:false,
				
			}).done(function(data){
				if(data.backJson!="00"){
					 $.messager.alert('提示','添加失败,请刷新后重试');
					 $('#danopenadd').dialog('close');
					 return false;
				}else{
				   $.messager.alert('提示','保存成功');
				   loadtree();
				}
			});
			
			$('#danopenadd').dialog('close');
			return false;
			}else if(bzw=="editor"){
				if(addopenname.typename.value=="" || addopenname.typename.value==null){
					$.messager.alert('提示','类别名称不能为空！','',function(){
						addopenname.typename.focus();
					});
					return false;
				}
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var pId = GlobaltreeNode.getParentNode().id;
				var id = GlobaltreeNode.id;
				var name = addopenname.typename.value;
				var code = addopenname.code.value;
				var nodes_type=zTree.getNodesByParam("name",name,GlobaltreeNode.getParentNode());
				if(nodes_type.length>1 || (nodes_type.length ==1 && nodes_type[0].id != id)){
					$.messager.alert('提示','已存在同名分类，请勿重复！');
					return false;
				}
				$.ajax({
					type : "POST",
					url : "/archivesmanagement/InitZtreeAction!updateTreeNode.action",
					data:{
						treeid:GlobaltreeNode.id,
						parentId : pId,
					    name : name,
					    createtime:addopenname.dateshow.value,
					    code:code
						  },
					dataType : "json",
					async:false,
					
				}).done(function(data){
					if(data.backJson!="00"){
						 $.messager.alert('提示','更新失败,请刷新后重试');
						 $('#danopenadd').dialog('close');
						 return false;
					}else{
						 $.messager.alert('提示','保存成功');
					   loadtree();
					}
				});
				
				$('#danopenadd').dialog('close');
				return false;
			}
		}
		
		/*
		 *自适应高度 
		 * */
		function laoutheight(){
			var itshow=$('#centershow').height();
			document.getElementById('contentshow').style.minHeight=itshow-320+"px";
			document.getElementById('contentshow').style.marginBottom="15px";
			document.getElementById('arciveguiheight').style.minHeight=itshow-280+"px";
			var itoo=$('#arciveguiheight').height();
			document.getElementById('archiveheight').style.minHeight=itoo+"px";
		}
		
		