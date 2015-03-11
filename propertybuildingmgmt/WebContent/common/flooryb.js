$(document).ready(function(){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction!loadTreeListnode.action',
		success:function(data){
			treeinit(data.treelist);
			treeonclick(data.treelist[0]);
		}
	});
	datagridinit();
	nopdatagridinit();
	saldatagridinit();
	areadatagridinit();
	lddatagridinit();
	tabsinit();
	tabsCompanyinit();
	//$('#northheight').style.height=$('#westgeight').height()/2;
	
	//查看业态类型比例单选按钮触发事件
	$('.scaleType').change(
	    function(){
	    	var selectNode =  $('#floorlist').tree('getSelected');
			datagridinit();
			tofgettable(tempData.countRatiolist);
	    }
	);
});
	

var totalnum=null; //业态饼图总户数
var totalarea=null; //业态饼图总面积
var areanum=null; //面积饼图总面积
var nopnum=null; //人数规模饼图总户数
var salarea=null; //自持、租赁饼图总面积
var arraylist=null;  //业态比列图形数据
var arrayarealist=null;  //面积图形化数据
var arraynoplist=null; //人数规模图形化数据
var arraysallist=null; //自持租赁图形化数据
var tempData=null;//点击节点请求返回的数据
/*
 * 初始化树形
 * */
function treeinit(obj){
	$('#floorlist').tree({
		data:obj,
		animate:true,
		onClick:treeonclick

	});
}

//双击树形节点
var treenodegloab=null;
function treeonclick(node){
	
	treenodegloab=node;

	    
		if(node.children!=null){
			$('#imgtabs').tabs('select',0);
			$('#imgtabs').tabs('getTab','楼栋业主信息').panel('options').tab.hide();
			$('#companel').panel('close');
			controllayout(1);
		}else{
			$('#imgtabs').tabs('getTab','楼栋业主信息').panel('options').tab.show();
		}

	$.messager.progress({text:'加载数据中...'});
	arraylist=new Array();
	arrayarealist=new Array();
	arraynoplist=new Array();
	arraysallist=new Array();
	var buildFlag = "true";
	if(!node.build){
		buildFlag = "false";
	}

	if(node.id>0){
		$.ajax({
			type:'post',
			url:'/propertybuildingmgmt/FloorTableAction_loadTfloorList.action',
			data:{
				buildidt:node.id,
				buildFlag:buildFlag
			},
			success:function(data){
				tempData = null;
				tempData = data;
				
				
				
        
				tofgettable(data.countRatiolist);
				areagettable(data.countarealist);
				nopgettable(data.countnoplist);
				salgettable(data.countsallist);
				
				if(node.iconCls=="icon-building")
				{
					loadldinfo(node);
				    return false;
				}
				$.messager.progress("close");
			}
		});	
	}
	
}

/*
 * 控制layout大小
 * shixin
 * */
function controllayout(flag){
    var panellayout = $('#leftcontorl').layout('panel','north');
    var hidelayout = $('#leftcontorl').layout('panel','center');
    if(flag=="1"){
    	panellayout.panel('maximize');
    	hidelayout.panel('close');
    }
    if(flag=="2"){
    	panellayout.panel('restore');
    	hidelayout.panel('open');
    	}
}


/*
 * 根据楼栋所包含的行业现实业态
 * */
function loadIndustryByBuild(id){
	var obj=new Array();
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_LoadIndustry.action',
		async:false,
		data:{
			buildidt:id
		},
		success:function(data){
		    obj=data.industrybyid;
		}
	});
	
	  return obj;
}


var datacallbackshow=null; //新增修改回调数据
var datalistnow=null;
function loadldinfo(node){
	//节点id不为空并且点击的节点为楼栋的时候
	if((node.id!=null || node.id!=undefined || node.id!="" ) && node.build == true){
		$.ajax({
			type:'post',
			url:'/propertybuildingmgmt/FloorTableAction_loadfloit.action',
			data:{
				buildidt:node.id,
				floortypeid:1
			},
			success:function(data){
				datalistnow=data.builddata;
				datacallbackshow=data.builddata.floors;
				getcomboboxfloor(data.builddata.floors);
				getyzbbshow(data.builddata,data.usertype,data.industrybyid,node.id); //生成标准层
				getbncshowit(node.id,data.usertype,data.industrybyid);//生成避难层
			    $.messager.progress("close");
				
			}
		});
	}
}

var bncdatacallback=new Array();
function getbncshowit(id,userid,objarrs){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadfloit.action',
		data:{
			buildidt:id,
			floortypeid:2
		},
		success:function(data){	

			bncdatacallback=data.builddatabn.floors;
			if(userid!=1){
				$('#companel').panel('close');
			}else{
				setTimeout(loadCompanyList(treenodegloab), 2000);
				$('#companel').panel('open');
				controllayout(2);
			}
			getbncshow(data.builddatabn,userid,objarrs);
			getbncboxselect(data.builddatabn);
		

		}
	});
}

