package com.hlc.archivesmgmt.service;
import java.util.List;

import com.hlc.archivesmgmt.entity.ArchivesCategory;


/**
 * ZtreeService
 */
public interface ZtreeService extends BaseService<ArchivesCategory> {
	
	public List<ArchivesCategory> Ztreeload();
    
    public String delZtreeNodeFace(Integer id);
    
    public String addZtreeNodeFace(ArchivesCategory archivesCategory);
    
    public String updateZtreeNodeFace(ArchivesCategory archivesCategory);
    
}
