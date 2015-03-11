package com.honglicheng.dev.sys.utils;


/**
 * 字符串工具类
 */
public class StringUtil {
	
	/**
	 * 将字符串按照指定的分隔符进行拆分成数组
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}
	
	/**
	 * 将数组对象转换成字符串表示,用","为分隔符
	 */
	public static String arr2Str(Object[] arr){
		String temp = "" ;
		if(ValidateUtil.isValid(arr)){
			for(int i = 0 ; i < arr.length ; i ++){
				if(i == (arr.length - 1)){
					temp = temp + arr[i];
				}
				else{
					temp = temp + arr[i] + ",";
				}
			}
		}
		return temp ;
	}
}
