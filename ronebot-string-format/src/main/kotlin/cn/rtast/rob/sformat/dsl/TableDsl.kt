/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.sformat.dsl

import cn.rtast.rob.sformat.Table

public inline fun table(builder: Table.Builder.() -> Unit): Table =
    Table.Builder().apply(builder).build()

public fun Table.Builder.row(vararg content: String): Table.Builder =
    this.addRow(*content)