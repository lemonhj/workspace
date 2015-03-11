/* *********************************************************************
 *   @name:	JsonUtil
 *   @desc:	Json工具类
 * @author:	weide
 *   @date:	2014-04-11 11:59:41
 *   @memo: 
 *@version: 2.0
	1.0		2014-04-11 11:59:41
			(1)提供Json对象转换为格式化字符串接口 convertToString(jsonObj, line, tab, level);
			范例：
				//文本输入换行和制表符
				JsonUti.n = "\n";
        JsonUti.t = "\t";
        var jstr = JsonUti.convertToString(json);
        //网页代码换行和制表符
        JsonUti.n = "<br />";
        JsonUti.t = "&nbsp;&nbsp;&nbsp;";
        var html = JsonUti.convertToString(json);
  2.0		2014-09-27 11:16:01
  		(2)支持DOM对象的转换，但是只进行第一层属性输出，深度根高的不进行详细输出。
 ********************************************************************* */
var JsonUtil = {

    //定义换行符
    n: "\n",
    //定义制表符
    t: "\t",

    /*
     *  @name:	convertToString
     *@access:	public
     *  @desc:	转换String
     * @param:	jsonObj		Json		对象
     * @param:	line			string	换行字符串
     * @param:	tab				string	制表字符串
     * @param:	level			int			制表字符串
     *@return:	string
     */
    convertToString: function(jsonObj, line, tab, level) {
    		if( line!=null && typeof line!="undefined" && line.constructor == String && line!="" ) JsonUtil.n = line;
    		if( tab!=null &&  typeof tab!="undefined" &&  tab.constructor == String &&  tab!=""  ) JsonUtil.t = tab;

				var startLevel = 1;
				if( level!=null &&  typeof level!="undefined" &&  level.constructor == Number &&  level>0  ) startLevel = level;
        return JsonUtil.__writeObj(jsonObj, startLevel);
    },

    /*
     *  @name:	__writeObj
     *@access:	private
     *  @desc:	写对象
     * @param:	obj				Json	对象
     * @param:	level			int		层次（基数为1）
     * @param:	isInArray	bool	此对象是否在一个集合内
     *@return:	string
     */
		__writeObj: function(obj, level, isInArray) {
        //如果为空，直接输出null
        if (obj == null) {
            return "null";
        }
        //为普通类型，直接输出值
        //针对类型：String Date Boolean Function Number RegExp Object+* (Math Event)
        if (obj.constructor != Object && obj.constructor != Array) {
            var v = obj.toString();
            var tab = isInArray ? JsonUtil.__repeatStr(JsonUtil.t, level - 1) : "";
            if (obj.constructor == String || obj.constructor == Date) {
                //时间格式化只是单纯输出字符串，而不是Date对象
                return tab + ("\"" + v + "\"");
            }else if (obj.constructor == Boolean) {
                return tab + v.toLowerCase();
            }else if (/^\[object html.*element\]$/ig.test(obj.constructor)) {
            		return tab + "[HTML-element]" + JsonUtil.n + JsonUtil.__writeDom(obj, level);
            }else if (/^\[object .*\]$/ig.test(obj.constructor)) {
            		return tab + "[Dom-object]" 	+ JsonUtil.n + JsonUtil.__writeDom(obj, level);
            }else {
                return tab + (v);
            }
        }
        //写Json对象，缓存字符串
        var currentObjStrings = [];
        //针对类型：Object Array
        //遍历属性
        for (var name in obj) {
            var temp = [];
            //格式化Tab
            var paddingTab = JsonUtil.__repeatStr(JsonUtil.t, level);
            temp.push(paddingTab);
            //写出属性名
            temp.push(name + " : ");

            var val = obj[name];
            if (val == null) {
                temp.push("null");
            }else {
                var c = val.constructor;

                if (c == Array) { //如果为集合，循环内部对象
                    temp.push(JsonUtil.n + paddingTab + "[" + JsonUtil.n);
                    var levelUp = level + 2;    //层级+2

                    var tempArrValue = [];      //集合元素相关字符串缓存片段
                    for (var i = 0; i < val.length; i++) {
                        //递归写对象                         
                        tempArrValue.push(JsonUtil.__writeObj(val[i], levelUp, true));
                    }

                    temp.push(tempArrValue.join("," + JsonUtil.n));
                    temp.push(JsonUtil.n + paddingTab + "]");
                }else if (c == Object) {
                    //递归写对象
                    temp.push(JsonUtil.__writeObj(val, level + 1));
                }else if (c == Function) {
                    temp.push("[Function]->" + val);
                    temp.push(JsonUtil.n + paddingTab);
                }else if (/^\[object html.*element\]$/ig.test(c)) {
                    temp.push("[HTML-element]");
                    temp.push(JsonUtil.__writeDom(val, level + 1));
                }else if (/^\[object .*\]$/ig.test(c)) {
                    temp.push("[Dom-object]");
                    temp.push(JsonUtil.__writeDom(val, level + 1));
                }else {
                		temp.push(JsonUtil.__writeObj(val, level + 1));
                }
            }
            //加入当前对象“属性”字符串
            currentObjStrings.push(temp.join(""));
        }
        return (level > 1 && !isInArray ? JsonUtil.n : "")                  		//如果Json对象是内部，就要换行格式化
            + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "{" + JsonUtil.n		//加层次Tab格式化
            + currentObjStrings.join("," + JsonUtil.n)                       		//串联所有属性值
            + JsonUtil.n + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "}";   //封闭对象
    },

    /*
     *  @name:	__writeDom
     *@access:	private
     *  @desc:	写DOM
     * @param:	obj				DOM	对象
     * @param:	level			int		层次（基数为1）
     * @param:	isInArray	bool	此对象是否在一个集合内
     *@return:	string
     */
		__writeDom: function(dom, level, isInArray) {
				if (!/^\[object .*\]$/ig.test(dom.constructor)) {
						return JsonUtil.__writeObj(dom, level, isInArray);
				}
				var paddingTab = JsonUtil.__repeatStr(JsonUtil.t, level);//写Json对象，缓存字符串
        var currentObjStrings = [];
				for (var name in dom) {
						var temp = [];
						var val = dom[name];
						temp.push(paddingTab);
            //写出属性名
            temp.push(name + " : ");
            if (null==val) {
            		temp.push("null");
						} else if (/^\[object html.*element\]$/ig.test(val.constructor)) {
								var flag = "undefined"!=typeof val.outerHTML && null!=val.outerHTML ? "->outerHTML" : ("undefined"!=typeof val.innerHTML&&null!=val.innerHTML?"->innerHTML":"");
        				var html = "->outerHTML"==flag ? val.outerHTML : ("->innerHTML"==flag?val.innerHTML:val.toString());
            		temp.push("[HTML-element]" + flag + (JsonUtil.n+paddingTab) + "{\"" + JsonUtil.n + html + (JsonUtil.n+paddingTab) + "\"}");
            }  else if (/^\[object .*\]$/ig.test(val.constructor)) {
								temp.push("[DOM-object]" + (JsonUtil.n+paddingTab) + "{" + JsonUtil.n + val.toString() + (JsonUtil.n+paddingTab) + "}");
            } else {
            		temp.push(JsonUtil.__writeObj(val, level + 1));
            }
            //加入当前对象“属性”字符串
            currentObjStrings.push(temp.join(""));
				}
        return (level > 1 && !isInArray ? JsonUtil.n : "")                  		//如果Json对象是内部，就要换行格式化
            + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "{" + JsonUtil.n		//加层次Tab格式化
            + currentObjStrings.join("," + JsonUtil.n)                       		//串联所有属性值
            + JsonUtil.n + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "}";   //封闭对象
		},

    /*
     *  @name:	__isArray
     *@access:	private
     *  @desc:	判断对象是否为数组
     * @param:	obj		Json	对象
     *@return:	bool
     */
    __isArray: function(obj) {
        if (obj) {
            return obj.constructor == Array;
        }
        return false;
    },

    /*
     *  @name:	__repeatStr
     *@access:	private
     *  @desc:	获取重复字符串
     * @param:	str		string	子串
     * @param:	times	int			重复次数
     *@return:	string
     */
    __repeatStr: function(str, times) {
        var newStr = [];
        if (times > 0) {
            for (var i = 0; i < times; i++) {
                newStr.push(str);
            }
        }
        return newStr.join("");
    }
};