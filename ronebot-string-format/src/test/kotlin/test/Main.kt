/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package test

import cn.rtast.rob.sformat.Table
import cn.rtast.rob.sformat.dsl.row
import cn.rtast.rob.sformat.dsl.table

fun main() {
    val table1 = Table.Builder()
    table1.addRow("1", "2", "3")
    table1.addRow("2", "4", "5")
    println(table1.build())
    println(table {
        row("1", "2", "3")
    })
}