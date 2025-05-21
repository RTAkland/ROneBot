/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:52 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event

import cn.rtast.rob.event.BaseDispatchEvent
import cn.rtast.rob.milky.milky.MilkyAction

public interface MilkyEvent : BaseDispatchEvent<MilkyAction> {
    override val action: MilkyAction
}