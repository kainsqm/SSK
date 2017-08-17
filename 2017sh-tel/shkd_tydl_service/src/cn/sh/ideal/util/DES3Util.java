package cn.sh.ideal.util;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 3DES加密工具类
 */
public class DES3Util {
	protected static Log log = LogFactory.getLog(DES3Util.class);
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
	public static String encrypt(String plainText){
		Key deskey = null;
		try{
			DESedeKeySpec spec = new DESedeKeySpec(SECRETKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64.encode(encryptData);
		}catch(Exception e){
			log.error("3DES加密"+e);
		}
		return null;
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 */
	public static String decrypt(String encryptText){
		Key deskey = null;
		try{
			DESedeKeySpec spec = new DESedeKeySpec(SECRETKEY.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
			return new String(decryptData, encoding);
		}catch(Exception e){
			log.error("3DES解密"+e);
		}
		return null;
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
			String plainText){
		Key deskey = null;
		try{
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64.encode(encryptData);
		}catch(Exception e){
			log.error("3DES加密"+e);
		}
		return null;
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
	 * @throws Exception
	 */
	public static String decryptString(String secretKey, String iv,
			String encryptText){
		Key deskey = null;
		try{
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
			return new String(decryptData, encoding);
		}catch(Exception e){
			log.error("3DES解密"+e);
		}
		return null;
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
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String decryptStringURLDecoder(
			String encryptText){
		try{
			String retJsonStr = decryptString(SECRETKEY,IV,
					URLDecoder.decode(encryptText));
			return retJsonStr;
		}catch(Exception e){
			log.error("URLEncoder编码解密信息"+e);
		}
		return null;
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
		try{
			String base64Str = encryptString(SECRETKEY,IV,plainText);
			return URLEncoder.encode(base64Str);
		}catch(Exception e){
			log.error("URLEncoder编码加密信息"+e);
		}
		 return null;
	}
}