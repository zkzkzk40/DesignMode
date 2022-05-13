package cn.edu.combinationmode.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/7 23:34
 * @Description: 该类用于 树根信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeRoot {
    /**
     * 规则树ID
     */
    private Long treeId;
    /**
     * 规则树根ID
     */
    private Long treeRootNodeId;
    /**
     * 规则树名称
     */
    private String treeName;
}
