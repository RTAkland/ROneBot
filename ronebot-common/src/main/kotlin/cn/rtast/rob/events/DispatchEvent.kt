/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package cn.rtast.rob.events

import cn.rtast.rob.SendAction

interface DispatchEvent<A: SendAction> {
    val action: A
}