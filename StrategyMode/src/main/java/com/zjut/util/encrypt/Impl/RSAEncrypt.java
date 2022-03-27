package com.zjut.util.encrypt.Impl;

import com.zjut.common.MyFile;
import com.zjut.util.encrypt.EncryptStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
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
    /**
     * 加密用私钥
     */
    public static String key="RSA";

    private static final KeyPair keyPair ;

    static {
        keyPair=generateRSAKeyPair(1024);
    }

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
        byte[] result = null;
        if (data != null
                && data.length > 0
                && key != null) {
            // 1、创建Cipher 使用RSA
            try {
                Cipher cipher = Cipher.getInstance(RSAEncrypt.key);
            //设置Key
                cipher.init(Cipher.ENCRYPT_MODE, key);
                result = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return result;
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
        byte[] ret = null;
        if (data != null
                && data.length>0
                && key!=null) {
            // 1、创建Cipher 使用RSA
            try {
                Cipher cipher = Cipher.getInstance(RSAEncrypt.key);
                //设置Key
                cipher.init(Cipher.DECRYPT_MODE,key);
                ret = cipher.doFinal(data);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Test
    public void test(){

        String str="RSA加密算法";
        KeyPair keyPair=generateRSAKeyPair(512);
        RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();

        //私钥加密,公钥解密
        byte[] enStr=rsaEncrypt(str.getBytes(), keyPair.getPrivate());
        System.out.println(new String(rsaDecrypt(enStr, keyPair.getPublic())));
        //公钥加密,私钥解密
        enStr=rsaEncrypt(str.getBytes(), keyPair.getPublic());
        System.out.println(new String(rsaDecrypt(enStr, keyPair.getPrivate())));

    }
    @Test
    public void test2(){

    }
    @Test
    public void test3(){

        String str="RSA加密算法";
        byte[] bytes=str.getBytes();

        MyFile myFile=encryptBytes(bytes);
        System.out.println(new String(decryptBytes(myFile)));
    }
    public static void main(String[] args) {
        try {
            String src = "cakin24 security rsa";
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
            System.out.println("Public Key : " + rsaPublicKey.getEncoded());
            System.out.println("Private Key : " + rsaPrivateKey.getEncoded());
            //2.私钥加密、公钥解密——加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("私钥加密、公钥解密——加密 : " + result);
            //3.私钥加密、公钥解密——解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密、公钥解密——解密：" + new String(result));
            //4.公钥加密、私钥解密——加密
            x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(src.getBytes());
            System.out.println("公钥加密、私钥解密——加密 : " + result);
            //5.公钥加密、私钥解密——解密
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(result);
            System.out.println("公钥加密、私钥解密——解密：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
