package com.hlc.archivesmgmt.util;

import java.util.Collection;

/**
 * У�鹤����
 * @author linbotao
 *
 */
public class ValidateUtil {
	/**
	 * �ж��ַ����Ƿ���Ч
	 */
	public static boolean isValid(String str){
		if(str == null || "".equals(str)){
			return false ;
		}
		return true ;
	}
	
	/**
	 * �жϼ����Ƿ���Ч
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){
			return false ;
		}
		return true ;
	}
	
	/**
	 * �ж������Ƿ���Ч
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false ;
		}
		return true ;
	}
}