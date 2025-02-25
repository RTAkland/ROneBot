/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import com.mojang.brigadier.CommandDispatcher

interface IBrigadierCommand<B : ICommandSource> {
    /**
     * 注册指令
     */
    fun register(dispatcher: CommandDispatcher<B>)
}