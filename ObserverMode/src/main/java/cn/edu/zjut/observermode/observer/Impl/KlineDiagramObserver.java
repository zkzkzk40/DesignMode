package cn.edu.zjut.observermode.observer.Impl;

import cn.edu.zjut.observermode.controller.WebSocketServer;
import cn.edu.zjut.observermode.common.KStockAllData;
import cn.edu.zjut.observermode.common.Stock;
import cn.edu.zjut.observermode.observer.Observer;
import cn.edu.zjut.observermode.subject.Impl.StockExchangeCenter;
import cn.edu.zjut.observermode.subject.Subject;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import static java.lang.Thread.sleep;


/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 14:41
 * @Description: 该类用于 K线图的模拟
 * Echarts所需信息如下
 * option = {
 *   xAxis: {
 *     data: ['2017-10-24', '2017-10-25', '2017-10-26', '2017-10-27']
 *   },
 *   yAxis: {},
 *   series: [
 *     {
 *       type: 'candlestick',
 *       data: [
 *         [20, 34, 10, 38], 开盘加 收盘价 最低点 最高点
 *         [40, 35, 30, 50],
 *         [31, 38, 33, 44],
 *         [38, 15, 5, 42]
 *       ]
 *     }
 *   ]
 * };
 */
public class KlineDiagramObserver implements Observer {

    KStockAllData kStockAllData=new KStockAllData();

    Subject subject;


    @Override
    public void getChangeInfo(Stock stock) {
        Date date=stock.getDate();
        kStockAllData.addNewStock(stock);
        WebSocketServer.sendKlineInfo(kStockAllData);
    }

    public KlineDiagramObserver(Subject subject) {
        this.subject = subject;
        subject.addObserver(this);
    }

    public static void main(String[] args) {
        StockExchangeCenter stockExchangeCenter=new StockExchangeCenter();
        KlineDiagramObserver klineDiagramObserver=new KlineDiagramObserver(stockExchangeCenter);
        Calendar calendar=Calendar.getInstance();

        Stock stock=new Stock("茅台",100,calendar.getTime());
        stockExchangeCenter.giveNewStock(stock);
        stockExchangeCenter.notifyObservers();

        calendar.add(Calendar.MINUTE,1);

        Stock stock2=new Stock("茅台",120,calendar.getTime());
        stockExchangeCenter.giveNewStock(stock2);
        stockExchangeCenter.notifyObservers();

        calendar.add(Calendar.DAY_OF_MONTH,1);

        Stock stock3=new Stock("茅台",110,calendar.getTime());
        stockExchangeCenter.giveNewStock(stock3);
        stockExchangeCenter.notifyObservers();

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("xData",klineDiagramObserver.kStockAllData.getKStockAtomicDataVector());
        System.out.println(jsonObject.toJSONString());
    }
}
