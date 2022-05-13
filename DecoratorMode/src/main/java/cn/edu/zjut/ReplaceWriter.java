package cn.edu.zjut;

import cn.edu.zjut.exception.FilePathNullException;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 12:36
 * @Description: 该类用于 文本替换基础类
 */
public abstract class ReplaceWriter {
    /**
     * 改变敏感词
     * @author zk
     * @date 2022/5/13 19:43
 	 * @param newSIGN
     */
    protected abstract void changeSIGN(char newSIGN);
    /**
     * 筛选并替换敏感词,即执行敏感词过滤
     * @author zk
     * @date 2022/5/13 19:27
 	 * @param str
	 * @return java.lang.String
     */
    public abstract String filter(String str) throws FilePathNullException;

    /**
     * 添加新的敏感词列表
     * @author zk
     * @date 2022/5/13 19:45
 	 * @param path
     */
    protected abstract void addSensativeWords(String path);
}
