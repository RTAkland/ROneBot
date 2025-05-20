/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/20/25, 10:48 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("ArrowUtil")

/**
 * 为Java使用者提供的静态函数用于获取[Either]对象的
 * 返回值, 共计4个函数分别用于获取右侧，右侧(可空), 左侧
 * 左侧(可空)的值, 除此之外, Java使用者还可以使用
 * [Either]对象内置的方法:
 * 获取右侧(成功)值: `Either#getOrNull`
 * 获取左侧(失败)值: `Either#swip#getOrNull`
 */

package cn.rtast.rob.milky.util.arrow

import arrow.core.Either

/**
 * 获取左侧的值, 可空
 */
public fun <L, R> getLeftOrNull(either: Either<L, R>): L? = either.swap().getOrNull()

/**
 * 获取左侧的值
 */
public fun <L, R> getLeft(either: Either<L, R>): L =
    requireNotNull(either.swap().getOrNull()) { "左边值为空, 右侧值为: ${either.success()}" }

/**
 * 获取右侧的值, 可空
 */
public fun <L, R> getRightOrNull(either: Either<L, R>): R? = either.getOrNull()

/**
 * 获取右侧的值
 */
public fun <L, R> getRight(either: Either<L, R>): R =
    requireNotNull(either.getOrNull()) { "右边值为空, 左侧值为: ${either.failure()}" }