/**
 * 2011-01-11
 */
package com.qingyu.coder;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;


/**
 * MD加密组件
 * 
 * @author ShenHuaJie
 * @version 1.0
 * @since 1.0
 */
public abstract class MDCoder extends SecurityCoder {

    /**
     * MD2加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD2(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD2");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD4加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD4(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD4");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * MD5加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeMD5(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * Tiger加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeTiger(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("Tiger");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * TigerHex加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeTigerHex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeTiger(data);
        // 做十六进制编码处�?
        return new String(Hex.encodeHex(b));
    }

    /**
     * Whirlpool加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeWhirlpool(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("Whirlpool");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * WhirlpoolHex加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeWhirlpoolHex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeWhirlpool(data);
        // 做十六进制编码处�?
        return new String(Hex.encodeHex(b));
    }

    /**
     * GOST3411加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static byte[] encodeGOST3411(byte[] data) throws Exception {
        // 初始化MessageDigest
        MessageDigest md = MessageDigest.getInstance("GOST3411");
        // 执行消息摘要
        return md.digest(data);
    }

    /**
     * GOST3411Hex加密
     * 
     * @param data 待加密数�?
     * @return byte[] 消息摘要
     * @throws Exception
     */
    public static String encodeGOST3411Hex(byte[] data) throws Exception {
        // 执行消息摘要
        byte[] b = encodeGOST3411(data);
        // 做十六进制编码处�?
        return new String(Hex.encodeHex(b));
    }
}
