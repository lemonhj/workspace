<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<!DOCTYPE html>
<html>
  <head>
   <meta charset="utf-8">
    <title>档案管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- bootstrap css -->
    <link href='<s:url value="/css/icomoon/style.css" />' rel="stylesheet">
  	<link href='<s:url value="css/main.css"/>' rel="stylesheet"> <!-- Important. For Theming change primary-color variable in main.css  -->
  	<link rel="stylesheet" type="text/css" href='<s:url value="jq/themes/default/easyui.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="jq/themes/icon.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="/jq/demo/demo.css"/>'>
    <link rel="stylesheet" type="text/css" href='<s:url value="jq/themes/IconExtension.css"/>'>
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
      <a style="text-align:right; color:#FFFFFF;float:right;width:160px;">欢迎
      <s:property value="#session['user'].username" />&nbsp;&nbsp;</a>
    </header>
    
    
    
    <div  class="container-fluid">
      <div class="dashboard-container">
        <div class="top-nav">
          <ul>
            <li>
              <a href="/archivesmanagement/naviAction!naviArchive.action" class="selected">
                <div class="fs1" aria-hidden="true" data-icon="&#xe020;"></div>
                档案库
              </a>
            </li>
            <li>
              <a href="/archivesmanagement/naviAction!naviCategories.action">
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
          <li>
              <a href="/archivesmanagement/naviAction!naviArchive.action">
                档案管理
              </a>
            </li>
            <li><a href="#" class="heading">档案查询</a></li>
            
          </ul>
          
        </div>
        
        <div id="contentshow" class="dashboard-wrapper">
        <div class="left-sidebar" style="width:100%">
			<div class="row-fluid">
              
              <div class="span12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">
                      查询
                    </div>
                    <span class="tools">
                    <button class="btn btn-warning2" type="button"  onclick="$('#w').window('open');var val=$('#as_archive').form('validate');
                    	if(!val){
                    		return false;
                    	}">
                             高级搜索
                            </button>
                    </span>
                  </div>
                  <div class="widget-body">
                    
                    
                    
                    <div class="navbar navbar-static" id="navbar-example">
                      <div class="navbar-inner">
                        <div style="width: auto;" class="container">
                          <a href="javascript:void(0);" class="brand">
                            请选择搜索种类
                          </a>
                          <ul role="navigation" class="nav">
                            <li class="dropdown" style="margin-top:5px;">
                              <select id="searchType" name="searchType" style="width:80px;height:30px;">   
							    <option>档案名</option>     
							  </select>
                            </li>
                            
                        <div id="bd" class="navbar-form">
                          <div class="input-append">
                            <input style="width:300px;height:20px;" name="fileName" id="fileName" type="text"/>
                            <button class="btn btn-info" type="button" id="search" onclick="searchFiles()">
                            		搜索
                            </button>
                          </div>
                        </div>
                          </ul>
                        </div>
                      </div>
                    </div>
                    
                    
                    
                  </div>
                </div>
              </div>
              </div>
            </div>
            
        <div class="left-sidebar" style="width:100%;">
			<div class="row-fluid">
              
              <div class="span12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">
                      结果
                    </div>
                  </div>
                  <div class="widget-body" style="width:auto;">
					<table id="dg"></table>
                  </div>
                </div>
              </div>
              </div>
            </div>    
            
            
        </div>
      </div>
      <!--/.fluid-container-->
    </div>
    <div id="gjss" style="display:none">
    <div id="w" class="easyui-window" title="高级搜索" data-options="iconCls:'icon-search',closed:true" style="width:400px;height:257px;padding:3px;">
     
		    <div title="档案名" data-options="headerWidth:80" style="overflow:auto;padding:0 20px 0 20px;">   
		        <div data-options="region:'center',split:true" title="" style="">
		          <form id="as_files" method="post">
          			<table cellpadding="5">
          				<tr>
          					<td colspan="2"></td>
          				</tr>
	            		<tr>
			                <td>档&nbsp;&nbsp;案&nbsp;&nbsp;名</td>
			                <td><input class="easyui-validatebox textbox" name="as_fileName"></td>
	            		</tr>
	            		<tr>
			            	<td>归属分类</td>
			                <td>
			                	<input id="tree_f" class="easyui-combotree" name="as_archiveName_f"/>
			                </td>
		            	</tr>
	            		<tr>
			                <td>保存位置</td>
			                <td>
			                	<input id="tree_path_f" class="easyui-combotree" name="as_filePath_f"/>
			                </td>
		            	</tr>
		            	<tr>
			                <td>归档年度</td>
			                <td>
			                	<input id="tree_date_f" class="easyui-validatebox textbox" name="as_date_f"/>
			                </td>
		            	</tr>
			            <tr>
			            	<td colspan="2">
				            	<div style="padding:5px 0;">
							        <a href="#" id="fSearch" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="advancedSearch_f();" style="width:80px">搜索</a>
							        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" onclick="$('#w').window('close')">取消</a>
				    			</div>
			    			</td>
			            </tr>
	        		</table>
	        	  </form>
        		</div>
        
		        	
  
		    </div> 

    </div> 
    </div>
     <script type="text/javascript" src='<s:url value="/js/common/jquery-1.6.2.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="jq/jquery.easyui.min.js"/>'></script>
    <script type="text/javascript" src='<s:url value="jq/locale/easyui-lang-zh_CN.js"/>'></script>
    <script type="text/javascript" src='<s:url value="js/archivalView/archivalSearch.js"/>'></script>
    <script type="text/javascript" src='<s:url value="js/archivalView/property.js"/>'></script>
    <script type="text/javascript" src='<s:url value="js/archivalView/archiveCommon.js"/>'></script>
    <script src="js/jquery.scrollUp.js">
    </script>
    </div>
    <div data-options="region:'south',border:false" style="height:50px;">
	    <footer>
	      	<p style="height:20px;font-weight:bold;font-size:12px;line-height:18px;text-align:center;">Copyright &copy; 2014-2015 Homnicen Group. All Rights Reserved.&nbsp;&nbsp;宏立城集团版权所有
				&nbsp;&nbsp;技术支持：宏立城集团信息系统管理部<br>联系部门：宏立城集团档案中心</p>
	    </footer>
    </div>
    </body>
    </html>