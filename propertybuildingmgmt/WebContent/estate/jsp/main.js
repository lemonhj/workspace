//获取当前屏幕的宽高
var clientWidth = $(window).width();
var clientHeight =  $(window).height();
var pieSize = $(window).width() / 10;
	_menus = {
			basic : userMenu
		};

$(document).ready(function(){
	var ws = 0;
	if(window.screen.width/window.screen.height>1.5){
		ws = 3;
		setwidandhei('utility',clientWidth/8,clientHeight/1.34);
	}else{
		ws = 2.9;
		setwidandhei('utility',clientWidth/20,clientHeight/1.34);
	}
	//设置4个饼图的宽高
	setwidandhei('tcontent',clientWidth/(ws-0.2),clientHeight/3);
	setwidandhei('acontent',clientWidth/ws,clientHeight/3);
	setwidandhei('ncontent',clientWidth/(ws-0.2),clientHeight/3);
	setwidandhei('scontent',clientWidth/ws,clientHeight/3);
	
	$('#tabs').tabs({
		onSelect:function(title){
			if(title == '欢迎使用'){
				//获取饼图数据并加载
				getFourImageData();
			}
		}
	});
	
});

/*请求4个饼状图数据*/
function getFourImageData(){
	var arraytoflist=new Array();  //业态比列图形数据
	var arrayarealist=new Array(); //面积图形化数据
	var arraynoplist=new Array(); //人数规模图形化数据
	var arraysallist=new Array(); //自持租赁图形化数据
	$.ajax({
		url:'/propertybuildingmgmt/FloorTableAction_fourList.action',
		type:'post',
		data:{
			buildFlag:'false'
		},
		success:function(data){
			//业态比列图形
			gettofdata(data.countRatiolist,arraytoflist);
			imagesinit(null, arraytoflist, tcontent,pieSize);
			setTimeout(function(){
				//入驻面积比例图形
				getareadata(data.countarealist,arrayarealist);
				imagesinit(null, arrayarealist, acontent,pieSize);
				setTimeout(function(){
					//人数规模比例图形
					getnopdata(data.countnoplist,arraynoplist);
					imagesinit(null, arraynoplist, ncontent,pieSize);
					setTimeout(function(){
						//自持、租赁比例图形
						getsaldata(data.countsallist,arraysallist);
						imagesinit(null, arraysallist, scontent,pieSize);
					}, 1500);
				}, 1500);
			}, 1500);
		}
	});
}

/*计算业态类型比例并且将数据格式转换为饼状图数据格式*/
function gettofdata(obj,list){
	var totalnum = 0; //业态类型总户数
	var totalarea = 0;//业态类型总面积
	for(var i=0;i<obj.length;i++){
		totalnum += obj[i].countname;
		totalarea += obj[i].countArea;
	}
	if(totalnum == 0) return;
	for(var j=0;j<obj.length;j++){
		var piedata = new Object();
		piedata.name = obj[j].name;
		piedata.y = parseFloat(changeTwoDecimal((obj[j].countname/totalnum)*100));
		piedata.color = obj[j].countColor;
		list.push(piedata);
	}
}

/*计算入驻面积比例并且将数据格式转换为饼状图数据格式*/
function getareadata(obj,list){
	var totalarea = 0;//总面积
	for(var i=0;i<obj.length;i++){
		totalarea+=parseFloat(obj[i].areanum);
	}
	if(totalarea == 0) return;
	for(var j=0;j<obj.length;j++){
		var piedata=new Object();
		if(obj[j].areaname=="3"){
			piedata.name="已入驻面积";
	  	}else if(obj[j].areaname=="2"){
	  		piedata.name="在装修面积";
	  	}else if(obj[j].areaname=="1"){
	  		piedata.name="未装修面积";
	  	}else if(obj[j].areaname=="4"){
	  		piedata.name="待售面积";
	  	}else if(obj[j].areaname=="5"){
	  		piedata.name="空置面积";
	  	}
		if(totalarea!=0){
		  piedata.y=parseFloat(changeTwoDecimal((obj[j].areanum/totalarea)*100));
		  list.push(piedata);
		}
	}
}

