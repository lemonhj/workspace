<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>系统菜单项设置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/IconExtension.css">
		<!-- link end -->

		<!-- script  start-->
		<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.min.js"></script>
		<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path%>/estate/business/sys_edit/menu.mgmt.js"></script>
		<!-- script  end-->
	</head>
	<body  class="easyui-layout" >
        <div data-options="region:'north'" style="height:60px;padding:10px">
	        <div style="padding:5px 0;">
	        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_add'" style="width:80px" onclick="addMenu()">新增</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_edit'" style="width:80px" onclick="editMenu()">修改</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_delete'" style="width:80px" onclick="deleteMenu()">删除</a>
	    	</div>
        </div>
        <div data-options="region:'center',title:'系统菜单项设置',iconCls:'icon-building_link',resizable:true,modal:true,closed:false">
             <ul id="menuTree" style="width:300px;padding:10px;"></ul>
        </div>
        <div id="win" class="easyui-window" data-options="iconCls:'icon-edit',closed:true" style="width:400px;height:200px;">
        	<table style="padding:15px;font-size: 14px;">
        		<tr style="height:35px;">
        			<td>菜&nbsp;单&nbsp;名:</td>
        			<td><input class="easyui-validatebox textbox" id="tname"/></td>
        			<td id="errorTips" style="color:red;font-weight:bold;"></td>
        		</tr>
        		<tr style="height:35px;">
        			<td>所属菜单:</td>
        			<td><input class="easyui-validatebox textbox" id="parentName" readonly="readonly"/></td>
        		</tr>
        		<tr style="height:35px;">
        			<td></td>
	        			<td><input id="saveBtn" type="button" value="保存" onClick="addInfo()"/>
	        			<input id="editBtn" type="button" value="修改" onClick="editInfo()"/>
	        			<input type="button" value="取消" onClick="$('#win').window('close')"/>
        			</td>
        		</tr>
        	</table>
        </div>
	</body>
	<!-- js代码段 -->
	<script type="text/javascript">
		var height = document.body.offsetHeight-170;
		var width =  document.documentElement.clientWidth;
		//设置grid的高度
		//document.getElementById("dg").style.height=height+"px";
	</script>
</html>
