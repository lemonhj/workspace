package com.hlc.archivesmgmt.service;

import java.io.InputStream;


/**
 * ����excelService
* @ClassName: Excelservice 
* @Description: TODO(������һ�仰��������������) 
* @author linbotao
* @date 2014��9��4�� ����2:23:51 
*
 */
public interface ExcelService {
	 InputStream getExcelInputStream(String id);
	void saveFromExcel();  
}
