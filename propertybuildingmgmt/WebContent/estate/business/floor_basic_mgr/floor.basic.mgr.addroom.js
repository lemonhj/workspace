function addData(){
	var getRoomCount = formRoom.roomNum.value;
	//var getRoomArea = formRoom.roomArea.value;
	if(getRoomCount == null || getRoomCount == ""){
		$.messager.alert("消息提示", "请输入房型数量！");
		return false;
	}
	var arrs = new Array();
	var count =0;
	var record = parent.$('#dg3').datagrid('getRows');
	var getComBoboxValu = parent.$("#typeData").combobox("getValue");
	if(record.length>0&&((record[0].roomType).toString() == getComBoboxValu.toString())){ //获取已经存在的房型
		count = record.length;
		for(var j=0;j<record.length;j++){
			var obj1 = new Object();
			obj1 =record[j];
			arrs.push(obj1);
		}
	}
	for(var i=1;i<=getRoomCount;i++){//新增房型
		var obj = new Object();
		obj.roomId = "";
		obj.roomFloor = "";
		obj.roomType=ThisUrlParam()["getType"];
		if(record.length>0){
			obj.roomOrder =(i+record.length).toString();
			obj.roomNo = (i+record.length).toString();			
		}else{
			obj.roomOrder =(i).toString();
			obj.roomNo = (i).toString();			
		}
		obj.roomState = "1";
		obj.roomArea = "";
		arrs.push(obj);
	}
	if(ThisUrlParam()["getType"] == '1'){
		parent.getAllBuildTfloorRoomData.bzc = arrs;
	}else
	if(ThisUrlParam()["getType"] == '2'){
		parent.getAllBuildTfloorRoomData.bnc = arrs;
	}else
	if(ThisUrlParam()["getType"] == '4'){
		parent.getAllBuildTfloorRoomData.tsc1 = arrs;
	}else
	if(ThisUrlParam()["getType"] == '5'){
		parent.getAllBuildTfloorRoomData.tsc2 = arrs;
	}
	parent.$('#dg3').datagrid("loadData",arrs);
	shoutWin();
}
function shoutWin(){
	parent.$('#win1').window('close');
}