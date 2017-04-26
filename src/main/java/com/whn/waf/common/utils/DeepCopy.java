package com.whn.waf.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepCopy {

	/**
	 * 深度复制,复制的整个对象图
	 */
	public static Serializable deeplyCopy(Serializable src){
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(src);
			oos.close();
			baos.close();
			
			byte[] bytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			
			return copy ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
}
