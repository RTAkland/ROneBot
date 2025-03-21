/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob.util

public expect fun Any.toJson(): String

public expect inline fun <reified T> String.fromJson(): T

public expect inline fun <reified T> String.fromArrayJson(): T
