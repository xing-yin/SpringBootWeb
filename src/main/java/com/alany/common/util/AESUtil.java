package com.alany.common.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 对称加密
 */
@Slf4j
public class AESUtil {

    /**
     * 加解密 key
     */
    public static final String AES_KEY = "da6f9a78-4174-471c-9624-0dc29e24a29e";

    /**
     * 加密
     *
     * @param key     加密key
     * @param content 待加密内容
     * @return
     */
    public static String encryptAES(String key, String content) {
        String aesEncode = null;
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keygen.init(128, secureRandom);
            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            SecretKey secretKey = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] byteEncode = content.getBytes("utf-8");
            byte[] byteAES = cipher.doFinal(byteEncode);
            aesEncode = new BASE64Encoder().encode(byteAES);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AESEncode error: {},content is:{}", e.getMessage(), content);
        }
        return aesEncode;
    }

    /**
     * 解密
     *
     * @param encodekey 加密key
     * @param content   待解密内容
     * @return
     */
    public static String decryptAES(String encodekey, String content) {
        String aesDecode = null;
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodekey.getBytes());
            keygen.init(128, secureRandom);
            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            byte[] byteDecode = cipher.doFinal(byteContent);
            aesDecode = new String(byteDecode, "utf-8");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("AESDecode error: {},content:{}", e.getMessage(), content);
        }
        return aesDecode;
    }

}
