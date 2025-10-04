/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:15 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.rob.onebot.stream.util.chunkedBySize
import org.junit.Test
import java.io.File
import kotlin.random.Random
import kotlin.test.assertEquals

class TestChunkJvm {

    private val testContent = ByteArray(409600 + Random.nextInt(10)) { Random.nextInt(10).toByte() }
    private val filePath = File("src/test-data/test-file.txt")

    @Test
    fun `test chunk on jvm`() {
        val result = filePath.chunkedBySize()
        val expectResult = "a009ffbaef3ba40810ccfcb781001ceca858b41c8580a94e9b03000c1d6c7848"
        assertEquals(expectResult, result.sha256)
    }
}