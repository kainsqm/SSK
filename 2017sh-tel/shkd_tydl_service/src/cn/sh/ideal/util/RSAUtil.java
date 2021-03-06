package cn.sh.ideal.util;
/** 
 *  
 */  
  
import java.io.ByteArrayOutputStream;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.math.BigInteger;  
import java.security.KeyFactory;  
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.SecureRandom;  
import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.RSAPrivateKeySpec;  
import java.security.spec.RSAPublicKeySpec;  
  
import javax.crypto.Cipher;  

import org.apache.log4j.Logger;

import cn.sh.ideal.controller.UserController;
  
/** 
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 
 * 
 *  
 */  
public class RSAUtil {  
	private static Logger log = Logger.getLogger(RSAUtil.class);
    	  //String aa = RSAUtil.getClass().getResource("").toString();

    /** 
     * * 生成密钥对 * 
     *  
     * @return KeyPair * 
     * @throws EncryptException 
     */  
    public  KeyPair generateKeyPair(){  
    	 KeyPair keyPair=null;
        try {  
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低  
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());  
            keyPair= keyPairGen.generateKeyPair();   
           
        } catch (Exception e) {  
        	log.error("RSAUtil.generateKeyPair出现异常："+e.getMessage(),e);
        }  
        return keyPair;  
    }  
  
   /* public  KeyPair getKeyPair() throws Exception {  
    	String allPath = this.getClass().getResource("").toString();
    	String path = allPath.substring(6,allPath.indexOf("/classes")+1);
    	RSAKeyStore=path;
        FileInputStream fis = null;  
        ObjectInputStream oos = null; 
        KeyPair kp =null;
        try {
        	  fis = new FileInputStream(RSAKeyStore);  
              oos = new ObjectInputStream(fis); 
              kp= (KeyPair) oos.readObject();  
		} finally{
			oos.close();  
	        fis.close(); 
		}
       
         
        return kp;  
    }  
    
    public  void saveKeyPair(KeyPair kp) throws Exception {  
    	
    	String allPath = this.getClass().getResource("").toString();
    	String path = allPath.substring(6,allPath.indexOf("/classes")+1);
    	RSAKeyStore=path;
        FileOutputStream fos = new FileOutputStream(RSAKeyStore);  
        ObjectOutputStream oos = new ObjectOutputStream(fos);  
        // 生成密钥  
        oos.writeObject(kp);  
        oos.close();  
        fos.close();  
    }  */
  
    /** 
     * * 生成公钥 * 
     *  
     * @param modulus * 
     * @param publicExponent * 
     * @return RSAPublicKey * 
     * @throws Exception 
     */  
    public  RSAPublicKey generateRSAPublicKey(byte[] modulus,  
            byte[] publicExponent) {  
        KeyFactory keyFac = null;  
        RSAPublicKey key=null;
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
        	log.error("RSAUtil.generateRSAPublicKey出现异常："+ex.getMessage(),ex); 
        }  
  
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(  
                modulus), new BigInteger(publicExponent));  
        try {  
        	key=(RSAPublicKey) keyFac.generatePublic(pubKeySpec);  
        } catch (InvalidKeySpecException ex) {  
        	log.error("RSAUtil.generateRSAPublicKey出现异常："+ex.getMessage(),ex); 
        }  
        return key;
    }  
  
    /** 
     * * 生成私钥 * 
     *  
     * @param modulus * 
     * @param privateExponent * 
     * @return RSAPrivateKey * 
     * @throws Exception 
     */  
    public  RSAPrivateKey generateRSAPrivateKey(byte[] modulus,  
            byte[] privateExponent) {  
        KeyFactory keyFac = null;  
        RSAPrivateKey key=null;
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
        	log.error("RSAUtil.generateRSAPrivateKey出现异常："+ex.getMessage(),ex); 
        }  
  
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(  
                modulus), new BigInteger(privateExponent));  
        try {  
        	key=(RSAPrivateKey) keyFac.generatePrivate(priKeySpec);  
        } catch (InvalidKeySpecException ex) {  
        	log.error("RSAUtil.generateRSAPrivateKey出现异常："+ex.getMessage(),ex); 
        }  
        return key;
    }  
  
    /** 
     * * 加密 * 
     *  
     * @param key 
     *            加密的密钥 * 
     * @param data 
     *            待加密的明文数据 * 
     * @return 加密后的数据 * 
     * @throws Exception 
     */  
    public  byte[] encrypt(PublicKey pk, byte[] data){  
    	byte[] raw=null;
        try {  
            Cipher cipher = Cipher.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024  
            // 加密块大小为127  
            // byte,加密后为128个byte;因此共有2个加密块，第一个127  
            // byte第二个为1个byte  
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小  
            int leavedSize = data.length % blockSize;  
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1  
                    : data.length / blockSize;  
            raw = new byte[outputSize * blocksSize];  
            int i = 0;  
            while (data.length - i * blockSize > 0) {  
                if (data.length - i * blockSize > blockSize)  
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i  
                            * outputSize);  
                else  
                    cipher.doFinal(data, i * blockSize, data.length - i  
                            * blockSize, raw, i * outputSize);  
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到  
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了  
                // OutputSize所以只好用dofinal方法。  
  
                i++;  
            }  
            
        } catch (Exception e) {  
        	log.error("RSAUtil.encrypt出现异常："+e.getMessage(),e); 
        }  
        return raw;  
    }  
  
    /** 
     * * 解密 * 
     *  
     * @param key 
     *            解密的密钥 * 
     * @param raw 
     *            已经加密的数据 * 
     * @return 解密后的明文 * 
     * @throws Exception 
     */  
    public  byte[] decrypt(PrivateKey pk, byte[] raw){  
    	byte [] rawbyte=null;
        try {  
            Cipher cipher = Cipher.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            cipher.init(cipher.DECRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();  
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);  
            int j = 0;  
  
            while (raw.length - j * blockSize > 0) {  
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));  
                j++;  
            }  
            rawbyte= bout.toByteArray();  
        } catch (Exception e) {  
        	log.error("RSAUtil.decrypt出现异常："+e.getMessage(),e); 
        }  
        return rawbyte;
    }  
  
    
 
    
    
    
    /** 
     * * * 
     *  
     * @param args * 
     * @throws Exception 
     */  
    public static void main(String[] args){  
    	RSAUtil util=new RSAUtil();
        KeyPair rsap = (KeyPair) util.generateKeyPair();  
        String test = "hello world";  
        byte[] en_test = util.encrypt(rsap.getPublic(), test.getBytes());  
        byte[] de_test = util.decrypt(rsap.getPrivate(), en_test);  
        System.out.println(new String(de_test));  
    }  
}  