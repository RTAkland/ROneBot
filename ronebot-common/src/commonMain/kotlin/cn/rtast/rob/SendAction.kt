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
public interface SendAction<out T : BaseBotInstance> {
    /**
     * 只能发送文本数据的方法
     */
    public suspend fun send(message: String)
}