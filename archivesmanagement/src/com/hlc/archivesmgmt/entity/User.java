package com.hlc.archivesmgmt.entity;


/**
 * �û�ʵ��
 * @author linbotao
 *
 */
public class User {
	/* id */
	private Integer id;
	
	/*�û���*/
	private String username;
	
	/* ���� */
	private String password ;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}