function getbncboxselect(objdatas){
	var dataitem=$('#floorid').combobox('getData');
	var arrs=new Array();
	var obj = null;
	arrs = dataitem;

	for(var i=0;i<objdatas.floors.length;i++){
		obj=new Object();
		obj.id=objdatas.floors[i].id;
		obj.text=objdatas.floors[i].no+"（避难层）";
		onegetdata.push(objdatas.floors[i]);
		arrs.push(obj);
	}
	$('#floorid').combobox({
	    data:arrs
	});
}

function getbncshow(objdata,id,objarrs){
	
	objdata.usertype=id;   //获取登录用户类型
		hlc("#bncsg_podt").building({
				style:
				{
					isShowOpBar: false,
					selectedColor: "green",
					decoratingColor: "yellow",

					fontSize: 9
				},
				config:
				{
					color:CorporationIndustryType,
					sizes:CorporationSizes,
					state:RoomInState,
					ftype:FloorStructureType,
					btype:BuildingStructureType,
					roomperson:CorporationSta,
					cropstate:CorporationIsIn,
					hasindustry:objarrs
				},
				building: objdata,
				onLoadCorp: function (like, callback) {
                    openWindow(like,callback); 
					return true;
				},
				onChangeGrid: function (info) {
					switch(info.oper){
					case "insert":
						yinrugs(info);
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



/*
 * 获取楼层下拉
 * */
var onegetdata=new Array();
function getcomboboxfloor(objdata){
	onegetdata = objdata;
	var obj=null;
	var arrs=new Array();
	for(var i=0;i<objdata.length;i++){
		obj=new Object();
		obj.id=objdata[i].id;
		obj.text=objdata[i].no;
		arrs.push(obj);
	}
	
	$('#floorid').combobox({
		valueField:'id',    
	    textField:'text',
	    editable:false,
	    value:'请选择...',
	    data:arrs,
	    onSelect:getcomboboxrooms
	});
}

/*
 * 通过楼层查询房间
 * */

function getcomboboxrooms(record){
	$.messager.progress({msg:'整理房间数据...'});
	var obj=null;
	var arrs=new Array();
	for(var i=0;i<onegetdata.length;i++){
		if(onegetdata[i].id==record.id){
		    for(var b=0;b<onegetdata[i].rooms.length;b++){
		    	if(onegetdata[i].rooms[b].corp!=null){continue;}
		    	obj=new Object();
				obj.id=onegetdata[i].rooms[b].id;
				obj.text=onegetdata[i].rooms[b].no;
				obj.foolrid=onegetdata[i].id;
				arrs.push(obj);
		    }
		    break;
		}	
	}
	if(arrs.length==0){
		$.messager.alert('提示','无可租售的房间！','',function(r){
			$('#roomtree').tree('loadData',[]);
			$.messager.progress('close');
			document.getElementById("roomsidnow").style.display="";
		});
		
		return false;
	}
	document.getElementById("roomsidnow").style.display="none";
	$('#roomtree').tree('loadData',arrs);
	$.messager.progress('close');
	
}

function onclcikroomtree(node){
	$('#roomtree').tree('check',node.target);
}

/*
 * 添加选择房间列表
 * */

function addselectrooms(){
	
	var objroomtree=$('#roomtree').tree('getChecked');
	if(objroomtree==null || objroomtree==""){
		$.messager.alert('提示','	请选择房间');
		return false;
	}
	var selectrooms=$('#selectroomtree').tree('getRoots');
	if(selectrooms!=null && selectrooms!=""){
		for(var i=0;i<objroomtree.length;i++){
			for(var b=0;b<selectrooms.length;b++){
				if(objroomtree[i].id==selectrooms[b].id){
					$.messager.alert('提示','	请勿重复');
					return false;
				}
			}
		}
	}
	$('#selectroomtree').tree('append',{parent:null,data:objroomtree});

	document.getElementById("selectroomsidnow").style.display="none";
	while(objroomtree.length>0){
		$('#roomtree').tree('pop',objroomtree[objroomtree.length-1].target);
		objroomtree.length--;
	}
}

/*
 *删除已选房间数据 
 * */
function delselectrooms(){

	 var node = $('#selectroomtree').tree('getSelected');
	 if (node){
		    $.messager.confirm('提示','你确定要退租房间'+node.text,function(r){
		    	if(r){
		    		$('#selectroomtree').tree('remove',node.target);
					$.ajax({
						type:'post',
						url:'/propertybuildingmgmt/FloorTableAction!roomdeltable.action',
						data:{
							roomid:node.id
						},
						succuss:function(data){
							
						}
					});
					treeonclick(treenodegloab);
		    	}
		    });
	
		}
//		if(document.getElementById("roomsidnow").style.display==""){
//		$('#floorid').combobox('select',node.foolrid);
//	}

}

/*
 * 统计业态总户数、总面积
 * */
function totaljs(obj){
	totalnum=0;
	totalarea=0;
	if(obj.length != 0){ 
		totalnum=0;
		totalarea=0;
		for(var i=0;i<obj.length;i++){
			totalnum+=obj[i].countname;
			totalarea += parseFloat(obj[i].countArea);
		}
		if(totalarea != 0){
			totalarea = changeTwoDecimal(totalarea);
		}
	}
}

/*
 * 统计面积饼图总面积
 * */
function totalareajs(obj){
	areanum=0;
	if(obj.length != 0){
		for(var i=0;i<obj.length;i++){
			areanum+=parseFloat(obj[i].areanum);
		}
		if(areanum!=0){
			areanum=changeTwoDecimal(areanum);
		}
	}
}


/*
 * 初始化业态百分比表格
 * */

function datagridinit(){
	arraylist = new Array();
	if($("input[name='scaleType']:checked").val() == '户数'){
		$('#floordata').datagrid({    
			title:'业态户数比例', 
			width:310,
			showFooter:true,
			singleSelect:true,
			onSelect:function(rowIndex, rowData){
				queryDataFn(rowIndex, rowData,"业态户数");
			},
		    columns:[[    
		        {field:'name',title:'行业类型',width:80},    
		        {field:'countname',title:'户数(户)',width:120},
		        {field:'ratio',title:'所占比列',width:100,formatter:function(value,rowdata,index){
		        	  if(value==null && totalnum!=0){
			              var piedata=new Object();
			        	  piedata.name=rowdata.name;
			        	  piedata.y=parseFloat(changeTwoDecimal((rowdata.countname/totalnum)*100));
			        	  piedata.color=rowdata.countColor;
			        	  arraylist.push(piedata);
		        	  return changeTwoDecimal((rowdata.countname/totalnum)*100)+"%";
		        	  }else{
		        		  return value;
		        	  }
		        }}
		    ]],
		    rowStyler:function(index,row){
                return 'cursor:pointer'; 
		    },
		    onClickRow: function (rowIndex, rowData) {
                $(this).datagrid('unselectRow', rowIndex);
            }
		});
	}else if($("input[name='scaleType']:checked").val() == '面积'){
		$('#floordata').datagrid({    
			title:'业态面积比例', 
			width:310,
			showFooter:true,
			singleSelect:true,
			onSelect:function(rowIndex, rowData){
				queryDataFn(rowIndex, rowData,"业态面积");
			},
		    columns:[[    
		        {field:'name',title:'行业类型',width:80},    
		        {field:'countArea',title:'所占面积(㎡)',width:120},
		        {field:'ratio',title:'所占比列',width:100,formatter:function(value,rowdata,index){
		        	  if(value==null){
			              var piedata=new Object();
			        	  piedata.name=rowdata.name;
			        	  piedata.y=parseFloat(changeTwoDecimal((rowdata.countArea/totalarea)*100));
			        	  piedata.color=rowdata.countColor;
			        	  arraylist.push(piedata);
			        	  return changeTwoDecimal((rowdata.countArea/totalarea)*100)+"%";
		        	  }else{
		        		  return value;
		        	  }
		        }}    
		    ]],
		    rowStyler:function(index,row){
                return 'cursor:pointer'; 
		    },
		});
	}
}

/*
 * 业态类型数据加载
 * */
function tofgettable(obj){
	totaljs(obj);
	var datashow=new Object();
	datashow.rows=obj;
	datashow.footer=[{name:'<span style="font-weight:bold">总计</span>',
	countname:totalnum==null?'<span style="font-weight:bold">'+0+'</span>':'<span style="font-weight:bold">'+totalnum+' 户</span>',
	countArea:totalarea==null?'<span style="font-weight:bold">'+0+'</span>':'<span style="font-weight:bold">'+totalarea+' ㎡</span>',
	ratio:''}];
	$('#floordata').datagrid('loadData',datashow);
	imageinit(null,arraylist,container);
}


/*
 * 初始化入住面积数据表格
 * */
function areadatagridinit(){
	$('#areadata').datagrid({    
		title:'房间状态比例', 
		width:310,
		showFooter:true,
		singleSelect:true,
		onSelect:function(rowIndex, rowData){
			queryDataFn(rowIndex, rowData,"入驻面积");
		},
	    columns:[[    
	        {field:'areaname',title:'面积类型',width:80,formatter:function(value,rowdata,index){
	        	if(value=="3"){
	        		return "入驻面积";
	        	}else if(value=="2"){
	        		return "装修面积";
	        	}else if(value=="4"){
	        		return "待售面积";
	        	}else if(value=='5'){
	        		return "空置面积";
	        	}else{
	        		return value;
	        	}
	        }},    
	        {field:'areanum',title:'面积 (㎡)',width:120,formatter:function(value,rowdata,index){
		        	  var piedata=new Object();
		        	  if(rowdata.areaname=="3"){
		        		  piedata.name="已入驻面积";
			        	}else if(rowdata.areaname=="2"){
			        		piedata.name="在装修面积";
			        	}else if(rowdata.areaname=="4"){
			        		piedata.name="待售面积";
			        	}else if(rowdata.areaname=="5"){
			        		piedata.name="空置面积";
			        	}else{
			        		return value;
			        	}
		        	  if(areanum!=0){
			        	  piedata.y=parseFloat(changeTwoDecimal((rowdata.areanum/areanum)*100));
			        	  arrayarealist.push(piedata);
		        	  }
		        	  return value;
	        }},
	        {field:'ratio',title:'所占比例',width:100,formatter:function(value,rowdata,index){
	        	if(value == null && areanum !=0){
	        		return changeTwoDecimal((rowdata.areanum/areanum)*100)+"%";
	        	}else{
	        		return value;
	        	}
	        }}
	    ]],
	    rowStyler:function(index,row){
	    	if(row.areaname == '2' || row.areaname == '3'){
	    		return 'cursor:pointer'; 
	    	}
	    },
	    onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
        }    
	}); 
}

/*
 * 入住面积数据加载
 * */
function areagettable(obj){
	totalareajs(obj);
	var datashow=new Object();
	datashow.rows=obj;
	datashow.footer=[{areaname:'<span style="font-weight:bold">总计</span>',areanum:'<span style="font-weight:bold">'+areanum+' ㎡</span>',ratio:''}];
	$('#areadata').datagrid('loadData',datashow);
	imageinit(null,arrayarealist,areacontent);
}

/*
 * 初始化人数规模表格 
 * */
function nopdatagridinit(){
	$('#nopdata').datagrid({
		title:'人数规模比例',
		width:310,
		showFooter:true,
		singleSelect:true,
		onSelect:function(rowIndex, rowData){
			queryDataFn(rowIndex, rowData,"人数规模");
		},
		columns:[[
		    {field:'nopName',title:'人数规模',width:80},
		    {field:'nopCount',title:'户数(户)',width:120},
		    {field:'ratio',title:'所占比例',width:100,formatter:function(value,rowdata,index){
		    	if(value==null && totalnum!=0){
		              var piedata=new Object();
		        	  piedata.name=rowdata.nopName;
		        	  piedata.y=parseFloat(changeTwoDecimal((rowdata.nopCount/totalnum)*100));
		        	  arraynoplist.push(piedata);
		        	  return changeTwoDecimal((rowdata.nopCount/totalnum)*100)+"%";
	        	  }else{
	        		  return value;
	        	  }
		    }},
		]],
		rowStyler:function(index,row){
            return 'cursor:pointer'; 
	    },
	    onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
        }
	});
}

/*
 * 人数规模数据加载
 * */
function nopgettable(obj){
	var temp = obj;
	for(var i in temp){
		for(var j in CorporationSizes.values){
			if(temp[i].nopName==CorporationSizes.values[j].value){
				temp[i].nopName = CorporationSizes.values[j].parse;
				break;
			}
		}
	}
	var datashow=new Object();
	datashow.rows = temp;
	datashow.footer = [{nopName:'<span style="font-weight:bold">总计</span>',nopCount:'<span style="font-weight:bold">'+totalnum+' 户</span>',ratio:''}];
	$('#nopdata').datagrid('loadData',datashow);
	imageinit(null, arraynoplist, nopcontent);
}

/*
 * 初始化自持、租赁比例表格
 * */
function saldatagridinit(){
	$('#saldata').datagrid({
		title:'自持、租赁比例',
		width:310,
		showFooter:true,
		singleSelect:true,
		onSelect:function(rowIndex, rowData){
			queryDataFn(rowIndex, rowData,"自赁比例");
		},
		columns:[[
			{field:'salName',title:'房间属性',width:80},
			{field:'salCount',title:'所占面积(㎡)',width:120},
			{field:'ratio',title:'所占比例',width:100,formatter:function(value,rowdata,index){
				if(value==null && salarea!=0){
		              var piedata=new Object();
		        	  piedata.name=rowdata.salName;
		        	  piedata.y=parseFloat(changeTwoDecimal((rowdata.salCount/salarea)*100));
		        	  arraysallist.push(piedata);
		        	  return changeTwoDecimal((rowdata.salCount/salarea)*100)+"%";
	        	  }else{
	        		  return value;
	        	  }
			}}
		]],
		rowStyler:function(index,row){
            return 'cursor:pointer'; 
	    },
	    onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
        }
	});
}

/*
 * 自持、租赁比例数据加载
 * */
function salgettable(obj){
	salarea = 0;
	var temp = obj;
	for(var i in temp){
		for(var j in CorporationSta.values){
			if(temp[i].salName==CorporationSta.values[j].value){
				temp[i].salName = CorporationSta.values[j].parse;
				break;
			}
		}
	}
	
	//计算总面积
	for(var i=0;i<obj.length;i++){
		salarea += obj[i].salCount;
	}
	salarea = changeTwoDecimal(salarea);
	var datashow=new Object();
	datashow.rows = temp;
	datashow.footer = [{salName:'<span style="font-weight:bold">总计</span>',salCount:'<span style="font-weight:bold">'+salarea+' ㎡</span>',ratio:''}];
	$('#saldata').datagrid('loadData',datashow);
	imageinit(null, arraysallist, salcontent);
	$.messager.progress("close");
}



/*
 * 图形化初始化
 * */
function imageinit(buildname,obj,id){
	var chart=null;
		chart = new Highcharts.Chart({
			chart: {
				renderTo: id,
				options3d: {
					enabled: true,
	                alpha: 60,
	                beta: 0
	            }
			},
			colors: ['#AA4643',  '#89A54E', '#71588F', '#4198AF', 
			         '#DB843D', '#93A9CF', '#D19392', '#B9CD96', '#A99BBD','#4572A7'],
			title: {
				text: ''
			},
			plotArea: {
				shadow: null,
				borderWidth: null,
				backgroundColor: null
			},
			tooltip: {
				formatter: function() {
					return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
				}
			},
			//去掉打印按钮
			exporting:{enabled:false},
			//不显示highCharts版权信息
			credits:{enabled:false},
			plotOptions: {
				pie: {
					size:280,
					allowPointSelect: true,
					depth: 40,
					slicedOffset:35,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
						color: '#000000',
						connectorColor: '#000000',
						formatter: function() {
							return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
						}
					}
				}
			},
		    series: [{
				type: 'pie',
				name: 'Browser share',
				data:obj
			}]
		});

}

/*
 * 小数点后取2位 且四舍五入
 * */
function changeTwoDecimal(x) {
	var f_x = parseFloat(x);
	if (isNaN(f_x)) {
		alert('function:changeTwoDecimal->parameter error');
		return false;
	}
	var f_x = Math.round(x * 100) / 100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0) {
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + 2) {
		s_x += '0';
	}
		return s_x;
	}
