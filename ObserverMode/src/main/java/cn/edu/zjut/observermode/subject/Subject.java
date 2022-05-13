package cn.edu.zjut.observermode.subject;

import cn.edu.zjut.observermode.common.Stock;
import cn.edu.zjut.observermode.observer.Observer;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:16
 * @Description: 该类用于 实现主题接口
 */
public interface Subject {
    /**
     * 添加观察者集合
     * @author zk
     * @date 2022/4/3 14:33
 	 * @param observer 观察者
     */
    public void addObserver(Observer observer);
    /**
     * 将股票交易信息提交给观察者
     * @author zk
     * @date 2022/4/4 9:44 
     */
    public void notifyObservers();
    /**
     * 提供新的股票交易信息
     * @author zk
     * @date 2022/4/3 14:52
 	 * @param stock 股票交易
     */
    public void giveNewStock(Stock stock);
}
