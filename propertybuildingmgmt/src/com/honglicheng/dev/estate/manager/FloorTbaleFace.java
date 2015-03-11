package com.honglicheng.dev.estate.manager;


import java.util.List;
import java.util.Map;



import com.honglicheng.dev.estate.model.*;

public interface FloorTbaleFace {
	

     public List<TBuilding> getTBuilding();
     
     public List<EasyTree> getTCommunity(boolean flag,Integer id);
      
     public List getTFloors(Integer buildid,String flag);
     
     public List loadareainfo(Integer buildid,String flag);
     
     public BuildingNode loadbuilddata(Integer id,Integer type);
     
     public List loadBuildhascorp(Integer id,String name);
     
     public Integer addCorporation(TCorporation item);
     
     public void updateroom(Integer id,Integer corpid,Integer stateid);
     
     public void updateroominfo(Integer id,String owner,String tel);
     
     public void clearroominfo(Integer id,Integer corpid,Integer stateid);
     
     public void decorate(Integer id,Integer stateid);
     
     public List<TCorporation> loadcorplistnow(String value,Integer id);
     
     public void delcorption(Integer id);
     
     public List<Integer> LoadIndustryId(Integer id);
     
     public void updatecorpstate(Integer stateid,Integer id);
     
     
     /**通过楼栋id查询其下公司的人数规模比例*/
     public List loadnopinfo(Integer id,String flag);
     
     /**通过id查询其下的房间自持、租赁比例*/
     public List loadsalinfo(Integer id,String flag);
     
     /**通过Id查询区域信息*/
     public TCommunity findInfoById(Integer tid);
     
     /**新增区域信息*/
     public boolean insertInfo(String name,String no,Integer pid);
     
     /**查询区域是否存在*/
     public boolean isExistsByName(String name);
     
     /**修改区域信息*/
     public boolean editInfo(String name,Integer tid);
     
     /**删除区域信息*/
     public String deleteInfo(Integer tid);
     
     /**查询楼栋表里是否存在选择区域*/
     public boolean isExistsByTid(Integer tid);
     
	 /**获取业态查询按钮查询的数据*/
     public Map<String,List<CorpRoomBuildComInd>> uicListFindByWhere(String formData);
 	/*点击业态报表中的grid时查询公司信息*/
	public Map<String,List <CorpRoomBuildComInd>> querySelGridDataToShow(String formData);
}
