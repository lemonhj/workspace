package com.honglicheng.dev.sys.contral.action;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.sys.manager.BaseSysUserFacade;
import com.honglicheng.dev.sys.manager.BaseSysUserImpl;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.security.Role;
import com.honglicheng.dev.sys.service.RoleService;
import com.honglicheng.dev.sys.service.UserService;
import com.honglicheng.dev.sys.utils.MyMD5Util;
import com.opensymphony.xwork2.ActionSupport;


@Controller
@Scope("prototype")
public class UserAuthorizeAction extends ActionSupport {

	/** 
	* 串行化ID
	*/ 
	private static final long serialVersionUID = -5492202063767859280L;

	private String delNums;
	
	public String getDelNums() {
		return delNums;
	}

	public void setDelNums(String delNums) {
		this.delNums = delNums;
	}

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	private List<BaseSysUser> allUsers ;
	
	private BaseSysUser user;
	
	private String rolestostr;
	
	private String deluserid;
	
	private String newpw;
	
	private String modifyFlag = "false";
	
	/**
	 * 
	* @Title: getAllRights 
	* @Description: 获取当前用户列表 
	* @return String    返回类型 
	 */
	public String loadAllUsers(){
		allUsers = userService.findAllUsers();
		return SUCCESS;
	}
	

	
	
	/**
	 * 
	* @Title: deleteUsers 
	* @Description: 删除所选择的用户
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String deleteUsers(){
		String[] nums = deluserid.split(",");
		Integer[] ids = new Integer[nums.length];
		for(int i=0;i<nums.length;i++){
			ids[i] = Integer.parseInt(nums[i]);
		}
		userService.deleteUsers(ids);
		return SUCCESS;
	}
	
	
	/**
	 * 
	* @Title: deleteUser 
	* @Description: 删除单个用户 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String deleteUser(){
		userService.deleteEntity(user);
		return SUCCESS;
	}
	

	public String addUser(){
		try{
		JSONArray arrayid=JSONArray.fromObject(rolestostr);
		Role rolesitem;
		for(int i=0;i<arrayid.size();i++){
			JSONObject obj=arrayid.getJSONObject(i);
			rolesitem  = roleService.getEntity(Integer.parseInt(obj.getString("id")));
			user.getRoles().add(rolesitem);
		}
		user.setSuPassword(MyMD5Util.getEncryptedPwd(user.getSuPassword()));
		userService.saveOrUpdateEntity(user);
		allUsers = userService.findAllUsers();
		}catch(Exception e){
			System.out.println(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: updateUser 
	* @Description: 修改密码
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String updateUser(){
		BaseSysUserFacade userFacade = new BaseSysUserImpl();
		String encryptedPwd = null;
		String oldPw = user.getSuPassword();
		user = userService.getEntity(user.getSuId());
		try {
			if(userFacade.loginValid(user.getSuPassword(), oldPw)){
				encryptedPwd = MyMD5Util.getEncryptedPwd(newpw);
				user.setSuPassword(encryptedPwd);
				userService.updateEntity(user);
				modifyFlag = "true";
			}else{
				modifyFlag = "wrongPW";
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	

	public List<BaseSysUser> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<BaseSysUser> allUsers) {
		this.allUsers = allUsers;
	}

	public BaseSysUser getUser() {
		return user;
	}

	public void setUser(BaseSysUser user) {
		this.user = user;
	}

	public String getRolestostr() {
		return rolestostr;
	}

	public void setRolestostr(String rolestostr) {
		this.rolestostr = rolestostr;
	}



	public String getDeluserid() {
		return deluserid;
	}



	public void setDeluserid(String deluserid) {
		this.deluserid = deluserid;
	}

	public String getNewpw() {
		return newpw;
	}

	public void setNewpw(String newpw) {
		this.newpw = newpw;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}


	
}