/*
 * tabs触发事件
 * */
function tabsinit(){
	$('#imgtabs').tabs({
		onSelect:selecttabs
	});
}

/*
 * tabs点击触发
 * */

function selecttabs(title,index){
	
	if(index==0){
		$('#excelImports').linkbutton('disable');
	}else if(index==1){
		$('#excelImports').linkbutton('enable');
	}
}

/*
 * 楼栋业主信息表格初始化
 * */
var dialogredarrs=new Array();
var dialogblacarrs=new Array();
function lddatagridinit(){
	 $('#corplist_id').datagrid({
	        idField: 'id',
	        fitColumns: true,
	        singleSelect: true,
	        height:200,
	        onClickRow:onclickrow,
	        columns: [[
	            { field: 'id', hidden: true },
	            { field: 'name', title: '单位名称', align: 'center',formatter:function(value,rowData,index){
	            	var arrsit=new Array();
		        	arrsit=ithasroomslist(rowData.id);
		        	var str=value.length>12?value.substr(0,6)+"...":value;
		        	if(arrsit.length==0){
		        		//rowData.index=index;
		        		//dialogredarrs.push(rowData);
		        		return "<a  style='cursor:pointer;' title='"+value+"' class='easyui-tooltip'><font color=red>"+str+"</font></a>";
		        	}
		        	//dialogblacarrs.push(rowData);
		        	return "<a  style='cursor:pointer' href='#' title='"+value+"' class='easyui-tooltip'>"+str+"</a>";
	            }},
				{ field: 'alias', title: '单位别称', align: 'center' },
	            { field: 'fullname', title: '单位全称',  align: 'center' },
	            { field: 'type', title: '行业', align: 'center'},
	            { field: 'size', title: '企业进驻人数规模',  align: 'center' },
	            { field: 'contactor', title: '单位联系人名称',  align: 'center' },
	            { field: 'tel', title: '单位联系电话',  align: 'center' },
	            { field: 'state',  hidden: true},
	            { field: 'staterooms', hidden: true}
	        ]]
	    });
}

