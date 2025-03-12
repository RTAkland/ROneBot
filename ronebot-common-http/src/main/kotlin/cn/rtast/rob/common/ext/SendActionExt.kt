/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.common.ext

import cn.rtast.rob.SendAction

/**
 * 对原本的[cn.rtast.rob.SendAction]进行了拓展,
 * 让其可以使用http的方式调用suspend方法
 */
public interface SendActionExt : SendAction {
    /**
     * 用HTTP的方式发送数据[payload]到指定端点[api]
     */
    public suspend fun send(api: String, payload: Any?): String

    override suspend fun send(message: String) {}

    override suspend fun send(message: Any) {}
}