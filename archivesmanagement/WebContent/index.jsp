<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>档案管理系统</title>

	<!--- CSS --->
	<link rel="stylesheet" type="text/css" href='<s:url value="/css/style.css" />'>
	<link href='<s:url value="/jq/themes/default/easyui.css"/>' rel="stylesheet" type="text/css" >
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/themes/icon.css"/>'>
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/demo/demo.css"/>'>
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/themes/IconExtension.css"/>'>
	<link rel="stylesheet" href='<s:url value="/css/ztree/zTreeStyle/zTreeStyle.css"/>' type="text/css">
	</head>
	<body>
		<div id="container">
			<s:form name="loginform" method="post"  action="LoginAction_login" theme="simple">
			<div style="float:left; width:420px;">
				<div class="login" style="width:370px;">宏立城档案管理系统</div>
				<div class="username-text">用户:</div>
				<div class="password-text" style="width:150px;">密码:</div>
				<div class="username-field">
					<input type="text" name="username"/><br>
					<div style="padding:15px 0 0 8px;"><s:actionerror /></div>
				</div>
				<div class="password-field" style="width:180px;">
					<input type="password" name="password"/>
				</div>
				</div>
				<div style="float:left;width:100px;">
				<s:submit name="submit" style="cursor:pointer; height:260px;" value="登陆" />
				</div>
			</s:form>
		</div>
		<div id="footer">
			Copyright &copy; 2014.宏立城集团版权所有.
		</div>
	  <script type="text/javascript" src='<s:url value="/js/common/jquery-1.6.2.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="jq/jquery.easyui.min.js"/>'></script>
      <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.all-3.5.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.core-3.5.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.exedit-3.5.min.js"/>'></script>
      <script type="text/javascript" src='<s:url value="js/archivalView/property.js"/>'></script>
      <script type="text/javascript" src='<s:url value="js/archivalView/archiveCommon.js"/>'></script>
		
	</body>
</html>