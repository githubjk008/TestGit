package com.qingyu.utils;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.qingyu.coder.DESCoder;
import com.qingyu.coder.HmacCoder;
import com.qingyu.coder.MDCoder;
import com.qingyu.coder.RSACoder;
import com.qingyu.coder.SHACoder;

/**
 * 数据加密辅助�?默认编码UTF-8)
 * 
 * @author ShenHuaJie
 * @since 2011-12-31
 */
public final class SecurityUtil {
    private SecurityUtil() {
    }

    /**
     * 默认算法密钥
     */
    private static final byte[] ENCRYPT_KEY = { -11, 0, 105, 7, -22, 26, -49, 87 };

    public static final String  CHARSET     = "UTF-8";

    /**
     * BASE64解码
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static final byte[] decryptBASE64(String key) {
        try {
            return Base64.decodeBase64(key);
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    /**
     * BASE64编码
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static final String encryptBASE64(byte[] key) {
        try {
            return Base64.encodeBase64String(key);
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    /**
     * 数据解密，算法（DES�?
     * 
     * @param cryptData 加密数据
     * @return 解密后的数据
     */
    public static final String decryptDes(String cryptData) {
        return decryptDes(cryptData, ENCRYPT_KEY);
    }

    /**
     * 数据加密，算法（DES�?
     * 
     * @param data 要进行加密的数据
     * @return 加密后的数据
     */
    public static final String encryptDes(String data) {
        return encryptDes(data, ENCRYPT_KEY);
    }

    /**
     * 基于MD5算法的单向加�?
     * 
     * @param strSrc 明文
     * @return 返回密文
     */
    public static final String encryptMd5(String strSrc) {
        String outString = null;
        try {
            outString = Hex.encodeHexString((MDCoder.encodeMD5(strSrc.getBytes(CHARSET))));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return outString;
    }

    /**
     * SHA加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encryptSHA(String data) {
        try {
            return encryptBASE64(SHACoder.encodeSHA256(data.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    /**
     * HMAC加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encryptHMAC(String data) {
        return encryptHMAC(data, ENCRYPT_KEY);
    }

    /**
     * 数据解密，算法（DES�?
     * 
     * @param cryptData 加密数据
     * @return 解密后的数据
     */
    public static final String decryptDes(String cryptData, byte[] key) {
        String decryptedData = null;
        try {
            // 把字符串解码为字节数组，并解�?
            decryptedData = new String(DESCoder.decrypt(decryptBASE64(cryptData), key));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    /**
     * 数据加密，算法（DES�?
     * 
     * @param data 要进行加密的数据
     * @return 加密后的数据
     */
    public static final String encryptDes(String data, byte[] key) {
        String encryptedData = null;
        try {
            // 加密，并把字节数组编码成字符�?
            encryptedData = encryptBASE64(DESCoder.encrypt(data.getBytes(), key));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    /**
     * HMAC加密
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static final String encryptHMAC(String data, byte[] key) {
        try {
            return encryptBASE64(HmacCoder.encodeHmacSHA512(data.getBytes(CHARSET), key));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
    }

    /**
     * RSA签名
     * 
     * @param data 原数�?
     * @return
     */
    public static final String signRSA(String data, String privateKey) {
        try {
            return encryptBASE64(RSACoder.sign(data.getBytes(CHARSET), decryptBASE64(privateKey)));
        } catch (Exception e) {
            throw new RuntimeException("签名错误，错误信息：", e);
        }
    }

    /**
     * RSA验签
     * 
     * @param data 原数�?
     * @return
     */
    public static final boolean verifyRSA(String data, String publicKey, String sign) {
        try {
            return RSACoder.verify(data.getBytes(CHARSET), decryptBASE64(publicKey), decryptBASE64(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签错误，错误信息：", e);
        }
    }

    /**
     * 数据加密，算法（RSA�?
     * 
     * @param data 数据
     * @return 加密后的数据
     */
    public static final String encryptRSAPrivate(String data, String privateKey) {
        try {
            return encryptBASE64(RSACoder.encryptByPrivateKey(data.getBytes(CHARSET), decryptBASE64(privateKey)));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    /**
     * 数据解密，算法（RSA�?
     * 
     * @param cryptData 加密数据
     * @return 解密后的数据
     */
    public static final String decryptRSAPublic(String cryptData, String publicKey) {
        try {
            // 把字符串解码为字节数组，并解�?
            return new String(RSACoder.decryptByPublicKey(decryptBASE64(cryptData), decryptBASE64(publicKey)));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encryptDes("SHJR"));
        System.out.println(decryptDes("INzvw/3Qc4q="));
        System.out.println(encryptMd5("SHJR"));
        System.out.println(encryptSHA("1"));
        Map<String, Object> key = RSACoder.initKey();
        String privateKey = encryptBASE64(RSACoder.getPrivateKey(key));
        String publicKey = encryptBASE64(RSACoder.getPublicKey(key));
        System.out.println(privateKey);
        System.out.println(publicKey);
        String sign = signRSA("132", privateKey);
        System.out.println(sign);
        String encrypt = encryptRSAPrivate("132", privateKey);
        System.out.println(encrypt);
        String org = decryptRSAPublic(encrypt, publicKey);
        System.out.println(org);
        System.out.println(verifyRSA(org, publicKey, sign));

        // System.out.println("-------列出加密服务提供�?----");
        // Provider[] pro = Security.getProviders();
        // for (Provider p : pro) {
        // System.out.println("Provider:" + p.getName() + " - version:" +
        // p.getVersion());
        // System.out.println(p.getInfo());
        // }
        // System.out.println("");
        // System.out.println("-------列出系统支持的消息摘要算法：");
        // for (String s : Security.getAlgorithms("MessageDigest")) {
        // System.out.println(s);
        // }
        // System.out.println("-------列出系统支持的生成公钥和私钥对的算法�?);
        // for (String s : Security.getAlgorithms("KeyPairGenerator")) {
        // System.out.println(s);
        // }
    }
}
