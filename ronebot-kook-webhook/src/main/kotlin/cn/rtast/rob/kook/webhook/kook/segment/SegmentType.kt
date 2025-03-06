/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.webhook.kook.segment

public enum class SegmentType(public val type: Int) {
    Text(1), Image(2), Video(3), File(4), Audio(8),
    KMarkdown(9), Card(10), System(255)
}