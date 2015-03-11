package com.hlc.archivesmgmt.struts.comm;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


/**
 * Struts2 类型转换类
 * @author linbotao
 *
 */
public class DateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values,
            Class toClass) {
			String date = values[0];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			}

	@Override
	public String convertToString(Map context, Object o) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format((Date)o);
		}
}
