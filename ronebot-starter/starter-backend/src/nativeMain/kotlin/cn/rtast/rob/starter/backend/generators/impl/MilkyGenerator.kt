/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 6:27 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.backend.generators.impl

import cn.rtast.rob.starter.backend.generators.BaseGeneratorImpl
import cn.rtast.rob.starter.common.GeneratorProperty

class MilkyGenerator(
    override val property: GeneratorProperty,
) : BaseGeneratorImpl() {
    override val protocolName: String = "milky"
}