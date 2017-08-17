package cn.sh.ideal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 */
public class DES3Util {
	
	// 密钥
	private final static String SECRETKEY = "btohblkhjejho401fx4604fs";
	// 向量
	private final static String IV = "3267hisc";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @return
	 */
	public static String encrypt(String plainText) {
		Key deskey = null;
		DESedeKeySpec spec;
		byte[] encryptData={};
		try {
			spec = new DESedeKeySpec(SECRETKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			encryptData= cipher.doFinal(plainText.getBytes(encoding));
		} catch (Exception e) {
		}	 
		return Base64.encode(encryptData);
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String decrypt(String encryptText) throws UnsupportedEncodingException{
		Key deskey = null;
		byte[] decryptData={};
		try {
			DESedeKeySpec spec = new DESedeKeySpec(SECRETKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			decryptData = cipher.doFinal(Base64.decode(encryptText));		
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return new String(decryptData, encoding);
	}

	/**
	 * 3DES加密
	 * 
	 * @param secretKey
	 *            秘钥
	 * @param iv
	 *            偏移向量
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encryptString(String secretKey, String iv,
			String plainText)  {
		Key deskey = null;
		byte[] encryptData={};
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			encryptData = cipher.doFinal(plainText.getBytes(encoding));	
		} catch (Exception e) {

		}
		return Base64.encode(encryptData);
	}

	/**
	 * 3DES解密
	 * 
	 * @param secretKey
	 *            秘钥
	 * @param iv
	 *            偏移向量
	 * @param encryptText
	 *            密文
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static String decryptString(String secretKey, String iv,
			String encryptText) throws UnsupportedEncodingException{
		Key deskey = null;
		byte[] decryptData={};
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			decryptData = cipher.doFinal(Base64.decode(encryptText));
		} catch (Exception e) {

		}
		return new String(decryptData, encoding);
	}

	/**
	 *  URLEncoder编码解密信息
	 * 
	 * @param secretKey
	 *            秘钥
	 * @param iv
	 *            偏移向量
	 * @param encryptText
	 *            密文
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String decryptStringURLDecoder(
			String encryptText) throws UnsupportedEncodingException {
		String retJsonStr = decryptString(SECRETKEY,IV,
				URLDecoder.decode(encryptText));
		return retJsonStr;
	}

	/**
	 * URLEncoder编码加密信息
	 * 
	 * @param secretKey
	 * @param iv
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String encryptStringURLEncoder(
			String plainText){
		String base64Str = encryptString(SECRETKEY,IV, plainText);
		return URLEncoder.encode(base64Str);
	}


	public static void main(String[] args) throws Exception {
		               //xsGDh2A2GvovuqFzVEGkn7qJ7kO5XqShpYya+OQ0bgTR89SDlUgtT6QP/KEx RRrb
		               //xsGDh2A2GvovuqFzVEGkn7qJ7kO5XqShpYya+OQ0bgTR89SDlUgtT6QP/KEx RRrb
		 String str = "xsGDh2A2GvovuqFzVEGkn7qJ7kO5XqShpYya%2BOQ0bgTR89SDlUgtT6QP%2FKEx+RRrb";
		 System.out.println(decryptStringURLDecoder(str));
	}
	
	
}