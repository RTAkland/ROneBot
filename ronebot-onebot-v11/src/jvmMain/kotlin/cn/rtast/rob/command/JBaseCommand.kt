/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/16 19:34
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.command

/**
 * 作用和原版的BaseCommand一样, 只有在jvmMain源码集内才能被java使用
 */
public abstract class JBaseCommand : BaseCommand() {
    abstract override val commandNames: List<String>
}