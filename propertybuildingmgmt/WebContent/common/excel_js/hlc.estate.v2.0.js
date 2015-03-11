
var getnode=new Array();
function hlc(destid) {

	/** ###########################################################
	 * @Desc:	首先，验证参数有效性
	 * @Dill:	
	 ** ###########################################################*/
	if(null==destid || typeof destid=="undefined" || destid.constructor!=String) {
		alert("错误：必须指定一个有效的容器元素ID！");
		return null;
	}
	if(!/^#.*$/.test(destid)) {
		alert("错误：指定容器ID时必须以#为前缀！");
		return null;
	}
	destid = destid.substr(1);
	var container = document.getElementById(destid);
	if(null==container || typeof container=="undefined" || !/^\[object .*element\]$/ig.test(container)) {
		alert("错误：必须指定一个有效的容器元素！");
		return null;
	}
	if(typeof container.HLC!="undefined" && container.HLC!=null && container.HLC.constructor==Object) {
		return container.HLC;
	}

	/** ###########################################################
	 * @Desc:	然后，构建实体对象
	 * @Dill:	
	 ** ###########################################################*/
	var HLC_LdGrid = container.HLC = new Object();
	HLC_LdGrid.container = destid;	//外部容器ID
	HLC_LdGrid.table = "";			//楼栋表格ID
	HLC_LdGrid.browser = new Object();
	var ua = navigator.userAgent.toLowerCase();
	if (window.ActiveXObject) {
		HLC_LdGrid.browser.IE = ua.match(/msie ([\d.]+)/)[1];
	} else if (!document.getBoxObjectFor) {
		HLC_LdGrid.browser.FF = ua.match(/firefox\/([\d.]+)/)[1];
	} else if (window.MessageEvent && !document.getBoxObjectFor) {
		HLC_LdGrid.browser.CM = ua.match(/chrome\/([\d.]+)/)[1];
	} else if (window.opera) {
		HLC_LdGrid.browser.OP = ua.match(/opera.([\d.]+)/)[1];
	} else if (window.openDatabase) {
		HLC_LdGrid.browser.SF = ua.match(/version\/([\d.]+)/)[1];
	}

	/** ###########################################################
	 * @Desc:	最后，定义实体的接口方法
	 * @Dill:
	 * 	1.	2014-10-01	weide		public void building();			构建表格控件
	 * 	2.	2014-10-01	weide		public void loaddata();			加载表格数据
	 ** ###########################################################*/
	/**
	 * @Method: building()
	 *  @Param: Json Object param	构建参数
	 * @Return: void
	 * @Access: public
	 *   @Desc: 构建表格控件
	**/
	
	var usertypevalue=null;
	
	HLC_LdGrid.building = function (param){
		var pstyle = param.style;					//风格属性
		var pconfig = param.config;					//配置信息
		var pdata = param.building;					//楼栋数据
		var pdebug = param.debug;					//调试输出函数
		var pchange = param.onChangeGrid;			//表格变更时的回调函数: function(info){...}
		var pload = param.onLoadCorp;				//加载公司信息回调函数: function(callback){...}
		var psave = param.onSave;					//保存楼栋信息
		var pclear = param.onClear;					//清空楼栋信息
		
	  //this.attrs = param;
		this.attrs = new Object();
		this.attrs.style = pstyle;
		this.attrs.config = pconfig;
		this.attrs.building = pdata;
		this.attrs.onChangeGrid = pchange;
		this.attrs.onLoadCorp = pload;
		this.attrs.onSave = psave;
		this.attrs.onClear = pclear;
		usertypevalue=pdata.usertype;
		
		
		
		this.DEBUG = (null!=pdebug && "undefined"!=typeof pdebug && pdebug.constructor==Function) ? pdebug : function () {
			var debug = document.getElementById("debug");
			if(debug==null) return;
			for(var i=0; i<arguments.length; i++) {
				debug.value += arguments[i]+"\r\n";
			}
		};
	
		this.loaddata();
	};
	
	/*
		解析风格数据
	*/
	HLC_LdGrid.__parsestyle = function (style) {
		style.isReadOnly = style.isReadOnly ? style.isReadOnly : false;
		style.isShowOpBar = style.isShowOpBar ? style.isShowOpBar : false;
		style.decoratingColor = style.decoratingColor ? style.decoratingColor : "white";
		style.selectedColor = style.selectedColor ? style.selectedColor : "blue";
		
		style.width = style.width ? style.width : 0;
		style.height = style.height ? style.height : 0;
		style.fontSize = style.fontSize ? style.fontSize : 9;
		
		return style;
	};
	
	/*
		解析配置数据
	*/
	HLC_LdGrid.__parseconf = function (conf) {
		var config = conf;
		//判断数据有效性
		if(config==null || typeof config=="undefined" || config.constructor!=Object) {
			//alert("错误：配置数据必须是一个指定格式的JSON对象！");
			return null;
		}
		
		var getById = function(value) {
			for(var i=0; i<this.values.length; i++) {
				if(value==this.values[i].id) return this.values[i];
			}
			return {values:[]};
		};
		var getByTag = function(value) {
			for(var i=0; i<this.values.length; i++) {
				if(value==this.values[i].tag) return this.values[i];
			}
			return {values:[]};
		};
		var getByValue = function(value) {
			for(var i=0; i<this.values.length; i++) {
				if(value==this.values[i].value) return this.values[i];
			}
			return {values:[]};
		};
		var getByParse = function(value) {
			for(var i=0; i<this.values.length; i++) {
				if(value==this.values[i].parse) return this.values[i];
			}
			return {values:[]};
		};
		
		if(config.color!=null && typeof config.color!="undefined" && config.color.constructor==Object) {
			config.color.getById = getById;
			//config.color.getByTag = getByTag;
			//config.color.getByValue = getByValue;
			//config.color.getByParse = getByParse;
		} else {
			alert("警告：未配置单位归属行业颜色！");
		}
		if(config.sizes!=null && typeof config.sizes!="undefined" && config.sizes.constructor==Object) {
			config.sizes.getById = getById;
			config.sizes.getByTag = getByTag;
			config.sizes.getByValue = getByValue;
			config.sizes.getByParse = getByParse;
		} else {
			alert("警告：未配置单位人数规模信息！");
		}
		if(config.state!=null && typeof config.state!="undefined" && config.state.constructor==Object) {
			config.state.getById = getById;
			config.state.getByTag = getByTag;
			config.state.getByValue = getByValue;
			config.state.getByParse = getByParse;
		} else {
			alert("警告：未配置房间入驻状态信息！");
		}
		if(config.ftype!=null && typeof config.ftype!="undefined" && config.ftype.constructor==Object) {
			config.ftype.getById = getById;
			config.ftype.getByTag = getByTag;
			config.ftype.getByValue = getByValue;
			config.ftype.getByParse = getByParse;
		} else {
			alert("警告：未配置楼层结构类型信息！");
		}
		
		if(config.roomperson!=null && typeof config.roomperson!="undefined" && config.roomperson.constructor==Object) {
			config.roomperson.getById = getById;
			config.roomperson.getByTag = getByTag;
			config.roomperson.getByValue = getByValue;
			config.roomperson.getByParse = getByParse;
		} else {
			alert("警告：未配置楼层结构类型信息！");
		}
		

		
		if(config.cropstate!=null && typeof config.cropstate!="undefined" && config.cropstate.constructor==Object) {
			config.cropstate.getById = getById;
			config.cropstate.getByTag = getByTag;
			config.cropstate.getByValue = getByValue;
			config.cropstate.getByParse = getByParse;
		} else {
			alert("警告：未配置楼层结构类型信息！");
		}
		
		if(config.btype!=null && typeof config.btype!="undefined" && config.btype.constructor==Object) {
			config.btype.getById = getById;
			config.btype.getByTag = getByTag;
			config.btype.getByValue = getByValue;
			config.btype.getByParse = getByParse;
		} else {
			alert("警告：未配置楼栋结构类型信息！");
		}
		
		return config;
	};
	
	/*
		解析楼栋数据
	*/
	HLC_LdGrid.__parsedata = function (data) {
		var building = data;
		//判断数据有效性
		if(building==null || typeof building=="undefined" || building.constructor!=Object) {
			//alert("错误：楼栋数据必须是一个指定格式的JSON对象！");
			return null;
		}
		var floors = building.floors;
		if(floors==null || typeof floors=="undefined" || floors.constructor!=Array) {
			//alert("错误：楼层数据必须是指定格式的楼层JSON对象数组！");
			return null;
		}
		
		building.totalHeight = 0;		//楼栋总高
		building.totalFloors = 0;		//楼栋层数
		building.maxFloorRooms=0;		//楼栋楼层最大房间数目
		//楼层排序（冒泡法）
		for(var i=0; i<floors.length; i++) {
			for(var j=i+1; j<floors.length; j++) {
				if(floors[i].order<floors[j].order) {
					var tmp = floors[i];
					floors[i] = floors[j];
					floors[j] = tmp;
				}
			}
			building.totalHeight += floors[i].height;
			building.totalFloors ++;
			var rooms = floors[i].rooms;
			if(rooms==null || typeof rooms=="undefined" || rooms.constructor!=Array) {
				//alert("错误：楼层["+floors[i].no+"]的数据必须是房间数据数组！");
				return null;
			}
			floors[i].totalArea = 0;
			floors[i].totalRooms= 0;
			//房间排序（冒泡法）
			for(var m=0; m<rooms.length; m++) {
				for(var n=m+1; n<rooms.length; n++) {
					if(parseInt(rooms[m].order)>parseInt(rooms[n].order)) {
						var tmp = rooms[m];
						rooms[m] = rooms[n];
						rooms[n] = tmp;
					}
				}
				floors[i].totalArea += rooms[m].area;
				floors[i].totalRooms++;
			}
			building.maxFloorRooms = building.maxFloorRooms<floors[i].totalRooms ? floors[i].totalRooms : building.maxFloorRooms;
			floors[i].rooms = rooms;	//非必要：因为对象会按址引用
		}
		building.floors = floors;		//非必要：因为对象会按址引用
		return building;
	};
	
	/**
	 * @Method: loaddata()
	 *  @Param: (无)
	 * @Return: void
	 * @Access: public
	 *   @Desc: 加载表格楼栋数据
	**/
	HLC_LdGrid.loaddata = function () {
		
		//整理分析数据
		var style = this.attrs.style = this.__parsestyle(this.attrs.style);
		var config = this.attrs.config = this.__parseconf(this.attrs.config);
		var building = this.attrs.building = this.__parsedata(this.attrs.building);
		if(config==null || building==null) return false;
		
		var floors = building.floors;
		
		if(floors==null) return false;
		
		var width = parseInt(style.width);   //容器宽
		var height= parseInt(style.height); //容器高

		var cellminwidth = 40;
		var cellminheight= 30;
		var cellmaxwidth = 200;
		var cellmaxheight= 150;
		var border = "1px solid #000000";
		
		//容器节点对象
		var container = document.getElementById(this.container);
		
		//创建框架表格
		var frametable = document.createElement("TABLE");					//创建表格
		while(container.hasChildNodes()) container.removeChild(container.firstChild);	//清空容器
		container.appendChild(frametable);
		
		frametable.cellSpacing = "0px";
		frametable.cellPadding = "0px";
		frametable.style.width = width + "px";
		frametable.style.height = height + "px";
		frametable.style.borderTop = border;
		frametable.style.borderLeft = border;
		
		//第一行: 楼栋标题
		var rowtitle = frametable.insertRow();								//创建一行（楼栋标题）
		var celltitle = rowtitle.insertCell();								//创建一个单元
	  
		celltitle.style.width = width + "px";
		celltitle.style.height = 40 + "px";	//固定标题栏高度为 40px
		height -= 40;
   		celltitle.style.borderRight = border;
   		celltitle.style.borderBottom = border;
        if(building.floors.length==0){return false;}
		var html = ""
		  	+ "<div style=' text-align:center; font-size:16pt;'>"
		  	+ (building.floors[0].type==1?building.name:"避难层")
		  	+ "</div>";
		celltitle.innerHTML = html;
   		celltitle.title = ""
	   		//+ "楼栋编号："+building.no+"\r\n"
	   		//+ "归属小区："+building.path+"\r\n"
	   		+ "楼栋名称："+building.name+"\r\n";
	   		//+ "楼栋类型："+config.btype.getByValue(building.type).parse+"";
	  
		//第二行: 行业颜色
   		if(config.hasindustry.length!=0){
   	
		var rowtype = frametable.insertRow();								//创建一行（行业类型颜色提示）
		var celltype = rowtype.insertCell();								//创建一个单元
	
		celltype.style.width = width + "px";
		celltype.style.height = 30 + "px";	//固定颜色栏高度为 30px
		height -= 30;
   		celltype.style.borderRight = border;
   		celltype.style.borderBottom = border;
   	
   		var shape = "border-radius: 100px/50px; -moz-border-radius: 100px/50px; -webkit-border-radius: 100px/50px;";
   		var html = "";
   		for(var index = 0; index<config.color.values.length; index++) {
   			for(var indexins=0;indexins<config.hasindustry.length;indexins++){
   				if(config.hasindustry[indexins]==config.color.values[index].id){
   					html += ""
   						+ "<span style='margin-right:5px; margin-left:5px; "+shape+" background: "+config.color.values[index].color+"; font-size:"+style.fontSize+"pt; border:1px solid #000000;'>"
   						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
   						+ config.color.values[index].name
   						+ "&nbsp;&nbsp;&nbsp;&nbsp;"
   						+ "</span>";
   				}
   			}
			
   		}
   
   		celltype.innerHTML = html;
   		}
   		
   		//新增面积行显示
   		
   		var rowareaitem=floors[0].rooms;
   		if(rowareaitem.length>0){
   			var rowareas = frametable.insertRow();							//创建一行（楼层数据列表）
   			var cellareas = rowareas.insertCell();						   //创建一个单元 
   			var html="<div><table cellpadding='0' cellspacing='0'><tr><td style='width:50px; text-align:center; height:35px; border-right:1px solid #000;border-bottom:1px solid #000;font-size:11pt;'>面积</td>";
   			for(var i=0;i<rowareaitem.length;i++){
              html+="<td style='width:150px; height:35px; text-align:center; border-right:1px solid #000;border-bottom:1px solid #000;'>"+rowareaitem[i].area+"㎡</td>";  
   			}
   			html+="</tr></table></div>";
   			cellareas.innerHTML=html;
   		}
   		
   		//第三行: 楼层数据
   	
		var rowbuilding = frametable.insertRow();							//创建一行（楼层数据列表）
		var cellbuilding = rowbuilding.insertCell();						//创建一个单元
	  
		//第四行: 操作栏位
		if(!style.isReadOnly && style.isShowOpBar) {
			var rowop = frametable.insertRow();								//创建一行（信息操作）
			var cellop = rowop.insertCell();								//创建一个单元
	  
			cellop.style.width = width + "px";
			cellop.style.height = 30 + "px";	//固定操作栏高度为 30px
	  		height -= 30;
     		cellop.style.borderRight = border;
     		cellop.style.borderBottom = border;
		  
			var html = ""
			  	+ "<div style='text-align:center;'>"
			  	+ "	<button onclick='this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.HLC.save();'>保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			  	+ "	<button onclick='this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.HLC.clear();'>清空</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			  	+ "	<button onclick='this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.HLC.cancel();'>取消</button>"
			  	+ "</div>";
			cellop.innerHTML = html;
		}
	  
		cellbuilding.style.width = width + "px";
		cellbuilding.style.height = height + "px";
		//cellbuilding.style.borderRight = border;
		//cellbuilding.style.borderBottom = border;
   	
   		//创建楼栋结构
   		var buildingdiv = document.createElement("DIV");
   		cellbuilding.appendChild(buildingdiv);
		//buildingdiv.style.width = "100%";
		//buildingdiv.style.height = "100%";
   	
		var floororderwidth = 35;	//固定楼层号列宽度为 35px
		var buildingwidth = width;
		var buildingheight= height;
//		if(((cellminwidth*building.maxFloorRooms)+floororderwidth)>buildingwidth) {
//	   		buildingdiv.style.overflowX = "scroll";
//	   		buildingheight -= 0;	//横向滚动条不占用高度!!
//		}
//		if((cellminheight*building.totalFloors)>buildingheight) {
//	   		buildingdiv.style.overflowY = "scroll";
//	   		buildingwidth -= 20;	//预留20px作纵向滚动条!!
//		}
		//创建楼栋表格
		var buildingtable = document.createElement("TABLE");				//创建表格（楼栋）
		buildingdiv.appendChild(buildingtable);
   	
		buildingtable.id = "building_" + container.id;
		this.table = buildingtable.id;
		buildingtable.hlcBuilding = building;								//楼栋数据
		buildingtable.hlcContainer = container.id;							//容器元素ID
		buildingtable.hlcSelectedColor = style.selectedColor;				//选中时，单元的背景色
		buildingtable.hlcSelectedCells = new Array();						//当前选中的单元格数组
		buildingtable.hlcRoomCells = new Array();
		buildingtable.hlcRoomCellTds = new Array();
		buildingtable.cellSpacing = "0px";
		buildingtable.cellPadding = "0px";
		buildingtable.style.width = buildingwidth + "px";	
   		buildingtable.style.height = buildingheight + "px";
		buildingtable.style.border = "0px";
		
		var sumheight = 0;
		for(var i=0; i<floors.length; i++) {
			var row = buildingtable.insertRow();							//创建一行（楼层: 第i层）
			row.id = "floor_" + i + "_" + floors[i].order;
	    	var floorwidth = buildingwidth;
	    	var floorheight = 0;
	    	//按照层高比例占用宽度
	    	if(i<floors.length-1) {
	    		floorheight = 35;
	    		sumheight += floorheight;
	    	//最后一个楼层占用剩余高度
	    	} else {
	    		floorheight = 35;
	    	}
			//创建楼层号列
			var cellorder = row.insertCell();								//创建第1格（楼层号）
		    cellorder.id = "floor_" + i + "_0_" + floors[i].no;
		    floorwidth -= floororderwidth;
		    cellorder.style.borderRight = border;
	     	cellorder.style.borderBottom = border;
	     	var html = "<div style='width:50px;height:35px;line-height:35px;font-size:"+(style.fontSize+2)+"pt; text-align:center;'>"+floors[i].no+"</div>";
	     	cellorder.innerHTML = html;
		 	cellorder.title = ""
		 		+ "楼层编号："+floors[i].no+"\r\n"
		 		//+ "楼层结构："+config.ftype.getByValue(floors[i].type).parse+"\r\n"
		 		+ "楼层高度："+floors[i].height+"米";
	     	//创建房间列表列
	     	var celllist = row.insertCell();								//创建第2格（房间列表）
		    celllist.style.width = floorwidth + "px";
		    celllist.style.height = floorheight + "px";
		  	celllist.style.borderRight = border;
	    	celllist.style.borderBottom = border;
	     	//创建楼层表格
	     	var floortable = document.createElement("TABLE");				//创建表格（楼层）
			floortable.id = "floor_" + i + "_1_" + floors[i].no;
			floortable.cellSpacing = "0px";
			floortable.cellPadding = "0px";
			floortable.style.width = floorwidth + "px";	
     		floortable.style.height = "100%";
			floortable.style.border = "0px";
			//创建楼层行
			var row = floortable.insertRow();
			row.hlcFloor = floors[i];										//楼层数据
			//创建房间格
			////一个单位入驻多个房间
			var prev = null;
			var sumwidth = 0;
			for(var j=0; j<floors[i].rooms.length; j++) {
		     	var roomwidth = 150;
		     	var roomheight = "40px";
		    	//按照面积比例占用宽度
//		    	if(j<floors[i].rooms.length-1) {
//		    		roomwidth = Math.round(floorwidth*(floors[i].rooms[j].area/floors[i].totalArea));
//		    		sumwidth += roomwidth;
//		    	//最后一个房间占用剩余宽度
//		    	} else {
//		    		roomwidth = floorwidth - sumwidth;
//		    	}
//		     	roomwidth = roomwidth>cellminwidth  ? roomwidth : cellminwidth;		//保证单元格不会太小
//		     	roomheight= roomheight>cellminheight? roomheight: cellminheight;	//保证单元格不会太小
			    //roomwidth = roomwidth<cellmaxwidth  ? roomwidth : cellmaxwidth;	//保证单元格不会太大
			    //roomheight= roomheight<cellmaxheight? roomheight: cellmaxheight;	//保证单元格不会太大
				//创建一个房间
		     	var cell = document.createElement("TD");							//创建一格（房间: 第j间）
		     	buildingtable.hlcRoomCellTds.push(cell);
		     	cell.id = "room_" + i + "_" + j + "_" + floors[i].rooms[j].no;
		     	cell.hlcRoom = floors[i].rooms[j];									//单元格对应的房间信息
		     	cell.hlcOrder = {floor:floors[i].order, room:floors[i].rooms[j].order};		//单元格对应的楼栋坐标
		     	cell.hlcXY = {x:j, y:i};													//单元格对应的表格坐标
		     	cell.hlcChecked = false;													//单元格自身的选中状态
		     	var showname = null==floors[i].rooms[j].corp ? "" : floors[i].rooms[j].corp.name;
		     	if(floors[i].rooms[j].state==config.state.getByTag("ZX").value && floors[i].rooms[j].corp!=null) {
		     		showname +="("+ config.state.getByTag("ZX").parse+")";
		     		cell.hlcOrignalColor =  config.color.getById(floors[i].rooms[j].corp.type).color;
		     	} else if(null!=floors[i].rooms[j].corp) {
		     		cell.hlcOrignalColor = config.color.getById(floors[i].rooms[j].corp.type).color;
		    	} else {
		    		cell.hlcOrignalColor = cell.style.backgroundColor;
		    	}
		    	cell.hlcOrignalWH = {w:roomwidth, h:roomheight};					//单元格原始宽高
		    	if(!style.isReadOnly) {
		    		if(this.attrs.building.usertype==1){
			    	cell.oncontextmenu = this.oncontextmenucell;
			    	cell.onclick = this.onclickcell;
		    		}else{
		    			cell.onclick = this.onclickcell;
		    		}
		    	}
		     	cell.style.width = roomwidth+"px";
		     	cell.style.height = roomheight+"px";
		     	var islastroom = j>=floors[i].rooms.length-1 ? true : false;		//是否最后一格单元格？
		     	cell.hlcOrignalIsLastRoom = islastroom;
		     	cell.hlcIsLastRoom = cell.hlcOrignalIsLastRoom;
		     	if(!cell.hlcIsLastRoom) {											//最后一格的右边框不画
		    		cell.style.borderRight = border;
		    	}
	     		//cell.style.borderBottom = border;
		     	cell.style.backgroundColor = cell.hlcOrignalColor;
		     	//获取公司名称(汉字)长度
		     	var len = 0;
		     	for(var z=0; z<showname.length; z++) {
		     		len += (showname.charCodeAt(z)>255) ? 2 : 1;
		     	}
		     	var itemshowit=0==len?"line-height:"+40+"px":"";
		     	len = Math.ceil(len/2);
		         
		     	
		     
		     	//PT与PX换算关系
		     	var FnPtToPx = function (pt) {
		     		return 12; //暂时这样！！！
		     	};
		     	//计算公司名称可能会被自动分为几行？
		     	var lineheight = 0==len ? parseInt(cell.style.height)
		     		: parseInt(cell.style.height)/(Math.ceil((len*FnPtToPx(style.fontSize))/parseInt(cell.style.width)));
		     	
		     	var html = "";
		     	html += ""
		     		+	"<div id='"+cell.id+"_inner' name='inner' style='position:relative; width:"+cell.style.width+"; height:100%;'>"
					+	"	<div id='"+cell.id+"_no' name='no'    style='z-index:1; position:absolute; width:100%; height:100%; line-height:37px;"
					+"font-size:"+(2*style.fontSize)+"pt; text-align:center; filter:Alpha(Opacity=10); opacity:0.10;'>"
					+			floors[i].rooms[j].no
					+	"	</div>"
	     			+	"	<div id='"+cell.id+"_corp' name='corp'  style='z-index:2; overflow:hidden; position:absolute; width:100%; height:100%;"
	     			+" font-size:"+(1*style.fontSize)+"pt; font-family:Tahoma,Verdana,微软雅黑,新宋体; text-align:center;'>"
	     			+"<div title='"+showname+"' style='cursor:pointer;overflow:hidden;word-break:break-all;height:100%;"+itemshowit+"' class='easyui-tooltip'>"
	     			+			showname
	     			+	"</div>	</div>"
		    		+	"</div>";
		    	cell.innerHTML = html;
		    	if(style.isReadOnly) {
				 	cell.title = ""
				 		+ "房间编号："+floors[i].rooms[j].no+"\r\n"
				 		+ "房间状态："+config.state.getByValue(floors[i].rooms[j].state).parse+"\r\n"
				 		+ "房间面积："+floors[i].rooms[j].area+"平方米";
			 	}
		    	
		    	
		     	cell.hlcCells = new Array();					//单元格占用的房间数组
		     	buildingtable.hlcRoomCells.push(cell);
		    	//合并房间
				if(null!=prev
					&& "undefined"!=typeof prev.hlcRoom && null!=prev.hlcRoom
					&& "undefined"!=typeof prev.hlcRoom.corp && null!=prev.hlcRoom.corp
					&& "undefined"!=typeof prev.hlcRoom.corp.id && null!=prev.hlcRoom.corp.id
					&& "undefined"!=typeof floors[i].rooms[j].corp && null!=floors[i].rooms[j].corp
					&& "undefined"!=typeof floors[i].rooms[j].corp.id && null!=floors[i].rooms[j].corp.id
					&& prev.hlcRoom.corp.id==floors[i].rooms[j].corp.id)
				{
					if(this.attrs.building.usertype==1){  //管理员账号
						
					}else{  //非管理员账号
						
						if(prev.hlcRoom.state == floors[i].rooms[j].state){
							//当两间房状态一样时  合并
							prev.colSpan = parseInt(prev.colSpan)+parseInt(cell.colSpan);
							var w = 0;
							w = parseInt(prev.style.width)+parseInt(cell.style.width);
							prev.style.width = w + "px";
							w = parseInt(prev.childNodes[0].style.width)+parseInt(cell.childNodes[0].style.width);
							prev.childNodes[0].style.width = (w+1) + "px";									//因为合并后少了一条边框，即1px
							prev.style.borderRight = cell.hlcIsLastRoom ? "0px" : prev.style.borderRight;	//最后一格的右边框不画
							prev.hlcIsLastRoom = cell.hlcIsLastRoom;
							prev.childNodes[0].childNodes[1].innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;" + floors[i].rooms[j].no;
							prev.hlcCells.push(cell);
							continue;	
						}
					}
				}
		     	prev = cell;
		     	row.appendChild(cell);
			}
			celllist.appendChild(floortable);
   		}
		return true;
	};

	/** ###########################################################
	 * @Desc:	定义房间单元格的事件响应方法
	 * @Dill:
	 * 	1.	2014-10-01	weide		public void onclickcell();			表格单元格单击响应
	 * 	2.	2014-10-01	weide		public void ondbclickcell();		表格单元格双击响应
	 *	3.	2014-10-01	weide		public void oncontextmenucell		表格单元格右键菜单响应
	 ** ###########################################################*/
	/**
	 * @Method: onclickcell()
	 *  @Param: HTML Event e	事件
	 * @Return: void
	 * @Access: public
	 *   @Desc: 表格单元格单击响应
	**/

	
	HLC_LdGrid.onclickcell = function (e) {
		

			if(usertypevalue==1){  //管理员操作
//			   
				//即：[td].[tr].[tbody].[table].td.tr.tbody.table
				
				var table = this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
				table.hlcSelectedCells=new Array();
				table.hlcSelectedCells.push(this);
				this.hlcChecked = !this.hlcChecked;
		        if(getnode[0]==this.childNodes[0]){
		        	this.childNodes[0].style.background="";
		        	getnode=new Array();
			        return false;
		        }
					if(getnode.length!=0){
						  for(var i=0;i<getnode.length;i++){
						    	getnode[i].style.background="";
						    }
						    getnode=new Array();
					}
					var div = this.childNodes[0];
					getnode.push(div);
					div.style.background="url('./common/images/ok.png') no-repeat #c1c1c1";	//FF IE7
			    }else{
			    	var table = this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
	     	    	table.hlcSelectedCells=new Array();
			    	table.hlcSelectedCells.push(this);
			    	HLC_LdGrid.__display();
			    }
//			div.style.filter = "alpha(opacity=50)";				//IE
//			div.style.opacity = "0.5";							//FF
			//添加选中的单元格
		
	};
	
	/**
	 * @Method: ondbclickcell()
	 *  @Param: HTML Event e	事件
	 * @Return: void
	 * @Access: public
	 *   @Desc: 表格单元格双击响应
	**/
	HLC_LdGrid.ondbclickcell = function (e) {
		
			
	};
	
	/*
		弹出式菜单
	*/
	HLC_LdGrid.__menu = function (e, items) {
		var id = this.container + "__menu";
		var menu = document.getElementById(id);
		if(null==menu) {
			var menu = document.createElement("DIV");
			document.body.appendChild(menu);
			menu.hlcClose = function () {
				this.style.visibility = "hidden";		
			};
			menu.id = id;
			menu.style.display = "block";
			menu.style.visibility = "hidden";
			menu.style.backgroundColor = "#f0f0f0";
			menu.style.width = "120px";
			//menu.style.position = "fixed!important";		//FF IE7
			menu.style.position = "absolute";				//IE6
			menu.style.zIndex = 997;
    		menu.style.fontSize = "9pt";
    		menu.style.fontFamily = "Tahoma,Verdana,微软雅黑,新宋体";
    		menu.style.border = "1px solid #cccccc";
	    	for(var i=0; i<items.length; i++) {
	    		var menuitem = document.createElement("DIV");
				menuitem.hlcContainer = this.container;
	    		menuitem.hlcText = items[i].text;
	    		menuitem.hlcFlag = items[i].op;
	    		menu.appendChild(menuitem);
	    		if(items[i].styleshow=="none"){
	    			menuitem.style.color="#d9d9d9";
	    		}else{
	    			menuitem.onmouseover = function () {
		    			this.style.backgroundColor="#dfdfdf";
		    		};
		    		menuitem.onmouseout = function () {
		    			this.style.backgroundColor="#f1f1f1";
		    		};
		    		menuitem.onclick = function () {
		    			document.getElementById(this.hlcContainer).HLC.onopmenuclick(this.hlcFlag);
		    		};
	    		}
	    		menuitem.style.lineHeight = "25px";
	    		if(i!=0) menuitem.style.borderTop = menu.style.border;
	    		menuitem.innerHTML = "<span style='cursor:pointer;'>&nbsp;"+menuitem.hlcText+"&nbsp;</span>";
	    	}
		}else{
			menu.innerHTML="";
			for(var i=0; i<items.length; i++) {
	    		var menuitem = document.createElement("DIV");
				menuitem.hlcContainer = this.container;
	    		menuitem.hlcText = items[i].text;
	    		menuitem.hlcFlag = items[i].op;
	    		menu.appendChild(menuitem);
	    		if(items[i].styleshow=="none"){
	    			menuitem.style.color="#d9d9d9";
	    		}else{
	    			menuitem.onmouseover = function () {
		    			this.style.backgroundColor="#dfdfdf";
		    		};
		    		menuitem.onmouseout = function () {
		    			this.style.backgroundColor="#f1f1f1";
		    		};
		    		menuitem.onclick = function () {
		    			document.getElementById(this.hlcContainer).HLC.onopmenuclick(this.hlcFlag);
		    		};
	    		}
	    		menuitem.style.lineHeight = "25px";
	    		if(i!=0) menuitem.style.borderTop = menu.style.border;
	    		menuitem.innerHTML = "<span style='cursor:pointer;'>&nbsp;"+menuitem.hlcText+"&nbsp;</span>";
	    	}
		}
		menu.style.visibility = "visible";
		menu.hlcOnClickDocument = document.onclick;
		document.hlcMenu = id;
		document.onclick = function () {
			//alert(JsonUtil.convertToString(document.hlcMenu));
			var menu = document.getElementById(document.hlcMenu);
			menu.style.visibility = "hidden";
			document.onclick = menu.hlcOnClickDocument;			
		}
		
		//获取当前鼠标右键按下后的位置，据此定义菜单显示的位置
		
		var context = document.getElementById(this.table);
    	var rightedge = context.clientWidth-e.clientX;
	    var bottomedge = context.clientHeight-e.clientY;
	
	    //如果从鼠标位置到容器右边的空间小于菜单的宽度，就定位菜单的左坐标（Left）为当前鼠标位置向左一个菜单宽度
	    if (rightedge < menu.offsetWidth) {
	    	menu.style.left = context.scrollLeft + e.clientX - menu.offsetWidth + "px";
	    //否则，就定位菜单的左坐标为当前鼠标位置
	    } else {
	    	menu.style.left = context.scrollLeft + e.clientX + "px";
	    }
	    //如果从鼠标位置到容器下边的空间小于菜单的高度，就定位菜单的上坐标（Top）为当前鼠标位置向上一个菜单高度
	    if (bottomedge < menu.offsetHeight) {
	    	menu.style.top = context.scrollTop + e.clientY - menu.offsetHeight + "px";
	    //否则，就定位菜单的上坐标为当前鼠标位置
	    } else {
	    	menu.style.top = context.scrollTop + e.clientY+ "px";
	    }
	    
		return menu;
	};
	
	/**
	 * @Method: oncontextmenucell()
	 *  @Param: HTML Event e	事件
	 * @Return: void
	 * @Access: public
	 *   @Desc: 表格单元格右键菜单响应
	**/
	HLC_LdGrid.oncontextmenucell = function (e) {
		
		if(getnode.length!=0){
			  for(var i=0;i<getnode.length;i++){
			    	getnode[i].style.background="";
			    }
			    getnode=new Array();
		}
		
		var table = this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;

		table.hlcSelectedCells=new Array();

		var div = this.childNodes[0];
		getnode.push(div);
		div.style.background="url('./common/images/ok.png') no-repeat #c1c1c1";	//FF IE7
//		div.style.filter = "alpha(opacity=50)";				//IE
//		div.style.opacity = "0.5";							//FF
		//添加选中的单元格
		table.hlcSelectedCells.push(this);
		
		var e = e || window.event;
		//即：[td].[tr].[tbody].[table].td.tr.tbody.table
		var container = document.getElementById(table.hlcContainer);
		//弹出菜单
		var items=null;
		if(table.hlcSelectedCells[0].hlcRoom.state!=4){
			items = [{text:"查看",op:"display",styleshow:""},
    	             {text:"编辑房间",op:"insert",styleshow:""},
    	             //{text:"装修",op:"decorate"},
    	             {text:"清除公司",op:"delete",styleshow:table.hlcSelectedCells[0].hlcRoom.corp==null?"none":""}];
		}else{
			items = [{text:"查看",op:"display",styleshow:""},
			         {text:"编辑房间",op:"insert",styleshow:""},
    	             //{text:"装修",op:"decorate"},
    	             {text:"清除公司",op:"delete",styleshow:table.hlcSelectedCells[0].hlcRoom.corp==null?"none":""}];
		}
    	
		var menu = container.HLC.__menu(e,items);
		if (document.all) {  
			e.returnValue = false;	//IE
		} else {  
	    	e.preventDefault();
	    };
		return false;
	};
	
	/*
		弹出对话框的同时，弹出保护表格控件的背景幕布
	*/
	HLC_LdGrid.__background = function () {
		var id = this.container + "__background";
		var background = document.getElementById(id);
		if(null==background) {
			var background = document.createElement("DIV");
			document.body.appendChild(background); 
			background.hlcClose = function () {
				this.style.visibility = "hidden";
			};
			background.id = id;
			background.style.display = "block";
			background.style.visibility = "hidden";
			background.style.backgroundColor = "#666";
			background.style.width = "100%";
			background.style.height = "100%";
			background.style.left = "0px";
			background.style.top = "0px";						//FF IE7
			background.style.filter = "alpha(opacity=50)";		//IE
			background.style.opacity = 0.5;						//FF
			background.style.zIndex = 998;
			//background.style.position = "fixed!important";	//FF IE7
			background.style.position = "absolute";				//IE6
			background.style["_top"] = ""
				+ "expression(eval(document.compatMode && document.compatMode=='CSS1Compat') ? "
		        + "documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 : "
		        + "document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2)";
		}
		background.style.visibility = "visible";
		return background;
	};
	
	/*
	 * 弹出产看框
	 * 
	 * */
	HLC_LdGrid.__easyshow = function (html){
	
		$('#lookdiv').dialog({
			title: '查看',
			width: 800,    
		    height: 260,  
		    closed:false,
		    content:html	
		});
	};
	
	/*
		弹出对话框
	*/
	HLC_LdGrid.__dailog = function (html) {
		var background = this.__background();
		var id = this.container + "__dailog";
		var dailog = document.getElementById(id);
		if(null==dailog) {
			var dailog = document.createElement("DIV");
			document.body.appendChild(dailog); 
			dailog.hlcContainer = this.container;
			dailog.hlcBackground = background;
			dailog.hlcClose = function () {
				this.style.visibility = "hidden";
				this.hlcBackground.hlcClose();
			};
			dailog.id = id;
			dailog.style.display = "block";
			dailog.style.visibility = "hidden";
			dailog.style.backgroundColor = "#ffffff";
			dailog.style.zIndex = 999;
			//dailog.style.width = width + "px";
			//dailog.style.height = height + "px";
			dailog.style.left = "40%";
			dailog.style.top = "10%";							//FF IE7
			//dailog.style["margin-left"] = "-300px!important";	//FF IE7 该值为本身宽的一半
			//dailog.style["margin-top"] = "-150px!important";	//FF IE7 该值为本身高的一半
			//dailog.style["margin-top"] = "0px";
			//dailog.style.position = "fixed!important";		//FF IE7
			dailog.style.position = "absolute";					//IE6
			dailog.style["_top"] = ""
				+ "expression(eval(document.compatMode && document.compatMode=='CSS1Compat') ? "
		        + "documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 : "
		        + "document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2)";
		    dailog.style.fontSize = "9pt";
    	}
	    dailog.innerHTML = html;
	    dailog.style.visibility = "visible";
		return dailog;
	};
	
	/*
		查询展示房间信息
	*/
	HLC_LdGrid.__display = function () {
		var table = document.getElementById(this.table);
		var form = "form__display_" + table.hlcContainer;
		var config = this.attrs.config;
		if(table.hlcSelectedCells.length<=0) {
			$.messager.alert('提示','错误：不允许的操作！必须先选择要操作的房间。');
			return;
		}
		var selectedroom = null;
		var selectedcorp = null;
		var selectedhtml = "";
		var list = new Object();
		//整理选中的房间数据
		var info = this.__arrangselected(table.hlcSelectedCells);
		table.hlcArrangedInfo = info;

		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			list=table.hlcSelectedCells[i].hlcCells;
			var floor = table.hlcSelectedCells[i].parentNode.hlcFloor;
			var selected = "";
			if(null!=room.corp && null==selectedcorp) {
				selectedroom = room;
				selectedcorp = room.corp;
				selected = "selected";
			}
			selectedhtml += "<option value='"+i+"' onclick='loadroominfonow("+i+")' "+selected+">"+floor.no+"    "+room.no+"</option>";
		}
		selectedroom = null==selectedroom ? table.hlcSelectedCells[0].hlcRoom : selectedroom;
	    
		var selectfanghao=table.hlcSelectedCells[0].hlcRoom.no;
		var selectroomareashow=table.hlcSelectedCells[0].hlcRoom.area;

		
if(list!=null || list!=""){
			for(var i=0;i<list.length;i++){
			   if(selectfanghao!="")selectfanghao+=",";
			   selectfanghao+=list[i].hlcRoom.no;
			   selectroomareashow+=list[i].hlcRoom.area;
		}
}
      
		var html = ""
			+ "<form id='"+form+"'><table style='margin-left:50px; line-height:25px;'>"
			+ "	<tr><td valign='top' style='text-align:right; padding-right:10px;'>房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</td><td style='width:170px;'valign='top'><label><div style='width:150px;overflow:auto;word-break:break-all'>"+selectfanghao+"</div></label></td>"
			+ "	<td valign='top' style='text-align:right; padding-right:10px;'>面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积:</td><td style='width:170px;' valign='top'><label>"+selectroomareashow+"</label></td>"
			+ "	<td valign='top' style='text-align:right; padding-right:10px;'>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</td><td style='width:170px;' valign='top'><label>"+config.state.getByValue(selectedroom.state).parse+"</label></td></tr>"
			+ "	<tr><td style='text-align:right; padding-right:10px;' valign='top'>入驻单位:</td><td><label><div style='width:150px;overflow:auto;word-break:break-all'>"+(null==selectedcorp?"":selectedcorp.name)+"</div></label></td>"
			+ "	<td style='text-align:right; padding-right:10px;' valign='top'>单位别称:</td><td><label><div style='width:150px;overflow:auto;word-break:break-all'>"+(null==selectedcorp?"":selectedcorp.alias)+"</div></label></td>"
			+ "	<td style='text-align:right; padding-right:10px;' valign='top'>单位全称:</td><td><label><div style='width:150px;overflow:auto;word-break:break-all'>"+(null==selectedcorp?"":selectedcorp.fullname)+"</div></label></td></tr>"
			+ "	<tr><td style='text-align:right; padding-right:10px;'>行业分类:</td><td><label>"+(null==selectedcorp?"":config.color.getById(selectedcorp.type).name)+"</label></td>"
			+ "	<td style='text-align:right; padding-right:10px;'>人数规模:</td><td><label>"+(null==selectedcorp?"":config.sizes.getByValue(selectedcorp.size).parse)+"</label></td>"
			+ "	<td style='text-align:right; padding-right:10px;'>租户类型:</td><td><label>"+(null==selectedcorp?"":config.roomperson.getByValue(selectedcorp.state).parse)+"</label></td></tr>"
			+ "	<tr><td style='text-align:right; padding-right:10px;'>业主姓名:</td><td><label>"+(null==selectedroom?"":selectedroom.owner)+"</label></td>"
			+ "	<td style='text-align:right; padding-right:10px;'>联系电话:</td><td><label>"+(null==selectedroom?"":selectedroom.ownertel)+"</label></td></tr>"
			+ "</table></form>";
		
			this.__easyshow(html);
//			dailog.hlcRooms = list;
//			dailog.hlcConfig = config;
//			dailog.hlcForm = form;
//			dailog.hlcOnSelectedRoom = function () {
//				var form = document.getElementById(this.hlcForm);
//				for(var i=0; i<this.hlcRooms.length; i++) {
//					var selected = this.hlcRooms[i];
//					if(form.croom.value!=selected.id) continue;
//					form.no.value = selected.no;
//					form.area.value = selected.area;
//					form.state.value = this.hlcConfig.state.getByValue(selected.state).parse;
//					var corp = selected.corp;
//					form.cid.value = null==corp?"":corp.id;
//					form.cname.value = null==corp?"":corp.name;
//					form.alias.value = null==corp?"":corp.alias;
//					form.fullname.value = null==corp?"":corp.fullname;
//					form.type.value = null==corp?"":this.hlcConfig.color.getById(corp.type).name;
//					form.sizes.value = null==corp?"":this.hlcConfig.sizes.getByValue(corp.size).parse;
//					form.contactor.value = null==corp?"":corp.contactor;
//					form.tel.value = null==corp?"":corp.tel;
//					break;
//				}
//			};
			table.hlcArrangedInfo=null;
	};
	
	
	/*
		加载公司信息
	*/
	HLC_LdGrid.__loadcorp = function (form) {
		if(/^\s*$/ig.test(form.name.value)) {
			$.messager.alert('提示','错误：必须输入模糊名称！');
			return false;
		}
		//表单进入不可编辑状态
	    form.hlcSetEnable(false);
	    //回调用户获取企业列表
	    var contid = this.container;
	    var formid = form.id;
		var ret = this.onloadcorporation( form.ckname.value, function(corp){
	    	var _this = document.getElementById(contid).HLC;
	    	var form = document.getElementById(formid);
	    	if("undefined"==typeof corp || null==corp || corp.constructor!=Object) {
	    		//alert("错误：回执的单位信息必须是一个指定格式的JSON对象！");
	    		form.hlcSetEnable(false);
	    		return;
	    	}
	    	var fieldname = "";
	    	if("undefined"==typeof corp.id) fieldname += (""==fieldname?"":",") + "id";
	    	if("undefined"==typeof corp.name) fieldname += (""==fieldname?"":",") + "name";
	    	if("undefined"==typeof corp.type) fieldname += (""==fieldname?"":",") + "type";
	    	if(""!=fieldname){
	    		//alert("错误：回执的单位信息必须包含字段："+fieldname+"！");
	    		form.hlcSetEnable(false);
	    		return;
	    	}
	    	var fieldvalue= "";
	    	if(null==corp.id || /^\s*$/ig.test(corp.id)) fieldvalue += (""==fieldvalue?"":",") + "id";
	    	if(null==corp.name || /^\s*$/ig.test(corp.name)) fieldvalue += (""==fieldvalue?"":",") + "name";
	    	if(null==corp.type || /^\s*$/ig.test(corp.type)) fieldvalue += (""==fieldvalue?"":",") + "type";
	    	if(""!=fieldvalue){
	    		//alert("错误：回执的单位信息必须包含取值的字段："+fieldvalue+"中存在无效值！");
	    		form.hlcSetEnable(false);
	    		return;
	    	}
	    	
	    	if("undefined"==typeof corp.name) corp.name = "";
	    	if("undefined"==typeof corp.alias) corp.alias = "";
	    	if("undefined"==typeof corp.fullname) corp.fullname = "";
	    	if("undefined"==typeof corp.size) corp.size = "";
	    	if("undefined"==typeof corp.contactor) corp.contactor = "";
	    	if("undefined"==typeof corp.tel) corp.tel = "";
	    	if("undefined"==typeof corp.state) corp.state = "";
	    	if("undefined"==typeof corp.staterooms) corp.staterooms = "";
	    	form.cid.value = corp.id;
	    	form.cname.value = corp.name;
	    	form.alias.value = corp.alias;
			form.fullname.value = corp.fullname;
			form.type.value = corp.type;	//_this.hlcConfig.color.getById(corp.type).name;
			form.sizes.value = corp.size;	//_this.hlcConfig.sizes.getByValue(corp.size).parse;
			form.state.value = corp.state;
			form.contactor.value = corp.contactor;
			form.tel.value = corp.tel;
			form.oper.value = "insert";
            form.statecorp.value = corp.staterooms;
			//表单进入不可编辑状态
	    	form.hlcSetEnable(false);
    	} );
		//表单恢复回可编辑状态
    	form.hlcSetEnable(ret);
	};
	
	/*
		整理所选房间单元格信息
	*/
	HLC_LdGrid.__arrangselected = function (selects) {
		//按楼层排序
		for(var i=0; i<selects.length; i++) {
			for(var j=i+1; j<selects.length; j++) {
				if(selects[i].hlcXY.y < selects[j].hlcXY.y) continue;
				var tmp = selects[i];
				selects[i] = selects[j];
				selects[j] = tmp;
			}
		}
		//整理要操作的房间数组
		var rooms = new Array();
		//按房间排序
		for(var i=0; i<selects.length; i++) {
			for(var j=i+1; j<selects.length&&selects[i].hlcXY.y==selects[j].hlcXY.y; j++) {
				if(selects[i].hlcXY.x < selects[j].hlcXY.x) continue;
				var tmp = selects[i];
				selects[i] = selects[j];
				selects[j] = tmp;
			}
			rooms.push(selects[i].hlcRoom);
			//占用的房间也排序
			var cells = selects[i].hlcCells;
			for(var m=0; m<cells.length; m++) {
				for(var n=m+1; n<cells.length; n++) {
					if(cells[m].hlcXY.x < cells[n].hlcXY.x) continue;
					var tmp = selects[m];
					selects[m] = selects[n];
					selects[n] = tmp;
				}
				rooms.push(cells[m].hlcRoom);
			}
			selects[i].hlcCells = cells;	//非必要：因为对象会按址引用
		}
		//按楼层结构顺序整理
		var floors = new Array();
		for(var i=0; i<selects.length; i++) {
			var tmp = selects[i];
			for(var j=0; j<floors.length; j++) {
				if("undefined"==typeof floors[j][0] || selects[i].hlcXY.y==floors[j][0].hlcXY.y) {
					floors[j].push(tmp);
					tmp = null;
					break;
				}
			}
			if(null!=tmp) {
				floors.push([selects[i]]);
			}
		}
		return {selects:floors, rooms:rooms};
	};
	
	/*
		填充入驻单位信息进表格
	*/
	HLC_LdGrid.__fillcorp = function (corp,selects) {
		var config = this.attrs.config;
		var table = document.getElementById(this.table);
		for(var i=0; i<selects.length; i++) {
			var prev = null;
			for(var j=0; j<selects[i].length; j++) {
				var cell = selects[i][j];
				//填充公司信息
				
				cell.hlcRoom.corp = corp;
				cell.hlcOrignalColor = config.color.getById(cell.hlcRoom.corp.type).color;
				cell.hlcChecked = false;
				cell.style.backgroundColor = cell.hlcOrignalColor;
				var div = cell.childNodes[0];
				div.style.backgroundColor = "";	//FF IE7
				div.style.filter = "";			//IE
				div.style.opacity = "";			//FF
				cell.childNodes[0].childNodes[3].innerHTML = cell.hlcRoom.corp.name;
				//房间相邻
				if((null!=prev&&prev.hlcXY.x+1==cell.hlcXY.x) || (null!=prev&&0<prev.hlcCells.length&&prev.hlcCells[prev.hlcCells.length-1].hlcXY.x+1==cell.hlcXY.x))
				{
					//删除被合并的单元格
//					cell.parentNode.removeChild(cell);
//					//合并显示内容
//					//prev.colSpan = parseInt(prev.colSpan)+parseInt(cell.colSpan);
//					var w = 0;
//					w = parseInt(prev.style.width)+parseInt(cell.style.width);
//					prev.style.width = w + "px";
//					w = parseInt(prev.childNodes[0].style.width)+parseInt(cell.childNodes[0].style.width);
//					prev.childNodes[0].style.width = (w+1) + "px";									//因为合并后少了一条边框，即1px
//					prev.style.borderRight = cell.hlcIsLastRoom ? "0px" : prev.style.borderRight;	//最后一格的右边框不画
//					prev.hlcIsLastRoom = cell.hlcIsLastRoom;
//					prev.childNodes[0].childNodes[1].innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;" + cell.childNodes[0].childNodes[1].innerHTML;
//					//合并占用房间
//					prev.hlcCells.push(cell);
//					prev.hlcCells=prev.hlcCells.concat(cell.hlcCells);
//					cell.hlcCells = new Array();
//					continue;
				}
				prev = cell;
			}
		}
	};
	
	/*
		菜单项响应：引入公司（单位入驻信息）
	*/
	HLC_LdGrid.__insert = function () {
		var table = document.getElementById(this.table);
		var form = "form___insert_" + table.hlcContainer;
		var config = this.attrs.config;
		var must = "<span style='color:red;'>*</span>";
		
		if(table.hlcSelectedCells.length<=0) {
			alert("错误：不允许的操作！必须先选择要操作的房间。");
			return;
		}
		var message = "";
		var room=null;
//
//		for(var i=0; i<table.hlcSelectedCells.length; i++) {
//			 room = table.hlcSelectedCells[i].hlcRoom;
//			if(null!=room.corp) {
//				message += (message=="" ? "" : "\r\n") + (room.no+"\t"+room.corp.name);
//			}
//		}
		
		
		var selectedcorp = null;
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			room = table.hlcSelectedCells[i].hlcRoom;
			var corp = room.corp;
			if(null==corp) continue;
			selectedcorp = null==selectedcorp ? corp : selectedcorp;
			if(corp.id!=selectedcorp.id) {
                $.messager.alert('提示','错误：不允许的操作！必须选择相同入驻单位的房间。');
				return;
			}
		}
		
		

		
		if(selectedcorp==null){
			selectedcorp=new Object();
		}
		
		
		


		var selectedtype = "";
		for(var i=0; i<config.color.values.length; i++) {
			var type = config.color.values[i];
			selectedtype += "<option value='"+type.id+"' "+(type.id==(selectedcorp.type==undefined?"":selectedcorp.type)?"selected":"")+">"+type.name+"</option>";
		}
		var selectedsize = "";
		for(var i=0; i<config.sizes.values.length; i++) {
			var size = config.sizes.values[i];
			selectedsize += "<option value='"+size.value+"' "+(size.value==(selectedcorp.size==undefined?"":selectedcorp.size)?"selected":"")+" >"+size.parse+"</option>";
		}
		
		var selectroomstate="";
		for(var i=0; i<config.roomperson.values.length; i++) {
			var state = config.roomperson.values[i];
			selectroomstate += "<option onclick='onselectstate("+i+")' value='"+state.value+"' "+(state.value==(selectedcorp.state==undefined?"":selectedcorp.state)?"selected":"")+">"+state.parse+"</option>";
		}
	
		var radiocorpstate="<tr><td colspan='2'>";
		var radiocorp="<tr><td colspan='2'><div id='zicizl'><input  type='radio' name='roomstat' "+(room.corp!="" && room.corp!=null && room.corp.state==1?"checked":"")+"  value='1'/>自&nbsp;&nbsp&nbsp;持&nbsp&nbsp;<input  type='radio' name='roomstat' "+(room.corp!="" && room.corp!=null && room.corp.state==2?"checked":"")+" value='2'/>租&nbsp;&nbsp&nbsp;赁</div></td></tr>";
		for(var i=0; i<config.cropstate.values.length; i++){
			var cropstates=config.cropstate.values[i];
			if(cropstates.value==4){continue;}
			if(cropstates.value==2){
				radiocorpstate+="<input  type='radio' name='statecorp' checked  value='"+cropstates.value+"'/>"+cropstates.parse+"&nbsp;&nbsp;";
			}else{
				radiocorpstate+="<input  type='radio' name='statecorp'  value='"+cropstates.value+"' "+(cropstates.value==(selectedcorp.staterooms==undefined?"":selectedcorp.staterooms)?"checked":"")+"/>"+cropstates.parse;
			}
		}
		radiocorpstate+="</td></tr>";
	
	
		var html = ""
			+"<div style='width:300px; height:20px; border:1px solid #dfdfdf; padding-left:10px;'>编辑房间</div>"
			+ "<div style='width:300px; border:1px solid #dfdfdf; padding-left:10px;text-align:center;'>"
			+ "	<form id='"+form+"' name='"+form+"'>"
			+ "		<input name='oper' type='hidden' value='insert'></input>"
			+ "		<input name='cid' type='hidden' value='"+(selectedcorp.id==undefined?"":selectedcorp.id)+"'></input>"
			+ "		<table style='margin-left:25px; margin-top:5px;'><tr><td>公司查询：</td><td><input name='ckname' type='text' value=''></input>"
			+ "			<span onclick='this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.hlcOnLoadCorp();' style='cursor:pointer; border:1px solid #dfdfdf;'>"
			+ "				查询"
			+ "			</span>"
			+ "		</p></td></tr>"
			+ "		<tr><td>单位名称：</td><td><input id='cnameid' name='cname' type='text' value='"+(selectedcorp.name==undefined?"":selectedcorp.name)+"'></input></td></tr>"
			+ radiocorpstate
			  + "<tr><td>业主姓名:</td><td><input name='yzcontactor' type='text' value='"+room.owner+"'></input></td></tr>"
			  + "<tr><td>联系电话:</td><td><input name='yztel' type='text' value='"+room.ownertel+"'></input></td></tr>"
			 +radiocorp
			+ "		</table><p><input name='alias' type='hidden' value='"+(selectedcorp.alias==undefined?"":selectedcorp.alias)+"'></input></p>"
			+ "		<p><input name='fullname' type='hidden' value='"+(selectedcorp.fullname==undefined?"":selectedcorp.fullname)+"'></input></p>"
			+ "		<p><div style='display:none;'>行业分类:"
			+ "			<select name='type' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedtype
			+ "			</select>"+must
			+ "		</div></p>"
			+ "		<p><div style='display:none;'>人数规模:"
			+ "			<select name='sizes' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedsize
			+ "			</select>"+must
			+ "		</div></p>"
			+ "		<p><div style='display:none;'>租户类型:"
			+ "			<select name='state' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectroomstate
			+ "			</select>"+must
			+ "		</div></p>"
