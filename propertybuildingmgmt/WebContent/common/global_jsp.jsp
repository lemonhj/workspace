<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- java中的引用 start -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- 
@File:global_jsp.jsp
@Desc:公共的JSP,把相公共的内容放置在这里，其中包括link,script,全局变量等
@Date:2014-09-15
@Author：yanglh
@Version:V1.0
@Copyright(c) 2014, Guizhou HOMNICEN Group. All rights reserved.
 -->
<!-- java中的引用 End -->

<!-- 页面page的引用start -->

<!-- 页面page的引用  end -->

<!-- 系统自动生成 start -->
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- 系统自动生成 End -->

<!-- link引入  start -->
<link rel="stylesheet" type="text/css"  href="<%=path %>/estate/css/default.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=path %>/common/jquey/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/IconExtension.css"/>

<!-- link引入  End -->

<!-- script引入 start  -->
<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/jquey/js/jquery.easyui.min-1.2.0.js"></script>
<script type="text/javascript"  src="<%=path %>/common/jquey/js/outlook.js"></script>
<!-- script引入 End  -->

<!-- 全局变量定义引入等 start -->

<!-- 全局变量定义引入等 end -->

