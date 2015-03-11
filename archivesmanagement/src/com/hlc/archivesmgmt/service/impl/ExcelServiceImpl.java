package com.hlc.archivesmgmt.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.FileDescription;
import com.hlc.archivesmgmt.service.ArchiveCateService;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.service.ExcelService;
import com.hlc.archivesmgmt.service.FileDescService;
import com.hlc.archivesmgmt.service.ZtreelocationService;
/**
 * 操作excelService
* @ClassName: Excelservice 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author linbotao
* @date 2014年9月4日 下午2:23:51 
*
 */
@Service("excelService")
public class ExcelServiceImpl implements ExcelService{

	@Resource
	private FileDescService fileDescService;
	
	@Resource
	private ArchiveService archiveService;
	
	@Resource
	private ArchiveCateService acs;
	
	@Resource
	private ZtreelocationService zs;
	
	private Archive archive;
	
	@Override  
    public InputStream getExcelInputStream(String id) {  
		
		List<FileDescription> fileList = fileDescService.findFileDescById(Integer.parseInt(id));
		
        //将OutputStream转化为InputStream  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  	
        putDataOnOutputStream(out,fileList);  
        return new ByteArrayInputStream(out.toByteArray());  
    }  
  
    private void putDataOnOutputStream(OutputStream os,List list) {  
        jxl.write.Label label;  
        WritableWorkbook workbook;  
        WritableFont font;
        WritableCellFormat cFormat;
        try {  
            workbook = Workbook.createWorkbook(os);  
            WritableSheet sheet = workbook.createSheet("卷内目录", 0);
            sheet.mergeCells(0, 0, 6, 0);
            sheet.setRowView(0, 800);
            font = new WritableFont(WritableFont.ARIAL,24,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE);  
            cFormat = new WritableCellFormat(font);
            cFormat.setAlignment(Alignment.CENTRE);
            cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            label = new jxl.write.Label(0, 0, "卷 内 目 录",cFormat);
            sheet.addCell(label);
            font = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE);  
            cFormat = new WritableCellFormat(font);  
            sheet.mergeCells(0, 1, 1, 1);
            label = new jxl.write.Label(0, 1, "类别: ",cFormat);
            sheet.addCell(label);
            sheet.mergeCells(2, 1, 3, 1);
            label = new jxl.write.Label(2, 1, "项目: ",cFormat);
            sheet.addCell(label);
            sheet.mergeCells(4, 1, 5, 1);
            label = new jxl.write.Label(4, 1, "区: ",cFormat);
            sheet.addCell(label);
            label = new jxl.write.Label(6, 1, "栋：",cFormat);
            sheet.addCell(label);
            sheet.mergeCells(0, 2, 1, 2);
            label = new jxl.write.Label(0, 2, "盒号: ",cFormat);
            sheet.addCell(label);
            sheet.mergeCells(2, 2, 3, 2);
            label = new jxl.write.Label(2, 2, "档号: ",cFormat);
            sheet.addCell(label);
            sheet.mergeCells(4, 2, 6, 2);
            label = new jxl.write.Label(4, 2, "保存位置: ",cFormat);
            sheet.addCell(label);
            font = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE);  
            cFormat = new WritableCellFormat(font);
            //水平居中对齐
            cFormat.setAlignment(Alignment.CENTRE);
            //竖直方向居中对齐
            cFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //label = new jxl.write.Label(k, i, labelStrs[k]);
            String[] labelStrs = {"序号","文件编号","责任人","文件题名","日期","页次","备注"};
            cFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            
            for(int i=0;i<list.size();i++){
            	FileDescription fd = (FileDescription) list.get(i);
            	if(i==0){
            		for(int k=0;k<labelStrs.length;k++){
            			label = new jxl.write.Label(k, i+3, labelStrs[k],cFormat);
            			sheet.addCell(label);  
            		}
            	  }
            		for(int j=0;j<labelStrs.length;j++){
            			switch(j){
            			case 0:label = new jxl.write.Label(j, i+4, i+1+"",cFormat);sheet.addCell(label); 
            			break;
            			//case 1:label = new jxl.write.Label(j, i+4, formatDid(fd.getCode()),cFormat);sheet.addCell(label); 
            			case 1:label = new jxl.write.Label(j, i+4, fd.getCode(),cFormat);sheet.addCell(label); 
            			break;
            			case 2:label = new jxl.write.Label(j, i+4, fd.getPersonInCharge(),cFormat);sheet.addCell(label);
            			break;
            			case 3:label = new jxl.write.Label(j, i+4, fd.getFileName(),cFormat);sheet.addCell(label);  
            			break;
            			case 4:label = new jxl.write.Label(j, i+4, fd.getWrittenDate()+"",cFormat);sheet.addCell(label);
            			break;
            			case 5:label = new jxl.write.Label(j, i+4, fd.getPages()+"",cFormat);sheet.addCell(label); 
            			break;
            			case 6:label = new jxl.write.Label(j, i+4, fd.getRemarks(),cFormat);sheet.addCell(label); 
            			break;
            			default:
            			}
            			 
            		}
            	}
            workbook.write();  
            workbook.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    private String formatDid(Integer dId){
		String reId = "0000" + dId;
		return reId.substring(reId.length()-4,reId.length());
	}
    
    public void saveFromExcel(){
    	try {
            Workbook book = Workbook.getWorkbook(new File("d:/拆迁档案目录.xls"));
            
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int boxid = 0;
            int temp = 0;
            // 得到单元格
            for(int i = 0,j=1; i < sheet.getRows(); i++,j++) {
            	
            	String boxidStr = sheet.getCell(3,i).getContents().trim();
            	if(boxidStr != ""){
            		boxid = Integer.parseInt(boxidStr);
            	}
            	
            	
            	
            	if(temp != boxid ){
            		archive = new Archive();
            		archive.setDirectoryId(boxid+"");
            		archive.setArchivedYear("2014");
            		archive.setRetentionPeriod("0");
            		archiveService.saveEntity(archive);
            		temp = boxid;
            		j=1;
            	}
            	
            	
            	FileDescription f = new FileDescription();
            	f.setCode(j+"");
            	f.setFileName(sheet.getCell(1,i).getContents());
            	f.setRegistrationTime(new Date(System.currentTimeMillis()));
            	f.setRetentionPeriod("0");
            	f.setSecurityLevel(0);
            	f.setWrittenDate(new Date(System.currentTimeMillis()));
            	f.setArchived(0);
            	f.setStoreWay(0);
            	f.setPages(0);
            	f.setRemarks((sheet.getCell(2,i).getContents()));
            	f.setArchive(archive);
            	fileDescService.saveEntity(f);
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
}
