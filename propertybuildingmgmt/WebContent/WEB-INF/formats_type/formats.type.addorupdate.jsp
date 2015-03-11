<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'formats.type.addorupdate.jsp' starting page</title>
    
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
		<script type="text/javascript" src="<%=path%>/estate/business/formats_type/formats.type.addorupdate.js"></script>
		<!-- 自定义js start -->
		<style type="text/css">
			table td{padding: 0 5px;height:30px;line-height: 30px;}
		</style>
		
	</head>
	<body style="background-color:white;">
	 <form id="dgForm" action="" method="post">
         <table cellpadding="0px" style="line-height: 35px;">
            <tr>
                <td>类型名称:</td>
                <td><input  name="name" id="name" value='<s:property value="ename"/>'></td>
                <td><font id="errorTipn" style="font-size: 16xp;color: red;font-weight: bold;letter-spacing:1px;"></font></td>
            </tr>
            <tr>
            	<td>颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:</td>
                <td>
                	<input type="hidden" name="color" id="color"  value='<s:property value="ecolor"/>' readonly/>
                	<div id="colorDiv" style='margin-top:3px;float: left;background-color: <s:property value="ecolor"/>;width:90px;height:20px;'></div>
                	<input type="button" id="cbtn" name="cbtn" value="选色" style="float: left;margin-left: 4px;"/>
                </td>
                <td><font id="errorTipc" style="font-size: 16xp;color: red;font-weight: bold;letter-spacing:1px;"></font></td>
            </tr>
            <tr>
            	<td colspan="2">
            		<s:if test='where =="isAdd"'>
            		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px;" onclick="saveBtn()" id="savaBtn">保存</a>
            		</s:if>
            		<s:if test='where == "isMod"'>
            		<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px;" onclick="upDataBtn()" id="upDataBtn">修改</a>
            		<input type="hidden" name="editId" value='<s:property value="editId"/>'/>
            		</s:if>
            		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px;margin-left: 20px;" onclick="backBtn()">取消</a>
            	</td>
            </tr>
	        </table>
	    </form>
	        
	       
   		<script type="text/javascript">
   			$(function(){
   				$("#cbtn").bigColorpicker("color","L",5);
   			});
   		</script>
	</body>
</html>