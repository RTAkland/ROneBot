/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import com.mojang.brigadier.CommandDispatcher

public interface IBrigadierCommand<B : ICommandSource> {
    /**
     * 注册指令
     */
    public fun register(dispatcher: CommandDispatcher<B>)
}