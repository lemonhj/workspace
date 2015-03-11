<%@page import="com.honglicheng.dev.sys.model.BaseSysUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<%
BaseSysUser s = (BaseSysUser)session.getAttribute("user");
String  userMenu = s.getMenuRight();
Integer suId = s.getSuId();
%>
<!DOCTYPE html>
<html>
	<head>
		<title>物业楼栋管理系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<!-- 引入全局jsp start-->
		<%@ include file="/common/global_jsp.jsp"%>
		<!-- 引入全局jsp end -->

		<script type="text/javascript">
			var userMenu = <%=userMenu%>;
		</script>

		<!-- 自定义的link start  -->
		<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/demo/demo.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/estate/css/main.css" />
		
		<!-- 自定义的link end -->

		<script type="text/javascript" src="<%=path %>/common/jquey/jsDisc/highcharts.js"></script>
		<script type="text/javascript" src="<%=path %>/common/jquey/jsDisc/highcharts-3d.js"></script>
		<script type="text/javascript"  src="<%=path %>/common/excel_js/config001.js"></script>
		<!-- 自定义script start-->
		<script type="text/javascript" src="<%=path %>/estate/jsp/main.js"></script>
		<!-- 自定义script end-->
		
		
		<style type="text/css">
			body,div,h1,tr,table{
				margin: 0;
				padding: 0;
			}
			td{
				padding: 0 5px;
				overflow:hidden;
			}
			td h1{
				font-size: 13px;
			}
		</style>
	</head>

	<body style="background-color:#E6EEF8;" class="easyui-layout"  scroll="no" onload="clockon()">
		<noscript>
			<div
				style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
				<img src="<%=path %>/estate/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
		
		
		<div region="north" split="false" border="false"
			style="overflow: hidden; padding-top:8px; height: 60px; background: url(<%=path %>/estate/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
			<span style="float: right; padding-right: 20px;" class="head">用户
			<s:property value="#session['user'].suUsername" />&nbsp;&nbsp;<a style="margin:0 10px; text-decoration:none; cursor: pointer;" onclick="editpassword()">修改密码</a><a style="text-decoration: none" href="javascript:void(0)" id="loginOut">安全退出</a>
			</span>
	
			<span style="padding-left: 10px; font-size: 16px; float: left;"><img
					src="<%=path %>/estate/images/CategorizeMenu.png" 
					align="absmiddle" /> 物业楼栋管理系统</span>
			<ul id="css3menu"
				style="padding: 0px; margin: 0px; list-type: none; float: left; margin-left: 40px;">
				<li>
					<a class="active" name="basic" href="javascript:;" title="基础数据">
					</a>
				</li>
				<!-- <li><a name="point" href="javascript:;" title="积分管理">基础资料管理</a></li>  -->

			</ul>
			
		</div>
		<div region="south" split="false" border="false" 
			style="height: 36px; background: #D2E0F2;">
			<div class="footer" style="font-weight:100;line-height:15px;">
					Copyright © 2014-2015 Homnicen Group. All Rights Reserved.  
<div> 技术支持：宏立城集团信息系统管理部</div>
			</div>
		</div>
		<div region="west" hide="true" border="false" split="false" title="导航菜单"
			style="width: 180px; border-bottom:1px #99BBE8 solid" id="west">
			<div id='wnav' class="easyui-accordion" border="false" >
				<!--  导航内容 -->

			</div>

		</div>
		<div id="mainPanle" region="center"
			style="background: #eee; padding-left:2px; overflow-y: hidden">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div title="欢迎使用" style="padding: 20px;" id="home">
					<table cellspacing="0" cellpadding="0" style="margin-left: 10px;float:left;">
						<tr>
							<td style="text-align: center;border-right: 1px solid black;border-bottom: 1px solid black;">
								<h1>公司业态户数比例</h1>
								<div id="tcontent"></div>
							</td>
							<td style="text-align: center;border-bottom: 1px solid black;padding-left: 1px;">
								<h1>房间入驻面积比例</h1>
								<div id="acontent"></div>
							</td>
						</tr>
						
						<tr>
							<td style="text-align: center;border-right: 1px solid black;padding-top: 20px;">
								<h1>公司人数规模比例</h1>
								<div id="ncontent" ></div>
							</td>
							<td style="text-align: center;padding:20px 0 0 1px;">
								<h1>房间自持、租赁比例</h1>
								<div id="scontent"></div>
							</td>
						</tr>
					</table>
					<!-- 水电费报表 -->
					<div id="utility" style="border:2px solid black;height:555px;width:430px;float:right;text-align: center;">
						<h1 style="font-size:16px;margin-top: 230px;">公摊水电报表正在建设中...</h1>
					</div>
				</div>
			</div>
		</div>
		
<div id="passwordedit" class="easyui-dialog" style="width:400px;height:200px;" title="修改密码" closed=true iconCls="icon-edit"> 
			   <form id="modifyPw" name="passwordform" method="post">
			      <table style="line-height:30px;">
			         <tr>
			            <td align="right">旧密码:</td><td><input id="suPassWord" type="password" name="user.suPassword"/></td>
			         </tr>
			         <tr>
			            <td align="right">新密码:</td><td><input id="newpw" type="password" name="newpw"/></td>
			         </tr>
			         <tr>
			            <td align="right">确认密码:</td><td><input id="confirmpw" type="password" name="confirmpw"/><input name="user.suId" type="hidden" value="<%=suId%>"/></td>
			         </tr>
			         
			           <tr>
			            <td align="right"><a class="easyui-linkbutton" onclick="modifyPw();">保存</a></td>
			            <td align="left"><a class="easyui-linkbutton" onclick="$('#passwordedit').dialog('close')">取消</a></td>
			         </tr>
			      </table>
			   </form>  
              </div> 


		<div id="mm" class="easyui-menu" style="width: 150px;">
			<div id="mm-tabupdate">
				刷新
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabclose">
				关闭
			</div>
			<div id="mm-tabcloseall">
				全部关闭
			</div>
			<div id="mm-tabcloseother">
				除此之外全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">
				当前页右侧全部关闭
			</div>
			<div id="mm-tabcloseleft">
				当前页左侧全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">
				退出
			</div>
		</div>



	</body>
</html>
