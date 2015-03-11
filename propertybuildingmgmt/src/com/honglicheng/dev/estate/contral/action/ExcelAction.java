package com.honglicheng.dev.estate.contral.action;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.manager.FloorTbaleFace;
import com.honglicheng.dev.estate.manager.FormatsTypeMgrFacade;
import com.honglicheng.dev.estate.model.BuildingNode;
import com.honglicheng.dev.estate.model.CorpNode;
import com.honglicheng.dev.estate.model.CropMerge;
import com.honglicheng.dev.estate.model.FloorNode;
import com.honglicheng.dev.estate.model.IndustryTypeInfo;
import com.honglicheng.dev.estate.model.RoomsNode;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ExcelAction  extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216811810024929438L;
	
	@Resource
	private FloorTbaleFace floortableface;
	
	@Resource
	private FormatsTypeMgrFacade formatsTypeMgrFacade;
	
	private BuildingNode Builddata;
	
	private BuildingNode bnclddata;
	
	//拥有的业态
	private List<IndustryTypeInfo> listi = new ArrayList<IndustryTypeInfo>();
		
	//楼栋名称
	private String ldongname;
	//楼栋总层数
	private String countfloors;
	//每层房间数 
	private String countmaxRooms;

	InputStream excelStream; 
	//导出文件名称
	private String filename;
	//楼栋id
	private String ldid;
	
	private Integer row_count=0;
	
	private Integer bytesnumber;
	
	
	public String getLdid() {
		return ldid;
	}
	public void setLdid(String ldid) {
		this.ldid = ldid;
	}
	
	public String getLdongname() {
		return ldongname;
	}
	public void setLdongname(String ldongname) {
		this.ldongname = ldongname;
	}
	public String getCountfloors() {
		return countfloors;
	}
	public void setCountfloors(String countfloors) {
		this.countfloors = countfloors;
	}
	public String getCountmaxRooms() {
		return countmaxRooms;
	}
	public void setCountmaxRooms(String countmaxRooms) {
		this.countmaxRooms = countmaxRooms;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

	public Integer getBytesnumber() {
		return bytesnumber;
	}
	public void setBytesnumber(Integer bytesnumber) {
		this.bytesnumber = bytesnumber;
	}
	/*
	 * 导出表格
	 * */
	public String createExcel(){
		Builddata=floortableface.loadbuilddata(Integer.parseInt(ldid),1);
		bnclddata=floortableface.loadbuilddata(Integer.parseInt(ldid),2);
		listi=formatsTypeMgrFacade.getFormatsData();
		List<FloorNode> floorsnode=Builddata.getFloors();
		List<FloorNode> floorsvnc=bnclddata.getFloors();
		JSONArray hascolor=JSONArray.fromObject(listi);
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
		WritableWorkbook book = Workbook.createWorkbook(out);
		WritableSheet sheet = book.createSheet(ldongname,0);
		create_excels(book,sheet,floorsnode,hascolor); 
		row_count+=5;
		create_excels(book,sheet,floorsvnc,hascolor); 
	    book.write();
	    book.close();
	    
		excelStream=new ByteArrayInputStream(out.toByteArray());
        excelStream.close();
		}catch(Exception e){
			
		}
	
		return "excel";
	}
	
	public List<IndustryTypeInfo> getListi() {
		return listi;
	}
	public void setListi(List<IndustryTypeInfo> listi) {
		this.listi = listi;
	}
	/*
	 * 创建表格
	 * */
