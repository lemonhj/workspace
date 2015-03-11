package com.honglicheng.dev.sys.contral.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;






import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.service.RightService;
import com.opensymphony.xwork2.ActionSupport;


@Controller
@Scope("prototype")
public class SysRightsAction extends ActionSupport {

	/** 
	* 串行化ID
	*/ 
	private static final long serialVersionUID = -2460536108286912212L;
	
	@Resource
	private RightService rightService;
	
	private List<Right> allRights ;
	
	private Right right;
	
	private String delNums;
	
	/**
	 * 
	* @Title: getAllRights 
	* @Description: 获取当前权限列表 
	* @return String    返回类型 
	 */
	public String loadAllRights(){
		allRights = rightService.findAllRights();
		return SUCCESS;
	}
	
	public String saveOrUpdateRight(){
		rightService.saveOrUpdateRight(right);
		return SUCCESS;
	}
	
	/**
	 * 编辑权限
	 */
	public String editRight(){
		return "editRightPage" ;
	}
	
	/**
	 * 删除权限 
	 */
	public String deleteRights(){
		String dels = delNums.substring(1, delNums.length());
		String[] nums = dels.split(",");
		Integer[] ids = new Integer[nums.length];
		for(int i=0;i<nums.length;i++){
			ids[i] = Integer.parseInt(nums[i]);
		}
		rightService.deleteRights(ids);
		return SUCCESS;
	}

	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}

	public Right getRight() {
		return right;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public String getDelNums() {
		return delNums;
	}

	public void setDelNums(String delNums) {
		this.delNums = delNums;
	}

	
	
}
