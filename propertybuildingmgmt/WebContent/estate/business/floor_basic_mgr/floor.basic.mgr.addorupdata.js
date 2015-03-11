var getAllBuildTfloorRoomData = new Object();//楼栋所有信息
var getMainDataId = 0;
var typeDataArrs = new Array();//组成楼栋类型combobox
var typeDataObj = new Object();//楼栋类型
typeDataObj.commId = 1;
typeDataObj.commName = "标准层";
typeDataArrs.push(typeDataObj);
var getComBoBoxValue= '';
$(function(){
	$('#builCommunity').combotree({
		onClick: function(node){
			if(node.endNode == 1){
				$('#builCommunity').combotree('clear');
				$.messager.alert("消息提示",node.text + '下不允许新建楼栋!');
			}
		}
	});
	
		$('#dg3').datagrid({
			toolbar: [{
				iconCls: 'icon-add',
				text:'新增',
				handler: function(){
					var getComBo = $("#typeData").combobox("getValue");
					if(getComBo == null || getComBo == ""){
						 $.messager.alert("消息提示", "请选择楼层类型！");
						 return false;						
					}else{
						append();
					}
				}
			}]
		});
		getMainDataId =ThisUrlParam()["getId"];
		//楼栋区域JSON请求数据
		$.ajax({
	        type: 'get',
	        url: '/propertybuildingmgmt/FloorTableAction_loadTreeListnode.action',
	        dataType:"json",
	        data:{flag:true},
	        success: function (data) {
	            $("#builCommunity").combotree({data:data.treelist});
//	            $('#tabId').tabs('disableTab', 1);
	            var tab_option = $('#tabId').tabs('getTab',"房型结构").panel('options').tab;
	            tab_option.hide();
	            if(ThisUrlParam()["getId"] != ""){
    	        	document.getElementById("backId").style.display = "none";
    	        	document.getElementById("saveId").style.display = "none";
		    		$.ajax({
		    	        type: 'post',
		    	        url: '/propertybuildingmgmt/florBaseMgrAction_getTFoolAndTBuildDataList.action?builId='+ThisUrlParam()["getId"],
		    	        dataType:"json",
		    	        success: function (data) {
		    	        	$("#dgform1").val("");
		    	            $.messager.progress('close');
		    	            var record = data.tbuQuery;
		    	            $("#builId").attr("value",record.builId);
		    	           	$("#builOrder").attr("value",record.builOrder);
		    	           	$("#builName").attr("value",record.builName);
		    	           	$("#builNo").attr("value",record.builNo);
		    	           	$("#builCommunity").combotree("setValue",record.builCommunity);
	//	    	           	$("#builCreate").attr("value",record.builCreate);
		    	           	$('#dataTime').datebox('setValue', record.builCreate);	// 设置日期输入框的值
		    	           	$("#buildBottom").attr("value",record.buildBottom);
		    	           	document.getElementById("buildBottom").disabled= "disabled";
		    	           	$("#builFloor").attr("value",record.builFloor);
		    	           	document.getElementById("builFloor").disabled= "disabled";
		    	           	$("#builMemo").attr("value",record.builMemo);
		    	            $('#dg2').datagrid("loadData",data.tFloorQuery);
	//	    	            $('#dg3').datagrid("loadData",data.troom);refreshBtnId
		    	            document.getElementById("refreshBtnId").style.display = "none";
		    	            document.getElementById("refreshBtnId").disabled= "disabled";
		    	            getTypeDataList(record.builId);
		    	            parent.treeId = record.builCommunity;
		    	        },
		    	        error: function () {
		    	            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
		    	        }
		    	    });
		        }else{
		        	if(parent.treeId != ""){
		        		$("#builCommunity").combotree("setValue",parent.treeId);
		        	}
		        	document.getElementById("builCommunity").focus();
		        	document.getElementById("refreshBtnId").disabled = false;
		        	document.getElementById("nextId").style.display = "";
		        	document.getElementById("backId").style.display = "none";
		        	document.getElementById("saveId").style.display = "none";
		        }
	        },
	        error: function () {
	            $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
	        }
	    });
		$('#dataTime').datebox({
			formatter: function(date){
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				return y+"-"+ m+'-'+d;
		    }
		});
		$('#dataTime').datebox('setValue',new Date());
})
//点击获取日期并处理日期格式问题
function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		}
function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
//获取页面传过来的参数
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//更新楼层信息
function  refreshBtn(){
	var getBuilFloor =  document.getElementById("builFloor").value;
	var getBuildBottom =  document.getElementById("buildBottom").value;
	if(getBuildBottom == "") getBuildBottom = 0;
	var getCount = parseInt(getBuilFloor)+parseInt(getBuildBottom);
	var tFloorArr = new Array();
	for(var i=0;i<parseInt(getBuilFloor)+parseInt(getBuildBottom);i++){
		var obj = new Object();
		if(i<parseInt(getBuildBottom)){
			obj.flooOrder = (-parseInt(getBuildBottom)+i);
			obj.flooNo = (parseInt(obj.flooOrder))+"F";
		}else{
			obj.flooOrder = ((i+1)-parseInt(getBuildBottom));
			obj.flooNo = (parseInt(obj.flooOrder))+"F";
		}
		obj.flooType = 1;
		tFloorArr.push(obj);
	}
	getAllBuildTfloorRoomData.tFloor = tFloorArr;
	if(getAllBuildTfloorRoomData.tFloor != null){
		$('#dg2').datagrid('loadData', getAllBuildTfloorRoomData.tFloor);
		$("#typeData").combobox("loadData",typeDataArrs);
	}
	
}
//保存
function saveData() {
	if(editorids!=null)$('#dg3').datagrid('endEdit',editorids);
	var objTbuil = new Object();
	objTbuil.builId = dgform1.builId.value;
	objTbuil.builName = dgform1.builName.value;
	objTbuil.builCommunity = dgform1.builCommunity.value;
	objTbuil.builMemo = dgform1.builMemo.value;
	objTbuil.builFloor = dgform1.builFloor.value;
	objTbuil.buildBottom = dgform1.buildBottom.value;
	objTbuil.builCreate = dgform1.builCreate.value;
	parent.treeId = dgform1.builCommunity.value;
    if(objTbuil.builName == null&& objTbuil.builCommunity == null &&objTbuil.builFloor == null && objTbuil.buildBottom == null){
    	$.messager.alert("消息提示", "数据填写不完整，请填写完整在保存！");
    	return false;
    }
    getAllBuildTfloorRoomData.tbuil = objTbuil;
    var tFloorArr = $('#dg2').datagrid("getRows");
    getAllBuildTfloorRoomData.tFloor = tFloorArr;
    for(var i= 0; i<typeDataArrs.length;i++){
    	if((typeDataArrs[i].commId == 1)&& (getAllBuildTfloorRoomData.bzc == null || getAllBuildTfloorRoomData.bzc == undefined)){
    		$.messager.alert("消息提示", "【标准层】房型列表为空，请新增标准层房型信息！");
    		return false;
    	}else if((typeDataArrs[i].commId == 2)&& (getAllBuildTfloorRoomData.bnc == null || getAllBuildTfloorRoomData.bnc == undefined)){
    		$.messager.alert("消息提示", "【避难层】房型列表为空，请新增避难层房型信息！");
    		return false;
    	}else if((typeDataArrs[i].commId == 4)&& (getAllBuildTfloorRoomData.tsc1 == null || getAllBuildTfloorRoomData.tsc1 == undefined)){
    		$.messager.alert("消息提示", "【特殊层1】房型列表为空，请新增特殊层1房型信息！");
    		return false; 		
    	}else if((typeDataArrs[i].commId == 5)&& (getAllBuildTfloorRoomData.tsc2 == null || getAllBuildTfloorRoomData.tsc2 == undefined)){
    		$.messager.alert("消息提示", "【特殊层2】房型列表为空，请新增特殊层2房型信息！");
    		return false; 		
    	}
    }
    //判断各种房型的面积是否为空
    if(getAllBuildTfloorRoomData.bzc != null && getAllBuildTfloorRoomData.bzc != undefined){
    	for(var i=0;i<getAllBuildTfloorRoomData.bzc.length;i++){
    		if((getAllBuildTfloorRoomData.bzc[i].roomArea == null) || (parseInt(parseFloat(getAllBuildTfloorRoomData.bzc[i].roomArea))<0) ){
        		$.messager.alert("消息提示", "【标准层】房型列表中房型编号为：【"+getAllBuildTfloorRoomData.bzc[i].roomOrder+"】房间面积填写错误，请重新填写房间面积！");
        		return false;	 			
    		}
    	}
    }
    if(getAllBuildTfloorRoomData.bnc != null && getAllBuildTfloorRoomData.bnc != undefined){
    	for(var i=0;i<getAllBuildTfloorRoomData.bnc.length;i++){
    		if((getAllBuildTfloorRoomData.bnc[i].roomArea == null ) || (parseInt(parseFloat(getAllBuildTfloorRoomData.bnc[i].roomArea)) <0) ){
    			$.messager.alert("消息提示", "【避难层】房型列表中房型编号为：【"+getAllBuildTfloorRoomData.bnc[i].roomOrder+"】房间面积填写错误，请重新填写房间面积！");
    			return false;	 			
    		}
    	}
    }
    if(getAllBuildTfloorRoomData.tsc1 != null && getAllBuildTfloorRoomData.tsc1 != undefined){
    	for(var i=0;i<getAllBuildTfloorRoomData.tsc1.length;i++){
    		if((getAllBuildTfloorRoomData.tsc1[i].roomArea == null) || (parseInt(parseFloat(getAllBuildTfloorRoomData.tsc1[i].roomArea)) <0) ){
    			$.messager.alert("消息提示", "【特殊层1】房型列表中房型编号为：【"+getAllBuildTfloorRoomData.tsc1[i].roomOrder+"】房间面积填写错误，请重新填写房间面积！");
    			return false;	 			
    		}
    	}
    }
    if(getAllBuildTfloorRoomData.tsc2 != null && getAllBuildTfloorRoomData.tsc2 != undefined){
    	for(var i=0;i<getAllBuildTfloorRoomData.tsc2.length;i++){
    		if((getAllBuildTfloorRoomData.tsc2[i].roomArea == null) || (parseInt(parseFloat(getAllBuildTfloorRoomData.tsc2[i].roomArea))<0) ){
    			$.messager.alert("消息提示", "【特殊层2】房型列表中房型编号为：【"+getAllBuildTfloorRoomData.tsc2[i].roomOrder+"】房间面积填写错误，请重新填写房间面积！");
    			return false;	 			
    		}
    	}
    }
    $.messager.progress({
	    text: '正在保存数据，请稍等...'
	});
	$.ajax({
		type : "post",
		url : "/propertybuildingmgmt/florBaseMgrAction_saveOrUpdataTBTfTRoom.action",
		dataType : "json",
		data : {
			builfloorRoom : JSON.stringify(getAllBuildTfloorRoomData)
		},
        success: function (data) {
        	$.messager.progress('close');
        	parent.dataGridId  = data.tbuiltfloortroom.tbuil.builId;
			$.messager.alert("消息提示", "数据保存成功！");
			shoutWinClose();
			editorids=null;
        },
        error: function () {
        	$.messager.progress('close');
            $.messager.alert("消息提示", "数据保存失败，请重新保存！");
        }
	});
}

