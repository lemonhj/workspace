// JavaScript Document
	var setting = {
			view: {
				addHoverDom: addHoverDomgl,
				removeHoverDom: removeHoverDomgl,
				selectedMulti: false,   //设置是否允许选中多个节点
				showIcon:true
			},
			edit: {
				enable: true,
				editNameSelectAll: false,
				showRemoveBtn: showRemoveBtngl,
				renameTitle: "编辑",
				removeTitle: "删除",
				showRenameBtn: showRenameBtngl
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDraggl,
				beforeEditName: beforeEditNamegl,
				beforeRemove: beforeRemovegl,
				onRemove: onRemovegl,
				onRename: onRenamegl
			}
		};

		

		function beforeDraggl(treeId, treeNodes) {
			return false;
		}
		
		function beforeEditNamegl(treeId, treeNode) {
			    bzwloc="editor";

					GlobalTreeidgl=treeId;
					GlobaltreeNodegl=treeNode;
					$('#postionopenadd').dialog({
						title:'修改档案位置管理',
						iconCls:'icon-edit'	
					});
					openaddwindowgl();
					return false;

		}
		
		function beforeRemovegl(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeManager");
			zTree.selectNode(treeNode);	
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		
		function onRemovegl(e, treeId, treeNode) {
			//删除后的回调函数
			$.ajax({
				type : "POST",
				url : "/archivesmanagement/ArchiveLocationAction!delTreeNode.action",
				data:{
					delId:treeNode.id
				},
				dataType : "json",
				async:false	
			}).done(function(data){
                   if(data.backJson!="00"){
                	   locloadtree();
                	   $.messager.alert('提示','节点下存在档案,请先删除档案后再删除此节点','',function(r){  
                		   return false;
                	   });
                	   
                   }else{
                	   locloadtree(); 
                   }
				 console.log(data);
				});
		}
		
	
		
		function onRenamegl(e, treeId, treeNode, isCancel) {
			
		}
		
		function showRemoveBtngl(treeId, treeNode) {
			if(treeNode.pId=="0" || treeNode.pId==null){
				return false;
			}
			
			if(treeNode.icon=="./img/box_picture.png"){
				return true;
			}
			
			if(treeNode.icon=="./img/box.png"){
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
		
		function showRenameBtngl(treeId, treeNode) {
			if(treeNode.pId=="0" || treeNode.pId==null){
				return false;
			}
			if(treeNode.pId=="1"){
				return true;
			}
			
			if(treeNode.icon=="./img/box_picture.png"){
				return true;
			}
			
			if(treeNode.icon=="./img/box.png"){
				return false;
			}
			
		}



		var newCount = 1;
		var GlobalTreeidgl=null;
		var GlobaltreeNodegl=null;
		function addHoverDomgl(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0 || treeNode.icon=="./img/box.png" || treeNode.icon=="./img/box_picture.png") return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				bzwloc="adder";
				GlobalTreeidgl=treeId;
				GlobaltreeNodegl=treeNode;
				$('#postionopenadd').dialog({
					title:'新增档案位置管理',
					iconCls:'icon-add'	
				});
				openaddwindowgl();
			});
		}
		
		function removeHoverDomgl(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		}
		
		var managerNodes ={};
		var bzwloc=null;
		var locloadtree=function(){
			$.ajax({
			type : "POST",
			url : "/archivesmanagement/ArchiveLocationAction!loadarchivalcation.action",
			dataType : "json",
			async:false
		}).done(function(data){
			console.log(data.treelist);
			managerNodes = data.treelist;
			});
			$.fn.zTree.init($("#treeManager"), setting, managerNodes);
			}
		
		$(document).ready(function(){
			locloadtree();
			$("#xzlx").css("display","");
		});
		
		
		/*
		 * 新增同级节点以及下级节点开窗
		 * */
		function openaddwindowgl(){
			$('#addlocation').form('clear');
			if(bzwloc=="adder"){
				addlocationopen.locdateshow.value=initdategl();
				addlocationopen.locparentname.value=GlobaltreeNodegl.name;
			}else if(bzwloc=="editor"){
				addlocationopen.loctypename.value=GlobaltreeNodegl.name;
				addlocationopen.locdateshow.value=JsonDateformat(GlobaltreeNodegl.createTime);
				if(GlobaltreeNodegl.getParentNode()==null){}else{
				addlocationopen.locparentname.value=GlobaltreeNodegl.getParentNode().name;}
			}
			$('#postionopenadd').dialog('open');
		}
		
		/*
		 * 初始化时间格式
		 * */
		function initdategl(){
            var date = new Date();
            var y = date.getFullYear();
            var mon = date.getMonth() + 1;
            var d = date.getDate();
            return y + '-' + mon + '-' + d;
		}
		
		/*
		 * 新增节点及修改节点保存
		 * */
		function addsavegl(){
			if(bzwloc=="adder"){
				if(addlocationopen.loctypename.value=="" || addlocationopen.loctypename.value==null){
					$.messager.alert('提示','类别名称不能为空！','',function(){
						addlocationopen.loctypename.focus();
					});
					return false;
				}
			var zTree = $.fn.zTree.getZTreeObj("treeManager");
			var pId = GlobaltreeNodegl.id;
			var viewtype=null;
			if(GlobaltreeNodegl.pId==0 || GlobaltreeNodegl.pId==null){
				viewtype=GlobaltreeNodegl.viewtype;
			}else{
				viewtype=GlobaltreeNodegl.viewtype+1;
			}
			var name = addlocationopen.loctypename.value;
			var nodes_type=zTree.getNodesByParam("name",name,GlobaltreeNodegl);
			if(nodes_type.length>0){
				$.messager.alert('提示','已存在同名档案室或柜，请勿重复！');
				return false;
			}
			$.ajax({
				type : "POST",
				url : "/archivesmanagement/ArchiveLocationAction!addTreeNode.action",
				data:{
					parentId : pId,
				    name : name,
				    viewtypes:viewtype,
				    locationStrname:GlobaltreeNodegl.locationStr+"-"+name,
				    createtime:addlocationopen.locdateshow.value
					  },
				dataType : "json",
				async:false,
				
			}).done(function(data){
				if(data.backJson!="00"){
					 $.messager.alert('提示','添加失败,请刷新后重试');
					 $('#postionopenadd').dialog('close');
					 return false;
				}else{
					locloadtree();
				}
			});
			
			$('#postionopenadd').dialog('close');
			return false;
			}else if(bzwloc=="editor"){
				if(addlocationopen.loctypename.value=="" || addlocationopen.loctypename.value==null){
					$.messager.alert('提示','类别名称不能为空！','',function(){
						addlocationopen.loctypename.focus();
					});
					return false;
				}
				var zTree = $.fn.zTree.getZTreeObj("treeManager");
				var pId = GlobaltreeNodegl.getParentNode().id;
				var name = addlocationopen.loctypename.value;
				var nodes_type=zTree.getNodesByParam("name",name,GlobaltreeNodegl.getParentNode());
				if(nodes_type.length>0){
					$.messager.alert('提示','已存在同名档案室或柜，请勿重复！');
					return false;
				}
				$.ajax({
					type : "POST",
					url : "/archivesmanagement/ArchiveLocationAction!updateTreeNode.action",
					data:{
						treeid:GlobaltreeNodegl.id,
						parentId : pId,
						viewtypes:GlobaltreeNodegl.viewtype,
					    name : name,
					    locationStrname:GlobaltreeNodegl.getParentNode().locationStr+"-"+name,
					    createtime:addlocationopen.locdateshow.value
						  },
					dataType : "json",
					async:false,
					
				}).done(function(data){
					if(data.backJson!="00"){
						 $.messager.alert('提示','更新失败,请刷新后重试');
						 $('#postionopenadd').dialog('close');
						 return false;
					}else{
						locloadtree();
					}
				});
				
				$('#postionopenadd').dialog('close');
				return false;
			}
		}
		
		
		
		
		
		
		
		
		