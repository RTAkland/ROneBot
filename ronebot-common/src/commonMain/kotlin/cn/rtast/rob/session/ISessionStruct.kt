/*
 * Copyright © 2025 RTAkland
 * Date: 10/6/25, 2:37 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.session

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.IPrivateMessage

public sealed interface ISessionStruct<T : IMessage> {
    public val message: T
    public val args: List<String>
    public val manager: ISessionManager<*, *, *, *>
}

public data class GroupSessionStruct<G : IGroupMessage>(
    override val args: List<String>,
    override val message: G,
    override val manager: ISessionManager<*, *, G, *>,
) : ISessionStruct<G>

public data class PrivateSessionStruct<P : IPrivateMessage>(
    override val args: List<String>,
    override val message: P,
    override val manager: ISessionManager<*, *, *, P>,
) : ISessionStruct<P>
