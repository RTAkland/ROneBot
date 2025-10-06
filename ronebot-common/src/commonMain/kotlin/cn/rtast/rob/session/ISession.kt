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
import kotlinx.coroutines.CompletableDeferred
import kotlin.uuid.ExperimentalUuidApi


public sealed interface ISession<T : ISessionStruct<K>, K : IMessage> {
    public val initMessage: K
    public val block: suspend (T) -> Unit

    @Suppress("PropertyName")
    public val __finished: CompletableDeferred<Unit>
}

public class GroupSession<T : IGroupMessage>(
    override val initMessage: T,
    override val block: suspend (GroupSessionStruct<T>) -> Unit,
    public val groupId: Long,
) : ISession<GroupSessionStruct<T>, T> {
    override val __finished: CompletableDeferred<Unit> = CompletableDeferred()
}


public class PrivateSession<T : IPrivateMessage>(
    override val block: suspend (PrivateSessionStruct<T>) -> Unit,
    override val initMessage: T,
) : ISession<PrivateSessionStruct<T>, T> {
    override val __finished: CompletableDeferred<Unit> = CompletableDeferred()
}