function onclickrow(rowIndex, rowData){
	selectdata=rowData;
}

function getyzbbshow(objdata,id,objarrs,ldid){
	
	objdata.usertype=id;   //获取登录用户类型
		hlc("#testsg_podt").building({
				style:
				{
					isShowOpBar: false,
					selectedColor: "green",
					decoratingColor: "yellow",

					fontSize: 9
				},
				config:
				{
					color:CorporationIndustryType,
					sizes:CorporationSizes,
					state:RoomInState,
					ftype:FloorStructureType,
					btype:BuildingStructureType,
					roomperson:CorporationSta,
					cropstate:CorporationIsIn,
					hasindustry:objarrs
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
						yinrugs(info);
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



var g_callback = null;
var selectdata=null;
function openWindow(like,fun) {
	g_callback = fun;
	$.messager.progress({msg:'加载数据中...'})

	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction!loadcorpnow.action',
		data:{
			selecttj:like,
			buildidt:treenodegloab.id
		},success:function(data){
			if(data.corplist==null || data.corplist==""){
				$.messager.alert('提示','暂无数据');
				$.messager.progress('close');
				return false;
			}
			$('#corplist_id').datagrid('clearSelections');
			$('#corplist_id').datagrid('loadData',data.corplist);
			$.messager.progress('close');
			$('#configcreate').dialog('open');
		}
	});
	
}

function clickOK() {
	g_callback(selectdata);
	selectdata=null;
	if(document.getElementById("cnameid").value!=""){
		document.getElementById("zicizl").style.display="";
	}else{
		document.getElementById("zicizl").style.display="none";
	}
	$('#configcreate').dialog('close');
}

/*
 * 引入公司
 * */
function yinrugs(info){
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction!roomys.action',
		data:{
			roomid:info.room[0].id,
			corpid:info.corp.id,
			roomradioid:info.corp.staterooms,
			corpstateradio:(info.corp.corpstateshow=="" || info.corp.corpstateshow==null?info.corp.state:info.corp.corpstateshow),
		},success:function(data){
			roominsert(info);
		}
	});
	
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
		url:'/propertybuildingmgmt/FloorTableAction!roomupdate.action',
		data:{
			yzname:info.room[0].owner,
			telnum:info.room[0].ownertel,
			corpdata:JSON.stringify(arrs)
		},success:function(data){
			treeonclick(treenodegloab);
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
			treeonclick(treenodegloab);
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
			treeonclick(treenodegloab);
			}
	});
	
}
//查询操作
function  checkQuery(){
	//开窗
	$('#queryData').form('clear');
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
	document.getElementById("corpName").focus();
}

