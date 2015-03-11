<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>楼栋基础设置</title>

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
		<script type="text/javascript" src="<%=path%>/common/excel_js/JsonUtil.js"></script>
		<!-- script  end-->

		<!-- 自定义js start -->
		<script type="text/javascript" src="<%=path%>/common/ajax.js"></script>
		<script type="text/javascript" src="<%=path%>/estate/business/floor_basic_mgr/floor.basic.mgr.js"></script>
		<!-- 自定义js start -->

		<!-- style start -->
		<style scoped="scoped">
			.tt-inner {
				display: inline-block;
				line-height: 12px;
				padding-top: 5px;
			}
			
			.tt-inner img {
				border: 0;
			}
		</style>
		<!-- style End -->
	</head>

<body class="easyui-layout">

	<div data-options="region:'north'" style="height:50px;padding:5px">
		<div style="padding:5px 0;">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-application_add'" style="width:80px"
				onclick="addOrUpdate('add')" theme="simple">新增</a> 
			<a  class="easyui-linkbutton"
				data-options="iconCls:'icon-application_form_edit'"
				style="width:80px" onclick="addOrUpdate('update')">修改</a> 
		  	<a  class="easyui-linkbutton"
				data-options="iconCls:'icon-application_delete'" style="width:80px" onclick="delectData()">删除</a>
		  	<a  class="easyui-linkbutton" id="testa"
				data-options="iconCls:'icon-search'" style="width:80px" onclick="queryWindosOpen()">查询</a>
		</div>
	</div>
	<!-- <div data-options="region:'south',split:true" style="height:50px;"></div> -->
	<div data-options="region:'west',split:true" title="楼栋区域"
		style="width:267px;">
		<div title="楼栋区域" style="padding:10px">
			<div id="treeList" class="easyui-tree"  style="width:200px;height:auto;"></div>
		</div>
	</div>
	<div data-options="region:'center',title:'楼栋列表',iconCls:'icon-map'">
		<table id="dg" class="easyui-datagrid" singleSelect=true rownumbers=true>
			<thead data-options="frozen:true">
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'builId'" width="100" hidden="true">序号</th>
					<th data-options="field:'builName'" width="220">楼栋名称</th>
					<th data-options="field:'builParentId',formatter:getDataValue" width="120">所属楼盘</th>
					<th data-options="field:'builCommunity',formatter:getDataValue" width="120">所属区域</th>
					<th data-options="field:'floorCount'" width="100">楼层数</th>
					<th data-options="field:'builCreate'" width="220">创建时间</th>
					<th data-options="field:'builScal'" width="220" hidden="true">总面积(㎡)</th>
					<th data-options="field:'builMemo'" width="220">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="win"></div>
    <div id="winQuery" class="easyui-window" data-options="iconCls:'icon-edit',closed:true" style="width:400px;height:200px;">
	   	<form id="dgformQuery" method="post">
			<table cellpadding="5">
				<tr>
					<td>区域</td>
					<td><input  id="builCommunity" name="tbu.builCommunity" class="easyui-combotree" data-options="valueField:'commId',textField:'commName',panelWidth:'200'"/></td>
				</tr>
				<tr>
					<td>楼栋名称</td>
					<td><input  id="builName" name="tbu.builName" class="easyui-validatebox textbox"
						data-options=""></td>
				</tr>
				<tr>
					<td></td>
					<td><a class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width:80px"
						onclick="queryData()">搜索</a></td>
				</tr>
			</table>
		</form>
   </div>
</body>
<script type="text/javascript">
	var height = window.screen.availHeight;
	var width =  document.documentElement.clientWidth;
	//设置grid的高度
	document.getElementById("dg").style.height=height-400+"px";
</script>
</html>
