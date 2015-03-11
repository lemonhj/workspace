package com.hlc.archivesmgmt.service;

import java.io.InputStream;


/**
 * 操作excelService
* @ClassName: Excelservice 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author linbotao
* @date 2014年9月4日 下午2:23:51 
*
 */
public interface ExcelService {
	 InputStream getExcelInputStream(String id);
	void saveFromExcel();  
}
