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
		<script type="text/javascript" src="<%=path%>/estate/business/formats_type/formats.type.js"></script>
		<!-- 自定义js start -->
		
		<!-- 样式的引入 start -->
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
		<!-- 样式的引入 start -->
	</head>
	<body  class="easyui-layout" >
        <div data-options="region:'north'" style="height:60px;padding:10px">
	        <div style="padding:5px 0;">
	        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_add'" style="width:80px" onclick="addOrUp('add')">新增</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_form_edit'" style="width:80px" onclick="addOrUp('update')">修改</a>
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_delete'" style="width:80px;" onclick="deleteBtn()">删除</a>
	    	</div>
        </div>
        <div data-options="region:'center',title:'业态类型',iconCls:'icon-building_link',resizable:true,modal:true,closed:false">
             <table id="dg" style="height:353px;"></table>
        </div>
        <div id="win" style="width:516px;height:250px;"></div>
	</body>
	<!-- js代码段 -->
	<script type="text/javascript">
		var height = document.body.offsetHeight-170;
		var width =  document.documentElement.clientWidth;
		//设置grid的高度
		document.getElementById("dg").style.height=height+"px";
	</script>
</html>
