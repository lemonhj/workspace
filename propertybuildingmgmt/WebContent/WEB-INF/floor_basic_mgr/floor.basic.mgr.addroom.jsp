<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>楼层房间新增</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- link start -->
<link rel="stylesheet" type="text/css"
	href="<%=path%>/common/jquey/jq/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/common/jquey/jq/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/common/jquey/jq/themes/IconExtension.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/common/jquey/jq/demo/demo.css">
<!-- link end -->

<!-- script  start-->
<script type="text/javascript"
	src="<%=path%>/common/jquey/jq/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/jquey/jq/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/url.js"></script>
<script type="text/javascript"
	src="<%=path%>/common/excel_js/JsonUtil.js"></script>
<!-- script  end-->


<!-- 自定义js start -->
<script type="text/javascript" src="<%=path%>/common/ajax.js"></script>
<script type="text/javascript" src="<%=path%>/estate/business/floor_basic_mgr/floor.basic.mgr.addroom.js"></script>
<!-- 自定义js start -->

<!-- 自定义style sttart -->
<style scoped="scoped">
.tt-inner {
	display: inline-block;
	line-height: 12px;
	padding-top: 5px;
	s
}

.tt-inner img {
	border: 0;
}
</style>
<!-- 自定义style End -->
</head>

<body>
	<form id="formRoom" method="post" name="formRoom">
		<div>
			<label for="name">房间数量：</label> <input class="easyui-validatebox"
				type="text" name="roomNum" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
		</div>
<!-- 		<div>
			<label for="email">房间面积：</label> <input class="easyui-validatebox"
				type="text" name="roomArea" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"  validType="length[0,4]" invalidMessage="输入的位数不能超过4位数！" />
		</div> -->
	</form>
	<div style="padding:5px 0 0 100px;"><a href="#" class="easyui-linkbutton"
		data-options="iconCls:'icon-save'" style="width:80px;"
		onclick="addData()">新增</a><a href="#" class="easyui-linkbutton"
		data-options="iconCls:'icon-cancel'" style="width:80px;paddint-left:100px;"
		onclick="shoutWin()">取消</a></div>
	</div>
</body>
</html>