//		  
			+ "		<div style='display:none;'><p>租户姓名:<input  name='contactor' type='text' value='"+(selectedcorp.contactor==undefined?"":selectedcorp.contactor)+"'></input></div></p>"
			+ "		<div style='display:none;'><p>联系电话:<input  name='tel' type='text' value='"+(selectedcorp.tel==undefined?"":selectedcorp.tel)+"'></input></div></p>"
			
			
			+ "	</form>"
			+ "		<p>"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnSave();'>保存</button>&nbsp;&nbsp;&nbsp;"
//			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnClear();'>清空</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcClose();'>取消</button>"
			+ "		</p>"
			+ "</div>";
		var dailog = this.__dailog(html);
		if(document.getElementById("cnameid").value!=""){
			document.getElementById("zicizl").style.display="";
		}else{
			document.getElementById("zicizl").style.display="none";
		}
		dailog.hlcConfig = config;
		dailog.hlcForm = form;
		document.getElementById(form).hlcSetEnable = function (enabled) {
			enabled = !enabled;
			this.cname.disabled = enabled;
			this.alias.disabled = enabled;
			this.fullname.disabled = enabled;
			this.type.disabled = enabled;
			this.sizes.disabled = enabled;
			this.state.disabled = enabled;
			this.statecorp.disabled = enabled;
//			this.yzcontactor.disabled = enabled;
//			this.yztel.disabled = enabled;
			this.contactor.disabled = enabled;
			this.tel.disabled = enabled;
		};
		
		document.getElementById(form).hlcSetEnable(false);
		dailog.hlcOnLoadCorp = function () {
			var form = document.getElementById(this.hlcForm);
			var _this = document.getElementById(this.hlcContainer).HLC;
			_this.__loadcorp(form);
		};
		dailog.hlcOnClear = function () {
			var form = document.getElementById(this.hlcForm);
			form.cid.value = "";
			form.cname.value = "";
			form.alias.value = "";
			form.fullname.value = "";
			form.type.value = "";
			form.sizes.value = "";
			form.state.value = "";
			form.statecorp.value="";
			form.yzcontactor.value = "";
			form.yztel.value = "";
			form.contactor.value = "";
			form.tel.value = "";
		};
	
		dailog.hlcOnSave = function(){
			
			
			var _this = document.getElementById(this.hlcContainer).HLC;
			var table = document.getElementById(_this.table);
			var form = document.getElementById(this.hlcForm);
			//整理编辑的公司信息
			var corp = {
				id: form.cid.value,
				name: form.cname.value,
				alias: form.alias.value,
				fullname: form.fullname.value,
				type: form.type.value,
				sizes: form.sizes.value,
				state:form.state.value,
				staterooms:form.statecorp.value,
				corpstateshow: form.roomstat.value,
//				yztel: form.yztel.value,
				contactor: form.contactor.value,
				tel: form.tel.value
			};
			
         
//			if(corp.name=="" || corp.name==null){
//				$.messager.alert('提示','请将带*号字段填写完整','',function(r){form.cname.focus();});
//				return false;
//			}
			
//			if(corp.sizes=="" || corp.sizes==null){
//				$.messager.alert('提示','请将带*号字段填写完整');
//				return false;
//			}
//			
//			if(corp.state=="" || corp.state==null){
//				$.messager.alert('提示','请将带*号字段填写完整');
//				return false;
//			}
//			
//			if(corp.type=="" || corp.type==null){
//				$.messager.alert('提示','请将带*号字段填写完整');
//				return false;
//			}
			//整理选中的房间数据
			var info = ("undefined"==table.hlcArrangedInfo||null==table.hlcArrangedInfo) ? _this.__arrangselected(table.hlcSelectedCells) : table.hlcArrangedInfo;
			for(var i=0;i<info.rooms.length;i++){
				info.rooms[i].owner=form.yzcontactor.value;
				info.rooms[i].ownertel = form.yztel.value;
			}
			//把公司信息填充进表格
			_this.__fillcorp(corp, info.selects);
			//填充后清空选中数组
			table.hlcSelectedCells = new Array();
			//触发表格变更事件（为了用户能够实时保存数据...）
			_this.onchangetable( {building:_this.attrs.building.id, oper: form.oper.value, corp: corp, room: info.rooms} );
			//关闭窗口
			this.hlcClose();
			//清除整理后的信息
			table.hlcArrangedInfo = null;
		    for(var i=0;i<getnode.length;i++){
		    	getnode[i].style.background="";
		    }
		    getnode=new Array();
		};
	
	};
	
	/*
		变更公司信息时刷新表格
	*/
	HLC_LdGrid.__refresh = function (corp, table) {
		var tds = table.hlcRoomCellTds;
		for(var i=0; i<tds.length; i++) {
			if(null==tds[i].hlcRoom.corp) continue;
			if(corp.id!=tds[i].hlcRoom.corp.id) continue;
			tds[i].childNodes[0].childNodes[3].innerHTML = corp.name;
		}
	};
	
	/*
		菜单项响应：变更（单位入驻信息）
	*/
	HLC_LdGrid.__update = function () {
		var table = document.getElementById(this.table);
		var form = "form___update_" + table.hlcContainer;
		var config = this.attrs.config;
		var must = "<span style='color:red;'>*</span>";
//		if(table.hlcSelectedCells.length<=0) {
//			$.messager.alert('提示','错误：不允许的操作！必须先选择要操作的房间。');
//			return;
//		}

		var room=null;
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			room = table.hlcSelectedCells[i].hlcRoom;
//			if(null==room.owner) continue;
//			selectedcorp = null==selectedcorp ? corp : selectedcorp;
//			if(corp.id!=selectedcorp.id) {
//                $.messager.alert('提示','错误：不允许的操作！必须选择相同入驻单位的房间。');
//				return;
//			}
		}
		
