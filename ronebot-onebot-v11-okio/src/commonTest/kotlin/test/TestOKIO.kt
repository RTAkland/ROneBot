/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/2
 */

package test

import kotlinx.io.Buffer
import kotlinx.io.files.SystemFileSystem
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.buffer
import okio.use
import kotlin.test.Test
import kotlinx.io.files.Path as KotlinXIOPath

class TestOKIO {

    @Test
    fun `Test OKIO`() {
        "README.md".toPath()
        val sinkPath = "build/test.txt".toPath()
        val readme = FileSystem.SYSTEM.source("README.md".toPath()).use { it.buffer().readByteArray() }
        val buffer = Buffer().apply {
            write(readme)
        }
        SystemFileSystem.sink(KotlinXIOPath(sinkPath.toString())).use { it.write(buffer, buffer.size) }
    }
}