var editIndex = undefined;
var editIndex1 = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#dg2').datagrid('validateRow', editIndex)){
		$('#dg2').datagrid('endEdit', editIndex);
		editIndex = true;
		return true;
	} else {
		return false;
	}
}
JsonUtil.convertToString()
function onClickCellRow(){
	if (editIndex == undefined){return true}
	if ($('#dg2').datagrid('validateRow', editIndex)){
		$('#dg2').datagrid('endEdit', editIndex);
	}
	$('#dg2').datagrid('unselectRow', editIndex);
}


function endEditing1(){
	if (editIndex1 == undefined){return true}
	if ($('#dg3').datagrid('validateRow', editIndex1)){
		$('#dg3').datagrid('endEdit', editIndex1);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
} 

function onClickCell(index, field){
	if (endEditing()){
		$('#dg2').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
}
function onClickCell1(index, field,value){
	if (endEditing1()){
		$('#dg3').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		editIndex1 = index;
	}
}
//处理下拉框问题
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

//新增楼层数量
function append(){
	var getValue = $("#typeData").combobox("getValue");
	if(getValue == null || getValue == ""){
		$.messager.alert("消息提示", "请选择楼层类型！");
		return false;
	}
	$('#win1').window({
		title: '房型新增',
		collapsible: false,
		minimizable: false,
		maximizable: false,
		closable : false,
	    height:210,
		width: 400,
		modal: true,
	    content: '<iframe title="房型新增" width="380px" height="160px" boreder="0" frameborder=0  src="/propertybuildingmgmt/naviAction_floor_basic_mgr_addroom.action?getType='+getValue+'"></iframe>'
	});
}

//获取新增的楼层结构数据并保存
/*
function saveAddData(){
	$('#dg3').datagrid('acceptChanges');
	var record = $('#dg3').datagrid('selectAll');
	var record1 = $('#dg3').datagrid('getSelections');
	if(record1.length==0)return false;
	
	$.messager.progress({
		text : '正在保存数据，请稍等...'
	});
	$.ajax({
		type : "post",
		url : "/propertybuildingmgmt/saveOrUpdataTRoom!saveOrUpdataTRoom.action",
		dataType : "json",
		data : {
			troomUpdate : JSON.stringify(getAllBuildTfloorRoomData),
			getMainBuildId: getMainDataId
		},
        success: function (data) {
			$.messager.progress('close');
			var getComValue = $("#typeData").combobox("getValue");
			loadList(getComValue);
			$.messager.alert("消息提示", "数据保存成功！");
        },
        error: function () {
        	$.messager.progress('close');
            $.messager.alert("消息提示", "数据保存失败，请重新保存！");
        }
	});
	$('#dg3').datagrid('uncheckAll');
}
*/
//删除楼层信息数据
/*
function deleteTRoom(){
	var getRecord = $('#dg3').datagrid('getSelections');
	if(getRecord.length >0){
		$.messager.confirm('信息提示','删除后不可恢复，您确认想要删除记录吗？',function(r){    
			if(r){
				for(var i=0;i<getRecord.length;i++){
					var rowIndex = $('#dg3').datagrid('getRowIndex', getRecord[i]);
					$('#dg3').datagrid('deleteRow',rowIndex);
				}
				var record = $('#dg3').datagrid('getRows');
				var arrs = new Array();
				for(var k=0;k<record.length;k++){
					var obj1 = new Object();
					obj1 = record[k];
					obj1.roomOrder = k+1;
					arrs.push(obj1);
				}
				var getValue = $("#typeData").combobox("getValue");
				if(getValue == 1){
					getAllBuildTfloorRoomData.bzc = arrs;
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bzc);
				}else
				if(getValue == 2){
					getAllBuildTfloorRoomData.bnc = arrs;
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bnc);
				}else
				if(getValue == 3){
					getAllBuildTfloorRoomData.none = arrs;
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.none);
				}else
				if(getValue == 4){
					getAllBuildTfloorRoomData.tsc1 = arrs;
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc1);
				}else
				if(getValue == 5){
					getAllBuildTfloorRoomData.tsc2 = arrs;
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc2);
				}
			}
		});
	}else{
		$.messager.alert("消息提示", "请选择您要删除的记录(可多选)！");	
		return false;		
	}
}*/

$('#builCommunity').combobox({
	formatter: function(row){
		var opts = $(this).combobox('options');
		return row[opts.textField];
	}
});


//关闭窗口
function shoutWin(){
	$.messager.confirm('信息提示','编辑状态改变，编辑的数据不可恢复，您确认想要取消编辑吗？',function(r){
		if(r){
			parent.$('#win').window('close');
		}
	})
}

//关闭窗口
function shoutWinClose(){
	parent.getlist();
	parent.$('#win').window('close');
}
//获取楼层的数据
function  getTypeDataList(getId){
	if(getId == "" || getId == null ) return null;
	$.ajax({
	    type: 'get',
		url: '/propertybuildingmgmt/florBaseMgrAction_getComboboxTypeData.action',
		dataType:"json",
		data : {
			getTypeMainId : getId
		},
		success: function (data) {
		    $("#typeData").combobox("loadData",data.getTypeList);
//		    $("#typeData").combobox("select",data.getTypeList[0].commId);
//		    getAllBuildTfloorRoomData  = data.returnTRoom;
    		typeDataArrs = data.getTypeList;
			if(data.returnTRoom.bzc.length>0){
				getAllBuildTfloorRoomData.bzc = data.returnTRoom.bzc;
			}
			if(data.returnTRoom.bnc.length>0){
				getAllBuildTfloorRoomData.bnc = data.returnTRoom.bnc;
			}
			if(data.returnTRoom.tsc1.length>0){
				getAllBuildTfloorRoomData.tsc1 = data.returnTRoom.tsc1;
			}
			if(data.returnTRoom.tsc2.length>0){
				getAllBuildTfloorRoomData.tsc2 = data.returnTRoom.tsc2;
			}
		    var getComBo = $("#typeData").combobox("getValue");
		    loadList(getComBo);
		},
		error: function () {
		    $.messager.alert("消息提示", "数据加载失败，请刷新页面！");
	    }
	});
}

function selectData(){
	 var getComBo = $("#typeData").combobox("getValue");
	loadList(getComBo);
}

function loadList(getComValue){
	if(getComValue == 1 && getAllBuildTfloorRoomData.bzc!= undefined ){
		$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bzc);
	}else
	if(getComValue == 2 && getAllBuildTfloorRoomData.bnc!= undefined){
		$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bnc);
	}else
	if(getComValue == 4&& getAllBuildTfloorRoomData.tsc1!= undefined){
		$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc1);
	}else
	if(getComValue == 5&& getAllBuildTfloorRoomData.tsc2!= undefined){
		$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc2);
	}else{
		 $('#dg3').datagrid('loadData', { total: 0, rows: [] });
	}
}
//上一步
function backBtn(){
	$('#tabId').tabs('enableTab', 0);
	$("#tabId").tabs("select", 0);
    var tab_option = $('#tabId').tabs('getTab',"主信息").panel('options').tab;
    tab_option.show();
    var tab_option = $('#tabId').tabs('getTab',"房型结构").panel('options').tab;
    tab_option.hide();
	document.getElementById("saveId").style.display = "none";
	document.getElementById("backId").style.display = "none";
	document.getElementById("nextId").style.display = "";
}
//下一步
function nextBtn(){
	var builCommunity = dgform1.builCommunity.value;
	
	if($('#builName').validatebox('isValid') == false){
		return;
	}
	if(builCommunity == ""){
		$.messager.alert("消息提示", "请选择区域！");
		return false;
	}
	var builName = dgform1.builName.value;
	if(builName == ""){
		$.messager.alert("消息提示", "请填写楼栋名称！");
		return false;
	}
	var builFloor = dgform1.builFloor.value;
	if(builFloor == ""){
		$.messager.alert("消息提示", "请填写楼栋层数！");
		return false;
	}
	
    var tFloorArr1 = $('#dg2').datagrid("getRows");
	if(!(tFloorArr1.length>0)){
		$.messager.alert("消息提示", "请点击【生成楼层层数】按钮生成生成楼层层数信息！");
		return false;
	}
	
	var arrayFlooType = new Array();
	for(var j=0;j<tFloorArr1.length;j++){
		if((parseInt(tFloorArr1[j].flooType) != 3)){
			arrayFlooType.push(parseInt(tFloorArr1[j].flooType));
		}else{
			continue;
		}
	}

	var  typeDataArrsNew = typeDataFn(arrayFlooType);
	$("#typeData").combobox("loadData",typeDataArrsNew);
	typeDataArrs = typeDataArrsNew;
	$('#tabId').tabs('enableTab', 1);
	$("#tabId").tabs("select", 1);
    var tab_option = $('#tabId').tabs('getTab',"主信息").panel('options').tab;
    tab_option.hide();
    var tab_option = $('#tabId').tabs('getTab',"房型结构").panel('options').tab;
    tab_option.show();
	document.getElementById("saveId").style.display = "";
	document.getElementById("backId").style.display = "";
	document.getElementById("nextId").style.display = "none";
	for(var i=0;i<typeDataArrsNew.length;i++){
		if(typeDataArrsNew[0].commId != null){
			$("#typeData").combobox("select",typeDataArrsNew[i].commId);
			if(typeDataArrsNew[0].commId == 1){
				if(getAllBuildTfloorRoomData.bzc != null || getAllBuildTfloorRoomData.bzc != undefined){
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bzc);		
				}else{
					$('#dg3').datagrid('loadData', { total: 0, rows: [] });
				}
			}else
			if(typeDataArrsNew[0].commId == 2){
				if(getAllBuildTfloorRoomData.bnc != null || getAllBuildTfloorRoomData.bnc != undefined){
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.bnc);		
				}else{
					$('#dg3').datagrid('loadData', { total: 0, rows: [] });
				}
			}else
			if(typeDataArrsNew[0].commId == 4){
				if(getAllBuildTfloorRoomData.tsc1 != null || getAllBuildTfloorRoomData.tsc1 != undefined){
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc1);		
				}else{
					$('#dg3').datagrid('loadData', { total: 0, rows: [] });
				}
			}else
			if(typeDataArrsNew[0].commId == 5){
				if(getAllBuildTfloorRoomData.tsc2 != null || getAllBuildTfloorRoomData.tsc2 != undefined){
					$('#dg3').datagrid("loadData",getAllBuildTfloorRoomData.tsc2);		
				}else{
					$('#dg3').datagrid('loadData', { total: 0, rows: [] });
				}
			}
			break;
		}
	}
	$('#dg2').datagrid('uncheckAll');
}

