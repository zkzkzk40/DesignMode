package cn.edu.combinationmode.model.aggregates;

import cn.edu.combinationmode.model.vo.TreeNode;
import cn.edu.combinationmode.model.vo.TreeRoot;
import lombok.Data;

import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/7 23:39
 * @Description: 该类用于 聚合对象，包含组织树信息
 */
@Data
public class TreeRich {
    /**
     * 树根信息
     */
    private TreeRoot treeRoot;
    /**
     * 树节点ID -> 子节点
     */
    private Map<Long, TreeNode> treeNodeMap;

    public TreeRich(TreeRoot treeRoot, Map<Long, TreeNode> treeNodeMap) {
        this.treeRoot = treeRoot;
        this.treeNodeMap = treeNodeMap;
    }
}
