package com.smart.common.enums;

/**
 * 限流类型
 *
 * @author smart
 */

public enum LimitType
{
    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP
}
