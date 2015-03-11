package com.hlc.archivesmgmt.util;

import java.util.Collection;

/**
 * 校验工具类
 * @author linbotao
 *
 */
public class ValidateUtil {
	/**
	 * 判断字符串是否有效
	 */
	public static boolean isValid(String str){
		if(str == null || "".equals(str)){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断集合是否有效
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断数组是否有效
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false ;
		}
		return true ;
	}
}