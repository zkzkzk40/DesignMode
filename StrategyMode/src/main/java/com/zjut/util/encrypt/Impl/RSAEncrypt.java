package com.zjut.util.encrypt.Impl;

import com.zjut.common.MyFile;
import com.zjut.util.encrypt.EncryptStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/23 18:25
 * @Description: 该类用于 RSA加密
 */

public class RSAEncrypt implements EncryptStrategy {

    public static String key="RSA";
    /**
     * 加密用钥匙
     */
    private static final KeyPair keyPair ;

    static {
        keyPair=generateRSAKeyPair(1024);
    }
    //加密不能超过117
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //解密时以128为最大长度
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 通过指定的密钥长度生成非对称的密钥对,keySize 推荐使用1024,2048 ，不允许低于1024
     *
     * @param keySize
     * @return java.security.KeyPair
     * @author zk
     * @date 2022/3/23 18:55
     */
    public static KeyPair generateRSAKeyPair(int keySize) {
        KeyPair ret = null;
        try {
            //1、准备生成
            KeyPairGenerator generator = KeyPairGenerator.getInstance(key);
            //2、初始化，设置秘钥长度
            generator.initialize(keySize);
            //3、生成，并且返回
            ret = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * RSA 加密
     * @author zk
     * @date 2022/3/23 18:59
     * @param data
     * @param key
     * @return byte[]
     */
    public static byte[] rsaEncrypt(byte[] data, Key key) {
        if (data != null
                && data.length > 0
                && key != null) {
                // 1、创建Cipher 使用RSA
            try {
                Cipher cipher = Cipher.getInstance(RSAEncrypt.key);
                //设置Key
                cipher.init(Cipher.ENCRYPT_MODE, key);
                //分段加密
                return segmentedEncryptionANDDecryption(data, cipher, MAX_ENCRYPT_BLOCK);
            } catch (NoSuchAlgorithmException | IOException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * RSA 解密
     * @author zk
     * @date 2022/3/23 18:59
     * @param data
     * @param key
     * @return byte[]
     */
    public static byte[] rsaDecrypt(byte[] data ,Key key){
        if (data != null
                && data.length>0
                && key!=null) {
            // 1、创建Cipher 使用RSA
            try {
                Cipher cipher = Cipher.getInstance(RSAEncrypt.key);
                //设置Key
                cipher.init(Cipher.DECRYPT_MODE,key);
                return segmentedEncryptionANDDecryption(data, cipher, MAX_DECRYPT_BLOCK);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 分段加密解密函数
     * @author zk
     * @date 2022/3/28 12:54
 	 * @param data 待操作的字节数组
 	 * @param cipher 已实例化的Cipher类
 	 * @param maxDecryptBlock 偏移量
	 * @return byte[]
     */
    private static byte[] segmentedEncryptionANDDecryption(byte[] data, Cipher cipher, int maxDecryptBlock) throws IllegalBlockSizeException, BadPaddingException, IOException {
        int inputLen = data.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while(inputLen - offLen > 0){
            byte[] cache;
            if(inputLen - offLen > maxDecryptBlock){
                cache = cipher.doFinal(data,offLen, maxDecryptBlock);
            }else{
                cache = cipher.doFinal(data,offLen,inputLen - offLen);
            }
            byteArrayOutputStream.write(cache);
            i++;
            offLen = i* maxDecryptBlock;
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 覆写加密算法,私钥加密公钥解密
     * @author zk
     * @date 2022/3/24 13:52
 	 * @param bytes 待加密字节数组
     */
    @Override
    public MyFile encryptBytes(byte[] bytes) {
        return new MyFile(EncryptTypeEnum.RSAEncrypt,rsaEncrypt(bytes, RSAEncrypt.keyPair.getPrivate()),RSAEncrypt.keyPair);
    }

    /**
     * 覆写解密算法,私钥加密公钥解密
     * @author zk
     * @date 2022/3/24 13:54
 	 * @param myFile
	 * @return byte[]
     */
    @Override
    public byte[] decryptBytes(MyFile myFile) {
        return rsaDecrypt(myFile.getFileTxt(), ((KeyPair) myFile.getKey()).getPublic());
    }
}
