/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob

/**
 * 这个接口并没有任何作用仅仅是为了抑制
 * IDE的非suspend接口的警告
 */
interface SendAction {
    /**
     * 定义一个可以发送任何类型数据的方法
     */
    suspend fun send(message: Any)

    /**
     * 只能发送文本数据的方法
     */
    suspend fun send(message: String)
}