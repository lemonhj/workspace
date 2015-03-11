package com.hlc.archivesmgmt.struts;

import com.hlc.archivesmgmt.entity.User;


/**
 * 用户关注接口
 * @author linbotao
 *
 */
public interface UserAware {
	public void setUser(User user);
}
