/*********************************************************************************************
* 描述: Ajax支持，辅助函数
* 作者: weide
* 日期: 2010-07-10 09:30:19
* 邮箱: weide001@gmail.com
* all copyright reserved by weide
*********************************************************************************************/

/**
 * 创建ajax请求对象
 * @return Object
 */
function CreadAjax()
{
	var xmlHttp = null;
	
	try{
		//Firefox, Opera 8.0+, Safari
		xmlHttp=new XMLHttpRequest();
		//针对某些特定版本的mozillar浏览器的bug修正
        //if(xmlHttp.overrideMimeType) xmlHttp.overrideMimeType("text/xml"); //FF: "未组织好"错误
	}catch (e){
		try{
			//Internet Explorer
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e){
			try{
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}catch (e){
				alert("本页面包含AJAX，您的浏览器版本过低，无法正常处理，请升级您的浏览器后访问！");
			}
		}
	}
	
	return xmlHttp;
}

/**
 * 请求ajax从服务器获取数据
 * @param url			目标处理脚本
 * @param data			参数【格式：1.字符串"key1=value1&key2=value2 ..." 2.json对象{key1:value1,key2:value2 ...}】
 * @param callback		响应回叫函数|容器id值
 * @param cb_last_arg	回叫函数最后一个参数	【缺省：null】	【注：当callback为function时有意义】
 * @param method		请求方式				【缺省：'POST'】
 * @param asyn			是否同步请求			【缺省：true】
 * @param full_care		是否响应所有状态		【缺省：false】
 * @return bool
 */
function ajax(url,data,callback,cb_last_arg,method,asyn,full_care){
	function _ajax_responsed_callback(){
		if( typeof callback=='undefined' || !callback || callback==null )
			return;
		switch(xmlHttp.readyState){
		    case 0: case 1: case 2: case 3:
		    if (true === full_care && typeof callback == 'function') callback(xmlHttp.readyState, cb_last_arg);
		    break;
		case 4:
			if( typeof callback=='function' ) callback( xmlHttp.responseText, cb_last_arg );
			if( typeof callback=='string' ){
				if( document.getElementById(callback) ){
					document.getElementById(callback).innerHTML = xmlHttp.responseText;
				}else{
					callback = new Function(callback+"; return true;");
					callback();
				}
			}
			break;
		default:
			break;
		}
	}

	var xmlHttp = CreadAjax();
	if (typeof xmlHttp=='undefined' || !xmlHttp || xmlHttp==null ) return false;
	method = method || (data?"POST":"GET");
	method = (method.toUpperCase()=="GET") ? "GET" : "POST";
	asyn = (typeof(asyn)=='undefined') ? true : asyn;
	xmlHttp.onreadystatechange = _ajax_responsed_callback;
	if( navigator.userAgent.indexOf("Firefox")>0 && !asyn ) xmlHttp.onload = _ajax_responsed_callback;

	xmlHttp.open( method, url, asyn );

	if( method=="POST" ) xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	if( typeof data=='object' ){
		var arguments_string = "";
		for(var property in data){
			arguments_string += arguments_string=="" ? property+"="+data[property] : "&"+property+"="+data[property];
		}
		data = arguments_string; arguments_string = null;
	}else if(typeof data!='string'){
		data = null;
	}

	data = method.toUpperCase()=="POST" ?(data || "") : null;
    xmlHttp.send(data);
    return true;
}
