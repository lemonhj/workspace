<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    
    <title>My JSP 'floor_ytbb.jsp' starting page</title>
    
    
    <!-- link引入  start -->
<link rel="stylesheet" type="text/css"  href="<%=path %>/estate/css/default.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=path %>/common/jquey/js/themes/icon.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/IconExtension.css"/>

<!-- link引入  End -->

<!-- script引入 start  -->
<script type="text/javascript" src="<%=path %>/common/jquey/jsDisc/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery.easyui.min.js"></script>

<script type="text/javascript"  src="<%=path %>/common/excel_js/JsonUtil.js"></script>
<script type="text/javascript"  src="<%=path %>/common/excel_js/hlc.estate.v2.0.js"></script>
<script type="text/javascript"  src="<%=path %>/common/excel_js/config001.js"></script>
<script type="text/javascript"  src="<%=path %>/common/excel_js/json001.js"></script>
<!-- script引入 End  -->
		
		  <style scoped="scoped">
        .tt-inner{
            display:inline-block;
            line-height:12px;
            padding-top:5px;
        }
        .tt-inner img{
            border:0;
        }
    </style>	

	

  </head>
  
  <body class="easyui-layout">
      <div data-options="region:'north',border:false" style="height:60px;padding:10px">
        <div style="padding:5px 0;">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-building_edit'" style="width:80px" onclick="$('#w').window('open')">修改</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-building_go'" style="width:80px">导出</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="width:80px">保存</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:80px">取消</a>
        
    </div>
        </div>
        
        <div  data-options="region:'west',split:true" title="" style="width:290px;">
        <div class="easyui-tabs" data-options="border:false" style="width:283px;">
        
        <div title="楼栋列表" style="padding:10px">
            <ul id="floorlist" class="easyui-tree" ></ul>
        </div>
        

    </div>
        
        </div>
        <div style="padding:5px;" data-options="region:'center',title:'楼栋业主信息',iconCls:'icon-building_link'">
        	<div id="testsg_podt"></div>
        </div>
    <div id="configcreate" class="easyui-dialog" title="公司列表" style="width:700px;height:300px; padding:20px;"   
        data-options="iconCls:'icon-add',resizable:true,modal:true,closed:true,buttons:'#toolsbtn'">
            <table id="corplist_id"></table>
        <div id="toolsbtn" style=" text-align:center;">
        <a onclick="clickOK()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">确定</a>
        </div>     
</div>     
  <script type="text/javascript"  src="<%=path %>/common/flooryzinfo.js"></script>
  </body>
</html>
