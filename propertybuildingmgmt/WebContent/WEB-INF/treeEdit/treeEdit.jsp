<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>业态类型设置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/themes/IconExtension.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/common/jquey/jq/demo/demo.css">
		<!-- link end -->

		<!-- script  start-->
		<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.min.js"></script>
		<script type="text/javascript" src="<%=path%>/common/jquey/jq/jquery.easyui.min.js"></script>
		<!-- script  end-->
		<script type="text/javascript" src="<%=path %>/common/excel_js/JsonUtil.js"></script>
		
		<!-- script  end-->
		
		<!-- 自定义js start -->
		<script type="text/javascript" src="<%=path%>/estate/business/treeEdit/tree.edit.js"></script>
	</head>
	<body  class="easyui-layout" >
        <div data-options="region:'north'" style="height:60px;padding:10px">
	        <div style="padding:5px 0;">
	        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_add'" style="width:80px" onclick="add()">新增</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_edit'" style="width:80px" onclick="edit()">修改</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_delete'" style="width:80px" onclick="deleteBtn()">删除</a>
	    	</div>
        </div>
        <div data-options="region:'center',title:'楼栋设置',iconCls:'icon-building_link',resizable:true,modal:true,closed:false">
             <ul id="tt" style="width:300px;padding:10px;"></ul>
        </div>
        <div id="win" class="easyui-window" data-options="iconCls:'icon-edit',closed:true" style="width:400px;height:200px;">
        	<table style="padding:15px;font-size: 14px;">
        		<tr style="height:35px;">
        			<td>区&nbsp;域&nbsp;名:</td>
        			<td><input class="easyui-validatebox textbox" id="tname"/></td>
        			<td id="errorTips" style="color:red;font-weight:bold;"></td>
        		</tr>
        		<tr style="height:35px;">
        			<td>所属区域:</td>
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
		document.getElementById("dg").style.height=height+"px";
	</script>
</html>
