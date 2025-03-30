/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.ext.file

import kotlinx.io.files.Path
import platform.posix.*

public actual fun Path.mkdirs() {
    mkdir(this.toString(), 755u)
}