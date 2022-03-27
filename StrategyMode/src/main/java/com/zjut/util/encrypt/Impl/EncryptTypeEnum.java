package com.zjut.util.encrypt.Impl;


import com.zjut.util.encrypt.EncryptStrategy;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/24 10:06
 * @Description: 该类用于 保存文件加密类型的枚举
 */

public enum EncryptTypeEnum {
    /**
     * 自定义加密
     */
    MyEncrypy("MyEncrypy"),
    /**
     * RSA加密
     */
    RSAEncrypt("RSAEncrypt"),
    /**
     * ASE加密
     */
    AESEncrypt("AESEncrypt"),
    /**
     * Base64加密
     */
    Base64Encrypt("Base64Encrypt");

    /**
     * 文件加密类型字段
     */
    private String encryptType;

    @Override
    public String toString() {
        return "EncryptTypeEnum{" +
                "encryptType='" + encryptType + '\'' +
                '}';
    }

    public static void main(String[] args) {
        try {
            Class tt=Class.forName(EncryptTypeEnum.RSAEncrypt.getEncryptClassName());
            EncryptStrategy encryptStrategy= (EncryptStrategy) tt.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    EncryptTypeEnum(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getEncryptClassName() {
        Class classType=RSAEncrypt.getClass();
        String className=classType.getName();
        return className.substring(0,className.lastIndexOf(".")+1)+this.encryptType;
    }
}
