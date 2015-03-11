
function hlc(destid) {
	//首先，验证参数有效性
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
	if(null==container || typeof container=="undefined" || !/^\[object .*element\]$/ig.test(container.constructor)) {
		alert("错误：必须指定一个有效的容器元素！");
		return null;
	}
	if(typeof container.HLC!="undefined" && container.HLC!=null && container.HLC.constructor==Object) {
		return container.HLC;
	}
	
	//然后，构建实体对象
	var HLC_LdGrid = container.HLC = new Object();
	HLC_LdGrid.container = destid;	//外部容器ID
	HLC_LdGrid.table = "";					//楼栋表格ID
	HLC_LdGrid.building = function (param){
		var pstyle = param.style;						//风格属性
		var pconfig = param.config;					//配置信息
		var pdata = param.building;					//楼栋数据
		var pdebug = param.debug;						//调试输出函数
		var pchange = param.onChangeGrid;		//表格变更时的回调函数: function(info){...}
		var pload = param.onLoadCorp;				//加载公司信息回调函数: function(callback){...}
		var psave = param.onSave;						//保存楼栋信息
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
		
		this.DEBUG = (null!=pdebug && "undefined"!=typeof pdebug && pdebug.constructor==Function) ? pdebug : function () {
			var debug = document.getElementById("debug");
			if(debug==null) return;
			for(var i=0; i<arguments.length; i++) {
				debug.value += arguments[i]+"\r\n";
			}
		};
	
		this.loaddata();
	};
	
	//最后，声明对象方法
	HLC_LdGrid.__parseconf = function (conf) {
		var config = conf;
		//判断数据有效性
		if(config==null || typeof config=="undefined" || config.constructor!=Object) {
			alert("错误：配置数据必须是一个指定格式的JSON对象！");
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
		}
		if(config.sizes!=null && typeof config.sizes!="undefined" && config.sizes.constructor==Object) {
			config.sizes.getById = getById;
			config.sizes.getByTag = getByTag;
			config.sizes.getByValue = getByValue;
			config.sizes.getByParse = getByParse;
		}
		if(config.state!=null && typeof config.state!="undefined" && config.state.constructor==Object) {
			config.state.getById = getById;
			config.state.getByTag = getByTag;
			config.state.getByValue = getByValue;
			config.state.getByParse = getByParse;
		}
		if(config.ftype!=null && typeof config.ftype!="undefined" && config.ftype.constructor==Object) {
			config.ftype.getById = getById;
			config.ftype.getByTag = getByTag;
			config.ftype.getByValue = getByValue;
			config.ftype.getByParse = getByParse;
		}
		if(config.btype!=null && typeof config.btype!="undefined" && config.btype.constructor==Object) {
			config.btype.getById = getById;
			config.btype.getByTag = getByTag;
			config.btype.getByValue = getByValue;
			config.btype.getByParse = getByParse;
		}
		
		return config;
	};
	HLC_LdGrid.__parsedata = function (data) {
		var building = data;
		//判断数据有效性
		if(building==null || typeof building=="undefined" || building.constructor!=Object) {
			alert("错误：楼栋数据必须是一个指定格式的JSON对象！");
			return null;
		}
		var floors = building.floors;
		if(floors==null || typeof floors=="undefined" || floors.constructor!=Array) {
			alert("错误：楼层数据必须是指定格式的楼层JSON对象数组！");
			return null;
		}
		
		building.totalHeight = 0;		//楼栋总高
		building.totalFloors = 0;		//楼栋层数
		building.maxFloorRooms=0;		//楼栋楼层最大房间数目
		//楼层排序（冒泡法）
		for(var i=0; i<floors.length; i++) {
			for(var j=i+1; j<floors.length; j++) {
				if(floors[i].order>floors[j].order) {
					var tmp = floors[i];
					floors[i] = floors[j];
					floors[j] = tmp;
				}
			}
			building.totalHeight += floors[i].height;
			building.totalFloors ++;
			var rooms = floors[i].rooms;
			if(rooms==null || typeof rooms=="undefined" || rooms.constructor!=Array) {
				alert("错误：楼层["+floors[i].no+"]的数据必须是房间数据数组！");
				return null;
			}
			floors[i].totalArea = 0;
			floors[i].totalRooms= 0;
			//房间排序（冒泡法）
			for(var m=0; m<rooms.length; m++) {
				for(var n=m+1; n<rooms.length; n++) {
					if(rooms[m].order>rooms[n].order) {
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
	HLC_LdGrid.loaddata = function () {
		
		var style = this.attrs.style;
		//整理分析数据
		var config = this.attrs.config = this.__parseconf(this.attrs.config);
		var building = this.__parsedata(this.attrs.building);
		if(config==null || building==null) return false;
		
		var floors = building.floors;
		if(floors==null) return false;
		
		var width = parseInt(style.width);
		var height= parseInt(style.height);
		var cellminwidth = 40;
		var cellminheight= 30;
		var cellmaxwidth = 200;
		var cellmaxheight= 150;
		var border = "1px solid #000000";
		
		//容器节点对象
		var container = document.getElementById(this.container);
		
		//创建框架表格
		var frametable = document.createElement("TABLE");		//创建表格
   	while(container.hasChildNodes()) container.removeChild(container.firstChild);	//清空容器
		container.appendChild(frametable);
		
		frametable.cellSpacing = "0px";
		frametable.cellPadding = "0px";
		frametable.style.width = width + "px";
		frametable.style.height = height + "px";
		frametable.style.borderTop = border;
		frametable.style.borderLeft = border;
		
		//第一行: 楼栋标题
		var rowtitle = frametable.insertRow();							//创建一行（楼栋标题）
	  var celltitle = rowtitle.insertCell();							//创建一个单元
	  
		celltitle.style.width = width + "px";
	  celltitle.style.height = 40 + "px";	//固定标题栏高度为 40px
	  height -= 40;
   	celltitle.style.borderRight = border;
   	celltitle.style.borderBottom = border;
	  
	  var html = ""
	  	+ "<div style='text-align:center; font-size:16pt;'>"
	  	+ building.no+"<span style='font-size:"+style.fontSize+"pt;'>("+building.path+")</span>"+building.name
	  	+ "</div>";
	  celltitle.innerHTML = html;
   	celltitle.title = ""
   		+ "楼栋编号："+building.no+"\r\n"
   		+ "归属小区："+building.path+"\r\n"
   		+ "楼栋名称："+building.name+"\r\n"
   		+ "楼栋类型："+config.btype.getByValue(building.type).parse+"";
	  
	  //第二行: 行业颜色
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
			html += ""
				+ "<span style='"+shape+" background: "+config.color.values[index].color+"; font-size:"+style.fontSize+"pt; border:1px solid #000000;'>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;"
				+ config.color.values[index].name
				+ "&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "</span>";
   	}
   	celltype.innerHTML = html;
   	
   	//第三行: 楼层数据
		var rowbuilding = frametable.insertRow();						//创建一行（楼层数据列表）
	  var cellbuilding = rowbuilding.insertCell();				//创建一个单元
	  
		//第四行: 操作栏位
	  if(style.isShowOpBar) {
			var rowop = frametable.insertRow();								//创建一行（信息操作）
		  var cellop = rowop.insertCell();									//创建一个单元
	  
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
   	
   	var floororderwidth = 30;	//固定楼层号列宽度为 30px
   	var buildingwidth = width;
   	var buildingheight= height;
   	if(((cellminwidth*building.maxFloorRooms)+floororderwidth)>buildingwidth) {
   		buildingdiv.style.overflowX = "scroll";
   		buildingheight -= 0;	//横向滚动条不占用高度!!
  	}
  	if((cellminheight*building.totalFloors)>buildingheight) {
   		buildingdiv.style.overflowY = "scroll";
   		buildingwidth -= 20;	//预留20px作纵向滚动条!!
  	}
   	//创建楼栋表格
   	var buildingtable = document.createElement("TABLE");				//创建表格（楼栋）
   	buildingdiv.appendChild(buildingtable);
   	
		buildingtable.id = "building_" + container.id;
		this.table = buildingtable.id;
		buildingtable.hlcBuilding = building;												//楼栋数据
		buildingtable.hlcContainer = container.id;									//容器元素ID
		buildingtable.hlcSelectedColor = style.selectedColor;				//选中时，单元的背景色
		buildingtable.hlcSelectedCells = new Array();								//当前选中的单元格数组
		buildingtable.hlcRoomCells = new Array();
		buildingtable.cellSpacing = "0px";
		buildingtable.cellPadding = "0px";
		buildingtable.style.width = buildingwidth + "px";	
   	buildingtable.style.height = buildingheight + "px";
		buildingtable.style.border = "0px";
		
		for(var i=0; i<floors.length; i++) {
			var row = buildingtable.insertRow();											//创建一行（楼层: 第i层）
			row.id = "floor_" + i + "_" + floors[i].order;
	    var floorwidth = buildingwidth;
	    var floorheight = buildingheight*(floors[i].height/building.totalHeight);
			//创建楼层号列
			var cellorder = row.insertCell();													//创建第1格（楼层号）
	    cellorder.id = "floor_" + i + "_0_" + floors[i].no;
	    cellorder.style.width = floororderwidth + "px";
	    floorwidth -= floororderwidth;
	    cellorder.style.height = floorheight + "px";
	    cellorder.style.borderRight = border;
     	cellorder.style.borderBottom = border;
     	var html = "<div style='font-size:"+(style.fontSize+2)+"pt; text-align:center;'>"+floors[i].no+"</div>";
     	cellorder.innerHTML = html;
		 	cellorder.title = ""
		 		+ "楼层编号："+floors[i].no+"\r\n"
		 		+ "楼层结构："+config.ftype.getByValue(floors[i].type).parse+"\r\n"
		 		+ "楼层高度："+floors[i].height+"米";
     	//创建房间列表列
     	var celllist = row.insertCell();													//创建第2格（房间列表）
	    celllist.style.width = floorwidth + "px";
	    celllist.style.height = floorheight + "px";
	  	celllist.style.borderRight = border;
    	celllist.style.borderBottom = border;
     	//创建楼层表格
     	var floortable = document.createElement("TABLE");					//创建表格（楼层）
			floortable.id = "floor_" + i + "_1_" + floors[i].no;
			floortable.cellSpacing = "0px";
			floortable.cellPadding = "0px";
			floortable.style.width = floorwidth + "px";	
     	floortable.style.height = floorheight + "px";
			floortable.style.border = "0px";
			//创建楼层行
			var row = floortable.insertRow();
			row.hlcFloor = floors[i];																	//楼层数据
			//创建房间格
			////一个单位入驻多个房间
			var prev = null;
			for(var j=0; j<floors[i].rooms.length; j++) {
	     	var roomwidth = floorwidth*(floors[i].rooms[j].area/floors[i].totalArea);
	     	var roomheight = floorheight;
	     	roomwidth = roomwidth>cellminwidth  ? roomwidth : cellminwidth;	//保证单元格不会太小
	     	roomheight= roomheight>cellminheight? roomheight: cellminheight;//保证单元格不会太小
	    //roomwidth = roomwidth<cellmaxwidth  ? roomwidth : cellmaxwidth;	//保证单元格不会太大
	    //roomheight= roomheight<cellmaxheight? roomheight: cellmaxheight;//保证单元格不会太大
				//创建一个房间
	     	var cell = document.createElement("TD");								//创建一格（房间: 第j间）
	     	cell.id = "room_" + i + "_" + j + "_" + floors[i].rooms[j].no;
	     	cell.hlcRoom = floors[i].rooms[j];								//单元格对应的房间信息
	     	cell.hlcOrder = {floor:floors[i].order, room:floors[i].rooms[j].order};		//单元格对应的楼栋坐标
	     	cell.hlcXY = {x:j, y:i};													//单元格对应的表格坐标
	     	cell.hlcChecked = false;													//单元格自身的选中状态
	     	if(null!=floors[i].rooms[j].corp) {
	     		cell.hlcOrignalColor = config.color.getById(floors[i].rooms[j].corp.type).color;
	    	} else {
	    		cell.hlcOrignalColor = cell.style.backgroundColor;
	    	}
	    	cell.hlcOrignalWH = {w:roomwidth, h:roomheight};	//单元格原始宽高
	    	cell.oncontextmenu = this.oncontextmenucell;
	     	cell.onclick = this.onclickcell;
	     	cell.ondblclick = this.ondbclickcell;
	     	cell.style.width = roomwidth+"px";
	     	cell.style.height = roomheight+"px";
	     	if(1<floors[i].rooms.length-j) {
	    		cell.style.borderRight = border;	//最后一格的右边框不画
	    	}
     	//cell.style.borderBottom = border;
	     	cell.style.backgroundColor = cell.hlcOrignalColor;
	     	var html = "";
	     	html += ""
	     		+	"<div id='"+cell.id+"_inner' name='inner' style='position:relative; width:100%; height:100%;'>"
					+ "	<div id='"+cell.id+"_no'   name='no'    style='z-index:1; position:absolute; width:100%; height:100%; line-height:"
					+				cell.style.height+"; font-size:"+(2*style.fontSize)+"pt; text-align:center; filter:Alpha(Opacity=10); opacity:0.10;'>"
					+			floors[i].rooms[j].no
					+	"	</div>"
     			+	"	<div id='"+cell.id+"_corp' name='corp'  style='z-index:2; position:absolute; width:100%; height:100%; line-height:"
     			+				cell.style.height+"; font-size:"+(1*style.fontSize)+"pt; text-align:center;'>"
     			+			(null==floors[i].rooms[j].corp ? "" : floors[i].rooms[j].corp.name)
     			+	"	</div>"
	    		+	"</div>";
	    	cell.innerHTML = html;
			 	cell.title = ""
			 		+ "房间编号："+floors[i].rooms[j].no+"\r\n"
			 		+ "房间状态："+config.state.getByValue(floors[i].rooms[j].state).parse+"\r\n"
			 		+ "房间面积："+floors[i].rooms[j].area+"平方米";
	    	
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
					prev.colSpan = parseInt(prev.colSpan)+parseInt(cell.colSpan);
					prev.style.width = (parseFloat(prev.style.width)+parseFloat(cell.style.width)) + "px";
					prev.childNodes[0].childNodes[1].innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;" + floors[i].rooms[j].no;
					prev.hlcCells.push(cell);
					continue;
				}
	     	prev = cell;
	     	row.appendChild(cell);
			}
			celllist.appendChild(floortable);
   	}
   	
		return true;
	};
	HLC_LdGrid.onclickcell = function (e) {
		//即：[td].[tr].[tbody].[table].td.tr.tbody.table
		var table = this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
		this.hlcChecked = !this.hlcChecked;
		if(this.hlcChecked) {
			this.style.backgroundColor = table.hlcSelectedColor;
			//添加选中的单元格
			table.hlcSelectedCells.push(this);
		} else {
			this.style.backgroundColor = this.hlcOrignalColor;
			//移除选中的单元格
			for(var i=0; i<table.hlcSelectedCells.length; i++) {
				if(table.hlcSelectedCells[i].hlcXY.y == this.hlcXY.y && table.hlcSelectedCells[i].hlcXY.x == this.hlcXY.x) {
					table.hlcSelectedCells.splice(i,1);
					break;
				}
			}
		}
	};
	HLC_LdGrid.ondbclickcell = function (e) {
		//打开录入窗口:
		//@todo: 开窗...
	};
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
			menu.style.backgroundColor = "#ffffff";
			menu.style.width = "100px";
		//menu.style.position = "fixed!important";		//FF IE7
			menu.style.position = "absolute";						//IE6
			menu.style.zIndex = 997;
    	menu.style.fontSize = "9pt";
    	menu.style.border = "1px solid #dfdfdf";
    	for(var i=0; i<items.length; i++) {
    		var menuitem = document.createElement("DIV");
				menuitem.hlcContainer = this.container;
    		menuitem.hlcText = items[i].text;
    		menuitem.hlcFlag = items[i].op;
    		menu.appendChild(menuitem);
    		menuitem.onmouseover = function () { this.style.backgroundColor="#dfdfdf;" };
    		menuitem.onmouseout = function () { this.style.backgroundColor="#ffffff;" };
    		menuitem.onclick = function () { document.getElementById(this.hlcContainer).HLC.onopmenuclick(this.hlcFlag); };
    		menuitem.style.lineHeight = "18px";
    		if(i!=0) menuitem.style.borderTop = menu.style.border;
    		menuitem.innerText = menuitem.hlcText;
    	}
		}
		menu.style.visibility = "visible";
		menu.hlcOnClickDocument = document.onclick;
		document.hlcMenu = id;
		document.onclick = function () {
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
    	menu.style.top = context.scrollTop + e.clientY + "px";
    }
		return menu;
	};
	HLC_LdGrid.oncontextmenucell = function (e) {
		var e = e || window.event;
		//即：[td].[tr].[tbody].[table].td.tr.tbody.table
		var table = this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
		var container = document.getElementById(table.hlcContainer);
		//弹出菜单
    var items = [{text:"查看",op:"display"},{text:"新建",op:"insert"},{text:"变更",op:"update"},{text:"清除",op:"delete"}];
		var menu = container.HLC.__menu(e,items);
		if (document.all) {  
			e.returnValue = false;	//IE  
		} else {  
    	e.preventDefault();
    };
		return false;
	};
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
			background.style.top = "0px";										//FF IE7
			background.style.filter = "alpha(opacity=50)";	//IE
			background.style.opacity = 0.5;									//FF
			background.style.zIndex = 998;
		//background.style.position = "fixed!important";	//FF IE7
			background.style.position = "absolute";					//IE6
			background.style["_top"] = ""
				+ "expression(eval(document.compatMode && document.compatMode=='CSS1Compat') ? "
        + "documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 : "
        + "document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2)";
		}
		background.style.visibility = "visible";
		return background;
	};
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
			dailog.style.top = "40%";													//FF IE7
		//dailog.style["margin-left"] = "-300px!important";	//FF IE7 该值为本身宽的一半
		//dailog.style["margin-top"] = "-150px!important";	//FF IE7 该值为本身高的一半
		//dailog.style["margin-top"] = "0px";
		//dailog.style.position = "fixed!important";				//FF IE7
			dailog.style.position = "absolute";								//IE6
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
	HLC_LdGrid.__display = function () {
		var table = document.getElementById(this.table);
		var form = "form__display_" + table.hlcContainer;
		var config = this.attrs.config;
		
		if(table.hlcSelectedCells.length<=0) {
			alert("错误：不允许的操作！必须先选择要操作的房间。");
			return;
		}
		var selectedroom = null;
		var selectedcorp = null;
		var selectedhtml = "";
		var list = new Array();
		//整理选中的房间数据
		var info = this.__arrangselected(table.hlcSelectedCells);
		table.hlcArrangedInfo = info;
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			list.push(room);
			var floor = table.hlcSelectedCells[i].parentNode.hlcFloor;
			var selected = "";
			if(null!=room.corp && null==selectedcorp) {
				selectedroom = room;
				selectedcorp = room.corp;
				selected = "selected";
			}
			selectedhtml += "<option value='"+room.id+"' "+selected+">"+floor.no+"    "+room.no+"</option>";
		}
		selectedroom = null==selectedroom ? table.hlcSelectedCells[0].hlcRoom : selectedroom;
		
		var html = ""
			+ "<div style='width:300px; height:20px; line-height:20px; border-left:1px solid #dfdfdf; border-top:1px solid #dfdfdf; border-right:1px solid #dfdfdf;'>"
			+ "	&nbsp;&nbsp;查看"
			+ "</div>"
			+ "<div style='width:300px; border:1px solid #dfdfdf; text-align:center;'>"
			+ "<form id='"+form+"'>"
			+ "	<p>所选房间:"
			+ "		<select name='croom' onchange='this.parentNode.parentNode.parentNode.parentNode.hlcOnSelectedRoom();' style='width:154px;'>"
			+ selectedhtml
			+ "		</select>"
			+ "	<p>房号:<input name='no' type='text' disabled value='"+selectedroom.no+"'></input></p>"
			+ "	<p>面积:<input name='area' type='text' disabled value='"+selectedroom.area+"'></input></p>"
			+ "	<p>状态:<input name='state' type='text' disabled value='"+config.state.getByValue(selectedroom.state).parse+"'></input></p>"
			+ "	<input name='cid' type='hidden' value='"+(null==selectedcorp?"":selectedcorp.id)+"'></input>"
			+ "	</p>"
			+ "	<p>入驻单位:<input name='cname' type='text' disabled value='"+(null==selectedcorp?"":selectedcorp.name)+"'></input></p>"
			+ "	<p>单位别称:<input name='alias' type='text' disabled value='"+(null==selectedcorp?"":selectedcorp.alias)+"'></input></p>"
			+ "	<p>单位全称:<input name='fullname' type='text' disabled value='"+(null==selectedcorp?"":selectedcorp.fullname)+"'></input></p>"
			+ "	<p>行业分类:<input name='type' type='text' disabled value='"+(null==selectedcorp?"":config.color.getById(selectedcorp.type).name)+"'></input></p>"
			+ "	<p>人数规模:<input name='sizes' type='text' disabled value='"+(null==selectedcorp?"":config.sizes.getByValue(selectedcorp.size).parse)+"'></input></p>"
			+ "	<p>联系人员:<input name='contactor' type='text' disabled value='"+(null==selectedcorp?"":selectedcorp.contactor)+"'></input></p>"
			+ "	<p>联系电话:<input name='tel' type='text' disabled value='"+(null==selectedcorp?"":selectedcorp.tel)+"'></input></p>"
			+ "</form>"
			+ "	<p><button onclick='this.parentNode.parentNode.parentNode.hlcClose();'>关闭</button></p>"
			+ "</div>";
			
			var dailog = this.__dailog(html);
			dailog.hlcRooms = list;
			dailog.hlcConfig = config;
			dailog.hlcForm = form;
			dailog.hlcOnSelectedRoom = function () {
				var form = document.getElementById(this.hlcForm);
				for(var i=0; i<this.hlcRooms.length; i++) {
					var selected = this.hlcRooms[i];
					if(form.croom.value!=selected.id) continue;
					form.no.value = selected.no;
					form.area.value = selected.area;
					form.state.value = this.hlcConfig.state.getByValue(selected.state).parse;
					var corp = selected.corp;
					form.cid.value = null==corp?"":corp.id;
					form.cname.value = null==corp?"":corp.name;
					form.alias.value = null==corp?"":corp.alias;
					form.fullname.value = null==corp?"":corp.fullname;
					form.type.value = null==corp?"":this.hlcConfig.color.getById(corp.type).name;
					form.sizes.value = null==corp?"":this.hlcConfig.sizes.getByValue(corp.size).parse;
					form.contactor.value = null==corp?"":corp.contactor;
					form.tel.value = null==corp?"":corp.tel;
					break;
				}
			};
	};
	HLC_LdGrid.__loadcorp = function (form) {
		if(/^\s*$/ig.test(form.name.value)) {
			alert("错误：必须输入模糊名称！");
			return false;
		}
		//表单进入不可编辑状态
    form.hlcSetEnable(false);
    //回调用户获取企业列表
    var contid = this.container;
    var formid = form.id;
    var ret = this.onloadcorporation( form.cname.value, function(corp){
    	var _this = document.getElementById(contid).HLC;
    	var form = document.getElementById(formid);
    	if("undefined"==typeof corp || null==corp || corp.constructor!=Object) {
    		alert("错误：回执的单位信息必须是一个指定格式的JSON对象！");
    		form.hlcSetEnable(true);
    		return;
    	}
    	var fieldname = "";
    	if("undefined"==typeof corp.id) fieldname += (""==fieldname?"":",") + "id";
    	if("undefined"==typeof corp.name) fieldname += (""==fieldname?"":",") + "name";
    	if("undefined"==typeof corp.type) fieldname += (""==fieldname?"":",") + "type";
    	if(""!=fieldname){
    		alert("错误：回执的单位信息必须包含字段："+fieldname+"！");
    		form.hlcSetEnable(true);
    		return;
    	}
    	var fieldvalue= "";
    	if(null==corp.id || /^\s*$/ig.test(corp.id)) fieldvalue += (""==fieldvalue?"":",") + "id";
    	if(null==corp.name || /^\s*$/ig.test(corp.name)) fieldvalue += (""==fieldvalue?"":",") + "name";
    	if(null==corp.type || /^\s*$/ig.test(corp.type)) fieldvalue += (""==fieldvalue?"":",") + "type";
    	if(""!=fieldvalue){
    		alert("错误：回执的单位信息必须包含取值的字段："+fieldvalue+"中存在无效值！");
    		form.hlcSetEnable(true);
    		return;
    	}
    	if("undefined"==typeof corp.alias) corp.alias = "";
    	if("undefined"==typeof corp.fullname) corp.fullname = "";
    	if("undefined"==typeof corp.sizes) corp.size = "";
    	if("undefined"==typeof corp.contactor) corp.contactor = "";
    	if("undefined"==typeof corp.tel) corp.tel = "";
    	form.cid.value = corp.id;
    	form.cname.value = corp.name;
    	form.alias.value = corp.alias;
			form.fullname.value = corp.fullname;
			form.type.value = corp.type;	//_this.hlcConfig.color.getById(corp.type).name;
			form.sizes.value = corp.size;	//_this.hlcConfig.sizes.getByValue(corp.size).parse;
			form.contactor.value = corp.contactor;
			form.tel.value = corp.tel;
			form.oper.value = "update";
			//表单进入不可编辑状态
    	form.hlcSetEnable(false);
    } );
		//表单恢复回可编辑状态
    form.hlcSetEnable(ret);
	};
	HLC_LdGrid.__arrangselected = function (selects) {
		//按楼层排序
		for(var i=0; i<selects.length; i++) {
			for(var j=i+1; j<selects.length; j++) {
				if(selects[i].hlcXY.y > selects[j].hlcXY.y) {
					var tmp = selects[i];
					selects[i] = selects[j];
					selects[j] = tmp;
				}
			}
		}
		//整理要操作的房间数组
		var rooms = new Array();
		//按房间排序
		for(var i=0; i<selects.length; i++) {
			for(var j=i+1; j<selects.length&&selects[i].hlcXY.y==selects[j].hlcXY.y; j++) {
				if(selects[i].hlcXY.x > selects[j].hlcXY.x) {
					var tmp = selects[i];
					selects[i] = selects[j];
					selects[j] = tmp;
				}
			}
			rooms.push(selects[i].hlcRoom);
			//占用的房间也排序
			var cells = selects[i].hlcCells;
			for(var m=0; m<cells.length; m++) {
				for(var n=m+1; n<cells.length; n++) {
					if(cells[m].hlcXY.x > cells[n].hlcXY.x) {
						var tmp = selects[m];
						selects[m] = selects[n];
						selects[n] = tmp;
					}
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
				cell.childNodes[0].childNodes[3].innerHTML = cell.hlcRoom.corp.name;
				//房间相邻
				if((null!=prev&&prev.hlcXY.x+1==cell.hlcXY.x) || (null!=prev&&0<prev.hlcCells.length&&prev.hlcCells[prev.hlcCells.length-1].hlcXY.x+1==cell.hlcXY.x))
				{
					//删除被合并的单元格
					cell.parentNode.removeChild(cell);
					//合并显示内容
					prev.colSpan = parseInt(prev.colSpan)+parseInt(cell.colSpan);
					prev.style.width = (parseFloat(prev.style.width)+parseFloat(cell.style.width)) + "px";
					prev.childNodes[0].childNodes[1].innerHTML += "&nbsp;&nbsp;&nbsp;&nbsp;" + cell.childNodes[0].childNodes[1].innerHTML;
					//合并占用房间
					prev.hlcCells.push(cell);
					prev.hlcCells=prev.hlcCells.concat(cell.hlcCells);
					cell.hlcCells = new Array();
					continue;
				}
				prev = cell;
			}
		}
	};
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
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			if(null!=room.corp) {
				message += (message=="" ? "" : "\r\n") + (room.no+"\t"+room.corp.name);
			}
		}
		if(""!=message) {
			alert("错误：不允许的操作！所选房间已有单位入驻。\r\n入驻单位：\r\n"+message);
			return;
		}
		
		var selectedtype = "";
		for(var i=0; i<config.color.values.length; i++) {
			var type = config.color.values[i];
			selectedtype += "<option value='"+type.id+"'>"+type.name+"</option>";
		}
		var selectedsize = "";
		for(var i=0; i<config.sizes.values.length; i++) {
			var size = config.sizes.values[i];
			selectedsize += "<option value='"+size.value+"'>"+size.parse+"</option>";
		}
		
		var html = ""
			+ "<div style='width:300px; height:20px; line-height:20px; border-left:1px solid #dfdfdf; border-top:1px solid #dfdfdf; border-right:1px solid #dfdfdf;'>"
			+ "	&nbsp;&nbsp;录入"
			+ "</div>"
			+ "<div style='width:300px; border:1px solid #dfdfdf; text-align:center;'>"
			+ "	<form id='"+form+"'>"
			+ "		<input name='oper' type='hidden' value='insert'></input>"
			+ "		<input name='cid' type='hidden' value=''></input>"
			+ "		<p>单位名称:<input name='cname' type='text' value='录入，或开窗选择...'></input>"+must
			+ "			<span onclick='this.parentNode.parentNode.parentNode.parentNode.hlcOnLoadCorp();' style='cursor:pointer; border:1px solid #dfdfdf;'>"
			+ "				..."
			+ "			</span>"
			+ "		</p>"
			+ "		<p>单位别称:<input name='alias' type='text' value=''></input></p>"
			+ "		<p>单位全称:<input name='fullname' type='text' value=''></input></p>"
			+ "		<p>行业分类:"
			+ "			<select name='type' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedtype
			+ "			</select>"+must
			+ "		</p>"
			+ "		<p>人数规模:"
			+ "			<select name='sizes' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedsize
			+ "			</select>"
			+ "		</p>"
			+ "		<p>联系人员:<input name='contactor' type='text' value=''></input></p>"
			+ "		<p>联系电话:<input name='tel' type='text' value=''></input></p>"
			+ "		<p>"
			+ "	</form>"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnSave();'>保存</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnClear();'>清空</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcClose();'>取消</button>"
			+ "		</p>"
			+ "</div>";
			
			var dailog = this.__dailog(html);
			dailog.hlcConfig = config;
			dailog.hlcForm = form;
			document.getElementById(form).hlcSetEnable = function (enabled) {
				enabled = !enabled;
				this.cname.disabled = enabled;
				this.alias.disabled = enabled;
				this.fullname.disabled = enabled;
				this.type.disabled = enabled;
				this.sizes.disabled = enabled;
				this.contactor.disabled = enabled;
				this.tel.disabled = enabled;
			};
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
				form.contactor.value = "";
				form.tel.value = "";
			};
			dailog.hlcOnSave = function () {
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
					contactor: form.contactor.value,
					tel: form.tel.value
				};
				//整理选中的房间数据
				var info = ("undefined"==table.hlcArrangedInfo||null==table.hlcArrangedInfo) ? _this.__arrangselected(table.hlcSelectedCells) : table.hlcArrangedInfo;
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
			};
	};
	HLC_LdGrid.__update = function () {
		var table = document.getElementById(this.table);
		var form = "form___update_" + table.hlcContainer;
		var config = this.attrs.config;
		var must = "<span style='color:red;'>*</span>";
		
		if(table.hlcSelectedCells.length<=0) {
			alert("错误：不允许的操作！必须先选择要操作的房间。");
			return;
		}
		var selectedcorp = null;
		for(var i=0; i<table.hlcSelectedCells.length; i++) {
			var room = table.hlcSelectedCells[i].hlcRoom;
			var corp = room.corp;
			if(null==corp) continue;
			selectedcorp = null==selectedcorp ? corp : selectedcorp;
			if(corp.id!=selectedcorp.id) {
				alert("错误：不允许的操作！必须选择相同入驻单位的房间。");
				return;
			}
		}
		if(null==selectedcorp) {
			alert("错误：不允许的操作！必须选择某个入驻单位。");
			return;
		}
		
		var selectedtype = "";
		for(var i=0; i<config.color.values.length; i++) {
			var type = config.color.values[i];
			selectedtype += "<option value='"+type.id+"' "+(type.id==selectedcorp.type?"selected":"")+">"+type.name+"</option>";
		}
		var selectedsize = "";
		for(var i=0; i<config.sizes.values.length; i++) {
			var size = config.sizes.values[i];
			selectedsize += "<option value='"+size.value+"' "+(size.value==selectedcorp.size?"selected":"")+">"+size.parse+"</option>";
		}
		
		var html = ""
			+ "<div style='width:300px; height:20px; line-height:20px; border-left:1px solid #dfdfdf; border-top:1px solid #dfdfdf; border-right:1px solid #dfdfdf;'>"
			+ "	&nbsp;&nbsp;变更"
			+ "</div>"
			+ "<div style='width:300px; border:1px solid #dfdfdf; text-align:center;'>"
			+ "	<form id='"+form+"'>"
			+ "		<input name='oper' type='hidden' value='update'></input>"
			+ "		<input name='cid' type='hidden' value='"+selectedcorp.id+"'></input>"
			+ "		<p>单位名称:<input name='cname' type='text' value='"+selectedcorp.name+"'></input>"+must
			+ "		</p>"
			+ "		<p>单位别称:<input name='alias' type='text' value='"+selectedcorp.alias+"'></input></p>"
			+ "		<p>单位全称:<input name='fullname' type='text' value='"+selectedcorp.fullname+"'></input></p>"
			+ "		<p>行业分类:"
			+ "			<select name='type' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedtype
			+ "			</select>"+must
			+ "		</p>"
			+ "		<p>人数规模:"
			+ "			<select name='sizes' style='width:154px;'>"
			+ "				<option value=''>请选择...</option>"
			+ selectedsize
			+ "			</select>"
			+ "		</p>"
			+ "		<p>联系人员:<input name='contactor' type='text' value='"+selectedcorp.contactor+"'></input></p>"
			+ "		<p>联系电话:<input name='tel' type='text' value='"+selectedcorp.tel+"'></input></p>"
			+ "		<p>"
			+ "	</form>"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnSave();'>保存</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcOnClear();'>清空</button>&nbsp;&nbsp;&nbsp;"
			+ "			<button onclick='this.parentNode.parentNode.parentNode.hlcClose();'>取消</button>"
			+ "		</p>"
			+ "</div>";
			
			var dailog = this.__dailog(html);
			dailog.hlcConfig = config;
			dailog.hlcForm = form;
			dailog.hlcOnClear = function () {
				var form = document.getElementById(this.hlcForm);
			//form.cid.value = "";
				form.cname.value = "";
				form.alias.value = "";
				form.fullname.value = "";
				form.type.value = "";
				form.sizes.value = "";
				form.contactor.value = "";
				form.tel.value = "";
			};
			dailog.hlcOnSave = function () {
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
					contactor: form.contactor.value,
					tel: form.tel.value
				};
				//整理选中的房间数据
				var info = ("undefined"==table.hlcArrangedInfo||null==table.hlcArrangedInfo) ? _this.__arrangselected(table.hlcSelectedCells) : table.hlcArrangedInfo;
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
			};
	};
	HLC_LdGrid.__resetcell = function (cell) {
		cell.colSpan = 1;
		cell.style.width = cell.hlcOrignalWH.w + "px";
		cell.childNodes[0].childNodes[1].innerHTML = cell.hlcRoom.no;
		cell.hlcRoom.corp = null;
		cell.hlcOrignalColor = "#ffffff";
		cell.hlcChecked = false;
		cell.style.backgroundColor = cell.hlcOrignalColor;
		cell.childNodes[0].childNodes[3].innerHTML = "";
	};
	HLC_LdGrid.__divide = function (selectedcells) {
		for(var i=0; i<selectedcells.length; i++) {
			var cell = selectedcells[i];
			//占用了其他房间
			if(0<cell.hlcCells.length) {
				this.__resetcell(cell);
			}
			for(var j=0; j<cell.hlcCells.length; j++) {
				var newcell = cell.hlcCells[j];
				cell.parentNode.insertBefore(newcell,cell.nextSibling);
				this.__resetcell(newcell);
			}
			cell.hlcCells = new Array();
		}
	};
	HLC_LdGrid.__delete = function () {
		var table = document.getElementById(this.table);
		
		if(table.hlcSelectedCells.length<=0) {
			alert("错误：不允许的操作！必须先选择要操作的房间。");
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
			alert("错误：不允许的操作！必须选择某个入驻单位。");
			return;
		}
		if(0!=numnull) {
			alert("错误：不允许的操作！不能清理空房间。\r\n空置房间：\r\n"+msgnull);
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
	};
	HLC_LdGrid.onopmenuclick = function (item) {
		switch (item) {
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
				break;
		}
	};
	HLC_LdGrid.onchangetable = function (info) {
		if(null!=this.attrs.onChangeGrid && typeof this.attrs.onChangeGrid!="undefined" && this.attrs.onChangeGrid.constructor==Function) {
			this.attrs.onChangeGrid(info);
		}
	};
	HLC_LdGrid.onloadcorporation = function (like, callback) {
		if(null!=this.attrs.onLoadCorp && typeof this.attrs.onLoadCorp!="undefined" && this.attrs.onLoadCorp.constructor==Function) {
			return this.attrs.onLoadCorp(like, callback);
		}
		return true;
	};
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
	HLC_LdGrid.cancel = function () {
		if(!confirm("您确定不需要保存本次所作的信息变更吗？")) return;
		//重新刷新数据
		this.loaddata();
	};
	
	return HLC_LdGrid;
}