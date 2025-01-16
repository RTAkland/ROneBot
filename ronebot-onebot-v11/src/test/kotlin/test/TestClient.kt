/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.command.arguments.AnyStringTypeArgument
import cn.rtast.rob.command.arguments.CharTypeArgument
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.enums.QQFace
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.BrigadierCommand
import cn.rtast.rob.util.CommandSource
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    override suspend fun onWebsocketErrorEvent(event: ErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
    ACommand()
)

class TestBrigadierCommand : BrigadierCommand() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun register(dispatcher: CommandDispatcher<CommandSource>) {
        val root = LiteralArgumentBuilder.literal<CommandSource>("/foo")
            .then(
                RequiredArgumentBuilder.argument<CommandSource, String>("bar", StringArgumentType.string())
                    .executes {
                        scope.launch {
                            try {
                                println(StringArgumentType.getString(it, "bar"))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        0
                    }
            )
            .then(
                LiteralArgumentBuilder.literal<CommandSource>("ss")
                    .then(
                        RequiredArgumentBuilder.argument<CommandSource, Any>(
                            "any",
                            AnyStringTypeArgument.anyStringType()
                        )
                            .executes {
                                println(AnyStringTypeArgument.getAnyString(it, "any")::class.java)
                                0
                            }
                    )
            ).then(
                LiteralArgumentBuilder.literal<CommandSource>("char")
                    .then(
                        RequiredArgumentBuilder.argument<CommandSource, Char>("char", CharTypeArgument.chatType())
                            .executes {
                                println(CharTypeArgument.getChar(it, "char")::class.java)
                                0
                            }
                    )
            )
        dispatcher.register(root)
        dispatcher.register(LiteralArgumentBuilder.literal<CommandSource>("/test").redirect(root.build()))
    }
}

class ACommand : BaseCommand() {
    override val commandNames = listOf("/1")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println(message.action.joinFriendFaceChain(message.sender.userId, 754571597L, QQFace.AoMan))
        println(message.message)
    }
}

suspend fun main() {
    val client = TestClient()
//    val wsAddress = "ws://127.0.0.1:4646"
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client).apply {
        println(this)
    }
    ROneBotFactory.brigadierCommandManager.register(TestBrigadierCommand())
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}