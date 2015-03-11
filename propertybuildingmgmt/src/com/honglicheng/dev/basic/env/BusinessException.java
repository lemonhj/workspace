package com.honglicheng.dev.basic.env;
/**
 * 业务逻辑层异常信息类
 *  
 * @author yanglh
 * @since 2014-09-02
 */
public class BusinessException extends RuntimeException {
	public BusinessException(String msg){
		super(msg);
	}
}
