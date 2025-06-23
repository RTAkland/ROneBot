/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 6/23/25, 4:39 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.event.raw.file

import kotlinx.serialization.Serializable

@Serializable
public data class DownloadFileResponse(
    val data: DownloadFileResponseInfo
) {
    @Serializable
    public data class DownloadFileResponseInfo(
        val file: String
    )
}