//		if(null==selectedcorp) {
//			$.messager.alert('提示','错误：不允许的操作！必须选择某个入驻单位。');
//			return;
//		}
		
//		var selectedtype = "";
//		for(var i=0; i<config.color.values.length; i++) {
//			var type = config.color.values[i];
//			selectedtype += "<option value='"+type.id+"' "+(type.id==selectedcorp.type?"selected":"")+">"+type.name+"</option>";
//		}
//		var selectedsize = "";
//		for(var i=0; i<config.sizes.values.length; i++) {
//			var size = config.sizes.values[i];
//			selectedsize += "<option value='"+size.value+"' "+(size.value==selectedcorp.size?"selected":"")+">"+size.parse+"</option>";
//		}
//		
//		var selectroomstate="";
//		for(var i=0; i<config.roomperson.values.length; i++) {
//			var state = config.roomperson.values[i];
//			selectroomstate += "<option onclick='onselectstate("+i+")'  value='"+state.value+"' "+(state.value==selectedcorp.state?"selected":"")+">"+state.parse+"</option>";
//		}
//		
//		var radiocorpstate="";
//		for(var i=0; i<config.cropstate.values.length; i++){
//			var cropstates=config.cropstate.values[i];
//			radiocorpstate+="<input  type='radio' name='statecorp'  value='"+cropstates.value+"' "+(cropstates.value==selectedcorp.staterooms?"checked":"")+"/>"+cropstates.parse;
//		}
		
		var html = ""
			+ "<div style='width:300px; height:20px; line-height:20px; border-left:1px solid #dfdfdf; border-top:1px solid #dfdfdf; border-right:1px solid #dfdfdf;'>"
			+ "	&nbsp;&nbsp;编辑业主信息"
			+ "</div>"
			+ "<div style='width:300px; border:1px solid #dfdfdf; text-align:center;'>"
			+ "	<form id='"+form+"'>"
			+ "		<input name='oper' type='hidden' value='update'></input>"
