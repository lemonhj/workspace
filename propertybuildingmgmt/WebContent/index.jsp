<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>物业楼栋管理系统</title>

	<!--- CSS --->
	<link rel="stylesheet" type="text/css" href="<%=path %>/common/css/style.css"/>
	<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/icon.css"/>
	<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/IconExtension.css"/>
	</head>	
	<body>
		<div id="container">
			<s:form name="loginform" method="post"  action="loginAction" onsubmit="return checkname()" theme="simple">
			<div style="float:left; width:420px;">
				<div class="login" style="width:370px;">宏立城物业楼栋管理系统</div>
				<div class="username-text">用户:</div>
				<div class="password-text" style="width:150px;">密码:</div>
				<div class="username-field">
					<input type="text" name="suUsername"/><br>
					<div style="padding:15px 0 0 8px;"><s:actionerror /></div>
				</div>
				<div class="password-field" style="width:180px;">
					<input type="password" name="suPassword"/>
				</div>
				</div>
				<div style="float:left;width:100px;">
				<s:submit name="submit" style="cursor:pointer; height:260px;" value="登陆" />
				</div>
			</s:form>
			<s:actionerror/>
		</div>
		<div id="footer">
			Copyright © 2014-2015 Homnicen Group. All Rights Reserved.    
			<div> 技术支持：宏立城集团信息系统管理部</div>
		</div>
		<script type="text/javascript">
		function checkname(){
			if(loginform.suUsername.value=="" || loginform.suPassword.value==""){
				alert('用户名和密码不能为空！');
				return false;
			}
		}
		</script>
	</body>
</html>