package com.honglicheng.dev.basic.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UUIDGenerator implements IdentifierGenerator{
	private static int sn = 0;

	public Serializable generate(SessionImplementor sessionImplementor, Object o)throws HibernateException {
		return getNewID();
	}

	
	public synchronized static String getNewID() {
		if(sn>=9999) {
			sn = 0;
		}
		// ��4��2��2ʱ2��2��2����3����4��21λ
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) 
				+ new java.text.DecimalFormat("0000").format( sn++ );
	}

}