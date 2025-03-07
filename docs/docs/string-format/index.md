目前这个模块没有啥用只能输出一种格式: Table表

```kotlin
fun main() {
    val table1 = Table.Builder()
    table1.addRow("1", "2", "3")
    table1.addRow("2", "4", "5")
    println(table1.build())
    println(table {
        row("1", "2", "3")
    })
}
```