/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:07 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.rob.onebot.stream.util.chunkedBySize
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TestChunk {

    private val testContent = ByteArray(409600 + Random.nextInt(10)) { Random.nextInt(10).toByte() }
    private val filePath = Path("src/test-data/test-file.txt")

    @Test
    fun `test fake data chunk`() {
        val buffer = Buffer().apply { write(testContent) }
        val chunked = buffer.chunkedBySize()
        println(chunked)

    }

    @Test
    fun `test file chunk`() {
        val actual = filePath.chunkedBySize()
        val expectResult = "a009ffbaef3ba40810ccfcb781001ceca858b41c8580a94e9b03000c1d6c7848"
        assertEquals(expectResult, actual.sha256)
    }
}