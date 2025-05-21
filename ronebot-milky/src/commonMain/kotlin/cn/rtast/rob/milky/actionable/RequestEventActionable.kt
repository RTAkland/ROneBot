/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

public interface RequestEventActionable {
    public suspend fun accept()
    public suspend fun reject()
    public suspend fun reject(reason: String)
}