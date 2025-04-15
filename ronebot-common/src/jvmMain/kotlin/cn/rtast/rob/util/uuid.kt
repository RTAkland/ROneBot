/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/15 15:48
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(ExperimentalUuidApi::class)
@file:JvmName("UuidUtil")

package cn.rtast.rob.util

import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * 将Kotlin的Uuid对象转换为Java的UUID对象
 */
public fun Uuid.toUUID(): UUID = UUID.fromString(this.toString())

/**
 * 将Kotlin的Uuid对象转换为Java的UUID对象
 */
public fun uuidToUUID(uuid: Uuid): UUID = uuid.toUUID()

