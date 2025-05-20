/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/20/25, 10:56 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.util.arrow

/**
 * 为Kotlin使用者设计的拓展函数
 * 对左右侧的值的获取更加的精准
 */

import arrow.core.Either

/**
 * 获取右侧成功结果, 可空
 */
public fun <L, R> Either<L, R>.successOrNull(): R? = this.getOrNull()

/**
 * 获取右侧成功结果
 */
public fun <L, R> Either<L, R>.success(): R = requireNotNull(this.getOrNull()) { "右侧值为空, 左侧值为: $this" }

/**
 * 获取左侧失败结果， 可空
 */
public fun <L, R> Either<L, R>.failureOrNull(): L? = this.swap().getOrNull()

/**
 * 获取左侧失败结果
 */
public fun <L, R> Either<L, R>.failure(): L = requireNotNull(this.swap().getOrNull()) { "左侧值为空, 右侧值为: $this" }
