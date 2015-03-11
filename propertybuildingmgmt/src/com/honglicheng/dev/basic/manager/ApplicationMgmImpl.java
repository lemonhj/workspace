package com.honglicheng.dev.basic.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.honglicheng.dev.basic.dao.BaseDao;

/**
 * 公用模块业务逻辑实现类.
 * 
 * @author yanglh
 * @since 2014-09-02
 */
@Service("applicationMgm")
public class ApplicationMgmImpl extends BaseMgmImpl implements
		ApplicationMgmFacade {

	@Resource
	private BaseDao baseDao;

	public static ApplicationMgmImpl getFromApplicationContext(
			ApplicationContext ctx) {
		return (ApplicationMgmImpl) ctx.getBean("applicationMgm");
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	

	/**
	 * 获取表的字段名称和字段注释组成的集合
	 * 
	 * @param table
	 *            表名
	 * @return
	 */
	public List getTableLable(String table) {
		List list = new ArrayList();
		String sql = "select column_name,comments from user_col_comments where table_name = '"
				+ table.toUpperCase() + "' order by comments";
		/*try {
			JdbcTemplate jdbc = Constant.getJdbcTemplate();
			list = jdbc.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return list;
	}

	public String getCodeValueArrStr(String catagory) {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getTemplate(String templateCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateFile(String blobid, FileItem item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateFile(String blobid, String businessid, FileItem item)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

	
