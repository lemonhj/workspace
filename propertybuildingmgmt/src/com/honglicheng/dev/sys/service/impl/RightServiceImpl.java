package com.honglicheng.dev.sys.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.basic.dao.BaseDaoI;
import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.service.RightService;
import com.honglicheng.dev.sys.utils.ValidateUtil;


/**
 * RightServiceImpl
 */
@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements
		RightService {
	
	/**
	 * 查询全部权限
	 */
	public List<Right> findAllRights(){
		return this.findEntityByHQL("from Right");
	}
	
	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="rightDao")
	public void setDao(BaseDaoI<Right> dao) {
		super.setDao(dao);
	}
	
	/**
	 * 保存/更新权限
	 */
	public void saveOrUpdateRight(Right right){
		//保存
		if(right.getId() == null){
			//查询最大权限位下的最大权限码
			String hql = "from Right r order by r.rightPos desc,r.rightCode desc";
			List<Right> list = this.findEntityByHQL(hql);
			//有权限记录
			if(ValidateUtil.isValid(list)){
				Right max = list.get(0);
				//若权限码达到最大值,则权限位 + 1
				if(max.getRightCode() == ((long)1<<60)){
					right.setRightPos(max.getRightPos() + 1);
					right.setRightCode(1);
				}
				else{
					right.setRightPos(max.getRightPos());
					right.setRightCode(max.getRightCode() << 1);
				}
			}
			//空记录
			else{
				right.setRightPos(0);
				right.setRightCode(1);
			}
		}
		//
		this.saveOrUpdateEntity(right);
	}

	/**
	 * 通过url追加权限
	 */
	public void addRightByURL(String url) {
		String hql = "select count(*) from Right r where r.rightUrl = ?" ;
		long count = (Long)this.uniqueResult(hql, url);
		if(count == 0){
			Right r = new Right();
			r.setRightUrl(url);
			r.setRightDesc("超链接");
			this.saveOrUpdateRight(r);
		}
	}
	
	/**
	 * 按照id删除权限
	 */
	public void deleteRight(Integer rightid){
		String hql = "delete from Right r where r.id = ?";
		this.batchEntityByHQL(hql, rightid);
	}
	
	/**
	 * //查询所有没有的权限
	 */
	public List<Right> findRightExcludeIds(Set<Right> rights){
		if(!ValidateUtil.isValid(rights)){
			return findAllRights();
		}
		else{
			String hql = "from Right r where r.id not in ("+extractIds(rights)+")";
			return this.findEntityByHQL(hql);
		}
	}
	
	/**
	 * 抽取所有id的字符串
	 */
	private String extractIds(Set<Right> rights){
		String temp = "";
		for(Right r:rights){
			temp = temp + r.getId() + ",";
		}
		return temp.substring(0,temp.length() - 1) ;
	}

	@Override
	public void deleteRights(Integer[] ids) {
		String hql = "delete from Right r where r.id in (:idd)";
		this.deleteEntityByIDS(hql, ids);
	}
}