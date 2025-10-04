/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 5:10 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.stream

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class StreamDataType {
    @SerialName("data_chunk")
    DataChunk,
    @SerialName("data_complete")
    DataComplete
}