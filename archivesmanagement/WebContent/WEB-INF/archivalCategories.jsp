<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
    <title>
      档案管理系统
    </title>
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- bootstrap css -->
    <link href='<s:url value="/css/icomoon/style.css" />' rel="stylesheet">
  	<link href='<s:url value="/css/main.css"/>' rel="stylesheet"> <!-- Important. For Theming change primary-color variable in main.css  -->
	<link rel="stylesheet" href='<s:url value="/css/ztree/zTreeStyle/zTreeStyle.css"/>' type="text/css">
    <link href='<s:url value="/jq/themes/default/easyui.css"/>' rel="stylesheet">
 	<link href='<s:url value="/jq/themes/icon.css"/>' rel="stylesheet">
 	<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
  </head>
  <body class="easyui-layout">
   <div id="centershow" data-options="region:'center',border:false" style="background-color:#212121;">
     <header>
    <a href="#" class="logo" style="cursor:default;">
        <img src="img/logo.png" alt="logo" />
      </a>
      <a href="#" class="logo">
      </a>
       <s:a style="color:#FFFFFF; float:right;width:60px; cursor:pointer;" href="/archivesmanagement/naviAction!logout.action">退出</s:a>
      <a style="text-align:right; color:#FFFFFF;float:right;width:60px;">欢迎
      <s:property value="#session['user'].username" />&nbsp;&nbsp;</a>
    </header>
    <div class="container-fluid">
      <div class="dashboard-container">
        <div class="top-nav">
              <ul>
            <li>
              <s:a href="/archivesmanagement/naviAction!naviArchive.action" class="selected">
                <div class="fs1" aria-hidden="true" data-icon="&#xe020;"></div>
                档案库
              </s:a>
            </li>
            <li>
              <a href="#"  class="selected">
					<div class="fs1" aria-hidden="true" data-icon="&#xe08e;"></div>
                系统管理
				</a>
            </li>
          </ul>
          <div class="clearfix">
          </div>
        </div>
        <div class="sub-nav">
          <ul>
            <li><a href="#" class="heading">档案分类管理</a></li>

          </ul>  
        </div>
 
        
        <div id="contentshow" class="dashboard-wrapper">
        <table>
        <tr>
        <td style="vertical-align:top;">
        <div class="left-sidebar" style="width:400px; min-height:400px; margin-right:10px;">
			<div class="row-fluid">
              
              <div class="span12">
                <div  class="widget" style="margin:0px;">
                  <div class="widget-header">
                    <div class="title">
                      档案类型管理
                    </div>
                  </div>
                  <div id="archiveheight" class="widget-body">
                    <ul id="treeDemo" class="ztree" style="width:360px;"></ul>
                  </div>                
                </div>
              </div>
              </div>
            </div></td>
            <td style="vertical-align:top;">
         <div class="left-sidebar" style="width:400px; min-height:400px; margin:0px;">
         <div class="row-fluid">   
              <div class="span12">
                <div  class="widget" style="margin:0px;">
                  <div class="widget-header">
                    <div class="title">
                     档案柜管理
                    </div></div>
            <div id="arciveguiheight" class="widget-body">
                    
                   <ul id="treeManager" class="ztree" style="width:360px;"></ul>      
                  </div>
                  </div>
                  </div></div>
            </div>
            </td>
            </tr>
                   </table>
          </div>
        
        </div>
 
      <!--/.fluid-container-->
    </div>
    <!-- 新增档案管理开窗 -->
    <div id="xzda" style="display:none">
    <div id="danopenadd" class="easyui-dialog" style="width:500px;height:350px; padding:15px; font-size:12px;"   
        data-options="resizable:true,modal:true,closed:true,buttons:'#modulecreatetools'">
        <form id="addarchives" name="addopenname">
         <table cellpadding="5">
		            <tr style="height:35px;">
		                <td>类别名称：</td>
		                <td><input class="easyui-validatebox textbox" name="typename" data-options="" value=""></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>类别编号：</td>
		                <td><input class="easyui-validatebox textbox" name="code" data-options=""></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>上级类别：</td>
		                <td><input name="parentname"  class="easyui-validatebox textbox" readonly="readonly" style="padding:2px;"></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>创建时间：</td>
		                <td><input name="dateshow" class="easyui-validatebox textbox" readonly="readonly" style="padding:2px;"/></td>
		            </tr>
		        </table>
		        </form>
        <div id="modulecreatetools" style=" text-align:center;">
        <a onclick="addsave()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
        <a onclick="$('#danopenadd').dialog('close')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a> 
        </div>     
        </div>
	</div>
<!-- 新增存放位置管理开窗 -->
	<div id="xzwz" style="display:none">
    <div id="postionopenadd" class="easyui-dialog" style="width:500px;height:350px; padding:15px; font-size:12px;"   
        data-options="resizable:true,modal:true,closed:true,buttons:'#modulelocationcreatetools'">
        <form id="addlocation" name="addlocationopen">
         <table cellpadding="5">
		            <tr style="height:35px;">
		                <td>类别名称：</td>
		                <td><input class="easyui-validatebox textbox" name="loctypename" data-options="" value=""></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>类别编号：</td>
		                <td><input class="easyui-validatebox textbox" data-options=""></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>上级类别：</td>
		                <td><input name="locparentname" class="easyui-validatebox textbox" readonly="readonly" style="padding:2px;"></td>
		            </tr>
		            <tr style="height:35px;">
		                <td>创建时间：</td>
		                <td><input name="locdateshow" class="easyui-validatebox textbox" readonly="readonly" style=" padding:2px;"/></td>
		            </tr>
		        </table>
		        </form>
        <div id="modulelocationcreatetools" style=" text-align:center;">
        <a onclick="addsavegl()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
        <a onclick="$('#postionopenadd').dialog('close')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a> 
        </div>     
        </div>
    </div>
        <script type="text/javascript" src='<s:url value="/js/common/jquery-1.6.2.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.all-3.5.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.core-3.5.min.js"/>'></script>
		<script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.exedit-3.5.min.js"/>'></script>
	 	<script type="text/javascript" src='<s:url value="/js/easyui/jquery.easyui.min.js"/>'></script>
	 	<script type="text/javascript" src='<s:url value="jq/locale/easyui-lang-zh_CN.js"/>'></script>
	 	<script type="text/javascript" src='<s:url value="/js/ztreeshow.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="/js/ztreeshowgl.js"/>'></script>
	    <script type="text/javascript" src='<s:url value="js/archivalView/JsonUtil.js"/>'></script>
	    </div>
	    <div data-options="region:'south',border:false" style="height:50px;">
    <footer>
      <p style="height:20px;font-weight:bold;font-size:12px;line-height:18px;text-align:center;">Copyright &copy; 2014-2015 Homnicen Group. All Rights Reserved.&nbsp;&nbsp;宏立城集团版权所有
				&nbsp;&nbsp;技术支持：宏立城集团信息系统管理部<br>联系部门：宏立城集团档案中心</p>
    </footer>
    </div>
    
    </body>
    </html>