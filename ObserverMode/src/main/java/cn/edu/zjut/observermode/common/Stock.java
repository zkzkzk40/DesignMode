package cn.edu.zjut.observermode.common;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:43
 * @Description: 该类用于 股票类
 */
@Data
public class Stock {
    /**
     * 股票名称
     */
    String name;
    /**
     * 股票价格
     */
    double price;
    /**
     * 交易时间
     */
    Date date;

    public Stock(String name, double price, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }
}