//查询后数据显示
function queryBtn(){
	if(treenodegloab.id == null){
		$.messager.alert('信息提示','请选择楼栋列表!');
		return false;
	}
	var obj = new Object();
	obj.corpName = queryData.corpName.value;
	obj.corpContacts = queryData.corpContacts.value;
	obj.roomOwner = queryData.roomOwner.value;
	obj.treeNode = treenodegloab.id;
	obj.treeFlag = treenodegloab.build.toString();
	if(obj.corpName == "" && obj.corpContacts == "" && obj.roomOwner == "" ){
		$.messager.alert('信息提示','请输入查询条件！');
		return false;
	}
	$.messager.progress({text:'正在加载数据'});
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction!ytQueryData.action',
		data:{
			formData:JSON.stringify(obj)
		},
		success:function(data){
			$.messager.progress('close');
			if(data.uicList == null){
				$.messager.alert('信息提示','查询不到满足你所需的条件,请重新输入其他条件进行查询！');
				return false;
			}else{
				$('#winQuery').window('close');  
				$('#dg3').treegrid("loadData",{total:0,rows:[]});
			}
			$('#winShow').window({
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
			if(queryData.roomOwner.value != ""){
				$('#dg3').treegrid("loadData",data.uicList.rows)
			}else{
				$('#dg3').treegrid("loadData",data.uicList)
			}
			
		},
		error:function(){
			$.messager.progress('close');
			$.messager.alert('信息提示','查询不到满足你所需的条件!');
		}
	});
}

