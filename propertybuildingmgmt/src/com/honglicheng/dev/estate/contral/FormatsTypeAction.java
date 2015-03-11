package com.honglicheng.dev.estate.contral;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.manager.FormatsTypeMgrFacade;
import com.honglicheng.dev.estate.model.IndustryTypeInfo;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class FormatsTypeAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4229793189011555827L;

	@Resource
	FormatsTypeMgrFacade formatsTypeMgrFacade;
	
	//行业类型信息列表
	private List<IndustryTypeInfo> listi = new ArrayList<IndustryTypeInfo>();
	
	//行业类型颜色
	private String color;
	
	//行业类型名
	private String name;
	
	//判断颜色是否存在
	private boolean flagColor;
	
	//判断类型是否存在
	private boolean flagName;
	
	//判断新增是否成功
	private boolean flagAdd;
	
	//判断修改是否成功
	private boolean flagEdit;
	
	//修改类型Id
	private String editId;
	
	//删除类型Id
	private String delectIds;
	
	//删除条数
	private Integer count;
	
	//不能删除业态类型
	private ArrayList<String> cdNames;
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/** 初始化行业类型信息表  */
	public String loadFormatsTypes(){
		listi = formatsTypeMgrFacade.getFormatsData();
		return SUCCESS;
	}

	/** 新增类型 */
	public String addType(){
		flagColor = formatsTypeMgrFacade.isColorExists(color);
		flagName = formatsTypeMgrFacade.isNameExists(name);
		//颜色和类型均不存在时进行新增操作
		if(flagColor && flagName){
			flagAdd = formatsTypeMgrFacade.addType(name, color);
		}
		listi = formatsTypeMgrFacade.getFormatsData();
		return SUCCESS;
	}
	
	/** 修改选择类型 */
	public String editType(){
		flagColor = formatsTypeMgrFacade.isColorExistsNoId(color, Integer.parseInt(editId));
		flagName = formatsTypeMgrFacade.isNameExistsNoId(name, Integer.parseInt(editId));
		//颜色和类型均不存在时进行修改操作
		if(flagColor && flagName){
			flagEdit = formatsTypeMgrFacade.editType(Integer.parseInt(editId), name, color);
		}
		listi = formatsTypeMgrFacade.getFormatsData();
		return SUCCESS;
	}
	/** 删除选择类型 */
	public String deleteTypes(){
		String del = delectIds.substring(1, delectIds.length());
		String[] nums = del.split(",");
		Integer[] ids = new Integer[nums.length];
		for(int i=0;i<nums.length;i++){
			ids[i] = Integer.parseInt(nums[i]);
		}
		//查询是否有公司绑定业态类型
		cdNames = new ArrayList<String>();
		List list = formatsTypeMgrFacade.canDeleteTypes(del);
		if(list.size() == 0){
			//删除类型
			count = formatsTypeMgrFacade.deleteTypes(ids);
		}else{
			//不能删除类型
			for(int i=0;i<list.size();i++){
				String temp = (String)list.get(i);
				cdNames.add(temp);
			}
		}
		listi = formatsTypeMgrFacade.getFormatsData();
		return SUCCESS;
	}
	
	public List<IndustryTypeInfo> getListi() {
		return listi;
	}

	public void setListi(List<IndustryTypeInfo> listi) {
		this.listi = listi;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getFlagColor() {
		return flagColor;
	}

	public void setFlagColor(boolean flagColor) {
		this.flagColor = flagColor;
	}

	public boolean getFlagName() {
		return flagName;
	}

	public void setFlagName(boolean flagName) {
		this.flagName = flagName;
	}

	public boolean getFlagAdd() {
		return flagAdd;
	}

	public void setFlagAdd(boolean flagAdd) {
		this.flagAdd = flagAdd;
	}

	public String getDelectIds() {
		return delectIds;
	}

	public void setDelectIds(String delectIds) {
		this.delectIds = delectIds;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public boolean getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(boolean flagEdit) {
		this.flagEdit = flagEdit;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public ArrayList<String> getCdNames() {
		return cdNames;
	}

	public void setCdNames(ArrayList<String> cdNames) {
		this.cdNames = cdNames;
	}

	
}
