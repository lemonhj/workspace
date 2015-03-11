package com.honglicheng.dev.estate.manager;

import java.util.List;

import com.honglicheng.dev.estate.model.ComIndustry;
import com.honglicheng.dev.estate.model.IndustryTypeInfo;
import com.honglicheng.dev.estate.model.TBuilding;

public interface FormatsTypeMgrFacade {

	//删除
	public String deleteData(String id,String result);
	//查询行业类型信息表
	public List<IndustryTypeInfo> getFormatsData();
	//查询颜色是否存在
	public boolean isColorExists(String color);
	//查询类型是否存在
	public boolean isNameExists(String name);
	//新增类型
	public boolean addType(String name,String color);
	//删除类型
	public Integer deleteTypes(Integer[] ids);
	//通过Id查询类型信息
	public IndustryTypeInfo findByCid(Integer cid);
	//修改选择类型
	public boolean editType(Integer id,String name,String color);
	//查询除了某个ID的类型是否存在
	public boolean isNameExistsNoId(String name,Integer id);
	//查询除了某个ID的颜色是否存在
	public boolean isColorExistsNoId(String color,Integer id);
	//查询是否有公司绑定了要删除的业态类型
	public List canDeleteTypes(String del);
}
