/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.event

import cn.rtast.rob.SendAction

public interface DispatchEvent<A : SendAction> {
    public val action: A
}