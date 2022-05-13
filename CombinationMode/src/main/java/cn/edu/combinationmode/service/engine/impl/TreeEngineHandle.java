package cn.edu.combinationmode.service.engine.impl;

import cn.edu.combinationmode.model.aggregates.TreeRich;
import cn.edu.combinationmode.model.vo.EngineResult;
import cn.edu.combinationmode.model.vo.TreeNode;
import cn.edu.combinationmode.service.engine.EngineBase;

import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 10:59
 * @Description: 该类用于 决策树引擎的实现
 */
public class TreeEngineHandle extends EngineBase {

    @Override
    public EngineResult process(Long treeId, String userId, TreeRich treeRich, Map<String, String> decisionMatter) {
        // 决策流程
        TreeNode treeNode = engineDecisionMaker(treeRich, treeId, userId, decisionMatter);
        // 决策结果
        return new EngineResult(userId, treeId, treeNode.getTreeNodeId(), treeNode.getNodeValue());
    }

}

