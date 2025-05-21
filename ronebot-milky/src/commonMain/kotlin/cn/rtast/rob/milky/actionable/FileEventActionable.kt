/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:58 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.actionable

import kotlinx.io.files.Path

public interface FileEventActionable {
    public suspend fun save(path: Path)
    public suspend fun readBytes(): ByteArray
}