//选择各分类比例行时统一处理查询
function queryDataFn(rowIndex, rowData,type){
	//业态户数  业态面积 入驻面积  人数规模 自赁比例
	var obj = new Object();
	if(treenodegloab.id == null){
		$.messager.alert('信息提示','请选择楼栋列表!');
		return false;
	}
	obj.treeId = treenodegloab.id;
	obj.treeFlag = treenodegloab.build.toString();
	obj.type=  type;
	$('#dg3').treegrid("loadData",{ total: 0, rows: [] });//清空历史数据
	if(type=="业态户数"){
		obj.where = rowData.name;
		quertSelectGrid(obj,"treeGrid");
		return true;
	}else if(type=="业态面积"){
		obj.where = rowData.name;
		quertSelectGrid(obj,"dataGrid");
		return true;
	}else if(type=="入驻面积"){
		obj.where = rowData.areaname;
		quertSelectGrid(obj,"dataGrid");
		return true;
	}else if(type=="人数规模"){
		obj.where = rowData.nopName;
		quertSelectGrid(obj,"treeGrid");
		return true;
	}else if(type=="自赁比例"){
		obj.where = rowData.salName;
		quertSelectGrid(obj,"dataGrid");
		return true;
	}
}

function quertSelectGrid(objValue,type){
	if(objValue.where == '4'|| objValue.where == '5'){
		return;
	}
	$.messager.progress({text:'正在加载数据'});
	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction!querySelGridDataToShow.action',
		data:{
			formData:JSON.stringify(objValue)
		},
		success:function(data){
			$.messager.progress('close');
			if(data.uicList == null){
				$.messager.alert('信息提示','查询不到满足你所需的条件！');
				return false;
			}else{
				$('#dg3').treegrid("loadData",{total:0,rows:[]});	
			}
			$('#winShow').window({
				closed:false,
				title:'查询列表',
				collapsible: false,
				minimizable: false,
				maximizable: false,
				resizable: false,
				draggable:	true,
				modal: true,
				shadow : true
			});
			if(type=="treeGrid"){
				$('#dg3').treegrid("loadData",data.uicList)
			}else{
				$('#dg3').treegrid("loadData",data.uicList.rows)
			}

		},
		error:function(){
			$.messager.progress('close');
			$.messager.alert('信息提示','查询不到满足你所需的条件!');
		}
	})
}

