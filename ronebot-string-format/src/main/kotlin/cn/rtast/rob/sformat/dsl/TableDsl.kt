/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.sformat.dsl

import cn.rtast.rob.sformat.Table

inline fun table(builder: Table.Builder.() -> Unit) =
    Table.Builder().apply(builder).build()

fun Table.Builder.row(vararg content: String) =
    this.addRow(*content)