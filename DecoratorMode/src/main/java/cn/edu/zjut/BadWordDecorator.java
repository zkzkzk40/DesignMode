package cn.edu.zjut;

import cn.edu.zjut.exception.FilePathNullException;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/13 18:31
 * @Description: 该类用于 敏感词过滤 装饰模式
 */
public class BadWordDecorator extends Decorator{

    public BadWordDecorator(ReplaceWriter replaceWriter) {
        super(replaceWriter);
    }

    @Override
    protected void changeSIGN(char newSIGN) {
        super.getReplaceWriter().changeSIGN(newSIGN);
    }


    public BadWordDecorator(ReplaceWriter replaceWriter,char newSign) {
        this(replaceWriter);
        changeSIGN(newSign);
    }

    public BadWordDecorator(ReplaceWriter replaceWriter,String newBadWordsPath) {
        this(replaceWriter);
        super.getReplaceWriter().addSensativeWords(newBadWordsPath);
    }

    @Override
    public String filter(String str) {
        try {
            return super.getReplaceWriter().filter(str);
        } catch (FilePathNullException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void addSensativeWords(String path) {
        super.getReplaceWriter().addSensativeWords(path);
    }

}
