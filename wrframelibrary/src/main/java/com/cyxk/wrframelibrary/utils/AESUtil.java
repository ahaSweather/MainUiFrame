package com.cyxk.wrframelibrary.utils;
import android.util.Log;
import org.apaches.commons.codec.binary.Base64;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密
 */
public class AESUtil {
	public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	public static final String CIPHER_ALGORITHM_AES = "AES";
	public static final String INITIALKEY = "b2f6a8j9r8f6n9g8";
	// 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	private static final String SKEY = "2zcsazd210sweater";// key，可自行修改
	private static final String IVPARAMETER = "2592069203123456";// 偏移量,可自行修改

	/**
	 * 根据参数生成KEY
	 */
	public static SecretKey getKey(String strKey) {
		SecretKey key;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException("Error initializing AES key Cause: " + e);
		}
		System.out.println(key);
		return key;
	}

//	/**
//	 * 文件file进行加密并保存目标在文件encryptedFile中
//	 *
//	 * @param file
//	 *            要加密的文件 如c:/test/srcFile.txt
//	 * @param encryptedFile
//	 *            加密后存放的文件名 如c:/加密后文件.txt
//	 */
//	public static void encryptFile(String file, String encryptedFile, RequestResultListener listener)
//			throws Exception {
//		SecretKeySpec skeySpec = new SecretKeySpec(SKEY.getBytes("ASCII"), "AES");
//		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
//		cipher.init(Cipher.ENCRYPT_MODE,skeySpec);
//		InputStream is = new FileInputStream(file);
//		OutputStream out = new FileOutputStream(encryptedFile);
//		CipherInputStream cis = new CipherInputStream(is, cipher);
//		byte[] buffer = new byte[1024];
//		int r;
//		while ((r = cis.read(buffer)) > 0) {
//			out.write(buffer, 0, r);
//		}
//		listener.onRequestFinish();
//		cis.close();
//		is.close();
//		out.close();
//	}
//
//	/**
//	 * 文件采用AES算法解密文件 文件file进行解密,解密后保存目标在文件decryptedFile中
//	 *
//	 *            已加密的文件 如c:/加密后文件.txt
//	 * @param decryptedFile
//	 *            解密后存放的文件名 如c:/ test
//	 *
//	 */
//	public static void decryptFile(String fileURL, String decryptedFile, RequestResultListener listener)
//			throws Exception {
//		SecretKeySpec skeySpec = new SecretKeySpec(SKEY.getBytes("ASCII"), "AES");
//		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
//		cipher.init(Cipher.DECRYPT_MODE,skeySpec);
//        URL url = new URL(fileURL);
//        InputStream is = url.openStream();
//		OutputStream out = new FileOutputStream(decryptedFile);
//		CipherOutputStream cos = new CipherOutputStream(out, cipher);
//		byte[] buffer = new byte[1024];
//		int r;
//		while ((r = is.read(buffer)) >= 0) {
//			System.out.println();
//			cos.write(buffer, 0, r);
//		}
//		listener.onRequestFinish();
//		cos.close();
//		out.close();
//		is.close();
//	}

	/**
	 * 使用AES 算法 加密，默认模式 AES
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptByNormal(String data) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
		cipher.init(Cipher.ENCRYPT_MODE, getKey(INITIALKEY), sr);
		byte[] bt = cipher.doFinal(data.getBytes());
		String strs = Encodes.encodeHex(bt);
		return strs;
	}

	/**
	 * 使用AES 算法 解密，默认模式 AES
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String detryptByNormal(String data) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
		cipher.init(Cipher.DECRYPT_MODE, getKey(INITIALKEY), sr);
		byte[] res = Encodes.decodeHex(data);
		res = cipher.doFinal(res);
		return new String(res);
	}

	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptByCBC(String data)  {
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(SKEY.getBytes(), "AES");
			IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encryptedData = cipher.doFinal(data.getBytes("utf-8"));
			return Base64.encodeBase64String(encryptedData);
		}catch (Exception e){
			return null;
		}

	}

	/**
	 * 使用AES 算法 解密，默认模式 AES/CBC/PKCS5Padding
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String detryptByCBC(String data) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(SKEY.getBytes("ASCII"), "AES");
			IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] res = Base64.decodeBase64(data);
			byte[] original = cipher.doFinal(res);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			LogUtil.e(Log.getStackTraceString(ex));
			return null;
		}
	}

}
