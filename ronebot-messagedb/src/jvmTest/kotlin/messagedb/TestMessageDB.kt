/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:OptIn(ExperimentalUuidApi::class)

package messagedb

import cn.rtast.rob.messagedb.createMessageDB
import kotlinx.io.files.Path
import kotlin.random.Random
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TestMessageDB {

    @Test
    fun testDB() {
        val keys = (0..100).map { Uuid.random() }
        val values = ('a'..'z').map { Random.nextLong(0, 9999999) }
        val db = createMessageDB(Path("data.bin"))
        for (k in keys) {
            for (v in values) {
                db.saveMessage(k, v)
            }
        }
    }
}