<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'floor_ytbb.jsp' starting page</title>
    
    
    <!-- link引入  start -->
<link rel="stylesheet" type="text/css"  href="<%=path %>/estate/css/default.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" 	href="<%=path %>/common/jquey/jq/themes/icon.css" />
<link rel="stylesheet" type="text/css"  href="<%=path %>/common/jquey/jq/themes/IconExtension.css"/>

<!-- link引入  End -->

<!-- script引入 start  -->
<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/jquey/jq/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/jquey/jsDisc/highcharts.js"></script>

<script type="text/javascript"  src="<%=path %>/common/excel_js/hlc.estate.v2.0.js"></script>
<script type="text/javascript"  src="<%=path %>/common/jquey/jsDisc/highcharts.js"></script>
<script type="text/javascript"  src="<%=path %>/common/jquey/jsDisc/highcharts-3d.js"></script>
<script type="text/javascript"  src="<%=path %>/common/excel_js/config001.js"></script>
<script type="text/javascript"  src="<%=path %>/common/excel_js/json001.js"></script>
 <script type="text/javascript"  src="<%=path %>/common/flooryb.js"></script>

<!-- script引入 End  -->
  <style type="text/css">
  	
  </style>
  </head>
  
  <body  class="easyui-layout">
      <div data-options="region:'north',border:false" style="height:60px;padding:10px">
        <div style="padding:5px 0;">
        <a onclick="checkQuery()" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px;">查询</a>
        <a onclick="createExcel()" id="excelImports" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-building_go',disabled:true" style="width:80px;">导出Excel</a>
       </div>
        </div>
        
        <div id="westshowid"  data-options="region:'west',split:true,border:true" style="width:220px;">
           <div id="leftcontorl"  class="easyui-layout" data-options="fit:true,border:true">   
            <div style="height:300px;" data-options="region:'north',maxHeight:300,minHeight:50,border:false,split:true,collapsible:false,title:'楼栋列表'">
                      <ul id="floorlist" class="easyui-tree" ></ul>
               </div>    
            <div style="border-top:3px solid #95B8E7" data-options="region:'center',minHeight:50,split:true,border:false">
                    <div id="companel" class="easyui-panel" title="公司列表"     
        style="padding:8px;" data-options="closable:false,collapsible:false,border:false,fit:true,tools:[{
			iconCls: 'icon-add',
			handler: companyadd
		},'-',{
			iconCls: 'icon-edit',
			handler: companyedit
		}]">   

                    <form>
         	         <input class="easyui-searchbox" data-options="prompt:'请输入公司名称',searcher:doSearch" style="width:173px"/>
                     </form>
                     <table id="companylist" style="height:auto"></table> </div>
                </div>   
        </div>
        </div>
        
        
        <div  data-options="region:'center',border:false">
        <!--公司开窗增添  -->
          <div id="companycz" class="easyui-dialog" title="添加公司" style="width:800px;height:450px; padding:10px;"   
        data-options="iconCls:'icon-save',resizable:false,closed:true,closable:false,modal:true,buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:savacompany
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:cancel_dialog
			}]">   
               <form id="companyform" name="companyformname" method="post">
               <table>
               <tr><td>
                <div style="padding:6px;  background-color:#EBF2FF;color:#0E2D5F;border:1px solid #95B8E7; border-bottom:0px;" >公司信息</div>
                <div style="padding:10px; border:1px solid #95B8E7;">
                 <table>
                     <tr style="height:30px;">
                       <td>单位名称:</td><td><input name='tcorporation.corpId' type='hidden'  value=''/>
                       <input id="buildidnow"  name="tcorporation.corpBuilding" type='hidden' value=''/>
                       <input style='width:120px;' name='tcorporation.corpName' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                       <tr style="height:30px;">
                       <td>单位别称:</td><td><input style='width:120px;' name='tcorporation.corpAlias' type='text' value=''/></td>
                       </tr>
                       <tr style="height:30px;">
                       <td>单位全称:</td><td><input style='width:120px;' name='tcorporation.corpFullname' type='text' value=''/></td>
                        </tr>
                       <tr style="height:30px;">
                       <td>行业分类:</td><td><input style='width:120px;' id="typeid" name='tcorporation.corpIndustry' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                       <tr style="height:30px;">
                       <td>人数规模:</td><td><input style='width:120px;' id="sizesid" name='tcorporation.corpSizes' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                       </table>
                       </div>
                        <div style="padding:6px; margin-top:5px; background-color:#EBF2FF;color:#0E2D5F;border:1px solid #95B8E7; border-bottom:0px;" >房屋信息</div>
                       <div style="padding:10px; border:1px solid #95B8E7;">
                       <table>
                       <tr style="height:30px;">
                       <td>租户类型:</td><td><input style='width:120px;' id="stateid" name='tcorporation.corpState' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                       <tr style="height:30px;">
                       <td>楼层:</td><td><input style='width:120px;' id="floorid" name='floorinfo' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                          <tr style="height:30px;">
                       <td>租户姓名:</td><td><input style='width:120px;' name='tcorporation.corpcontacts' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                       <tr style="height:30px;">
                       <td>联系电话:</td><td><input style='width:120px;' name='tcorporation.corpTel' type='text' value=''/><font color=red>*</font></td>
                       </tr>
                      
                    <!--   <tr style="height:30px;">
                       <td></td><td><input name='roomradio' type='radio' value='1'/>未装修<input name='roomradio' type='radio' value='2'/>装修<input name='roomradio' type='radio' value='3'/>入驻<font color=red>*</font></td></td>
                     </tr>-->
                 </table>
                 </div>
                 </td>
                 <td valign="top">
                 	<table>
                 	  <tr>
                       <td valign="top">房间:</td><td>
                       <div style="width:120px; height:270px; overflow-y:auto; border:1px solid #e1e1e1">
                       <div id="roomsidnow" style="width:120px; height:270px; position::relative; background-color:#e9e9e9; color:#c2c2c2; line-height:270px;">(请选择楼层加载房间)</div><ul id="roomtree" class="easyui-tree" data-options="checkbox:true,cascadeCheck:false,onClick:onclcikroomtree"></ul>
                       </div></td>
                       <td>
                           <div style="padding:10px;"><a  class="easyui-linkbutton" data-options="iconCls:'icon-rightarrow'" style="font-size:18px;cursor:pointer" onclick="addselectrooms()"></a></div> 
                            <div style="padding:10px;"><a class="easyui-linkbutton" data-options="iconCls:'icon-leftarrow'" style="font-size:18px;cursor:pointer" onclick="delselectrooms()"></a></div>    
                       </td>
                       <td valign="top">所选房间:</td>
                       <td>
                       <div style="width:120px; height:270px;  padding:5px; overflow-y:auto;border:1px solid #95B8E7;">
                       <div id="selectroomsidnow" style="width:120px; height:270px; position::relative; background-color:#ffffff;"></div><ul id="selectroomtree" class="easyui-tree"></ul>
                       </div>
                       </td>
                       </tr>
                 	    
                 	</table>
                 </td>
                 </tr>
                 </table>
                 
               </form>   
              </div>
               
        	 <div id="imgtabs" class="easyui-tabs" data-options="fit:true" style=""> 
        	 <div title="业态分布图" style="padding-left:10px;">     
        	 <table width="100%">
        	 <!-- 业态百分比 -->
        	 <tr>
        	 <td style="vertical-align:top;">
        	 <div style="padding-top:20px;position:relative">
        	 	 <div style="z-index:2;position :absolute;margin: 5px 0 0 180px;">
		        	 <input class="scaleType" name="scaleType" value="户数" type="radio"/>户数
		        	 <input class="scaleType" name="scaleType" value="面积" type="radio" checked="checked"/>面积
	        	 </div>
	         	 <div style="z-index:0;">
	             	<table id="floordata"></table>
	             </div>
	             <!--<div style="width:310px;">注：其他类因未取得公司营业执照，包含餐饮、会务服务、家政</div>-->
             </div>
             </td>
             <td>
             <div style=" width:100%;padding-bottom:10px;">
             <div id="container" style="width: 600px; height: 400px;"></div>
             </div>
             </td>
            </tr> 
            <tr><td colspan="2"><hr width="100%" color="#8DB2E3" size="1"></td></tr>
            <!-- 入住面积百分比 -->
            <tr>
        	 <td style="vertical-align:top">
             <div style="padding-top:20px;">
             <table id="areadata"></table>
             <div style="width:310px;">
             	注:<br>
             	装修面积：正在装修的房间的面积<br>
				入驻面积：已入驻办公的房间的面积<br>
				空置面积：已售出但未有公司签约的房间的面积<br>
				待售面积：尚未售出的房间的面积
			 </div>
             </div>
             </td>
             <td>
             <div style=" width:100%;padding-bottom:10px;">
             <div id="areacontent" style="width: 600px; height: 400px;"></div>
             </div>
             </td>
            </tr> 
            <tr><td colspan="2"><hr width="100%" color="#8DB2E3" size="1"></td></tr>
            <!-- 人数规模百分比 number of people-->
            <tr>
        	 <td style="vertical-align:top">
             <div style="padding-top:20px;">
             <table id="nopdata"></table>
             </div>
             </td>
             <td>
             <div style=" width:100%;padding-bottom:10px;">
             <div id="nopcontent" style="width: 600px; height: 400px;"></div>
             </div>
             </td>
            </tr>
            <tr><td colspan="2"><hr width="100%" color="#8DB2E3" size="1"></td></tr>
            <!-- 自持、租赁百分比 self and Lease-->
            <tr>
        	 <td style="vertical-align:top">
             <div style="padding-top:20px;">
             <table id="saldata"></table>
             </div>
             </td>
             <td>
             <div style=" width:100%;padding-bottom:10px;">
             <div id="salcontent" style="width: 600px; height: 400px;"></div>
             </div>
             </td>
            </tr>
            <tr><td colspan="2"><hr width="100%" color="#8DB2E3" size="1"></td></tr>
            </table>
            </div>
            
             <div title="楼栋业主信息">
                 <div id="testsg_podt" style="padding:75px 15px;"></div>
                 <div id="bncsg_podt" style="padding:55px 15px;"></div>
             </div>
            </div>
        </div>
     <div id="configcreate" class="easyui-dialog" title="公司列表" style="width:800px;height:300px; padding:20px;"   
        data-options="iconCls:'icon-add',resizable:true,modal:true,closed:true,buttons:'#toolsbtn'">
            <table id="corplist_id"></table>
        <div id="toolsbtn" style=" text-align:center;">
        <a onclick="clickOK()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">确定</a>
        </div>     
	</div>
    <div id="winQuery" class="easyui-window" data-options="iconCls:'icon-edit',closed:true" style="width:400px;height:300px;">
    	<form action="" id="queryData">
	    	<table style="padding:15px;font-size: 14px;">
	    		<tr style="height:35px;">
	    			<td>入驻单位:</td>
	    			<td><input id="corpName" class="easyui-validatebox textbox" name="corpName"/></td>
	    		</tr>
	    		<tr style="height:35px;">
	    			<td>联系人:</td>
	    			<td><input class="easyui-validatebox textbox" name="corpContacts"/></td>
	    		</tr>
	    		<tr style="height:35px;">
	    			<td>业主名:</td>
	    			<td><input class="easyui-validatebox textbox" name="roomOwner"/></td>
	    		</tr>
	    		<tr style="height:35px;">
	    			<td></td>
	     			<td><input id="saveBtn" type="button" value="查询" onClick="queryBtn()"/>
	     			<input type="button" value="取消" onClick="$('#winQuery').window('close')"/>
	    			</td>
	    		</tr>
	    	</table>
    	</form>
    </div>
    <div id="winShow" class="easyui-window" data-options="iconCls:'icon-ok',closed:true" style="width:1000px;height:430px;">
		<table id="dg3" title="查询结果列表" class="easyui-treegrid" style="width:980px;height:380px"
			data-options="
				rownumbers: true,
				idField: 'corpId',
				treeField: 'corpName'">
			<thead frozen="true">
				<tr>
					<th field='corpName'>入驻单位</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th field='corpContacts'>联系人</th>
					<th field='corpTel'>联系电话</th>
					<th field='corpSizes1'>公司规模</th>
					<th field='induName'>行业分类</th>
					<th field='builName'>所在楼栋</th>
					<th field='flooNo'>所在楼层</th>
					<th field='roomNo'>所选房间</th>
					<th field='roomArea'>面积(㎡)</th>
					<th field='corpState1'>房屋状态</th>
					<th field='roomState1'>入驻状态</th>
					<th field='roomOwner'>业主姓名</th>
					<th field='roomOwnerTel'>业主电话</th>
					
				</tr>
			</thead>
		</table>
    </div>
    
    <!-- 查看开窗 -->
    <div id="lookdiv" class="easyui-dialog" style="font-size:14px; padding-top:15px;" data-options="iconCls:'icon-save',closed:true,modal:true,buttons:'#toolsbtnlook'">   
      <div id="toolsbtnlook" style=" text-align:center;">
        <a onclick="$('#lookdiv').dialog('close')" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">确定</a>
        </div>  
    </div>
    
    <!-- 引入公司 -->
    <!--菜单  -->
     

  </body>
 
</html>
