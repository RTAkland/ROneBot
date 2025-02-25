/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.command.arguments.AnyStringArgumentType
import cn.rtast.rob.command.arguments.CharArgumentType
import cn.rtast.rob.command.arguments.getAnyString
import cn.rtast.rob.command.arguments.getChar
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
import cn.rtast.rob.permission.enums.BasicPermission
import cn.rtast.rob.permission.getPermissionManager
import cn.rtast.rob.permission.hasPermission
import cn.rtast.rob.permission.revokePermission
import cn.rtast.rob.permission.setPermission
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.BrigadierCommand
import cn.rtast.rob.util.CommandSource
import cn.rtast.rob.util.Commands
import com.mojang.brigadier.Command
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
            .requires { it.hasPermission(BasicPermission.Admin) }
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
                            AnyStringArgumentType.anyStringType()
                        )
                            .executes {
                                println(it.getAnyString("any"))
                                println(AnyStringArgumentType.getAnyString(it, "any")::class.java)
                                0
                            }
                    )
            ).then(
                LiteralArgumentBuilder.literal<CommandSource>("char")
                    .then(
                        RequiredArgumentBuilder.argument<CommandSource, Char>("char", CharArgumentType.char())
                            .executes {
                                println(CharArgumentType.getChar(it, "char")::class.java)
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
        message.sender.setPermission(3)
        println(message.sender.hasPermission(3))
        message.sender.revokePermission()
        println(message.sender.hasPermission(3))
    }

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
        message.reply(messageChain {
            text("111")
        })
    }
}

suspend fun main() {
    val client = TestClient()
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client).apply {
        println(this)
    }
    instance1.addListeningGroup(985927054)
    ROneBotFactory.getPermissionManager().apply {
        setUserPermission(3458671395.toString(), 3)
    }
    ROneBotFactory.brigadierCommandManager.register(TestBrigadierCommand())
    ROneBotFactory.brigadierCommandManager.register(
        Commands.literal("main")
            .requires { it.hasPermission(114514) }
            .then(
                Commands.argument("test", CharArgumentType.char())
                    .executes {
                        println(it.getChar("test"))
                        Command.SINGLE_SUCCESS
                    }
            ), listOf("main1", "1111")
    )
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}