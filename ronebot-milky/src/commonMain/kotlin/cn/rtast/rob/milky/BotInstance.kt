/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:54 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.milky.event.milky.BotInstanceCreatedEvent
import cn.rtast.rob.milky.event.milky.BotInstanceDisposedEvent
import cn.rtast.rob.milky.milky.MilkyAction
import cn.rtast.rob.milky.milky.MilkyListener
import cn.rtast.rob.milky.util.connectToEventEndpoint
import cn.rtast.rob.scheduler.BotCoroutineScheduler
import cn.rtast.rob.util.IBotManager
import cn.rtast.rob.util.getLogger
import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Bot实例
 */
public class BotInstance internal constructor(
    public val address: String,
    public val accessToken: String?,
    public val listener: MilkyListener,
    public val logLevel: LogLevel,
    public val ignoreSelf: Boolean = true
) : BaseBotInstance {
    public val action: MilkyAction = MilkyAction(this)
    internal val job = SupervisorJob()
    internal val logger = getLogger("[C]").apply { setLoggingLevel(logLevel) }
    internal val scope = CoroutineScope(Dispatchers.Default + job)
    internal val httpScope = CoroutineScope(Dispatchers.Default)
    internal lateinit var webSocketSession: ClientWebSocketSession

    @InternalROneBotApi
    public val httpClient: HttpClient = HttpClient(CIO) {
        install(WebSockets)
    }

    /**
     * 任务调度器
     */
    public val scheduler: BotCoroutineScheduler<BotInstance> = BotCoroutineScheduler(this)

    /**
     * 创建一个Bot实例， 并且在初始化时新开一个线程作为Websocket线程
     * 所有的API调用都是从http发起，所以在整个Bot生命周期内只会额外
     * 开启一个线程， 相比一OneBot11模块性能大幅提升
     */
    override suspend fun createBot(): BotInstance {
        this.dispatchEvent(BotInstanceCreatedEvent(action, this))
        scope.launch { connectToEventEndpoint() }
        return this
    }

    /**
     * 销毁一个Bot实例
     */
    override suspend fun disposeBot() {
        if (::webSocketSession.isInitialized) {
            this.dispatchEvent(BotInstanceDisposedEvent(action, this))
            webSocketSession.close()
            job.cancel()
        } else throw IllegalStateException("Websocket客户端未连接到服务器无法关闭连接")
    }

    /**
     * 阻塞主线程防止自动退出
     */
    public suspend fun join() {
        job.join()
    }
}
