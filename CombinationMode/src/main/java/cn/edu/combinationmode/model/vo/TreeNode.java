package cn.edu.combinationmode.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/7 23:36
 * @Description: 该类用于 树节点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {

    /**
     * 规则树ID
     */
    private Long treeId;
    /**
     *规则树节点ID
     */
    private Long treeNodeId;
    /**
     *节点类型；1子叶、2果实
     */
    private Integer nodeType;
    /**
     *节点值[nodeType=2]；果实值
     */
    private String nodeValue;
    /**
     *规则Key
     */
    private String ruleKey;
    /**
     *规则描述
     */
    private String ruleDesc;
    /**
     *节点链路
     */
    private List<TreeNodeLink> treeNodeLinkList;

    public TreeNode(Long treeId, Long treeNodeId, Integer nodeType, String nodeValue) {
        this.treeId = treeId;
        this.treeNodeId = treeNodeId;
        this.nodeType = nodeType;
        this.nodeValue = nodeValue;
    }
}