//初始化公司列表
var arrsredcompany=new Array();
var arrsblackcompany=new Array();
function companyinit(){
	$('#companylist').datagrid({
		width:173,
		//height:(parseInt($('#westshowid').height()/2)),
		singleSelect:true,
		
		 columns:[[    
			        {field:'id',hidden:true},    
			        {field:'name',title:'单位名称',width:170,formatter:function(value,rowData,index){
			        	var arrsit=new Array();
			        	arrsit=ithasroomslist(rowData.id);
			        	var strs=value.length>12?value.substr(0,9)+"...":value;
			        	if(arrsit.length==0){
			        		var str=value.length>9?value.substr(0,8)+"...":value;
			        		return "<a  style='cursor:pointer;float:left;' title='"+value+"' class='easyui-tooltip'><font color=red>"+str+"</font></a><a style='float:right;'><button onclick=\"delcorption('"+rowData.id+"','"+rowData.name+"')\" style='height:20px; cursor:pointer; background-color:#e1e1e1; border:1px solid #c1c1c1;'>删除</button></a>";
			        	}
			  
			        	return "<a  style='cursor:pointer' href='#' title='"+value+"' class='easyui-tooltip'>"+strs+"</a>";
			        }},
			        {field:'alias',hidden:true},
			        {field:'fullname',hidden:true}, 
			        {field:'type',hidden:true}, 
			        {field:'size',hidden:true}, 
			        {field:'contactor',hidden:true}, 
			        {field:'tel',hidden:true}, 
			        {field:'state',hidden:true},
			        {field:'staterooms',hidden:true}
			    ]]    
		
	});

}

/*
 * 公司新增
 * */
function companyadd(){
	
	ischangeroomstate=false;
	
	document.getElementById("roomsidnow").style.display="";
	
	document.getElementById("selectroomsidnow").style.display="";

	
	$('#companyform').form('clear');
	
	document.getElementById("buildidnow").value=treenodegloab.id;


	$('#roomtree').tree('loadData',[]);

	$('#selectroomtree').tree('loadData',[]);

	$('#companycz').dialog('open');

}


var ischangeroomstate=null;
/*
 * 公司修改
 * */
function companyedit(){

	$('#roomtree').tree('loadData',[]);
	$('#selectroomtree').tree('loadData',[]);
	

	document.getElementById("roomsidnow").style.display="";
	document.getElementById("selectroomsidnow").style.display="";
	$('#companyform').form('clear');
	
	var objdatagrid=$('#companylist').datagrid('getSelected');
	if(objdatagrid==null){
		$.messager.alert('提示','请选择要修改的公司');
		return false;
	}
	document.getElementsByName("tcorporation.corpId").item(0).value=objdatagrid.id;
	document.getElementsByName("tcorporation.corpBuilding").item(0).value=treenodegloab.id;
	document.getElementsByName("tcorporation.corpName").item(0).value=objdatagrid.name;
	document.getElementsByName("tcorporation.corpAlias").item(0).value=objdatagrid.alias;
	document.getElementsByName("tcorporation.corpFullname").item(0).value=objdatagrid.fullname;
	$('#typeid').combobox('select',objdatagrid.type);
	$('#sizesid').combobox('select',objdatagrid.size);
	$('#stateid').combobox('select',objdatagrid.state);
	
	document.getElementsByName("tcorporation.corpcontacts").item(0).value=objdatagrid.contactor;
	document.getElementsByName("tcorporation.corpTel").item(0).value=objdatagrid.tel;
	var arrs=new Array();
	arrs=ithasroomslist(objdatagrid.id);
	if(arrs.length!=0){
	   document.getElementById("selectroomsidnow").style.display="none"; 
	   $('#selectroomtree').tree('loadData',arrs);
	}else{
	
		$('#selectroomtree').tree('loadData',[]);

	}
	ischangeroomstate=true;
	$('#companycz').dialog('open');
}

