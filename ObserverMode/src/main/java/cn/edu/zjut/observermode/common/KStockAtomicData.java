package cn.edu.zjut.observermode.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.min;
import static java.lang.Double.max;
/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 21:02
 * @Description: 该类用于 实现K线图所需原子数据
 */
@Data
public class KStockAtomicData {
    /**
     * 开盘价
     */
    double beginPrice;
    /**
     * 收盘价
     */
    double endPrice;
    /**
     * 最高价
     */
    double maxPrice;
    /**
     * 最底价格
     */
    double minPrice;
    KStockAtomicData(Stock stock){
        beginPrice=stock.getPrice();
        endPrice=stock.getPrice();
        maxPrice=stock.getPrice();
        minPrice=stock.getPrice();
    }
    /**
     * 更新当天的Stock数据
     * @author zk
     * @date 2022/4/6 19:27
 	 * @param stock
     */
    public void updateStock(Stock stock){
        endPrice=stock.getPrice();
        maxPrice=max(maxPrice,stock.getPrice());
        minPrice=min(minPrice,stock.getPrice());
    }
    /**
     * 获取数据集合
     * @author zk
     * @date 2022/4/6 19:27
	 * @return java.util.List<java.lang.Double> List集合
     */
    public List<Double> getAtomicData(){
        List<Double> result = new ArrayList<>();
        result.add(beginPrice);
        result.add(endPrice);
        result.add(maxPrice);
        result.add(minPrice);
        return result;
    }
}
