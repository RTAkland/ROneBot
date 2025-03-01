/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/1
 */

package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage
import kotlin.reflect.KFunction

interface IReceiverScope<M : IMessage, R: IReceiverScope<M, R>> {
    val message: M
    val receiver: suspend R.() -> Unit
    val command: KFunction<*>
}

interface IPrivateReceiverScope<P: IPrivateMessage, R: IPrivateReceiverScope<P, R>> : IReceiverScope<P, R> {
    override val message: P
    override val receiver: suspend R.() -> Unit
}

interface IGroupReceiverScope<G: IGroupMessage, R: IGroupReceiverScope<G, R>> : IReceiverScope<G, R> {
    override val message: G
    override val  receiver: suspend R.() -> Unit
}