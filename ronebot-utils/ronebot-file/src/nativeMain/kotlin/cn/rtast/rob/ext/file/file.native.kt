/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:OptIn(ExperimentalForeignApi::class)

package cn.rtast.rob.ext.file

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import platform.posix.*

public actual fun Path.createFile() {
    fopen(this.name, "w").apply {
        fclose(this)
    }
}