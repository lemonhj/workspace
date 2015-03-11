
/**
 * 构建combobox数据
 * @param selectType 选择类型
 * @returns 需要构建的数据对象
 */
function constructArr(selectType,securityLevel){
	
	var arrayObject=[];
	for(var i=0;i<selectType.length;i++){
		
		arrayObject[i] = new array2obj(i,selectType[i]);
		if(i==securityLevel){
			arrayObject[i].selected = true;
		}
	}
	
	return arrayObject;
}

/**
 * 数组转对象
 */
function array2obj(label,value){
	this.label = label;
	this.value = value;
}

$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+m+'-'+d;
}

$.extend($.fn.validatebox.defaults.rules, {    
    maxLength: {    
        validator: function(value, param){
            return value.length < param;    
        },    
        message: '长度过长' 
    },
    isNumber:{
    	validator: function(value, param){
            return !isNaN(value);    
        },    
        message: '请输入数字' 
    }
}); 

function JsonDateformat(obj){
	var objdate=new Date(obj);
	return myformatter(objdate);
}

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function idformatter(id){
	var reId = "0000" + id;
	return reId.substring(reId.length-4,reId.length);
}

/**
 * 扩展validate控件
 */
$.extend($.fn.validatebox.defaults.rules, {
    initId: {
        validator: function(value, param){
        	 if(/^\d+$/.test(value)){
        		 if(value.length<1||value.length>4){
        			 return false;
        		 }
        		 var num = parseInt(value);
        		 if(num<0||num>9999){
        			 return false;
        		 }else{
        			 return true;
        		 }
        	 }else{
        		 return false;
        	 }
        },
        message: '请输入四位数字！'
    }
});

