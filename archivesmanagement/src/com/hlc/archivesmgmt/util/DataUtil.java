package com.hlc.archivesmgmt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 数据工具类
 * @author linbotao
 *
 */
public class DataUtil {
	/**
	 * 深度复制(复制整个对象图)
	 */
	public static Serializable deepCopy(Serializable src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 对象输出流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 序列化过程
			oos.writeObject(src);
			oos.close();
			baos.close();
			byte[] bytes = baos.toByteArray();
			// 字节数组输入流
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			// 对象输入流
			ObjectInputStream ois = new ObjectInputStream(bais);
			// 反序列化过程
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
