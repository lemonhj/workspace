<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String type = request.getParameter("where");
String cid = request.getParameter("cid");
%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<!-- link start -->
		<link rel="stylesheet" type="text/css"  href="<%=path %>/estate/css/default.css" />
		<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css" 	href="<%=path %>/common/jquey/jq/themes/icon.css" />
		<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/IconExtension.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/demo/demo.css"/>
		<link rel="stylesheet" type="text/css"  href="<%=path %>/common/css/jquery.bigcolorpicker.css"/>
		<!-- link end -->
		
		<!-- script  start-->
		<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery.min.js"></script>
		<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery.bigcolorpicker.js"></script>
		<!-- script  end-->
		
		<!-- 自定义js start -->
		<script type="text/javascript" src="<%=path%>/estate/business/sys_edit/rights.edit.js"></script>
		<!-- 自定义js start -->
		<style type="text/css">
			table td{padding: 0 5px;height:30px;line-height: 30px;}
		</style>
		
	</head>
	<body style="background-color:white;">
		<form id="rightForm" method="post">
           <table cellpadding="0px" style="line-height:35px;">
	            <tr>
	            	<td>权限名称:</td>
	                <td><input id="rightName" name="right.rightName" class="easyui-validatebox textbox"   data-options="required:true,missingMessage:'必须填写',validType:'initId'"></td>
	            </tr>
	            <tr>
	                <td>权限URL:</td>
	                <td><input id="rightUrl" name="right.rightUrl" class="easyui-validatebox textbox"  data-options="required:true,missingMessage:'必须填写',validType:'maxLength[120]'"></td>
	            </tr>
	            <tr>
	                <td>权限描述:</td>
	                <td>
	                <input id ="rightDesc" name="right.rightDesc"  class="easyui-textbox" ">
	                </td>
	            </tr>
	        </table>
	        	<input id="rightId" name="right.id" type="hidden">	
	        </form>
	        <div id="toolswenj"  style="text-align:center">
		        <div style="padding:5px 0;"></div>
		        <div onclick="saveORUpdateRight();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">保存</div>
		        <div class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" onclick="backBtn();">取消</div>
    		</div>  
   		<script type="text/javascript">
	   		$(document).ready(function(){ 
   				$("#cbtn").bigColorpicker("color","L",5);
   				if("<%=type%>" == "update"){
   					fillData("<%=cid%>");
   				}
			});
   		</script>
	</body>
</html>