//			+ "		<p>单位名称:<input name='cname' type='text' value='"+selectedcorp.name+"'></input>"+must
//			+ "		</p>"
//			+ "		<p>单位别称:<input name='alias' type='text' value='"+selectedcorp.alias+"'></input></p>"
//			+ "		<p>单位全称:<input name='fullname' type='text' value='"+selectedcorp.fullname+"'></input></p>"
//			+ "		<p>行业分类:"
//			+ "			<select name='type' style='width:154px;'>"
//			+ "				<option value=''>请选择...</option>"
//			+ selectedtype
//			+ "			</select>"+must
//			+ "		</p>"
//			+ "		<p>人数规模:"
//			+ "			<select name='sizes' style='width:154px;'>"
//			+ "				<option value=''>请选择...</option>"
//			+ selectedsize
//			+ "			</select>"+must
//			+ "		</p>"
//			+ "		<p>租户类型:"
//			+ "			<select name='state' style='width:154px;'>"
//			+ "				<option value=''>请选择...</option>"
//			+ selectroomstate
//			+ "			</select>"+must
//			+ "		</p>"
//			+ "		<p> "
//			+ radiocorpstate
//			+ "		</p>"
			+ "		<p>业主姓名:<input name='yzcontactor' type='text' value='"+room.owner+"'></input></p>"
			+ "		<p>联系电话:<input name='yztel' type='text' value='"+room.ownertel+"'></input></p>"
