/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.ext.file

import kotlinx.io.files.Path
import java.io.File

public actual fun Path.mkdirs() {
    File(this.toString()).mkdirs()
}

public actual fun Path.createFile() {
    File(this.toString()).createNewFile()
}