//点击下一步时获取楼层类型
function typeDataFn(arrayFlooType){
	var isRepeated = false;
	var hash = {};
	var result = [];
	var result =  new Array();
	var  typeDataArrsNew = new Array();
	for (var i = 0, elem; (elem = arrayFlooType[i]) != null; i++) {
		if (!hash[elem]) {
			result.push(elem);
		    hash[elem] = true;
		}
	}
	if(result.length == 0){
		return null;
	}else{
		for(var i=0;i<result.length;i++){
			var obj = new Object();
			if(parseInt(result[i]) == 1){
				obj.commId = 1;
				obj.commName = "标准层";
				typeDataArrsNew.push(obj);
			}else
			if(parseInt(result[i]) == 2){
				obj.commId = 2;
				obj.commName = "避难层";
				typeDataArrsNew.push(obj);
			}else
			if(parseInt(result[i]) == 4){
				obj.commId = 4;
				obj.commName = "特殊层1";
				typeDataArrsNew.push(obj);
			}else
			if(parseInt(result[i]) == 5){
				obj.commId = 5;
				obj.commName = "特殊层2";
				typeDataArrsNew.push(obj);
			}
		}
		return typeDataArrsNew;
	}
}


$.extend($.fn.validatebox.defaults.rules,{
	bName:{
		validator: function (value, param) {
			return /^[\w\u4e00-\u9fa5]+$/.test(value);
		},
		message:'名字中不能包含特殊字符'
	}
});

var editorids=null;
function onBeforeEditData(index,rowData){
	editorids=index;
}

function onSelectData(record){
	setTimeout(function(){
		if(record!=null){
			$('#dg2').datagrid('endEdit',editorids);
		}
		editorids=null;
	},500);
}


//处理编辑后鼠标移开后自动保存编辑的内容
$.extend($.fn.datagrid.methods, {      
    /**
     * 关闭消息提示功能    
     * @param {} jq    
     * @return {}    
     */     
    cancelCellEdit:function (jq) {      
        return jq.each(function () {      
            var data = $(this).data('datagrid');      
            if (data.factContent) {      
                data.factContent.remove();      
                data.factContent = null;      
            }      
            var panel = $(this).datagrid('getPanel').panel('panel');      
            panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')      
        });      
    }      
});