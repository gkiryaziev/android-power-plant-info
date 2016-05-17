package ua.kiryaziev.PowerPlantInfo;

import java.math.BigInteger;
import java.security.MessageDigest;

import android.util.Base64;

public class MyCrypt {

	/**
	 * Функция возвращает хеш сумму MD5
	 * @param data	Строка для хеширования
	 * @return			Хеш сумма 
	 */
	public static String getMD5(String data) {
		String output = null;
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(data.getBytes(), 0, data.length());
			output = new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * Функция декодирует по алгоритму Base64
	 * @param data	Данные
	 * @return			Текст 
	 */
	public static String decodeBase64(String data) {
		
		byte[] b = Base64.decode(data, Base64.DEFAULT);
		String text = "";
		try {
			text = new String(b, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

}
