/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

/**
 * 对任意请求类的事件执行快捷操作
 */
public interface RequestEventActionable {
    /**
     * 同意
     */
    public suspend fun accept()

    /**
     * 同意
     */
    public suspend fun accept(isFiltered: Boolean)

    /**
     * 拒绝
     */
    public suspend fun reject()

    /**
     * 拒绝
     */
    public suspend fun reject(isFiltered: Boolean)

    /**
     * 拒绝并附带消息
     */
    public suspend fun reject(reason: String)

    /**
     * 拒绝并附带消息
     */
    public suspend fun reject(isFiltered: Boolean, reason: String)
}