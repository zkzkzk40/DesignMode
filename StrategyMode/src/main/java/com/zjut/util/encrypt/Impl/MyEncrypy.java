package com.zjut.util.encrypt.Impl;

import com.zjut.common.MyFile;
import com.zjut.util.encrypt.EncryptStrategy;

import java.util.Random;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/24 22:17
 * @Description: 该类用于 TODO
 */
public class MyEncrypy implements EncryptStrategy {
    @Override
    public MyFile encryptBytes(byte[] bytes) {
        byte key= (byte) (new Random().nextInt(128));
        byte[] result =new byte[bytes.length];
        for(int i=0;i<bytes.length;i++){
            result[i]= (byte) (bytes[i]+key);
        }
        return new MyFile(EncryptTypeEnum.MyEncrypy,result,key);
    }

    @Override
    public byte[] decryptBytes(MyFile myFile) {
        byte key= (byte) myFile.getKey();
        byte[] result =new byte[myFile.getFileTxt().length];
        for(int i=0;i<result.length;i++){
            result[i]= (byte) (myFile.getFileTxt()[i]-key);
        }
        return result;
    }
}
