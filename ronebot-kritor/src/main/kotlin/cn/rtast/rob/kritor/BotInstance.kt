/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.kritor.util.InternalListener
import cn.rtast.rob.kritor.kritor.KritorAction
import cn.rtast.rob.kritor.kritor.KritorListener
import cn.rtast.rob.kritor.util.authenticate
import cn.rtast.rob.kritor.util.createAuthInterceptor
import io.grpc.Channel
import io.grpc.ClientInterceptors.intercept
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.TimeUnit

class BotInstance internal constructor(
    private val host: String,
    private val port: Int,
    private val account: String,
    private val ticket: String,
    private val listener: KritorListener
) : BaseBotInstance {

    lateinit var interceptedChannel: Channel
    lateinit var channel: ManagedChannel
    internal lateinit var internalListener: InternalListener
    lateinit var action: KritorAction
    override val isActionInitialized = true

    override suspend fun createBot(): BotInstance {
        val channel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .enableRetry()
            .executor(Dispatchers.IO.asExecutor())
            .build()
            .also { channel = it }
        channel.authenticate(account, ticket)
        val authInterceptor = createAuthInterceptor("114514ghpA@")
        interceptedChannel = intercept(channel, authInterceptor)
        action = KritorAction(this)
        internalListener = InternalListener(this, listener).apply { init() }
        return this
    }

    override suspend fun disposeBot() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        } catch (_: InterruptedException) {
            channel.shutdownNow()
        }
    }
}