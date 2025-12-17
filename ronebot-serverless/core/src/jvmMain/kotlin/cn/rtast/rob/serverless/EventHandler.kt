///*
// * Copyright © 2025 RTAkland
// * Date: 2025/12/16 21:16
// * Open Source Under Apache-2.0 License
// * https://www.apache.org/licenses/LICENSE-2.0
// */
//
//
//@file:OptIn(InternalROneBotApi::class)
//
//package cn.rtast.rob.serverless
//
//import cn.rtast.rob.annotations.InternalROneBotApi
//import cn.rtast.rob.event.raw.message.GroupMessage
//import cn.rtast.rob.event.raw.message.text
//import cn.rtast.rob.onebot.OneBotListener
//import cn.rtast.rob.util.MessageHandler
//import cn.rtast.rob.util.ws.processIncomingMessage
//import com.microsoft.azure.functions.HttpMethod
//import com.microsoft.azure.functions.HttpRequestMessage
//import com.microsoft.azure.functions.HttpResponseMessage
//import com.microsoft.azure.functions.HttpStatus
//import com.microsoft.azure.functions.annotation.AuthorizationLevel
//import com.microsoft.azure.functions.annotation.FunctionName
//import com.microsoft.azure.functions.annotation.HttpTrigger
//import kotlin.time.Duration.Companion.seconds
//
//@Suppress("FunctionName")
//@FunctionName("eventHandler")
//public fun _eventHandler(
//    @HttpTrigger(
//        name = "req",
//        route = "/handler",
//        methods = [HttpMethod.POST],
//        authLevel = AuthorizationLevel.ANONYMOUS
//    ) request: HttpRequestMessage<String>
//) : HttpResponseMessage {
//    val listener = object : OneBotListener {
//        override suspend fun onGroupMessage(message: GroupMessage) {
//            println(message.text)
//        }
//    }
//    val bot = createBot(listener)
//    val handler = MessageHandler(bot)
//    processIncomingMessage(bot, listener, request.body, 0.seconds, handler)
//    return request.createResponseBuilder(HttpStatus.OK).body("OK").build()
//}