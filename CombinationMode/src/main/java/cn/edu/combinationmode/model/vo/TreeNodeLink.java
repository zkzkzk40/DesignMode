package cn.edu.combinationmode.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 张坤
 * @DateTime: 2022/5/7 23:35
 * @Description: 该类用于 树节点链接链路
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNodeLink {

    /**
     * 节点From
     */
    private Long nodeIdFrom;
    /**
     * 节点To
     */
    private Long nodeIdTo;
    /**
     * 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围]
     */
    private Integer ruleLimitType;
    /**
     * 限定值
     */
    private String ruleLimitValue;

}

