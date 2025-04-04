/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/4
 */

package cn.rtast.rob.common.io

import cn.rtast.rob.io.RFile
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import okio.Path.Companion.toPath
import okio.Path as OkioPath

/**
 * 将[RFile]转换为[okio.Path]
 */
@ExperimentalROneBotApi
public fun RFile.toOkioPath(): OkioPath {
    return this.toString().toPath()
}