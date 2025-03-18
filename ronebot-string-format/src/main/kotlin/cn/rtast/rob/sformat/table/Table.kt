/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.sformat.table

/**
 * 快速的创建一个纯文本表格
 */
public class Table private constructor(
    private val rows: MutableList<List<String>>,
    private val columnWidths: List<Int>
) {
    public data class Builder(
        private var maxColumns: Int = 0,
        private val rows: MutableList<List<String>> = mutableListOf()
    ) {
        private val columnWidths: MutableList<Int> = mutableListOf()

        public fun addRow(vararg values: String): Builder {
            maxColumns = maxOf(maxColumns, values.size)
            rows.add(values.toList())
            for (i in values.indices) {
                if (i >= columnWidths.size) {
                    columnWidths.add(values[i].length)
                } else {
                    columnWidths[i] = maxOf(columnWidths[i], values[i].length)
                }
            }
            return this
        }

        public fun build(): Table {
            while (columnWidths.size < maxColumns) {
                columnWidths.add(10)
            }
            return Table(rows, columnWidths)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in rows) {
            for (i in row.indices) {
                sb.append(row[i].padEnd(columnWidths[i]))
                if (i < row.size - 1) sb.append(" | ")
            }
            sb.append("\n")
            if (row != rows.last()) sb.append("-".repeat(sb.length % 2 + columnWidths.sumOf { it } + maxOf(
                0,
                row.size - 1
            ) * 3)).append("\n")
        }
        return sb.toString()
    }
}