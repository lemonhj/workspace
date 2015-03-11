package com.hlc.archivesmgmt.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ���ݹ�����
 * @author linbotao
 *
 */
public class DataUtil {
	/**
	 * ��ȸ���(������������ͼ)
	 */
	public static Serializable deepCopy(Serializable src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// ���������
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// ���л�����
			oos.writeObject(src);
			oos.close();
			baos.close();
			byte[] bytes = baos.toByteArray();
			// �ֽ�����������
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			// ����������
			ObjectInputStream ois = new ObjectInputStream(bais);
			// �����л�����
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
