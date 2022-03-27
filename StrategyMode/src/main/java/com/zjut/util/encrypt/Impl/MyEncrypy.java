package com.zjut.util.encrypt.Impl;

import com.zjut.common.MyFile;
import com.zjut.util.encrypt.EncryptStrategy;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/24 22:17
 * @Description: 该类用于 TODO
 */
public class MyEncrypy implements EncryptStrategy {
    @Override
    public MyFile encryptBytes(byte[] bytes) {
        return new MyFile(EncryptTypeEnum.MyEncrypy,bytes);
    }

    @Override
    public byte[] decryptBytes(MyFile myFile) {
        return myFile.getFileTxt();
    }
}
