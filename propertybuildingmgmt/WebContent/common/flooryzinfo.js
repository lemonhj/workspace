// JavaScript Document

$(document).ready(function(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadTreeListnode.action',
		success:function(data){

			treeinit(data.treelist);
			datagridinit();
		}
	});
});


function datagridinit(){
	 $('#corplist_id').datagrid({
	        idField: 'id',
	        fitColumns: true,
	        singleSelect: true,
	        onClickRow:onclickrow,
	        columns: [[
	            { field: 'id', hidden: true },
	            { field: 'name', title: '单位名称', align: 'center' },
				{ field: 'alias', title: '单位别称', align: 'center' },
	            { field: 'fullname', title: '单位全称',  align: 'center' },
	            { field: 'type', title: '行业', align: 'center'},
	            { field: 'size', title: '企业进驻人数规模',  align: 'center' },
	            { field: 'contactor', title: '单位联系人名称',  align: 'center' },
	            { field: 'tel', title: '单位联系电话',  align: 'center' }
	        ]]
	    });
}
	
function onclickrow(rowIndex, rowData){
	selectdata=rowData;
}

function getyzbbshow(objdata){	
		hlc("#testsg_podt").building({
				style:
				{
					isShowOpBar: false,
					selectedColor: "green",
					decoratingColor: "yellow",
					width: 1500,
					height: 600,
					fontSize: 9
				},
				config:
				{
					color:CorporationIndustryType,
					sizes:CorporationSizes,
					state:RoomInState,
					ftype:FloorStructureType,
					btype:BuildingStructureType
				},
				building: objdata,
				onLoadCorp: function (like, callback) {
                     openWindow(like,callback); 
					
					//callback(corpdata);
				
					return true;
				},
				onChangeGrid: function (info) {
					switch(info.oper){
					case "insert":
					roominsert(info);
						break;
					case "update":
						roominsert(info);
							break;
					case "decorate":
						roomzx(info);
							break;
					case "delete":
						roomdel(info);
						break;
						default:break;
					}
				},
				onSave: function (info) {
					alert(JsonUtil.convertToString(info));
				},
				onClear: function (info) {
					alert(JsonUtil.convertToString(info));
				}
			});

}

function treeinit(obj){
	$('#floorlist').tree({
		data:obj,
		onClick:treeonclickit
	});
}
var g_callback = null;
var selectdata=null;
function openWindow(like,fun) {
	g_callback = fun;
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadcorpnow.action',
		data:{
			selecttj:like
		},success:function(data){
			if(data.corplist==null || data.corplist==""){
				$.messager.alert('提示','暂无数据');
			}
			$('#corplist_id').datagrid('loadData',data.corplist);
		}
	});
	$('#configcreate').dialog('open');
}

function clickOK() {
	g_callback(selectdata);
	selectdata=null;
	$('#configcreate').dialog('close');
}

function treeonclickit(node){
	var objname=$('#floorlist').tree('getParent',node.target).text+"-"+node.text;
	if(node!=null){
		$.ajax({
			type:'post',
			url:'/propertybuildingmgmt/FloorTableAction_loadfloit.action',
			data:{
				buildidt:node.id
			},
			success:function(data){
				data.builddata.path=objname;
				getyzbbshow(data.builddata);
			}
		});
	}
}

/*
 * 新建or变更
 * */
function roominsert(info){
var arrs= new Array();
	for(var i=0;i<info.room.length;i++){
		arrs.push(info.room[i].id);
	}
	
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_roomInsert.action',
		data:{
			roomdata:JSON.stringify(arrs),
			corpdata:JSON.stringify(info.corp)
		},success:function(data){
			info.corp.id=data.backcorpid;
		}
	});
}

/*
 *装修
 * */
function roomzx(info){
	var arrs= new Array();
	for(var i=0;i<info.room.length;i++){
		arrs.push(info.room[i].id);
	}
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_roomdecorate.action',
		data:{
			roomdata:JSON.stringify(arrs)
		},success:function(data){

		}
	});
}

/*
 *清除
 * */
function roomdel(info){
	var arrs= new Array();
	for(var i=0;i<info.room.length;i++){
		arrs.push(info.room[i].id);
	}
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_roomdel.action',
		data:{
			roomdata:JSON.stringify(arrs)
		},success:function(data){
			
		}
	});
	
}

