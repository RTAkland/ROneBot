/*
 * Copyright © 2025 RTAkland
 * Date: 11/6/25, 9:41 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.hooking

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.annotations.InternalROneBotApi
import kotlin.jvm.JvmInline

/**
 * 标记为可被hook
 */
public abstract class Hookable<T> {
    protected val hookableBlock: MutableMap<HookPoint<*>, suspend T.(Any) -> Result> = mutableMapOf()

    /**
     * 触发hook代码
     * 内部使用
     */
    @InternalROneBotApi
    public suspend fun <P : Any> T.triggerHooking(hookPoint: HookPoint<P>, param: P): Result =
        hookableBlock[hookPoint]?.invoke(this, param) ?: Result.Continued

    /**
     * 将hook代码附加到可被hook的类中
     */
    @ExperimentalROneBotApi
    @Suppress("UNCHECKED_CAST")
    public fun <P : Any> attach(hookPoint: HookPoint<P>, block: suspend T.(P) -> Result) {
        hookableBlock[hookPoint] = block as suspend T.(Any) -> Result
    }

    /**
     * 创建hookPoint标识符
     * 内部使用
     */
    @InternalROneBotApi
    public inline fun <reified P : Any> hookPoint(name: String): HookPoint<P> =
        HookPoint(name)

    /**
     * 控制流输出结果
     */
    public enum class Result {
        /**
         * 继续流程
         */
        Continued,

        /**
         * 终止流程
         */
        Terminated;

        public val isTerminated: Boolean get() = this == Terminated
        public val isContinued: Boolean get() = this == Continued
    }

    /**
     * Hook点标识符
     */
    @JvmInline
    public value class HookPoint<P : Any>(public val function: String)

}