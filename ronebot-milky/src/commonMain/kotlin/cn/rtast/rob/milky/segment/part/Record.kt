/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 10:21 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment.part

import cn.rtast.rob.entity.Resource
import cn.rtast.rob.entity.toResource

public data class ResourceRecord(
    var resource: Resource = "".toResource()
) : PartSegment

public data class UriRecord(
    var uri: String = ""
) : PartSegment