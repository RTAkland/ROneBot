/*
 * Copyright © 2025 RTAkland
 * Date: 10/7/25, 5:30 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.session

import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateSender

public data class SimulationSender(
    override val userId: Long
) : IGroupSender, IPrivateSender

//internal val Long.SENDER get() = SimulationSender(this)