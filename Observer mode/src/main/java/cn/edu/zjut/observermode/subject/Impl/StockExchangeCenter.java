package cn.edu.zjut.observermode.subject.Impl;

import cn.edu.zjut.observermode.common.Stock;
import cn.edu.zjut.observermode.observer.Observer;
import cn.edu.zjut.observermode.subject.Subject;

import java.util.ArrayList;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:23
 * @Description: 该类用于 模拟股票交易所并实现Observe接口
 */
public class StockExchangeCenter implements Subject {
    boolean change;
    Stock stock;
    /**
     * 存放观察者集合,非线程安全
     */
    ArrayList<Observer> arrayList;

    public StockExchangeCenter() {
        arrayList=new ArrayList<>();
        change=false;
        stock =null;
    }

    @Override
    public void addObserver(Observer observer) {
        if(!arrayList.contains(observer)){
            arrayList.add(observer);
        }
    }

    @Override
    public void notifyObservers() {
        arrayList.forEach(item-> item.getChangeInfo(stock));
    }

    @Override
    public void giveNewStock(Stock stock) {
        if(stock.equals(this.stock)){
            change=false;
        }else{
            this.stock = stock;
            change=true;
        }
        this.notifyObservers();
    }

}
