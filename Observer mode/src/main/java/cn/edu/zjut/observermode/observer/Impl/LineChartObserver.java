package cn.edu.zjut.observermode.observer.Impl;

import cn.edu.zjut.observermode.common.Stock;
import cn.edu.zjut.observermode.controller.WebSocketServer;
import cn.edu.zjut.observermode.observer.Observer;
import cn.edu.zjut.observermode.subject.Impl.StockExchangeCenter;
import cn.edu.zjut.observermode.subject.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 15:05
 * @Description: 该类用于 折线图的模拟
 * Echarts 所需信息如下
 * option = {
 *   xAxis: {
 *     type: 'category',
 *     data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
 *   },
 *   yAxis: {
 *     type: 'value'
 *   },
 *   series: [
 *     {
 *       data: [150, 230, 224, 218, 135, 147, 260],
 *       type: 'line'
 *     }
 *   ]
 * };
 */
public class LineChartObserver implements Observer {

    List<String> time;
    List<Double> price;
    Subject subject;
    static SimpleDateFormat lineDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void getChangeInfo(Stock stock) {
        time.add(lineDateFormat.format(stock.getDate()));
        price.add(stock.getPrice());
        WebSocketServer.sendSimplelineInfo(time,price);
    }

    public LineChartObserver(Subject subject) {
        time=new ArrayList<>();
        price=new ArrayList<>();
        this.subject = subject;
        subject.addObserver(this);
    }

    public static void main(String[] args) {
        StockExchangeCenter stockExchangeCenter=new StockExchangeCenter();
        LineChartObserver lineDiagramObserver=new LineChartObserver(stockExchangeCenter);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat klineDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Stock stock=new Stock("茅台",100,calendar.getTime());

        stockExchangeCenter.giveNewStock(stock);
        stockExchangeCenter.notifyObservers();

        calendar.add(Calendar.DAY_OF_MONTH,1);
        Stock stock2=new Stock("茅台",120,calendar.getTime());
        stockExchangeCenter.giveNewStock(stock2);
        stockExchangeCenter.notifyObservers();

        calendar.add(Calendar.MINUTE,1);
        Stock stock3=new Stock("茅台",110,calendar.getTime());
        stockExchangeCenter.giveNewStock(stock3);
        stockExchangeCenter.notifyObservers();
    }
}
