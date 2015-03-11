
/*********************************************************************************************
* 描述: 页面url信息
* 作者: weide
* 日期: 2010-6-30 10:16:33
* 邮箱: weide001@gmail.com
* all copyright reserved by weide
*********************************************************************************************/

/**
 * 获取url的路径名
 * @param url 		string	资源，页面地址
 * @param pathInfo 	bool	是否敏感PATH_INFO	【缺省: true】
 * @return 路径名称字符串
 * 备注：对于隐式PATH_INFO地址本函数是无能的
 */
function UrlPath(url,pathInfo){
	pathInfo = typeof(pathInfo)!='undefined' ? pathInfo : true;
	if( pathInfo && /^(.*)\.([ps]?htm[l]?|php[3-5]?|do|[aj]sp|xml|js|css)[\/\\].*$/i.test(url) ){
		url = url.substring(0,url.indexOf(RegExp.$2)+RegExp.$2.length);
	}
	if (url.indexOf('?')!=-1) url = url.substring(0,url.lastIndexOf('?'));
	if (url.indexOf('/')!=-1) url = url.substring(0,url.lastIndexOf('/')+1);
	return url;
}

/**
 * 获取url中的资源basename
 * @param url 		string	资源，页面地址
 * @param pathInfo 	bool	是否敏感PATH_INFO	【缺省: true】
 * @return 文件名称
 * 备注：对于隐式PATH_INFO地址本函数是无能的
 */
function UrlBase(url,pathInfo){
	pathInfo = typeof(pathInfo)!='undefined' ? pathInfo : true;
	if( pathInfo && /^(.*)\.([ps]?htm[l]?|php[3-5]?|do|[aj]sp|xml|js|css)[\/\\].*$/i.test(url) ){
		url = url.substring(0,url.indexOf(RegExp.$2)+RegExp.$2.length);
	}
	if (url.indexOf('?')!=-1) url = url.substring(0,url.lastIndexOf('?'));
	var basename = url.substr(url.lastIndexOf('/')+1,url.length);
	return basename;
}

/**
* 获取当前url
* @param is_full_url	bool 是否全地址
* @param is_inc_param	bool 是否包含url参数
* @return url
*/
function ThisUrl(is_full_url,is_inc_param)
{
 	var url = "";
 	if(is_full_url!=true){
 		url = document.URL.replace(/^\s*http[s]?:\/\/[^\/\\]+/i,'');
 	}else{
 		url = document.URL;
 	}

 	if(is_inc_param!=true) url = url.split("?")[0];
 	return url;
}

/**
 * 获取顶层窗口url
 * @param is_full_url	bool 是否全地址
 * @param is_inc_param	bool 是否包含url参数
 * @return url 
 */
function TopUrl(is_full_url,is_inc_param)
{
 	var url = "";
 	if(is_full_url!=true){
 		url = top.location.href.replace(/^\s*http[s]?:\/\/[^\/\\]+/i,'');
 	}else{
 		url = top.location.href;
 	}
 	
 	if(is_inc_param!=true) url = url.split("?")[0];
 	return url;
}

/**
 * 获取父窗口url
 * @param is_full_url	bool 是否全地址
 * @param is_inc_param	bool 是否包含url参数
 * @return url
 */
function ParentUrl(is_full_url,is_inc_param)
{
	var url = "";
 	if(is_full_url!=true){
 		url = parent.location.href.replace(/^\s*http[s]?:\/\/[^\/\\]+/i,'');
 	}else{
 		url = parent.location.href;
 	}
 	
 	if(is_inc_param!=true) url = url.split("?")[0];
 	return url;
}

/** 
 * 获取当前页面来自的域名
 * @return domain
 */
function ThisDomain(){
 	return location.hostname;
}

/**
 * 获取顶层页面来自的域名
 * @return domain
 */
function TopDomain(){
 	return top.location.hostname;
}

/**
 * 获取父窗口页面来自的域
 * @return domain
 */
function ParentDomain()
{
	return parent.location.hostname;
}

/**
 * 获取当前url中的参数
 * @return 以 array('key'=>'value') 形式字符串组成的数组
 */
function ThisUrlParam()
{
	var result = new Array();
	var url = ThisUrl(false,true);
	if(url.indexOf('?')<0) return result;
	var params = url.split("?");
 	params = params[1].split("&");
 	for( var i in params ){
 		var key = params[i].substr(0,params[i].indexOf('='));
		var val = params[i].substr(params[i].indexOf('=')+1);
		result[key] = val;
	}
	return result;
}
function UrlParam() { return ThisUrlParam(); }

