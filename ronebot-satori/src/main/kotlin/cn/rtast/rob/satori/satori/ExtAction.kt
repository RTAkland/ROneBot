/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/8
 */


package cn.rtast.rob.satori.satori

interface ExtAction {
    /**
     * 用HTTP的方式发送数据[payload]到指定端点[api]
     */
    suspend fun send(api: String, payload: Any?): String
}