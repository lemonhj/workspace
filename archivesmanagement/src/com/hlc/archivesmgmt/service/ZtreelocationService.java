package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.entity.ArchivesLocation;;


/**
 * ZtreelocationService
 */

public interface ZtreelocationService extends BaseService<ArchivesLocation> {
	
      public List<ArchivesLocation> ztreelocationload();
    
      public String dellocZtreeNodeFace(Integer id);
      
      public String addlocZtreeNodeFace(ArchivesLocation archivesLocation);
      
      public String updatelocZtreeNodeFace(ArchivesLocation archivesLocation);
      
      public List findLocaChildNodes(Integer as_filePath_a);
      
      public void addlocZtreeNodeCengFace(ArchivesLocation archivesLocation);
}