//		    +"<div id='selectshow' style='display:none;'>"
//			+ "		<p>租户姓名:<input id='zuhid' name='contactor' type='text' value='"+selectedcorp.contactor+"'></input></p>"
//			+ "		<p>联系电话:<input id='zuhtelid' name='tel' type='text' value='"+selectedcorp.tel+"'></input></p>"
//			+"</div>"
			+ "	</form>"
			+ "		<p>"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnSave();'>保存</button>&nbsp;&nbsp;&nbsp;"
			//+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnClear();'>清空</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcClose();'>取消</button>"
			+ "		</p>"
			+ "</div>";

		
		var dailog = this.__dailog(html);
		dailog.hlcConfig = config;
		dailog.hlcForm = form;
		dailog.hlcOnClear = function () {
			var form = document.getElementById(this.hlcForm);
			//form.cid.value = "";
//			form.cname.value = "";
//			form.alias.value = "";
//			form.fullname.value = "";
//			form.type.value = "";
//			form.sizes.value = "";
//			form.state.value = "";
//			form.statecorp.value="";
			form.yzcontactor.value = "";
			form.yztel.value = "";
//			form.contactor.value = "";
//			form.tel.value = "";
		};
		dailog.hlcOnSave = function () {
			var _this = document.getElementById(this.hlcContainer).HLC;
			var table = document.getElementById(_this.table);
			var form = document.getElementById(this.hlcForm);
            
//			//整理编辑的公司信息
//			var corp = {
//				id: form.cid.value,
////				name: form.cname.value,
////				alias: form.alias.value,
////				fullname: form.fullname.value,
////				type: form.type.value,
////				sizes: form.sizes.value,
////				state:form.state.value,
////				staterooms:$("input[name='statecorp']:checked").val(),
//				yzcontactor: form.yzcontactor.value,
//				yztel: form.yztel.value
////				contactor: form.contactor.value,
////				tel: form.tel.value
//			};
			
			if(form.yzcontactor.value=="" || form.yzcontactor.value==null){
				$.messager.alert('提示','请将带*号字段填写完整','',function(r){form.yzcontactor.focus();});
				return false;
			}

			//整理选中的房间数据
			var info = ("undefined"==table.hlcArrangedInfo||null==table.hlcArrangedInfo) ? _this.__arrangselected(table.hlcSelectedCells) : table.hlcArrangedInfo;
			for(var i=0;i<info.rooms.length;i++){
				info.rooms[i].owner=form.yzcontactor.value;
				info.rooms[i].ownertel = form.yztel.value;
			}
			//把公司信息填充进表格
			//_this.__fillcorp(corp, info.selects);
			//刷新公司信息
			//_this.__refresh(corp, table);
			//填充后清空选中数组
			table.hlcSelectedCells = new Array();
			//触发表格变更事件（为了用户能够实时保存数据...）
			_this.onchangetable( {building:_this.attrs.building.id, oper: form.oper.value, corp: null, room: info.rooms} );
			//关闭窗口
			this.hlcClose();
			//清除整理后的信息
			table.hlcArrangedInfo = null;
			
			 for(var i=0;i<getnode.length;i++){
			    	getnode[i].style.background="";
			    }
			    getnode=new Array();
		};
	};
	
	/*
		重置房间单元格
	*/
	HLC_LdGrid.__resetcell = function (cell) {
		cell.colSpan = 1;
		var w = cell.hlcOrignalWH.w;
		cell.style.width = w + "px";
		cell.childNodes[0].style.width = w + "px";										//因为拆分后与td一样宽
		cell.style.borderRight = cell.hlcIsLastRoom ? "0px" : cell.style.borderRight;	//最后一格的右边框不画
		cell.hlcIsLastRoom = cell.hlcOrignalIsLastRoom;
		cell.style.borderRight = cell.hlcIsLastRoom ? "0px" : "1px solid #000000";		//拆分后不再是最后一格
		var div = cell.childNodes[0];
		div.style.background = "";	//FF IE7
		div.style.filter = "";			//IE
		div.style.opacity = "";			//FF
		cell.childNodes[0].childNodes[1].innerHTML = cell.hlcRoom.no;
		cell.hlcRoom.corp = null;
		cell.hlcOrignalColor = "#ffffff";
		cell.hlcChecked = false;
		cell.style.backgroundColor = cell.hlcOrignalColor;
		cell.childNodes[0].childNodes[3].innerHTML = "";
	};
	
	/*
		拆分（已经合并的）房间单元格
	*/
	HLC_LdGrid.__divide = function (selectedcells) {
		for(var i=0; i<selectedcells.length; i++) {
			var cell = selectedcells[i];
			//清除当前房间
			this.__resetcell(cell);
			//房间占用了其他房间
			var current = cell;
			for(var j=0; j<cell.hlcCells.length; j++) {
				var newcell = cell.hlcCells[j];
				cell.parentNode.insertBefore(newcell,current.nextSibling);
				this.__resetcell(newcell);
				current = newcell;
			}
			cell.hlcCells = new Array();
		}
	};
	
	/*
		菜单项响应：清除（单位入驻信息）
	*/
	HLC_LdGrid.__delete = function () {
		var table = document.getElementById(this.table);
		
		if(table.hlcSelectedCells.length<=0) {
			$.messager.alert('提示','错误：不允许的操作！必须先选择要操作的房间。');
			return;
		}
		var message = "";
		var numnull = 0;
		var msgnull = "";
		var numcorp = 0;
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			var corp = room.corp;
			if(null==corp) {
				numnull ++;
				msgnull += (msgnull=="" ? "" : "\r\n") + room.no;
				continue;
			}
			numcorp ++;
			message += (message=="" ? "" : "\r\n") + (room.no+"\t"+corp.name);
		}
		if(0==numcorp) {
			$.messager.alert('提示','错误：不允许的操作！必须选择某个入驻单位。');
			return;
		}
		if(0!=numnull) {
			$.messager.alert('提示','错误：不允许的操作！不能清理空房间。\r\n空置房间：\r\n'+msgnull);
			return;
		}
		if(""!=message && false==confirm("您确定要清除全部所选单位的入驻信息?\r\n入驻单位：\r\n"+message)) {
			return;
		} 
		
		//整理选中的房间数据
		var info = this.__arrangselected(table.hlcSelectedCells);
		table.hlcArrangedInfo = info;
		//实施房间拆分
		this.__divide(table.hlcSelectedCells);
		//拆分后清空选中数组
		table.hlcSelectedCells = new Array();
		//触发表格变更事件（为了用户能够实时保存数据...）
		this.onchangetable( {building:this.attrs.building.id, oper: "delete", corp: null, room: info.rooms} );
		//清除整理后的信息
		table.hlcArrangedInfo = null;
		
		 for(var i=0;i<getnode.length;i++){
		    	getnode[i].style.background="";
		    }
		    getnode=new Array();
	};
	
	/*
		菜单项响应：装修（空房间）
	*/
	HLC_LdGrid.__decorate = function () {
		var table = document.getElementById(this.table);
		var config = this.attrs.config;
		var style = this.attrs.style;
		
		if(table.hlcSelectedCells.length<=0) {
			$.messager.alert('提示',"错误：不允许的操作！必须先选择要操作的房间。");
			return;
		}
		var message = "";
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			var corp = room.corp;
			if(null!=corp) {
				message += (message=="" ? "" : "\r\n") + (room.no+"\t"+corp.name);
			}
		}
		if(""!=message) {
			alert("错误：不允许的操作！不能装修已经入驻单位的房间。\r\n入驻单位：\r\n"+message);
			return;
		}
		var rooms = new Array();
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var cell = table.hlcSelectedCells[i];
			var room = cell.hlcRoom;
			rooms.push(room);
			cell.hlcOrignalColor = style.decoratingColor;
			cell.hlcChecked = false;
			cell.style.backgroundColor = cell.hlcOrignalColor;
			var div = cell.childNodes[0];
			div.style.backgroundColor = "";	//FF IE7
			div.style.filter = "";			//IE
			div.style.opacity = "";			//FF
			cell.childNodes[0].childNodes[3].innerHTML = config.state.getByTag("ZX").parse;
		}
		//处理后清空选中数组
		table.hlcSelectedCells = new Array();
		//触发表格变更事件（为了用户能够实时保存数据...）
		this.onchangetable( {building:this.attrs.building.id, oper: "decorate", corp: null, room: rooms} );
		 for(var i=0;i<getnode.length;i++){
		    	getnode[i].style.background="";
		    }
		    getnode=new Array();
	};

	/** ###########################################################
	 * @Desc:	定义楼栋表格的事件转发函数
	 * @Dill:
	 * 	1.	2014-10-01	weide		public void onopmenuclick();			菜单项点击事件响应
	 * 	2.	2014-10-01	weide		public void onchangetable();			表格变动事件响应回调函数
	 * 	2.	2014-10-01	weide		public void onloadcorporation();		选择已经存在的单位信息进行入驻时的回调函数
	 ** ###########################################################*/
	/**
	 * @Method: onopmenuclick()
	 *  @Param: String item	菜单项标识
	 * @Return: void
	 * @Access: public
	 *   @Desc: 菜单项点击事件响应回调函数
	**/
	HLC_LdGrid.onopmenuclick = function (item) {
		switch (item) {
			case "decorate":
				this.__decorate();
				break;
			case "display":
				this.__display();
				break;
			case "insert":
				this.__insert();
				break;
			case "update":
				this.__update();
				break;
			case "delete":
				this.__delete();
				break;
				
			default:
				alert("错误：无效(未实现)的操作！");
				break;
		}
	};
	
	/**
	 * @Method: onchangetable()
	 *  @Param: Json Object info	变更参数对象
	 * @Return: void
	 * @Access: public
	 *   @Desc: 表格变动事件响应回调函数
	**/
	HLC_LdGrid.onchangetable = function (info) {
		if(null!=this.attrs.onChangeGrid && typeof this.attrs.onChangeGrid!="undefined" && this.attrs.onChangeGrid.constructor==Function) {
			this.attrs.onChangeGrid(info);
		}
	};
	
	/**
	 * @Method: onloadcorporation()
	 *  @Param: String 		like		模糊关键字
	 *  @Param: Function 	callback	数据回执函数
	 * @Return: bool	是否加载完成？
	 * @Access: public
	 *   @Desc: 选择已经存在的单位信息进行入驻时的回调函数
	**/
	HLC_LdGrid.onloadcorporation = function (like, callback) {
		if(null!=this.attrs.onLoadCorp && typeof this.attrs.onLoadCorp!="undefined" && this.attrs.onLoadCorp.constructor==Function) {
			return this.attrs.onLoadCorp(like, callback);
		}
		return true;
	};

	/** ###########################################################
	 * @Desc:	定义楼栋表格按钮的回调函数
	 * @Dill:
	 * 	1.	2014-10-01	weide		public void save();			保存楼栋表格全部数据
	 * 	2.	2014-10-01	weide		public void clear();		清空楼栋表格
	 * 	2.	2014-10-01	weide		public void cancel();		取消对楼栋表格的变更（恢复到上一次保存时间点）
	 ** ###########################################################*/
	/**
	 * @Method: save()
	 *  @Param: (无)
	 * @Return: void
	 * @Access: public
	 *   @Desc: 保存楼栋表格全部数据
	**/
	HLC_LdGrid.save = function () {
		//遍历所有房间单元格
		var info = new Array();
		var cells = document.getElmentById(this.table).hlcRoomCells;
		for(var i=0; i<cells.length; i++) {
			//填充提交的数据（房间与入驻单位信息值对）
			info.push({room:cells[i].hlcRoom.id, corp:null==cells[i].hlcRoom.corp?null:cells[i].hlcRoom.corp.id});
		}
		//触发楼栋保存事件（为了用户能够一次性保存数据...）
		if(null!=this.attrs.onSave && typeof this.attrs.onSave!="undefined" && this.attrs.onSave.constructor==Function) {
			this.attrs.onSave({building:this.attrs.building.id, oper:"save", data:info});
		}
	};
	
	/**
	 * @Method: clear()
	 *  @Param: (无)
	 * @Return: void
	 * @Access: public
	 *   @Desc: 清空楼栋表格
	**/
	HLC_LdGrid.clear = function () {
		if(!confirm("您确定要全部清理楼栋的所有入驻单位信息吗？")) return;
		//清空入驻单位信息
		var floors = this.attrs.building.floors;
		for(var i=0; i<floors.length; i++) {
			var rooms = floors[i];
			for(var j=0; j<rooms.length; j++) {
				rooms[j].corp = null;
			}
		}
		//刷新数据
		this.loaddata();
		//触发楼栋清空事件（为了用户能够一次性保存数据...）
		if(null!=this.attrs.onClear && typeof this.attrs.onClear!="undefined" && this.attrs.onClear.constructor==Function) {
			this.attrs.onClear({building:this.attrs.building.id, oper:"clear", corp:null, room:null});
		}
	};
	
	/**
	 * @Method: cancel()
	 *  @Param: (无)
	 * @Return: void
	 * @Access: public
	 *   @Desc: 取消对楼栋表格的变更（恢复到上一次保存时间点）
	**/
	HLC_LdGrid.cancel = function () {
		if(!confirm("您确定不需要保存本次所作的信息合并吗？")) return;
		//重新刷新数据
		this.loaddata();
		
	};
	
	return HLC_LdGrid;
}



function onselectstate(i){
	if(i==1){
		document.getElementById("selectshow").style.display="";
	}else{
		document.getElementById("selectshow").style.display="none";
		document.getElementById("zuhid").value="";
		document.getElementById("zuhtelid").value="";
	}

}



