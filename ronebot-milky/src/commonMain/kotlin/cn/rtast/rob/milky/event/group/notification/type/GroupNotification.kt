/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:53 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.group.notification.type

import cn.rtast.rob.milky.enums.NotificationType

public sealed interface GroupNotification {
    /**
     * 消息通知类型
     */
    public val type: NotificationType

    /**
     * 群号
     */
    public val groupId: Long
}