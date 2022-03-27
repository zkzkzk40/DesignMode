package com.zjut.util;

import com.zjut.common.MyFile;
import com.zjut.util.encrypt.EncryptStrategy;
import com.zjut.util.encrypt.Impl.EncryptTypeEnum;
import com.zjut.util.io.FileOperation;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Author: 张坤
 * @DateTime: 2022/3/23 21:55
 * @Description: 该类用于 加密应用类
 */
@Data
public class EncodingContext {
    private EncryptStrategy encryptStrategy;

    /**
     * 加密文件内容并存储
     * @author zk
     * @date 2022/3/24 16:13
 	 * @param fileTXT
 	 * @param encryptTypeEnum
	 * @return com.zjut.common.MyFile
     */
    public MyFile encryptTxt(byte[] fileTXT, EncryptTypeEnum encryptTypeEnum){
        try {
            Class t=Class.forName(encryptTypeEnum.getEncryptClassName());
            encryptStrategy= (EncryptStrategy) t.newInstance();
            return encryptStrategy.encryptBytes(fileTXT);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密文件内容
     * @author zk
     * @date 2022/3/24 13:14
     * @param myFile 被加密的文件
     */
    public byte[] decryptTxt(MyFile myFile){
        try {
            Class t=Class.forName(myFile.getEncryptTypeEnum().getEncryptClassName());
            encryptStrategy= (EncryptStrategy) t.newInstance();
            System.out.println("加密策略:"+myFile.getEncryptTypeEnum());
            return encryptStrategy.decryptBytes(myFile);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
