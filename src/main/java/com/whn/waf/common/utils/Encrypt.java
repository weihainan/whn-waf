package com.whn.waf.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 建议使用 org.apache.commons.codec.digest.DigestUtils<br/>
 * String id = DigestUtils.md5Hex(String.format("%s_%s_%s_%s", application, tenant, ownerId, auditorId));
 *
 * @author weihainan.
 * @since 0.1 created on 2017/2/9.
 */
public final class Encrypt {

    private Encrypt() {
        // empty
    }

    /**
     * 传入文本内容，返回 MD5 (32) 串.
     */
    public static String MD5(final String strText) {
        return encrypt(strText, "MD5");
    }

    /**
     * 传入文本内容，返回 SHA-256 (64)串.
     */
    public static String SHA256(final String strText) {
        return encrypt(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 (128)串.
     */
    public static String SHA512(final String strText) {
        return encrypt(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密.
     */
    private static String encrypt(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte [] byteBuffer = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
}