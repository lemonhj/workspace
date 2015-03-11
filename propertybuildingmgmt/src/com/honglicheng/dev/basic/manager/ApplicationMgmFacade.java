package com.honglicheng.dev.basic.manager;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;


/**
 * 公用模块业务逻辑接口.
 *
 * @author yanglh
 * @since 2014-09-02
 */
public interface ApplicationMgmFacade {
	
	/**
	 * 根据指定的分类名称，获取自定义属性数据集合表示的字符串,用于客户端Ext下拉框组件
	 * @param catagory 分类名称
	 * @return
	 */
	String getCodeValueArrStr(String catagory);
		
	
	/**
	 * 获得表栏位名称和对应中文名称
	 * @param table
	 * @return
	 */
	List getTableLable(String table);
	
	/**
	 * 更新上传文件
	 * @param blobid
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public String updateFile(String blobid, FileItem item) throws Exception;
	/**
	 * 更新上传文件
	 * @param blobid
	 * @param businessid
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public String updateFile(String blobid,String businessid, FileItem item) throws Exception;

	/**
	 * 获取Office文档模板对象对应文档的二进制流
	 * @param templateCode
	 * @return
	 */
	InputStream getTemplate(String templateCode);

}
