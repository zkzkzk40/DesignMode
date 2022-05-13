package cn.edu.combinationmode.service.logic;

import cn.edu.combinationmode.model.vo.TreeNodeLink;

import java.util.List;
import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 10:53
 * @Description: 该类用于 TODO
 */
public interface LogicFilter {
    /**
     * 逻辑决策器
     * @author zk
     * @date 2022/5/8 10:54
 	 * @param matterValue 决策值
 	 * @param treeNodeLineInfoList 决策节点
	 * @return java.lang.Long 下一个节点Id
     */
    Long filter(String matterValue, List<TreeNodeLink> treeNodeLineInfoList);
    /**
     * 获取决策值
     * @author zk
     * @date 2022/5/8 10:54
 	 * @param treeId 决策树id
 	 * @param userId 用户id
 	 * @param decisionMatter  决策物料
	 * @return java.lang.String
     */
    String matterValue(Long treeId, String userId, Map<String, String> decisionMatter);
}
