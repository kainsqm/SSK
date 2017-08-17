package cn.sh.ideal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

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
	private static final String NoPadding="DES/CBC/PKCS5Padding";
	private static byte[] IV = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90,
			(byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	public static String encrypt(String userTel) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		ResourceBundle rb = ResourceBundle.getBundle("appconf");
		String appCode = rb.getString("integralappCode");
		String key = rb.getString("integralkey");
		String integralURL = rb.getString("integralURL");
		String timestamp = DateUtil.getDateStrCompact(new Date());
		String[] str = { appCode, userTel, timestamp };
		Arrays.sort(str);
		String bigStr = str[0] + str[1] + str[2];
		String sign = new SHA1().getDigestOfString(bigStr.getBytes())
				.toLowerCase();
		String data = appCode + "|" + userTel + "|" + timestamp + "|"
				+ sign.toUpperCase();

		System.out.println("data===" + data);
		String sertoken = encrypt(data, key);
		System.out.println("sertoken===" + sertoken);
		integralURL = integralURL.replace("###", URLEncoder.encode(sertoken));
		return integralURL;
	}

	/**
	 * 加密
	 * @param data 待加密的明文
	 * @param key 加密密钥
	 * @return 密文
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
		SecureRandom sr = new SecureRandom();
		DESKeySpec ks = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(ks);
		Cipher cip = Cipher.getInstance(NoPadding);// Cipher.getInstance("DES");
		IvParameterSpec ivSpec = new IvParameterSpec(IV);
		cip.init(Cipher.ENCRYPT_MODE, sk, ivSpec);// IV的方式
		// cip.init(Cipher.ENCRYPT_MODE, sk, sr);//没有传递IV
		return new String(new Base64().encode(cip.doFinal(data
				.getBytes("UTF-8"))));

	}

	/**
	 * 解密
	 * @param data 待解密的密文
	 * @param key 解密密钥
	 * @return 明文
	 * @throws IOException
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		if (data == null) {
			return null;
		}

		byte[] buf = new Base64().decode(data.getBytes("UTF-8"));

		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(NoPadding);
		IvParameterSpec ivSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, securekey, ivSpec);

		// cipher.init(Cipher.DECRYPT_MODE, securekey, sr);//没有传递IV

		return new String(cipher.doFinal(buf));
	}

	public static void main(String[] args) throws Exception {
		/*String key = "ideal123";
		String userTel = "http://www.baidu.com/";
		
		System.out.println(userTel);
		String token = DesUtil.encrypt(userTel, key);
		System.out.println(token);
		String text = DesUtil.decrypt(token, key);
		System.out.println(text);*/
		//System.out.println(DesUtil.encrypt("channel=yx&openid=e694381a9a0a4791&tel=18001797977", "afbcccccee"));
	//System.out.println(DesUtil.encrypt("111111", "shkdzj07112016"));
	
	System.out.println(DesUtil.decrypt("mWFTH8DD/e8s1LHXY6AONQ==", "shkdzj07112016"));
	//System.out.print(DesUtil.decrypt("oGYthuNdTwlrDEApP0QcMdHjasxU", "shanghaigonghuiwx201512"));
//		N4uQapwg5J8=
//		System.out
//				.println(DesUtil
//						.decrypt(
//								"ktrMMU1e09uUSiTxag9IBnJSfR0Nupt170xDtsNDpdYlrJItJgEaTUCIYdd9tU9zX4H2t7Rfm84=",
//								"afbcccccee"));;
								
	//System.out.println(DesUtil.encrypt("oGYthuF7kV1WqGFTX1nhY_MaV3Dg", "idealsh12252015"));
		//System.out.println(DesUtil.decrypt("s4V8hKz4wOc1ODZql1bmAigyMTeajJ1lVZsYhS0Og+g=", "idealsh12252015"));
	}

}
