package com.hlc.archivesmgmt.util;

/**
 * �ַ���������
 * @author linbotao
 *
 */
public class StringUtil {
	
	/**
	 * ���ַ�������ָ���ķָ������в�ֳ�����
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}
	
	/**
	 * ���������ת�����ַ�����ʾ,��","Ϊ�ָ���
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
