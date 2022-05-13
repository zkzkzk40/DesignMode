package cn.edu.combinationmode.controller;
import cn.edu.combinationmode.model.aggregates.TreeRich;
import cn.edu.combinationmode.model.vo.EngineResult;
import cn.edu.combinationmode.model.vo.UserInfo;
import cn.edu.combinationmode.service.engine.impl.TreeEngineHandle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/8 12:35
 * @Description: 该类用于 营销类,发送优惠券
 */
@Slf4j
@RestController
public class MarketingController {

    @Autowired
    TreeRich treeRich;

    @PostMapping("/getCoupon")
    public EngineResult getCoupon(@RequestBody UserInfo userInfo){
        log.info("用户信息:{}",userInfo);
        Map<String, String> decisionMatter = new HashMap<>();
        decisionMatter.put("gender", userInfo.getGender());
        decisionMatter.put("age", userInfo.getAge());
        EngineResult result = new TreeEngineHandle().process(10001L, "Oli09pLkdjh", treeRich, decisionMatter);
        return result;
    }
}
