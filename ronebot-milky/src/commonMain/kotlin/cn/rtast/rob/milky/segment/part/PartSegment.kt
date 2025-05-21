/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 10:22 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.segment.part

import kotlinx.serialization.Serializable

@Serializable
public sealed interface PartSegment {
    public operator fun plus(other: PartSegment): List<PartSegment> =
        listOf(this, other)

    public operator fun plus(other: List<PartSegment>): List<PartSegment> =
        listOf(this, *other.toTypedArray())
}