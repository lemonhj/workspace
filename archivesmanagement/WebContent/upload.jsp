<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri = "/struts-tags" prefix = "s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String fileId = session.getAttribute("fileId")+"";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>Upload</title>

<!--装载文件-->
<link href='<s:url value="/js/fileUpload/uploadify.css" />' rel="stylesheet">
<script type="text/javascript" src='<s:url value="/js/common/jquery-1.6.2.min.js"/>'></script>
<script type="text/javascript" src='<s:url value="/js/fileUpload/jquery.uploadify.min.js"/>'></script>
<script type="text/javascript" src='<s:url value="/js/fileUpload/swfobject.js"/>'></script>

<!--ready事件-->
<script type="text/javascript">
    	$(document).ready(function() {
    	    $("#uploadify").uploadify({
    	        //开启调试
    	        'debug' : false,
    	        //是否自动上传
    	        'auto':false,
    	        //超时时间
    	        'successTimeout':99999,
    	        //附带值
    	        'formData':{
    	            'fileId':'<%=fileId%>',
    	        },
    	        //flash
    	        'swf': "uploadify.swf",
    	        //不执行默认的onSelect事件
    	        'overrideEvents' : ['onDialogClose'],
    	        
    	        //文件选择后的容器ID
    	        'queueID':'fileQueue',
    	        //服务器端脚本使用的文件对象的名称 $_FILES个['upload']
    	        'fileObjName':'fileName',
    	        //上传处理程序
    	        'uploader':'<%=path%>/uploadFile.action',
    	        //浏览按钮的背景图片路径
    	       // 'buttonImage':'${pageContext.request.contextPath}/js/jquery.uploadify/uploadify-cancel.png',
    	        //浏览按钮的宽度
    	        'width':'100',
    	        //浏览按钮的高度
    	        'height':'32',
    	        //expressInstall.swf文件的路径。
    	        //'expressInstall':'expressInstall.swf',
    	        //在浏览窗口底部的文件类型下拉菜单中显示的文本
    	        //'fileTypeDesc':'支持的格式：',
    	        //允许上传的文件后缀
    	       // 'fileTypeExts':'*.jpg;*.jpge;*.gif;*.png',
    	        //上传文件的大小限制
    	        'fileSizeLimit':'10MB',
    	        'buttonText' : '选择文件',//浏览按钮
    	        //上传数量
    	        'queueSizeLimit' : 25,
    	        //每次更新上载的文件的进展
    	        'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
    	             //有时候上传进度什么想自己个性化控制，可以利用这个方法
    	             //使用方法见官方说明
    	        },
    	        //选择上传文件后调用
    	        'onSelect' : function(file) {
    	        },
    	        //返回一个错误，选择文件的时候触发
    	        'onSelectError':function(file, errorCode, errorMsg){
    	            switch(errorCode) {
    	                case -100:
    	                    alert("上传的文件数量已经超出系统限制的"+$('#uploadify').uploadify('settings','queueSizeLimit')+"个文件！");
    	                    break;
    	                case -110:
    	                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#uploadify').uploadify('settings','fileSizeLimit')+"大小！");
    	                    break;
    	                case -120:
    	                    alert("文件 ["+file.name+"] 大小异常！");
    	                    break;
    	                case -130:
    	                    alert("文件 ["+file.name+"] 类型不正确！");
    	                    break;
    	            }
    	        },
    	        //检测FLASH失败调用
    	        'onFallback':function(){
    	            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
    	        },
    	        //上传到服务器，服务器返回相应信息到data里
    	        'onUploadSuccess':function(file, data, response){
    	        	//$('#uploadify').uploadify('upload');
    	        	//alert(data);
    	        	refreshFileTab();
    	        }
    	    });
    	});
</script>
</head>

<body>
    <div id="fileQueue" style="width:300px"></div>
    <input type="file" name="uploadify" id="uploadify" multiple="true"/>
    <p>
        <a class="easyui-linkbutton" href="javascript:$('#uploadify').uploadify('upload','*')">开始上传</a>&nbsp;
        <a class="easyui-linkbutton" href="javascript:$('#uploadify').uploadify('cancel','*')">取消所有上传</a>
    </p>
</body>
</html>