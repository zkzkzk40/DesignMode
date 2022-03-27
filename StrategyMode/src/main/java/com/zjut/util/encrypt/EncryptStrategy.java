package com.zjut.util.encrypt;

import com.zjut.common.MyFile;

import java.io.File;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/23 17:28
 * @Description: 该类用于 加密工具父接口
 */
public interface EncryptStrategy {
    /**
     * 加密文件
     * @author zk
     * @date 2022/3/23 17:32
     * @param bytes
     */
    public abstract MyFile encryptBytes(byte[] bytes);
    /**
     * 解密文件
     * @author zk
     * @date 2022/3/23 17:32
     * @param myFile
     * @return byte[]
     */
    public abstract byte[] decryptBytes(MyFile myFile);
}
