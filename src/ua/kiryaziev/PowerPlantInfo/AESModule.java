package ua.kiryaziev.PowerPlantInfo;

import java.io.File;
import java.io.FileInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.os.Environment;
import android.util.Base64;

public class AESModule {

	SecretKeySpec keySpec;
	IvParameterSpec ivSpec;
	Cipher cipher;

	public AESModule(String AESKeyFileName, String iv) {
		try {

			byte[] IvByteArray = iv.getBytes();
			keySpec = new SecretKeySpec(getKeyFromFile(AESKeyFileName), "AES");
			ivSpec = new IvParameterSpec(IvByteArray);
			cipher = Cipher.getInstance("AES/CBC/NoPadding");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public byte[] getKeyFromFile(String fileName) {
		try {
			// getting key from file
			File sdcard = Environment.getExternalStorageDirectory();
			File f = new File(sdcard, fileName);
			FileInputStream fis = new FileInputStream(f);
			byte[] AESKeyByteArray = new byte[(int) f.length()];
			fis.read(AESKeyByteArray);
			fis.close();
			return AESKeyByteArray;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String AESEncryptB64(String DecryptedString) {
		try {

			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encData = cipher.doFinal(padString(DecryptedString).getBytes());
			return new String(Base64.encode(encData, Base64.DEFAULT), "UTF-8");			

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String AESDecryptB64(String AESEncryptedStringB64) {
		try {

			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] dec_b64 = Base64.decode(AESEncryptedStringB64, Base64.DEFAULT);
			byte[] decData = cipher.doFinal(dec_b64);
			return new String(decData, "UTF-8");

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int x = source.length() % size;
		int padLength = size - x;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}

}