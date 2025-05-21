/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 10:21 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment.part

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.entity.toResource
import cn.rtast.rob.milky.enums.ImageSubType

public data class ResourceImage(
    var resource: Resource = "".toResource(),
    var subType: ImageSubType = ImageSubType.Normal,
    var summary: String? = null
) : PartSegment

public data class UriImage(
    var uri: String = "",
    val subType: ImageSubType = ImageSubType.Normal,
    val summary: String? = null
) : PartSegment