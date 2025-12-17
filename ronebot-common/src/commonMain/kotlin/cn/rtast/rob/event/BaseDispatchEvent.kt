/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction

public interface BaseDispatchEvent<A : SendAction<BaseBotInstance>> {
    public val action: A
}