//	private void create_excel(OutputStream os,List<FloorNode> item,JSONArray colors){
//		try{
//    		//创建excel对象
//			WritableWorkbook book = Workbook.createWorkbook(os);
//    		//创建表格对象
//			WritableSheet sheet = book.createSheet(ldongname,0);
//			WritableFont ldtitlefont = new  WritableFont(WritableFont.TIMES, 16);
//	        WritableCellFormat formattitle = new  WritableCellFormat(ldtitlefont);
//	        formattitle.setAlignment(jxl.format.Alignment.CENTRE);
//	        formattitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//	        formattitle.setBorder(Border.ALL, BorderLineStyle.THIN);
//	        
//	        //生成标题
//			Label title=new Label(0,0,ldongname,formattitle);    //(列，行)
//			sheet.setRowView(0, 500);
//			sheet.addCell(title);
//			sheet.mergeCells(0, 0, Integer.parseInt(countmaxRooms), 0); 
//			
//			//生成业态map<id,color>
//			Map<String, String> op=new HashMap<String, String>();
//	        //生成业态
//	        if(colors.size()>0){
//	        	Integer cellindex=0;
//	        	//生成表格固定格式
//	        
//	        	for(int i=0;i<colors.size();i++){
//	        		JSONObject color_item=JSONObject.fromObject(colors.get(i));
//	        		WritableFont room_area = new  WritableFont(WritableFont.ARIAL, 10);
//	    	        WritableCellFormat format_room_area = new  WritableCellFormat(room_area);
//	    	        
//	    	        format_room_area.setBorder(Border.ALL, BorderLineStyle.THIN);
//	        		format_room_area.setBackground(getNearestColour(color_item.getString("color")));
//	        		op.put(color_item.getString("id"), color_item.getString("color"));
//	        		
//
//	        		Label colors_label=new Label(cellindex,1," ",format_room_area);
//	        		WritableCellFormat yt_Name = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));
//	        		yt_Name.setBorder(Border.ALL, BorderLineStyle.THIN);
//	        		yt_Name.setAlignment(Alignment.CENTRE);
//	        		yt_Name.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//	        		
//		        	Label yt_label=new Label(cellindex+1,1,color_item.getString("name"),yt_Name);
//		        	sheet.addCell(colors_label);
//	        		sheet.addCell(yt_label);
//	        		
//	        		cellindex+=2;
//	        	}
//	        	
//	        }
//	        
//	        //创建楼栋层数样式
//	        WritableCellFormat columstyle=new WritableCellFormat(new WritableFont(WritableFont.TIMES, 12));
//	        columstyle.setAlignment(jxl.format.Alignment.CENTRE);
//	        columstyle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//	        //创建房号房号行
//			Label roomstitle=new Label(0,2,"房号",columstyle);
//			sheet.addCell(roomstitle);
//			//创建面积行
//			Label areatitle=new Label(0,3,"面积",columstyle);
//			sheet.addCell(areatitle);
//			
//			
//			Integer floorshow=4;
//
//				//创建楼层数据
//		        if(item!=null){
//		        	
//		        	CropMerge compareitem=null;
//		        	List<Integer> listmerge=new ArrayList<Integer>();
//			        //遍历行
//		        	for(int ni=0;ni<item.size();ni++){
//		        		
//		        	FloorNode floor_Data=item.get(ni);
//		        	
//		        	List<RoomsNode> room_item=floor_Data.getRooms();
//		        	
//		        	Label label=new Label(0,floorshow,floor_Data.getNo(),columstyle);    //(列，行)
//		        	
//					 //将内容添加只表格中
//				    sheet.addCell(label);
//				    //遍历列
//				    
//				    
//		        	for(int n=0;n<room_item.size();n++){
//		        		  
//		        		WritableFont font = new  WritableFont(WritableFont.ARIAL, 10);
//							//内容样式对象
//					    WritableCellFormat format = new  WritableCellFormat(font);
//					    
//					    format.setAlignment(jxl.format.Alignment.CENTRE);
//					    
//					    format.setBorder(Border.ALL, BorderLineStyle.THIN);
//					    
//					    format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//					    
//					    format.setWrap(true);
//					    
//		        		RoomsNode room_Data=room_item.get(n);
//		        		
//		        		WritableCellFormat room_Name = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));
//		        		
//		        		room_Name.setBorder(Border.ALL, BorderLineStyle.THIN);
//		        		
//		        		room_Name.setAlignment(Alignment.CENTRE);
//		        		
//		        		room_Name.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//		        		
//		        		room_Name.setWrap(true);
//		        		
//		        		Label room_number = new Label(n+1,2,room_Data.getOrder(),room_Name);
//		        		
//		        		Label room_areas = new Label(n+1,3,room_Data.getArea().toString(),room_Name);
//		        		
//		        		sheet.setColumnView(n+1,10);
//
//		        		sheet.addCell(room_number);
//		        		
//		        		sheet.addCell(room_areas);
//		        		
//		        		CorpNode crop_Data=room_Data.getCorp();
//		        		
//		        		if(crop_Data!=null){
//		        			
//		        		String pp=op.get(crop_Data.getType().toString());
//		        		
//		        		if(pp!=null){
//		        			
//		        			 Colour lop=getNearestColour(pp);
//		        			 
//		        			 format.setBackground(lop);
//		        			 
//		        		}
//		        		
//		        		Label labelroom=new Label(n+1,floorshow,crop_Data.getName(),format);
//		        		sheet.addCell(labelroom);
//		        		if(compareitem==null){
//		        			compareitem=new CropMerge();
//		        			compareitem.setCropid(crop_Data.getId());
//		        			compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
//		        			compareitem.setCol_number(n+1);
//		        			compareitem.setRow_number(floorshow);
//		        			listmerge.add(n+1);
//		        		}else{
//		        			if(crop_Data.getId().equals(compareitem.getCropid()) && compareitem.getRoom_number().equals((Integer.parseInt(room_Data.getOrder())-1)) && compareitem.getRow_number().equals(floorshow)){
//		        				compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
//		        				listmerge.add(n+1);
//		        			}else{
//		        				if(listmerge.size()>1){
//		        				sheet.mergeCells(listmerge.get(0), floorshow, listmerge.get(listmerge.size()-1), floorshow);
//		        				}
//		        				listmerge=new ArrayList<Integer>();
//		        				compareitem.setCropid(crop_Data.getId());
//			        			compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
//			        			compareitem.setCol_number(n+1);
//			        			compareitem.setRow_number(floorshow);
//			        			listmerge.add(n+1);
//		        			}
//		        		  }
//		        		}else{
//		        			Label labelroom=new Label(n+1,floorshow," ",format);
//		        			if(listmerge.size()>1){
//		        				sheet.mergeCells(listmerge.get(0), floorshow, listmerge.get(listmerge.size()-1), floorshow);
//		        				}
//		        			compareitem=null;
//		        			if(listmerge.size()!=0){
//		        				listmerge=new ArrayList<Integer>();
//		        			}
//			        		sheet.addCell(labelroom);
//		        		}
//		        	  }
//					floorshow++;
//		        	}
//		        } 
//			
//
//	        //format.setBackground(jxl.format.Colour.BLUE_GREY);
//	       
//			
//			 //sheet.mergeCells(0, 0, 8, 0);   //(开始列，开始行，结束列，结束行)
//	          
//    		
//		    //jxl.write.Number number  =   new  jxl.write.Number( 1 ,  0 ,  555.123);
//		    //sheet.addCell(number);
//		    
//		    book.write();
//		    book.close();
//		      
//    	}catch(Exception e){
//    		System.out.println(e);
//    	}
//	}
	
	 //自动解析颜色变量，创建colour对象
	 private  Colour getNearestColour(String strColor) {  
	        Color cl = Color.decode(strColor);  
	        Colour color = null;  
	        Colour[] colors = Colour.getAllColours();  
	        if ((colors != null) && (colors.length > 0)) {  
	           Colour crtColor = null;  
	           int[] rgb = null;  
	           int diff = 0;  
	           int minDiff = 999;  
	           for (int i = 0; i < colors.length; i++) {  
	                crtColor = colors[i];  
	                rgb = new int[3];  
	                rgb[0] = crtColor.getDefaultRGB().getRed();  
	                rgb[1] = crtColor.getDefaultRGB().getGreen();  
	                rgb[2] = crtColor.getDefaultRGB().getBlue();  
	      
	                diff = Math.abs(rgb[0] - cl.getRed())  
	                  + Math.abs(rgb[1] - cl.getGreen())  
	                  + Math.abs(rgb[2] - cl.getBlue());  
	                if (diff < minDiff) {  
	                 minDiff = diff;  
	                 color = crtColor;  
	                }  
	           }  
	        }  
	        if (color == null)color = Colour.BLACK;  
	        
	        return color;  
	    }  
	 
	 private void create_excels(WritableWorkbook book,WritableSheet sheet,List<FloorNode> item,JSONArray colors){
		    try{
		    WritableFont ldtitlefont = new  WritableFont(WritableFont.TIMES, 16);
	        WritableCellFormat formattitle = new  WritableCellFormat(ldtitlefont);
	        formattitle.setAlignment(jxl.format.Alignment.CENTRE);
	        formattitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        formattitle.setBorder(Border.ALL, BorderLineStyle.THIN);
	        
	        //生成标题
			Label title=new Label(0,row_count,item.get(0).getType().equals(1)?ldongname:"避难层",formattitle);    //(列，行)
			sheet.setRowView(0, 500);
			sheet.addCell(title);
			sheet.mergeCells(0, row_count, Integer.parseInt(countmaxRooms), row_count); 
			
			//生成业态map<id,color>
			Map<String, String> op=new HashMap<String, String>();
	        //生成业态
	        if(colors.size()>0){
	        	Integer cellindex=0;
	        	//生成表格固定格式
	        	row_count++;
	        	for(int i=0;i<colors.size();i++){
	        		JSONObject color_item=JSONObject.fromObject(colors.get(i));
	        		op.put(color_item.getString("id"), color_item.getString("color"));
	        		if(item.get(0).getType().equals(2)){
	        			continue;
	        		}
	        		WritableFont room_area = new  WritableFont(WritableFont.ARIAL, 10);
	    	        WritableCellFormat format_room_area = new  WritableCellFormat(room_area);
	    	        format_room_area.setBorder(Border.ALL, BorderLineStyle.THIN);
	        		format_room_area.setBackground(getNearestColour(color_item.getString("color")));
	        		Label colors_label=new Label(cellindex,row_count," ",format_room_area);
	        		WritableCellFormat yt_Name = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));
	        		yt_Name.setBorder(Border.ALL, BorderLineStyle.THIN);
	        		yt_Name.setAlignment(Alignment.CENTRE);
	        		yt_Name.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        		
		        	Label yt_label=new Label(cellindex+1,row_count,color_item.getString("name"),yt_Name);
		        	sheet.addCell(colors_label);
	        		sheet.addCell(yt_label);
	        		
	        		cellindex+=2;
	        	}
	        	
	        }
	        row_count++;
	        //创建楼栋层数样式
	        WritableCellFormat columstyle=new WritableCellFormat(new WritableFont(WritableFont.TIMES, 12));
	        columstyle.setAlignment(jxl.format.Alignment.CENTRE);
	        columstyle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	        columstyle.setBorder(Border.ALL, BorderLineStyle.THIN);
	        //创建房号房号行
			Label roomstitle=new Label(0,row_count,"房号",columstyle);
			sheet.addCell(roomstitle);
			//创建面积行
			row_count++;
			Label areatitle=new Label(0,row_count,"面积",columstyle);
			sheet.addCell(areatitle);
			Integer bzh=row_count;
			

				//创建楼层数据
		        if(item!=null){
		        	
		        	CropMerge compareitem=null;
		        	List<Integer> listmerge=new ArrayList<Integer>();
			        //遍历行
		        	for(int ni=0;ni<item.size();ni++){
		        	
		        	row_count++;	
		        		
		        	FloorNode floor_Data=item.get(ni);
		        	
		        	List<RoomsNode> room_item=floor_Data.getRooms();
		        	
		        	Label label=new Label(0,row_count,floor_Data.getNo(),columstyle);    //(列，行)
		        	
					 //将内容添加只表格中
				    sheet.addCell(label);
				    //遍历列
				    
				    
		        	for(int n=0;n<room_item.size();n++){
		        		  
		        		WritableFont font = new  WritableFont(WritableFont.ARIAL, 10);
							//内容样式对象
					    WritableCellFormat format = new  WritableCellFormat(font);
					    
					    format.setAlignment(jxl.format.Alignment.CENTRE);
					    
					    format.setBorder(Border.ALL, BorderLineStyle.THIN);
					    
					    format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					    
					    format.setWrap(true);
					    
		        		RoomsNode room_Data=room_item.get(n);
		        		
		        		WritableCellFormat room_Name = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10));
		        		
		        		room_Name.setBorder(Border.ALL, BorderLineStyle.THIN);
		        		
		        		room_Name.setAlignment(Alignment.CENTRE);
		        		
		        		room_Name.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		        		
		        		room_Name.setWrap(true);
		        		
		        		Label room_number = new Label(n+1,bzh-1,room_Data.getOrder(),room_Name);
		        		
		        		Label room_areas = new Label(n+1,bzh,room_Data.getArea().toString(),room_Name);
		        		
		        		sheet.setColumnView(n+1,10);

		        		sheet.addCell(room_number);
		        		
		        		sheet.addCell(room_areas);
		        		
		        		CorpNode crop_Data=room_Data.getCorp();
		        		
		        		if(crop_Data!=null){
		        			
		        		String pp=op.get(crop_Data.getType().toString());
		        		
		        		if(pp!=null){
		        			
		        			 Colour lop=getNearestColour(pp);
		        			 
		        			 format.setBackground(lop);
		        			 
		        		}
		        		
		        		Label labelroom=new Label(n+1,row_count,crop_Data.getName(),format);
		        		sheet.addCell(labelroom);
		        		if(compareitem==null){
		        			compareitem=new CropMerge();
		        			compareitem.setCropid(crop_Data.getId());
		        			compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
		        			compareitem.setCol_number(n+1);
		        			compareitem.setRow_number(row_count);
		        			listmerge.add(n+1);
		        		}else{
		        			if(crop_Data.getId().equals(compareitem.getCropid()) && compareitem.getRoom_number().equals((Integer.parseInt(room_Data.getOrder())-1)) && compareitem.getRow_number().equals(row_count)){
		        				compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
		        				listmerge.add(n+1);
		        			}else{
		        				if(listmerge.size()>1){
		        				sheet.mergeCells(listmerge.get(0), row_count, listmerge.get(listmerge.size()-1), row_count);
		        				}
		        				listmerge=new ArrayList<Integer>();
		        				compareitem.setCropid(crop_Data.getId());
			        			compareitem.setRoom_number(Integer.parseInt(room_Data.getOrder()));
			        			compareitem.setCol_number(n+1);
			        			compareitem.setRow_number(row_count);
			        			listmerge.add(n+1);
		        			}
		        		  }
		        		}else{
		        			Label labelroom=new Label(n+1,row_count," ",format);
		        			if(listmerge.size()>1){
		        				sheet.mergeCells(listmerge.get(0), row_count, listmerge.get(listmerge.size()-1), row_count);
		        				}
		        			compareitem=null;
		        			if(listmerge.size()!=0){
		        				listmerge=new ArrayList<Integer>();
		        			}
			        		sheet.addCell(labelroom);
		        		}
		        	  }
		        	}
		        } 
		    }catch(Exception e){
		    	}
		    }
   
}
