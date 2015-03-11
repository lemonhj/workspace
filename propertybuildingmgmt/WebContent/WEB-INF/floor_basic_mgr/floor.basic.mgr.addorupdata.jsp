<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>楼栋基础设施新增和修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- link start -->
	<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/IconExtension.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/demo/demo.css">
	<!-- link end -->

	<!-- script  start-->
	<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/common/url.js"></script>
	<script type="text/javascript" src="<%=path%>/common/excel_js/JsonUtil.js"></script>
	<!-- script  end-->


	<!-- Dwr start-->
	  <script type='text/javascript' src='<%=path%>/dwr/util.js'></script>
	  <script type='text/javascript' src='<%=path%>/dwr/engine.js'></script>
	<!-- Dwr End-->
	
	<!-- 自定义js start -->
	<script type="text/javascript" src="<%=path%>/common/ajax.js"></script>
	<script type="text/javascript" src="<%=path%>/estate/business/floor_basic_mgr/floor.basic.mgr.addorupdata.js"></script>
	<!-- 自定义js start -->
	
	<!-- 自定义style sttart -->
    <style scoped="scoped">
        .tt-inner{
            display:inline-block;
            line-height:12px;
            padding-top:5px;s
        }
        .tt-inner img{
            border:0;
        }
    </style>
    <!-- 自定义style End -->
  </head>
<body class="easyui-layout">
	<div data-options="region:'center',split:true" title="" style="">
		<div id="tabId" class="easyui-tabs" data-options="tabWidth:100,tabHeight:30"
			style="width:956px;">
			<div title="主信息" style="padding:10px">
				<div>
					<form id="dgform1" method="post" name="dgform1">
						<table cellpadding="5">
							<tr>
								<td>区域<span style="color:red;">*</span></td>
								<td><input  id="builCommunity" name="builCommunity" class=easyui-combotree"  data-options="panelWidth:'200',panelHeight:'auto'"/></td>
								<td>楼栋名称<span style="color:red;">*</span></td>
								<td><input id="builName" name="builName" class="easyui-validatebox textbox" 
									data-options="validType:'bName'">
									<input id="builId" name="builId"
										class="easyui-validatebox textbox" data-options="" type="hidden"/>
								</td>
								<td>创建时间</td>
								<td><input id="dataTime"  name="builCreate" type="text" class="easyui-datebox" disabled=true></input></td>
							</tr>
							<tr>
								<td>楼栋层数<span style="color:red;">*</span></td>
								<td><input id="builFloor" name="builFloor"
									class="easyui-validatebox textbox" data-options="" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" onblur="this.value=(this.value)>50?50:this.value"  ></td>
								<td style="display: none;">地下层层数</td>
								<td style="display: none;"><input id="buildBottom" name="buildBottom" 
									class="easyui-validatebox textbox" data-options="" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
								<td>备注</td>
								<td><input id="builMemo" name="builMemo" class="easyui-validatebox textbox" data-options=""></td>
								<td colspan=2><a class="easyui-linkbutton" id="refreshBtnId"
									iconCls='icon-arrow_refresh' style="width:120px;float: right;"
									onclick="refreshBtn()">生成楼层</a></td>
							</tr>
						</table>
					</form>
				</div>
				<div  data-options="title:'楼层列表',iconCls:'icon-map'" >
					<table id="dg2" title='楼层列表' class="easyui-datagrid" style="height:260px;" data-options="onDblClickCell: onClickCell,onBeforeEdit:onBeforeEditData,onClickRow:onClickCellRow" singleSelect=true> 
						<thead>
							<tr>
								<!-- <th data-options="field:'ck',checkbox:true"></th> -->
								<th data-options="field:'flooId'"  width="100"  hidden="true">主键</th>
								<th data-options="field:'flooOrder'"  width="100">楼层编号</th>
								<th data-options="field:'flooBuilding'"  width="220"  hidden="true">归属</th>
								<th data-options="field:'flooNo'"  width="220"  hidden="true">序号</th>
								<td><input  id="builCommunity" name="builCommunity" class=easyui-combotree"  data-options="panelWidth:'200'"/></td>
								<th id='combo1' data-options="field:'flooType',width:80,align:'center',panelHeight:'auto',
										editor:{
											type:'combobox',options:{
												textField: 'label',
												valueField: 'value',
												editable:false,
												data: [
													{ label: '标准层',value: 1},
													{label: '避难层',value: 2},
													{label: '无' ,value: 3},
													{label: '特殊层1' ,value: 4},
													{'label':'特殊层2',value:5}
												],onSelect:onSelectData
											}
										},
										formatter:function(value,row,index){
											var set=[
													{ label: '标准层',value: 1},{label: '避难层',value: 2},{label: '无' ,value: 3},
													{label: '特殊层1' ,value: 4},{'label':'特殊层2',value:5}
												];
												for(var i = 0;i<set.length;i++)
												{
													if(value == set[value-1].value){
													    getComBoBoxValue = set[value-1].value;
														return set[value-1].label;
													}else{
														return '';
													}
												}
											}"  width="320" >楼层类型</th>
								<th data-options="field:'flooHeight'"  width="120"  hidden="true">高度</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div title="房型结构" style="padding:10px">
				<div style="padding-bottom: 10px;">
					<span class="combo-defaultText">楼层类型选择:</span>
					<input id="typeData" name="typeList" class="easyui-combobox" data-options="valueField:'commId',textField:'commName',panelHeight:'auto',defaultText:'请选择房型...',onSelect:selectData"> 
						 <!-- <a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-remove'" style="width:80px" onclick="deleteTRoom()">删除</a> -->
				</div>
				<div style="padding-bottom: 2px;">
					<table id="dg3" title='房型列表' class="easyui-datagrid" style="height:300px;" data-options="onClickCell: onClickCell1,onBeforeEdit:onBeforeEditData" singleSelect=true>
						<thead>
							<tr>
								<!-- <th data-options="field:'ck',checkbox:true"></th> -->
								<th data-options="field:'roomId'"  width="100"  hidden="true">房间ID</th>
								<th data-options="field:'roomFloor'"  width="100"  hidden="true">归属</th>
								<th data-options="field:'roomOrder'"  width="100" >房型编号</th>
								<th data-options="field:'roomNo'"  width="100"  hidden="true" >房间编号</th>
								<th data-options="field:'roomArea',editor:{type:'numberbox',options:{precision:2}}" required=true  missingMessage='必须填写' width="180" >房间面积</th>
								<th data-options="field:'roomState'" width="200" hidden="true">状态</th>
								<th data-options="field:'roomType'" width="200" hidden="true">房型</th>
								<th data-options="field:'roomCorporation'" width="200" hidden="true">进驻单位</th>
								<th data-options="field:'roomOwner'" width="200" hidden="true">业主名</th>
								<th data-options="field:'roomOwnerTel'" width="200" hidden="true">业主电话</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
		<div data-options="region:'south',split:false"
			style="height:30px;padding:5px 0 0 350px;">
			<div style="padding:5px 0;">
				<a id="backId" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-leftarrow'" style="width:80px" onclick="backBtn()">上一步</a>
				<a id="nextId" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-rightarrow'" style="width:80px" onclick="nextBtn()">下一步</a>
				<a id="saveId" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" style="width:80px" onclick="saveData()">保存</a> 
				<a id="removeId" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" style="width:80px"
					onclick="shoutWin()">取消</a>
			</div>
		</div>
	</div>
	<div id="win1"></div>
</body>
</html>
