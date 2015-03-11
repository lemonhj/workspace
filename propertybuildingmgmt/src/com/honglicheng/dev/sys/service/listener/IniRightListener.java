package com.honglicheng.dev.sys.service.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.service.RightService;


/**
 * 初始化权限监听器
 */
@Component
public class IniRightListener implements ApplicationListener,ServletContextAware{
	public static final String ALL_RIGHTS = "all_rights" ;
	/* 注入权限service */
	@Resource
	private RightService rightService ;
	
	/* 接受ServletContext */
	private ServletContext servletContext;
	
	public void onApplicationEvent(ApplicationEvent event) {
		Map<String, Right> map = new HashMap<String, Right>();
		if(event instanceof ContextRefreshedEvent){
			List<Right> list = rightService.findAllRights();
			for(Right r : list){
				map.put(r.getRightUrl(), r);
			}
			//将map存放到application范围
			if(servletContext != null){
				servletContext.setAttribute(ALL_RIGHTS, map);
			}
		}
	}
	/**
	 * 通过spring方式注入ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
