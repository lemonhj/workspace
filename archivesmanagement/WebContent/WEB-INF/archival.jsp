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
	<link href='<s:url value="/css/main.css"/>' rel="stylesheet"> <!-- Important. For Theming change primary-color variable in main.css  -->
	<link href='<s:url value="/jq/themes/default/easyui.css"/>' rel="stylesheet" type="text/css" >
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/themes/icon.css"/>'>
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/demo/demo.css"/>'>
	<link rel="stylesheet" type="text/css" href='<s:url value="/jq/themes/IconExtension.css"/>'>
	<link rel="stylesheet" href='<s:url value="/css/ztree/zTreeStyle/zTreeStyle.css"/>' type="text/css">
  	<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		.width_name{width:80px;}
		.width_value{width:150px;font-weight:bold;}
		.width_spe{width:80px;font-weight:bold;}
	</style>
  </head>
  <body class="easyui-layout" style="padding:0px;">
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
    <div class="container-fluid">
      <div class="dashboard-container">
        <div class="top-nav">
          <ul>
            <li>
              <a href="#" class="selected">
                <div  aria-hidden="true" data-icon="&#xe020;" class="fs1"></div>
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
            <li><a href="#" class="heading">档案管理</a></li>
            <li>
              <s:a href="/archivesmanagement/naviAction!searchArchive.action">
                档案查询
              </s:a>
            </li>
          </ul>
      
        </div>
 
        
        <div id="contentshow" class="dashboard-wrapper">
			<div id="boxshow" class="invoice" style="padding:0px;">
			   
            <div class="row-fluid" id="asd" >
              
              <div class="span3"  style="overflow:hidden;">  
                   <div id="treeboxshow" style="border-right:1px solid #CCCCCC;overflow:auto;">
				        <ul id="archivalTree" class="ztree" ></ul>
				        </div>  
              </div>
              <div id="tablewidth" class="span8" >
              <div align="left">
              <h5>
               <i class="icon-box_picture">
                            </i>档案盒信息
              </h5>
              </div>
              <div id="archiveShow" style="display:none">
              <div style="height:100px;">
	              <table cellpadding="5">
		            <tr>
		           	    <!-- 
		                <td class="width_name">档案题名：</td>
		                <td class="width_value"><div id="archiveName"></div></td>
		                 -->
		                <td class="width_name">档案类别号：</td>
		                <td class="width_spe"><div id="naviCode"></div></td>
		                <!--
		                <td class="width_name">责&nbsp;&nbsp;任&nbsp;&nbsp;者：</td>
		                <td class="width_spe"><div id="personInCharge"></div></td>
		                -->
		                <td class="width_name">保存位置：</td>
		                <td class="width_value">
		                	<div id="archivesLocation"></div>
						</td>
		                <td class="width_name">归档年度：</td>
		                <td class="width_spe"><div id="fileYear" ></div></td>
		            </tr>
		            <tr>
		            	<!--
		                <td class="width_name">成文日期：</td>
		                <td class="width_value"><div id="writtenDate" ></div></td>
		                <td class="width_name">归档年度：</td>
		                <td class="width_spe"><div id="fileYear" ></div></td>
		                <td class="width_name">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</td>
		                <td class="width_spe">
			                 <div id="securityLevel"></div>
		                </td>
		                <td class="width_name">归档日期：</td>
		                <td class="width_value"><div id="fileDate"></div></td>
		               
		            </tr>
		            <tr>
		             	-->
		                <td class="width_name">盒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
		                <td class="width_spe"><div id="directoryId"></div></td>
		                <!--
		                <td class="width_name">存放方式：</td>
		                <td class="width_spe">
			                <div id="storeWay"></div>
		                </td>
		                <td class="width_name">已&nbsp;&nbsp;销&nbsp;&nbsp;毁：</td>
		                <td class="width_spe">
			                <div id="destoryed"></div>
		                </td>
		                <td class="width_name">档案状态：</td>
		                <td class="width_value">
			                <div id="archiveState"></div>
		                </td>
		            </tr>
		            <tr>
		            	-->
		                <td class="width_name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
		                <td class="width_value"><div id="remarks"></div></td>
		            	<td></td>
		            	<td class="width_spe">
		                <div id="archivesCategory"></div>
		                </td>
		            </tr>
		        </table>
		        </div>
              <div class="btn-group">
                 <button type="button" class="btn btn-info" onclick="showFilePage(true);">
                  <i class=" icon-white icon-plus">
                  </i>
                  新建档案
                </button>
                <button type="button" class="btn " onclick="modifyFile();">
                  <i class=" icon-white icon-quill">
                  </i>
                  修改档案
                </button>
                <button type="button" class="btn btn-info" onclick="openbelongArchive()">
                  <i class=" icon-white icon-stack">
                  </i>
                  档案换档
                </button>
                <button type="button" class="btn" onclick="delFiles();">
                  <i class=" icon-white icon-minus">
                  </i>
                  删除档案
                </button>
                <button type="button" class="btn btn-info" onclick="exportExcel();">
                  <i class=" icon-white icon-drawer-3">	
                  </i>
                  目录打印
                </button>
              </div>
  
              <a>文件份数</a>
              <a style="margin-left:10px;" id="fileTotle"></a> 
              <a style="margin-left:10px;">总页数</a>
              <a style="margin-left:10px;" id="pageTotle"></a>
              
             	<table id="fileTable" style="width:auto;"></table>       
            
            </div>           
            </div>
            </div>
          </div>
          
        </div>
      </div>
      <!--/.fluid-container-->
    </div>
    <div id="wj" style="display:none">
    <div id="w" class="easyui-dialog" title="档案信息" data-options="iconCls:'icon-save',closed:true,buttons:'#toolswenj'" style="width:950px;height:380px;padding:3px">
        <div class="easyui-tabs" id="fileTabs" data-options="tabWidth:100,tabHeight:30,onSelect:function(title,index){if(index==1){$('#toolswenj').css('display','none');refreshFileTab();}else{$('#toolswenj').css('display','');}}"  >
        <div title="著录" style="padding:10px">
        <form id="newFileForm" method="post">
           <table cellpadding="5">
	            <tr style="height:45px;">
	            	<td>档案类别号</td>
	                <td><input id="new_categoryCode" name="archiveFile.categoryCode" class="easyui-validatebox textbox"  data-options="required:true,missingMessage:'必须填写'"></td>
	                <td>归档号</td>
	                <td><input id="new_code" name="archiveFile.code" class="easyui-validatebox textbox"  data-options="required:true,missingMessage:'必须填写'"></td>
	                <td>文件题名</td>
	                <td><input id="new_fileName" name="archiveFile.fileName" class="easyui-validatebox textbox" data-options="required:true,validType:'maxLength[50]',missingMessage:'必须填写'"/></td>
	            </tr>
	            <tr style="height:45px;">
	                <td>责任者</td>
	                <td><input id="new_chargeInPerson" name="archiveFile.personInCharge"  class="easyui-validatebox textbox" data-options="validType:'maxLength[30]'"></input></td>
	                <td>文件编号</td>
	                <td><input id="new_fileCode" name="archiveFile.fileCode" class="easyui-validatebox textbox" data-options="validType:'maxLength[20]'"></td>
	                <td>档案状态</td>
		                <td>
		               		<input  id="new_fileState" name="archiveFile.archived" class="easyui-combobox" data-options="valueField: 'label',textField: 'value',editable:false" />
		                </td>
	            </tr>
	            <tr style="height:45px;">
	                <td>成文日期</td>
	                <td><input id="new_writtenDate" name="archiveFile.writtenDate" class="easyui-datebox" data-options="editable:false,required:true,missingMessage:'必须选择'"></td>
	                <td>密级</td>
	                	<td>
			               <input id="new_securityLevel" name="archiveFile.securityLevel" class="easyui-combobox" data-options="valueField: 'label',textField: 'value',editable:false" />	                
		                </td>
	                <td>归档日期</td>
	                <td><input id ="new_regTime" name="archiveFile.registrationTime" class="easyui-datebox textbox" data-options="editable:false"></td>
	            </tr>
	            <tr style="height:45px;">
	                <td>保管期限</td>
	                <td>
	                	<input  id="new_retentionP" name="archiveFile.retentionPeriod" class="easyui-combobox" data-options="editable:false,valueField: 'label',textField: 'value'" />
	                </td>
	                <td>存放方式</td>
	                <td>
	                	<input  id="new_storeWay" name="archiveFile.storeWay" class="easyui-combobox" data-options="valueField: 'label',textField: 'value',editable:false" />
		            </td>
	                <td>页数</td>
	                <td><input id="new_pages" name="archiveFile.pages" class="easyui-validatebox textbox" data-options="required:true,validType:'isNumber[0]',missingMessage:'必须填写'"/></td>
	            </tr>
	            <tr>
	                <td>备注</td>
	                <td><input id="new_remarks" name="archiveFile.remarks" class="easyui-validatebox textbox" ></td>
	            </tr>
	        </table>
	        	<input id="new_fileId" name="archiveFile.fileId" type="hidden">	
	        </form>
	        <div id="toolswenj"  style="text-align:center">
		        <div style="padding:5px 0;"></div>
		        <div onclick="saveFile();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">保存</div>
		        <div class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" onclick="cancelFilePage();">取消</div>
    		</div>  
        </div>
       

        <div title="电子文档" style="padding:5px,height:400px">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-page_go'" style="width:80px" onclick="fileUpload();">上传文件</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-page_go'" style="width:80px" onclick="downloadSF();">下载文件</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-page_delete'" style="width:80px" onclick="delUploadFile();">删除文件</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-drive',disabled:'true'" style="width:80px">扫描文件</a>
                <table cellpadding="5">
	            <tr>
	                <td></td>
	            </tr>
	            </table >
     		
		            <table id="storeFile" class="easyui-datagrid" data-options="method:'get',border:false,singleSelect:true,fitColumns:true,autoRowHeight:true">
	                <thead>
	                    <tr>
	                        <th data-options="field:'fileName'" width="280">文件名</th>
	                        <th data-options="field:'uploadTime',formatter:function(value,row,index){
							return JsonDateformat(value);}" width="100">创建时间</th>
	                    </tr>
	                </thead>
	            </table>
	            
        </div>
        

   
	
    </div>
    </div>
    </div>
    <div id="hd" style="display:none">
    <div id="belongArchive" class="easyui-dialog" title="文件换挡" data-options="iconCls:'icon-save',closed:true,buttons:'#toolsopenbelong'" style="width:500px;height:350px;padding:3px">
        <ul id="belongArchiveTree" class="ztree" style="height:auto"></ul>
        <div id="toolsopenbelong" style="text-align:center">
        <a href="#" onclick="savebelongtoarchive()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" onclick="$('#belongArchive').dialog('close')">取消</a>
    </div> </div>
    </div>  
	<div id="da" style="display:none">
    <div id="d" class="easyui-dialog" title="档案盒信息" data-options="iconCls:'icon-save',closed:true,buttons:'#toolsdangan'" style="width:860px;height:325px;padding:3px">
		        <form id="archivalidform" name="archivalname" method="post">
		        <table cellpadding="5">
		            <tr style="height:45px;">
		                <td>盒号</td>
		                <td><input id="dnid" type="hidden" name="archive.archiveId"/><input id="dnhhid" name="archive.directoryId" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写'" style="width:200px;"/></td>
		                <td>保存位置</td>
		                <td>
		                <input id="dnwzid" class="easyui-combotree" data-options="required:true,missingMessage:'必须填写'" style="width:204px;" />
		                </td>
		                <td>归档年度</td>
		                <td><input id="dngdndid" name="archive.archivedYear" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写'"  style="width:200px;"/></td>
		            </tr>
		        	<tr style="height:45px;">
		        		<td>保管期限</td>
		                <td>
		                   <input id="dnbgqxid" name="archive.retentionPeriod" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>
					     </td>
		                <td>备注</td>
		                <td><input id="dnremark" name="archive.remarks" class="easyui-validatebox textbox"  style="width:200px;"/></td>
		            </tr>
		        
		        <!-- 
		            <tr style="height:45px;">
		                <td>盒号</td>
		                <td><input id="dnhhid" name="archive.directoryId" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写',validType:'initId'" style="width:200px;"/></td>
		                
		                <td>档案题名</td>
		                <td><input id="dnid" type="hidden" name="archive.archiveId"/><input id="dnname" name="archive.archiveName" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写',validType:'maxLength[20]'"  style="width:200px;"/></td>
		                <td>责任者</td>
		                <td><input id="dnzrid" name="archive.personInCharge" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写'"  style="width:200px;"/></td>
		            </tr>
		            <tr style="height:45px;">
		                <td>保存位置</td>
		                <td>
		                <input id="dnwzid" class="easyui-combotree" data-options="required:true,missingMessage:'必须填写'" style="width:204px;" />
		                
		                </td>
		                <td>成文日期</td>
		                <td><input id="dncjrqid" name="archive.writtenDate" class="easyui-datebox" data-options="editable:false,required:true,missingMessage:'必须填写'"  style="width:204px;"/></td>
		                <td>归档年度</td>
		                <td><input id="dngdndid" name="archive.archivedYear" class="easyui-validatebox textbox" data-options="required:true,missingMessage:'必须填写'"  style="width:200px;"/></td>
		            </tr>
		             <tr style="height:45px;">
		                <td>密级</td>
		                <td>
			                <input id="dnmjid" name="archive.securityLevel" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>	                
		                </td>
		                <td>归档日期</td>
		                <td><input id="dngdrqid" name="archive.fileDate"  class="easyui-datebox textbox" data-options="editable:false,required:true,missingMessage:'必须填写'"  style="width:204px;"/></td>
		                <td>保管期限</td>
		                <td>
		                   <input id="dnbgqxid" name="archive.retentionPeriod" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>
					     </td>
		            </tr>
		             <tr style="height:45px;">
		                
		                <td>存放方式</td>
		                <td>
			                <input id="dncffsid" name="archive.storeWay" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>
		                </td>
		                <td>已销毁</td>
		                <td>
			                <input id="dnxhid" name="archive.destoryed" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>
		                </td>
		                <td>档案状态</td>
		                <td>
			                <input id="dnztid" name="archive.archiveState" class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'必须填写',valueField: 'label',textField: 'value'" style="width:204px;"/>
		                </td>
		            </tr>
					 <tr style="height:45px;">
		                <td>备注</td>
		                <td><input id="dnremark" name="archive.remarks" class="easyui-validatebox textbox"  style="width:200px;"/></td>
		            </tr>
		             -->
		        </table>
		        </form>

		</div>
	</div> 
        <div id="toolsdangan" style="text-align:center">
        <a href="#" onclick="saveOrupdateArchive()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px" onclick="$('#d').dialog('close')">取消</a>
         </div>
         
 	  <script type="text/javascript" src='<s:url value="/js/common/jquery-1.6.2.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="jq/jquery.easyui.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="jq/locale/easyui-lang-zh_CN.js"/>'></script>
      <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.all-3.5.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.core-3.5.min.js"/>'></script>
	  <script type="text/javascript" src='<s:url value="/js/common/jquery.ztree.exedit-3.5.min.js"/>'></script>
      <script type="text/javascript" src='<s:url value="js/archivalView/property.js"/>'></script>
      <script type="text/javascript" src='<s:url value="js/archivalView/archiveCommon.js"/>'></script>
      <script type="text/javascript" src='<s:url value="js/archivalView/archivalMgr.js"/>'></script>
      <script src="js/jquery.scrollUp.js"></script>
    </div>
    
   <div id="win" class="easyui-window" title="多文件上传" style="width:600px;height:400px"
        data-options="iconCls:'icon-save',modal:true,closed:true,cache:false">
   </div>
    
    <div data-options="region:'south',border:false" style="height:50px;">
    <footer >
      <p style="height:20px;font-weight:bold;font-size:12px;line-height:18px;text-align:center;">Copyright &copy; 2014-2015 Homnicen Group. All Rights Reserved.&nbsp;&nbsp;宏立城集团版权所有
				&nbsp;&nbsp;技术支持：宏立城集团信息系统管理部<br>联系部门：宏立城集团档案中心</p>
    </footer>
    </div>
    </body>
    </html>