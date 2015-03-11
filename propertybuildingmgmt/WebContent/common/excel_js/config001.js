var BuildingStructureType = 
{
	id:10,
	name:'BuildingStructureType',
	parse:'楼栋结构类型',
	desc:null,
	values:
	[
		{id:91, tag:'SYL', value:1, parse:'商业楼', desc:null},
		{id:92, tag:'XZL', value:2, parse:'写字楼', desc:null},
		{id:93, tag:'ZZL', value:3, parse:'住宅楼', desc:null}
	]
};

var FloorStructureType = 
{
	id:11,
	name:'FloorStructureType',
	parse:'楼栋楼层结构类型',
	desc:null,
	values:
	[
		{id:1002, tag:'DXS', value:1, parse:'地下室', desc:null},
		{id:1006, tag:'RFC', value:2, parse:'人防层', desc:null},
		{id:1004, tag:'QLC', value:3, parse:'群楼层', desc:null},
		{id:1005, tag:'ZHC', value:4, parse:'转换层', desc:null},
		{id:1003, tag:'BNC', value:5, parse:'避难层', desc:null},
		{id:1001, tag:'BZC', value:6, parse:'塔楼层', desc:null}
	]
};

var RoomInState = 
{
	id:12,
	name:'RoomInState',
	parse:'房间入驻状态',
	desc:null,
	values:
	[
		{id:2002, tag:'ZX', value:2, parse:'装修中', desc:null},
		{id:2003, tag:'RZ', value:3, parse:'已入驻', desc:null},
		{id:2004, tag:'RZ', value:4, parse:'空置房', desc:null}
	]
};

var CorporationSizes = 
{
	id:13,
	name:'CorporationSizes',
	parse:'公司人数规模',
	desc:null,
	values:
	[
		{id:3001, tag:'B10', value:1, parse:'10人以内', desc:null},
		{id:3002, tag:'IN10_49', value:2, parse:'10-49人', desc:null},
		{id:3003, tag:'IN50_199', value:3, parse:'50-199人', desc:null},
		{id:3004, tag:'IN200_499', value:4, parse:'200-499人', desc:null},
		{id:3005, tag:'IN500_999', value:5, parse:'500-999人', desc:null},
		{id:3006, tag:'IN1000_4999', value:6, parse:'1000-4999人', desc:null},
		{id:3007, tag:'A5000', value:7, parse:'5000人以上', desc:null}
	]
};


var CorporationSta = 
{
	id:14,
	name:'CorporationSta',
	parse:'状态',
	desc:null,
	values:
	[
		{id:4001, tag:'B10', value:1, parse:'自持 ', desc:null},
		{id:4002, tag:'IN10_49', value:2, parse:'租赁', desc:null}
		]
};

var CorporationIsIn = 
{
	id:15,
	name:'CorporationSta',
	parse:'商户入驻状态',
	desc:null,
	values:
	[
		{id:5002, tag:'B11', value:2, parse:'装修中', desc:null},
		{id:5003, tag:'IN10_49', value:3, parse:'已入驻', desc:null},
		{id:5004, tag:'IN10_55', value:4, parse:'空置', desc:null}
		]
};


var CorporationIndustryType = new Object();

$(function(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/formatsType_loadFormatsTypes.action',
		success:function(data){
			CorporationIndustryType.values=data.listi;
			comboboxinit(data.listi,CorporationSta.values,CorporationSizes.values);
		}
	});
});


function comboboxinit(data,data2,data3){
	var obj=null;
	var arrs=new Array();
	for(var i=0;i<data.length;i++){
		obj=new Object();
		obj.id=data[i].id;
		obj.text=data[i].name;
		arrs.push(obj);
	}
	
	var obj1=null;
	var arrs1=new Array();
	for(var b=0;b<data2.length;b++){
		obj1=new Object();
		obj1.id=data2[b].value;
		obj1.text=data2[b].parse;
		arrs1.push(obj1);
	}
	
	var obj2=null;
	var arrs2=new Array();
	for(var c=0;c<data3.length;c++){
		obj2=new Object();
		obj2.id=data3[c].value;
		obj2.text=data3[c].parse;
		arrs2.push(obj2);
	}
	
	$('#typeid').combobox({
		valueField:'id',    
	    textField:'text',
	    editable:false,
	    value:'请选择...',
	    panelHeight:120,
	    data:arrs
	});
	$('#sizesid').combobox({
		valueField:'id',    
	    textField:'text',
	    editable:false,
	    value:'请选择...',
	    panelHeight:120,
	    data:arrs2
	});
	$('#stateid').combobox({
		valueField:'id',    
	    textField:'text', 
	    editable:false,
	    panelHeight:60,
	    value:'请选择...',
	    data:arrs1
	});
}
