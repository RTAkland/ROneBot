/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/14
 */


package cn.rtast.rob.entity

/**
 * 消息链的父类接口
 */
public interface IMessageChain {

    /**
     * 判断消息链是否为空
     */
    public val isEmpty: Boolean

    /**
     * 消息链中有多少元素
     */
    public val size: Int
}