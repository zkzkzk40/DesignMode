package cn.edu.combinationmode.service.logic.impl;

import cn.edu.combinationmode.service.logic.BaseLogic;

import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 10:56
 * @Description: 该类用于 树节点逻辑实现类,用于获取用户年龄
 */
public class UserAgeFilter extends BaseLogic {

    @Override
    public String matterValue(Long treeId, String userId, Map<String, String> decisionMatter) {
        return decisionMatter.get("age");
    }

}