/**
 * 获取顶层url中的参数
 * @return 以 array('key'=>'value') 形式字符串组成的数组
 */
function TopUrlParam()
{
	var result = new Array();
	var url = TopUrl(false,true);
 	if(url.indexOf('?')<0) return result;
	var params = url.split("?");
 	params = params[1].split("&");
 	for( var i in params ){
 		var key = params[i].substr(0,params[i].indexOf('='));
		var val = params[i].substr(params[i].indexOf('=')+1);
		result[key] = val;
	}
	return result;
}

/**
 * 获取父窗口url中的参数
 * @return 以 array('key'=>'value') 形式字符串组成的数组
 */
function ParentUrlParam()
{
	var result = new Array();
	var url = ParentUrl(false,true);
	if(url.indexOf('?')<0) return result;
	var params = url.split("?");
 	params = params[1].split("&");
 	for( var i in params ){
 		var key = params[i].substr(0,params[i].indexOf('='));
		var val = params[i].substr(params[i].indexOf('=')+1);
		result[key] = val;
	}
	return result;
}

/**
 * 格式化url，使之合法
 * @param url 页面，资源url地址
 * @return 合法的url
 */
function FmtUrl(url){
	 if(/^http(s)?:\/\//.test(url)){
		var http = url.substr(0,url.indexOf(':\/\/')+3);
		url = url.replace(/^http(s)?:\/\//,'');
		return url = http+url.replace(/\\+|\/+/g,'/',url);
	}else{
		return url = url.replace(/\\+|\/+/g,'/',url);
	}
}

/**
 * 获取JavaScript标签的src中传递的参数
 * @param script_id_or_src_filename	string	js标签id或者src值的文件名称部分【顺序：id, src】
 * @param args_attribute_name		string	js标签的这个属性的值也作为参数获取
 * @return 以 array('key'=>'value') 形式字符串组成的数组
 * @see 例子：
 * <script id="url-js" language="javascript" src="/com/url.js?name=weide&nick=wade" args="w=117&h=165"></script>
 */
function JsParam(script_id_or_src_filename, args_attribute_name){
	args_attribute_name = args_attribute_name || 'args';
	var paramsArr = new Array();
	var result = new Array();
	var script = document.getElementById(script_id_or_src_filename);
	if ( script && script.tagName.toLowerCase()=='script' ){
		if( script.src.indexOf('?')>=0 ){
			paramsArr = script.src.split('?')[1].split('&');
			var attribute = script.getAttribute(args_attribute_name);
			if ( attribute ) paramsArr = paramsArr.concat( attribute.split('&') );
		}
	}else if( script_id_or_src_filename!='' ){
		script_id_or_src_filename = script_id_or_src_filename.match(/([^\/\\]+\.\w+)(\\?[^=]+=.*(&[^=]+=.*)*)?$/i)[1];
		var pattern = new RegExp( eval('/^.*'+script_id_or_src_filename+'(\\?[^=]+=.*(&[^=]+=.*)*)?$/i') );
		var scripts = document.getElementsByTagName('script');
		for( var i=0; i<scripts.length; i++ ){
			if ( scripts[i].src.indexOf('?')>=0 && scripts[i].src.match(pattern) ){
				paramsArr = scripts[i].src.split('?')[1].split('&');
				var attribute = scripts[i].getAttribute(args_attribute_name);
				if ( attribute ) paramsArr = paramsArr.concat( attribute.split('&') );
			}
		}
	}
	for( var i in paramsArr ){
		var key = paramsArr[i].substr(0,paramsArr[i].indexOf('='));
		var val = paramsArr[i].substr(paramsArr[i].indexOf('=')+1);
		result[key] = val;
	}
	return result;
}

/**
 * 获取站点端口
 * @return int|string
 */
function UrlPort(){
	return window.location.port=="" ? 80 : window.location.port;
}

/**
 * 获取站点响应协议的协议类型
 * @return string
 */
function UrlProtocol(){
	return window.location.protocol;
}

/**
 * 获取url中的hash值
 * @return string
 */
function UrlHash(){
	return window.location.hash.substr(1,window.location.hash.length);
}

/**
 * 获取站点地址
 * @return string
 */
function UrlHost(){
	return window.location.host;
}