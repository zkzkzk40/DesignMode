package cn.edu.combinationmode.config;

import cn.edu.combinationmode.model.aggregates.TreeRich;
import cn.edu.combinationmode.model.vo.TreeNode;
import cn.edu.combinationmode.model.vo.TreeNodeLink;
import cn.edu.combinationmode.model.vo.TreeRoot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 13:34
 * @Description: 该类用于 TODO
 */
@Configuration
public class CouponConfig {
    @Bean
    public TreeRich getCoupon(){

        List<TreeNodeLink> treeNodeLinkList_1 = new ArrayList<>();
        // 链接：1->11
        treeNodeLinkList_1.add(new TreeNodeLink(1L,11L,1,"man"));
        // 链接：1->12
        treeNodeLinkList_1.add(new TreeNodeLink(1L,12L,1,"woman"));
        // 节点：1
        TreeNode treeNode_01 = new TreeNode(10001L,1L,1,
                null,"userGender","用户性别[男/女]",treeNodeLinkList_1);

        List<TreeNodeLink> treeNodeLinkList_11 = new ArrayList<>();
        // 链接：11->111
        treeNodeLinkList_11.add(new TreeNodeLink(11L,111L,3,"25"));
        // 链接：11->112
        treeNodeLinkList_11.add(new TreeNodeLink(11L,112L,5,"25"));
        // 节点：11
        TreeNode treeNode_11 = new TreeNode(10001L,11L,1,
                null,"userAge","用户年龄",treeNodeLinkList_11);


        List<TreeNodeLink> treeNodeLinkList_12 = new ArrayList<>();
        // 链接：12->121
        treeNodeLinkList_12.add(new TreeNodeLink(12L,121L,3,"25"));
        // 链接：12->122
        treeNodeLinkList_12.add(new TreeNodeLink(12L,122L,5,"25"));
        // 节点：12
        TreeNode treeNode_12 = new TreeNode(10001L,12L,1,
                null,"userAge","用户年龄",treeNodeLinkList_12);

        // 节点：111
        TreeNode treeNode_111 =
                new TreeNode(10001L,111L,2,"拼多多满五减一");

        // 节点：112
        TreeNode treeNode_112 =
                new TreeNode(10001L,112L,2,"淘宝满五减一");

        // 节点：121
        TreeNode treeNode_121 =
                new TreeNode(10001L,121L,2,"京东满五减一");

        // 节点：122
        TreeNode treeNode_122 =
                new TreeNode(10001L,122L,2,"当当满屋减一");

        // 树根
        TreeRoot treeRoot = new TreeRoot(10001L,1L,"规则决策树");

        Map<Long, TreeNode> treeNodeMap = new HashMap<>();
        treeNodeMap.put(1L, treeNode_01);
        treeNodeMap.put(11L, treeNode_11);
        treeNodeMap.put(12L, treeNode_12);
        treeNodeMap.put(111L, treeNode_111);
        treeNodeMap.put(112L, treeNode_112);
        treeNodeMap.put(121L, treeNode_121);
        treeNodeMap.put(122L, treeNode_122);

        return new TreeRich(treeRoot, treeNodeMap);
    }
}
