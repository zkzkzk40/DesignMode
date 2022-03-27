package com.zjut.common;

import com.zjut.util.encrypt.Impl.EncryptTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/24 10:01
 * @Description: 该类用于 保存文件加密后的信息
 */
@Data
public class MyFile implements Serializable {
    /**
     * 文件加密类型字段
     */
    private EncryptTypeEnum encryptTypeEnum;
    /**
     * 原文件路径
     */
    private String filePath;

    /**
     * 文件内容
     */
    private byte[] fileTxt;

    /**
     * 加密解密用秘钥
     */
    private Object key;

    public MyFile(EncryptTypeEnum encryptTypeEnum, String fileExtension, byte[] fileTxt) {
        this.encryptTypeEnum = encryptTypeEnum;
        this.filePath = fileExtension;
        this.fileTxt = fileTxt;
    }

    public MyFile(EncryptTypeEnum encryptTypeEnum, byte[] fileTxt, Object key) {
        this.encryptTypeEnum = encryptTypeEnum;
        this.fileTxt = fileTxt;
        this.key = key;
    }

    public MyFile(EncryptTypeEnum encryptTypeEnum, byte[] fileTxt) {
        this.encryptTypeEnum = encryptTypeEnum;
        this.fileTxt = fileTxt;
    }
}
