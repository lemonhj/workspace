/* *********************************************************************
 *   @name:	JsonUtil
 *   @desc:	Json������
 * @author:	weide
 *   @date:	2014-04-11 11:59:41
 *   @memo: 
 *@version: 2.0
	1.0		2014-04-11 11:59:41
			(1)�ṩJson����ת��Ϊ��ʽ���ַ����ӿ� convertToString(jsonObj, line, tab, level);
			������
				//�ı����뻻�к��Ʊ��
				JsonUti.n = "\n";
        JsonUti.t = "\t";
        var jstr = JsonUti.convertToString(json);
        //��ҳ���뻻�к��Ʊ��
        JsonUti.n = "<br />";
        JsonUti.t = "&nbsp;&nbsp;&nbsp;";
        var html = JsonUti.convertToString(json);
  2.0		2014-09-27 11:16:01
  		(2)֧��DOM�����ת��������ֻ���е�һ�������������ȸ��ߵĲ�������ϸ�����
 ********************************************************************* */
var JsonUtil = {

    //���廻�з�
    n: "\n",
    //�����Ʊ��
    t: "\t",

    /*
     *  @name:	convertToString
     *@access:	public
     *  @desc:	ת��String
     * @param:	jsonObj		Json		����
     * @param:	line			string	�����ַ���
     * @param:	tab				string	�Ʊ��ַ���
     * @param:	level			int			�Ʊ��ַ���
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
     *  @desc:	д����
     * @param:	obj				Json	����
     * @param:	level			int		��Σ�����Ϊ1��
     * @param:	isInArray	bool	�˶����Ƿ���һ��������
     *@return:	string
     */
		__writeObj: function(obj, level, isInArray) {
        //���Ϊ�գ�ֱ�����null
        if (obj == null) {
            return "null";
        }
        //Ϊ��ͨ���ͣ�ֱ�����ֵ
        //������ͣ�String Date Boolean Function Number RegExp Object+* (Math Event)
        if (obj.constructor != Object && obj.constructor != Array) {
            var v = obj.toString();
            var tab = isInArray ? JsonUtil.__repeatStr(JsonUtil.t, level - 1) : "";
            if (obj.constructor == String || obj.constructor == Date) {
                //ʱ���ʽ��ֻ�ǵ�������ַ�����������Date����
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
        //дJson���󣬻����ַ���
        var currentObjStrings = [];
        //������ͣ�Object Array
        //��������
        for (var name in obj) {
            var temp = [];
            //��ʽ��Tab
            var paddingTab = JsonUtil.__repeatStr(JsonUtil.t, level);
            temp.push(paddingTab);
            //д��������
            temp.push(name + " : ");

            var val = obj[name];
            if (val == null) {
                temp.push("null");
            }else {
                var c = val.constructor;

                if (c == Array) { //���Ϊ���ϣ�ѭ���ڲ�����
                    temp.push(JsonUtil.n + paddingTab + "[" + JsonUtil.n);
                    var levelUp = level + 2;    //�㼶+2

                    var tempArrValue = [];      //����Ԫ������ַ�������Ƭ��
                    for (var i = 0; i < val.length; i++) {
                        //�ݹ�д����                         
                        tempArrValue.push(JsonUtil.__writeObj(val[i], levelUp, true));
                    }

                    temp.push(tempArrValue.join("," + JsonUtil.n));
                    temp.push(JsonUtil.n + paddingTab + "]");
                }else if (c == Object) {
                    //�ݹ�д����
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
            //���뵱ǰ�������ԡ��ַ���
            currentObjStrings.push(temp.join(""));
        }
        return (level > 1 && !isInArray ? JsonUtil.n : "")                  		//���Json�������ڲ�����Ҫ���и�ʽ��
            + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "{" + JsonUtil.n		//�Ӳ��Tab��ʽ��
            + currentObjStrings.join("," + JsonUtil.n)                       		//������������ֵ
            + JsonUtil.n + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "}";   //��ն���
    },

    /*
     *  @name:	__writeDom
     *@access:	private
     *  @desc:	дDOM
     * @param:	obj				DOM	����
     * @param:	level			int		��Σ�����Ϊ1��
     * @param:	isInArray	bool	�˶����Ƿ���һ��������
     *@return:	string
     */
		__writeDom: function(dom, level, isInArray) {
				if (!/^\[object .*\]$/ig.test(dom.constructor)) {
						return JsonUtil.__writeObj(dom, level, isInArray);
				}
				var paddingTab = JsonUtil.__repeatStr(JsonUtil.t, level);//дJson���󣬻����ַ���
        var currentObjStrings = [];
				for (var name in dom) {
						var temp = [];
						var val = dom[name];
						temp.push(paddingTab);
            //д��������
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
            //���뵱ǰ�������ԡ��ַ���
            currentObjStrings.push(temp.join(""));
				}
        return (level > 1 && !isInArray ? JsonUtil.n : "")                  		//���Json�������ڲ�����Ҫ���и�ʽ��
            + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "{" + JsonUtil.n		//�Ӳ��Tab��ʽ��
            + currentObjStrings.join("," + JsonUtil.n)                       		//������������ֵ
            + JsonUtil.n + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "}";   //��ն���
		},

    /*
     *  @name:	__isArray
     *@access:	private
     *  @desc:	�ж϶����Ƿ�Ϊ����
     * @param:	obj		Json	����
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
     *  @desc:	��ȡ�ظ��ַ���
     * @param:	str		string	�Ӵ�
     * @param:	times	int			�ظ�����
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