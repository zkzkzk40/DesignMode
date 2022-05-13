package cn.edu.combinationmode.service.engine;

import cn.edu.combinationmode.service.logic.LogicFilter;
import cn.edu.combinationmode.service.logic.impl.UserAgeFilter;
import cn.edu.combinationmode.service.logic.impl.UserGenderFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 10:53
 * @Description: 该类用于 决策树节点配置类
 */
public class EngineConfig {

    static Map<String, LogicFilter> logicFilterMap;

    static {
        logicFilterMap = new ConcurrentHashMap<>();
        logicFilterMap.put("userAge", new UserAgeFilter());
        logicFilterMap.put("userGender", new UserGenderFilter());
    }

    public Map<String, LogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public void setLogicFilterMap(Map<String, LogicFilter> logicFilterMap) {
        EngineConfig.logicFilterMap = logicFilterMap;
    }

}
