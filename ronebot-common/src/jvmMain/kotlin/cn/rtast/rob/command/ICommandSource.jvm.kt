/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.entity.IFiredUser
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.enums.BrigadierMessageType

/**
 * 指令上下文接口
 */
public interface ICommandSource {
    /**
     * 当前Bot实例
     */
    public val botInstance: BaseBotInstance

    /**
     * 两种消息类型的父类
     */
    public val message: IMessage

    /**
     * 消息类型
     */
    public val messageType: BrigadierMessageType

    /**
     * 群聊消息(可能为空)
     */
    public val groupMessage: IGroupMessage?

    /**
     * 私聊消息(可能为空)
     */
    public val privateMessage: IPrivateMessage?

    /**
     * 触发命令的用户
     */
    public val firedUser: IFiredUser
}