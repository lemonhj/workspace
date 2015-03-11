package com.hlc.archivesmgmt.struts.action;

import java.io.IOException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * BaseAction,������,ר��Ϊ�̳ж���
 * @author linbotao
 *
 * @param <T>
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8063286189149005776L;
	protected GsonBuilder gsonBuilder = new GsonBuilder();	
	
	public abstract T getModel();

	public void prepare() throws Exception {
	}
	
	/**
	 * ��ǰ̨�����ȡ����Ϣ
	 * @param obj
	 * @throws IOException
	 */
	protected void outputJson(ServletResponse response,Object obj) throws IOException{
		
		String str = this.objectToJson(obj);
		outputJson(response,str);
	}
	
	/**
	 * ��ǰ̨�����ȡ����Ϣ
	 * @param json
	 * @throws IOException
	 */
	protected void outputJson(ServletResponse response,String json) throws IOException{
		
		response.setContentType("text/xml;charset=utf-8"); 
		((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
		response.getWriter().print(json);
	}
	
	/**
	 * ������ת����JSON
	 * @param obj
	 * @return
	 */
	protected String objectToJson(Object obj){
		
		Gson gson =  gsonBuilder.create();
		String str = gson.toJson(obj);
		return str;
	}
}