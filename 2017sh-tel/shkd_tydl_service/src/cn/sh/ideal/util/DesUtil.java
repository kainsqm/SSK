package cn.sh.ideal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DesUtil {

	private static byte[] IV = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90,
			(byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	public static final String DESC="DES/CBC/PKCS5Padding";

	/**
	 * 加密
	 * @param data 待加密的明文
	 * @param key 加密密钥
	 * @return 密文 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
			DESKeySpec ks = new DESKeySpec(key.getBytes(Constans.ENCODING));
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(ks);
			Cipher cip = Cipher.getInstance(DESC);
			IvParameterSpec ivSpec = new IvParameterSpec(IV);
			cip.init(Cipher.ENCRYPT_MODE, sk, ivSpec);// IV的方式
			return new String(new Base64().encode(cip.doFinal(data
					.getBytes("UTF-8"))));
	

	}

	/**
	 * 解密
	 * @param data 待解密的密文
	 * @param key 解密密钥
	 * @return 明文
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
			if (data == null) {
				return null;
			}

			byte[] buf = new Base64().decode(data.getBytes(Constans.ENCODING));
			DESKeySpec dks = new DESKeySpec(key.getBytes(Constans.ENCODING));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(DESC);
			IvParameterSpec ivSpec = new IvParameterSpec(IV);
			cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);
			return new String(cipher.doFinal(buf));
	}

}
