/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import kotlin.uuid.ExperimentalUuidApi

public sealed interface ISession<T: IMessage> {
    public val message: T
    public val args: List<String>
}

public data class GroupSessionStruct<G: IGroupMessage>(
    override val args: List<String>,
    override val message: G
) : ISession<G>

public data class PrivateSessionStruct<P: IPrivateMessage>(
    override val args: List<String>,
    override val message: P
) : ISession<P>
//public interface IPrivateSession<T> : ISession<T> {
//    override val message: IPrivateMessage
//    override val sender: IPrivateSender
//}

//public interface IGroupSession<T> : ISession<T> {
//    override val message: IGroupMessage
//    override val sender: IGroupSender
//}

public fun interface IGroupSession<T: IGroupMessage> {
    public fun consume(message: GroupSessionStruct<T>)
}

public fun interface  IPrivateSession<T: IPrivateMessage> {
    public fun consume(arg: PrivateSessionStruct<T>)
}