/*
 * 获取公司所占房屋列表
 * */
function ithasroomslist(id){
	var arrs=new Array();
	var item=null;
	if(datacallbackshow==null){return false;}
	for(var i=0;i<datacallbackshow.length;i++){
		for(var b=0;b<datacallbackshow[i].rooms.length;b++){
			if(datacallbackshow[i].rooms[b].corp==null){continue;}
			if(datacallbackshow[i].rooms[b].corp.id==id){
				item=new Object();
				item.id=datacallbackshow[i].rooms[b].id;
				item.text=datacallbackshow[i].rooms[b].no;
				item.foolrid=datacallbackshow[i].id;
				item.roomstateite=datacallbackshow[i].rooms[b].state;
				arrs.push(item);
			}
		}
	}

	return arrs;
}

/*
 * 加载公司请求
 * */

function loadCompanyList(node){

	$.ajax({
		type:'post',
		url:'/propertybuildingmgmt/FloorTableAction_loadCompanyList.action',
		data:{
			   buildidt:node.id,
			   companylike:node.likename==undefined || node.likename=="" || node.likename==null?"":node.likename 
			 },
		success:function(data){
			$('#companylist').datagrid('loadData',data.corplist);
		}
	});
}





/*
 * 公司列表tabs
 * */
function tabsCompanyinit(){
	companyinit();

}
/*
 * 保存新公司
 * */
function savacompany(){
	if(document.getElementsByName("tcorporation.corpName").item(0).value==""){
		$.messager.alert('提示','请填写公司名称');
		return false;
	}
	if($('#typeid').combobox('getValue')==""){
		$.messager.alert('提示','请选择行业分类');
		return false;
	}
	if($('#sizesid').combobox('getValue')==""){
		$.messager.alert('提示','请选择人员规模');
		return false;
	}
	if($('#stateid').combobox('getValue')==""){
		$.messager.alert('提示','请选择租户类型');
		return false;
	}
	
	if(document.getElementsByName("tcorporation.corpcontacts").item(0).value==""){
		$.messager.alert('提示','请填写租户姓名');
		return false;
	}
	
	if(document.getElementsByName("tcorporation.corpTel").item(0).value==""){
		$.messager.alert('提示','请填写租户电话');
		return false;
	}
	var roomidarrs=new Array();
	var roomstateidss=new Array();
	//获取已选择的房间信息
	var obj=$('#selectroomtree').tree('getRoots');
	
	

	if(obj!=null && obj.length!=0){
		for(var i=0;i<obj.length;i++){
			roomidarrs.push(obj[i].id);
			roomstateidss.push(obj[i].roomstateite);
		}
	}else{
		$.messager.alert('提示','请选择相应的房间');
		return false;
	}
	
	$.messager.progress({msg:'提交处理中....'});
	
	
	$('#companyform').form('submit', {
		url:'/propertybuildingmgmt/FloorTableAction!savecompany.action',
		onSubmit: function(param){
			param.roomid=JSON.stringify(roomidarrs);
			param.roomradioid=2;
		},
		success: function(data){
			$.messager.progress('close');
			cancel_dialog();
			ischangeroomstate=false;
			treeonclick(treenodegloab);
		}
	});
}	


/*
 * 楼栋公司查询
 * */
function doSearch(value){
	treenodegloab.likename=value;
	loadCompanyList(treenodegloab);
}


/*
 * 删除与rooms没关系的公司
 * */
function delcorption(id,name){
	$.messager.confirm('确认','您确认想要删除"'+name+'"吗？',function(r){    
	    if (r){  
	    	$.messager.progress({msg:'数据处理中...'});
	    	$.ajax({
	    		type:'post',
	    		url:'/propertybuildingmgmt/FloorTableAction!delcorption.action',
	    		data:{
	    			   corpid:id
	    			 },
	    		success:function(data){
	    			$.messager.progress('close');
	    			loadldinfo(treenodegloab);
	    		}
	    	});  
	    }    
	}); 
}


/*
 * 生成excel表格
 * */
function createExcel(){
	if(treenodegloab.children==null && treenodegloab.iconCls=="icon-building"){
		var ldid=treenodegloab.id;
	    var	ldongname=datalistnow.name;
	    var	countfloors=datalistnow.totalFloors;
	    var	countmaxRooms=datalistnow.maxFloorRooms;	
		window.location.href="/propertybuildingmgmt/ExcelAction_createExcel.action?ldid="+ldid+"&ldongname="+ldongname+"&countfloors="+countfloors+"&countmaxRooms="+countmaxRooms;
	}else{
		$.messager.alert('提示','请选择楼栋导出Excel');
		return false;
	}

}

function cancel_dialog(){
	$('#roomtree').tree('loadData',[]);
	$('#selectroomtree').tree('loadData',[]);
	$('#companycz').dialog('close');
}





