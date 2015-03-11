<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>系统权限设置</title>

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
		<!-- script  end-->

		
		<!-- 自定义js start -->
		<script type="text/javascript" src="<%=path%>/estate/business/sys_edit/users.mgmt.js"></script>
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
		        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-application_delete'" style="width:80px;" onclick="deluser()">删除</a>
	    	</div>
        </div>
        
        <div data-options="region:'center',title:'用户列表',iconCls:'icon-building_link',resizable:true,modal:true,closed:false,singleSelect:true">
             <table id="dg" style="height:353px;"></table>
        </div>
        
        <div id="userwin" class="easyui-dialog" title="添加用户" style="width:600px;height:400px;"  data-options="iconCls:'icon-save',resizable:false,closed:true,closable:false,modal:true,buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:saverole
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){$('#userwin').dialog('close');}
			}]">
             <form id="userform" method="post">
                 <table style="padding:10px;font-family:'Tahoma,Verdana,微软雅黑,新宋体'">
                    <tr>
                       <td style="text-align:right;">用户名：</td><td><input id="useridid" name="user.suId" type="hidden"/><input id="usersunameid" name="user.suUsername" type="text"  style="width:120px;"/></td>
                    </tr>
                    <tr>
                       <td style="text-align:right;">密&nbsp;&nbsp;码：</td><td><input id="userpasswordid" name="user.suPassword" type="password" style="width:120px;"/><button type="button" style="margin-left:10px;" onclick="initpassword()">初始化密码</button></td>
                    </tr>
                    <tr>
                       <td style="text-align:right;">昵&nbsp;&nbsp;称：</td><td><input id="usernickid" name="user.suNick" type="text" style="width:120px;"/></td>
                    </tr>
                 </table>
                 <table style="padding:20px;">
                  <tr>
                      <td valign="top">
                      <div style="padding-bottom:5px;">角色列表：</div>
                      <div style="padding:10px 0px; width:180px; border:1px solid #e1e1e1;min-height:100px;">
                         <ul  id="roletree" class="easyui-tree" data-options="animate:true,checkbox:true,onCheck:onchecktree,onClick:onClicktree"></ul>
                      </div>
                      </td>
                      <td>
                           <div style="padding:0 10px;"><a onclick="rightmove()" class="easyui-linkbutton" data-options="iconCls:'icon-rightarrow'" style="cursor:pointer;"></a></div>
                           <div style="padding:0 10px;margin-top:10px;"><a onclick="leftmove()" class="easyui-linkbutton" data-options="iconCls:'icon-leftarrow'" style="cursor:pointer;"></a></div>
                      </td>
                      <td  valign="top">
                       <div style="padding-bottom:5px;">已获取的角色：</div>
                      <div style="padding:10px 0px; width:180px; border:1px solid #e1e1e1;min-height:100px;">
                         <ul id="hasroletree" class="easyui-tree" data-options="animate:true,checkbox:true,onCheck:onchecktreeother,onClick:onClicktreeother"></ul>
                      </div>
                      </td>
                    </tr>
                 </table>
             </form>
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
