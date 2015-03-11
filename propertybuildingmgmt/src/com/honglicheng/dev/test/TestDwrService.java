package com.honglicheng.dev.test;

import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;

@RemoteProxy(creator=SpringCreator.class) 
public class TestDwrService {
    @Autowired  
    private MonthService monthService;  
      
    @RemoteMethod  
    public List<Month> test(){  
        List<Month> months = monthService.findIMonths();  
        return months;  
    }

}
