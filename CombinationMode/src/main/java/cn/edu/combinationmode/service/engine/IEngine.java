package cn.edu.combinationmode.service.engine;

import cn.edu.combinationmode.model.aggregates.TreeRich;
import cn.edu.combinationmode.model.vo.EngineResult;

import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 10:53
 * @Description: 该类用于 决策树引擎接口
 */
public interface IEngine {

    /**
     * 定义统一的接口操作
     * @author zk
     * @date 2022/5/8 11:59
 	 * @param treeId
 	 * @param userId
 	 * @param treeRich
 	 * @param decisionMatter
	 * @return cn.edu.combinationmode.model.vo.EngineResult
     */
    EngineResult process(final Long treeId, final String userId, TreeRich treeRich, final Map<String, String> decisionMatter);

}