/*计算人数规模总户数并且将数据格式转换为饼状图数据格式*/
function getnopdata(obj,list){
	var totalnum = 0;
	var temp = obj;
	for(var i=0;i<temp.length;i++){
		for(var j in CorporationSizes.values){
			if(temp[i].nopName==CorporationSizes.values[j].value){
				temp[i].nopName = CorporationSizes.values[j].parse;
				break;
			}
		}
	}
	
	for(var i=0;i<obj.length;i++){
		totalnum += obj[i].nopCount;
	}
	
	if(totalnum == 0) return;
	
	for(var i=0;i<obj.length;i++){
		if(obj[i].nopCount == 0) continue;
		var piedata=new Object();
  	    piedata.name=obj[i].nopName;
  	    piedata.y=parseFloat(changeTwoDecimal((obj[i].nopCount/totalnum)*100));
  	    list.push(piedata);
	}
}

/*计算自持、租赁总面积并且将数据格式转换为饼状图数据格式*/
function getsaldata(obj,list){
	var totalarea = 0;
	for(var i=0;i<obj.length;i++){
		totalarea += obj[i].salCount;
	}
	
	if(totalarea == 0) return;
	
	var temp = obj;
	for(var i=0;i<temp.length;i++){
		for(var j in CorporationSta.values){
			if(temp[i].salName==CorporationSta.values[j].value){
				temp[i].salName = CorporationSta.values[j].parse;
				break;
			}
		}
	}
	
	for(var i=0;i<obj.length;i++){
		var piedata=new Object();
  	    piedata.name=obj[i].salName;
  	    piedata.y=parseFloat(changeTwoDecimal((obj[i].salCount/totalarea)*100));
  	    list.push(piedata);
	}
}



//设置登录窗口
function openPwd() {
	$('#w').window( {
		title : '修改密码',
		width : 300,
		modal : true,
		shadow : true,
		closed : true,
		height : 160,
		resizable : false
	});
}
//关闭登录窗口
function closePwd() {
	$('#w').window('close');
}

//修改密码
function serverLogin() {
	var $newpass = $('#txtNewPass');
	var $rePass = $('#txtRePass');

	if ($newpass.val() == '') {
		msgShow('系统提示', '请输入密码！', 'warning');
		return false;
	}
	if ($rePass.val() == '') {
		msgShow('系统提示', '请在一次输入密码！', 'warning');
		return false;
	}

	if ($newpass.val() != $rePass.val()) {
		msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
		return false;
	}

	$.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
		msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
		$newpass.val('');
		$rePass.val('');
		close();
	});

}

$(function() {
	openPwd();

	$('#editpass').click(function() {
		$('#w').window('open');
	});

	$('#btnEp').click(function() {
		serverLogin();
	});

	$('#btnCancel').click(function() {
		closePwd();
	});

	$('#loginOut').click(function() {
		$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

			if (r) {
				//location.href = 'index.jsp';
				 location.href="/propertybuildingmgmt/naviAction_logout.action";
			}
		});
	});
});

/*
 * 图形化初始化
 * */
function imagesinit(buildname,obj,id,pieSize){
	var chart=null;
		chart = new Highcharts.Chart({
			chart: {
				renderTo: id,
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
					size:pieSize,
					allowPointSelect: true,
					cursor: 'pointer',
					slicedOffset:15,
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

/**
 * 修改密码
 */
function modifyPw(){
	var newpw = $("#newpw").val();
	var confirmpw = $("#confirmpw").val();

	if(passwordform.newpw.value =="" || passwordform.confirmpw.value ==""){
		$.messager.alert('提示','密码不能为空');
		return false;
	}
	
	if(passwordform.newpw.value.length<6 || passwordform.confirmpw.value.length<6){
		$.messager.alert('提示','新密码必须大于六位');
		return false;
	}
	
	if(newpw != confirmpw){
		$.messager.alert('提示','新密码和确认密码不一致！');
		return;
	}
	$('#modifyPw').form('submit', {
	    url:"/propertybuildingmgmt/UserAuthorizeAction_updateUser.action",
	    onSubmit: function(param){
	    	var val=$('#modifyPw').form('validate');
	    	if(!val){
	    		return false;
	    	}
	    },
	    success:function(data){
	    	var returnData = JSON.parse(data);
	    	if(undefined != returnData){
	    		if(returnData.modifyFlag == "true"){
	    			$.messager.alert('提示','修改成功！','',function(){
	    				$('#passwordedit').dialog('close');
	    			});
	    		}else if(returnData.modifyFlag == "wrongPW"){
	    			$.messager.alert('提示','原密码错误！');
	    		}
			}else{
				$.messager.alert('提示','修改失败！');
			}
	    }
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

//设置div的宽高
function setwidandhei(id,width,height){
	$('#'+id).css('width',width);
	$('#'+id).css('height',height);
}


/**
 * 密码修改开窗
 * */
function editpassword(){
	$('#passwordedit').dialog('open');
	$("#suPassWord").val("");
}

